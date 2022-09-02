/*
 * Permission is hereby granted, free  of charge, to any person obtaining
 * a  copy  of this  software  and  associated  documentation files  (the
 * "Software"), to  deal in  the Software without  restriction, including
 * without limitation  the rights to  use, copy, modify,  merge, publish,
 * distribute,  sublicense, and/or sell  copies of  the Software,  and to
 * permit persons to whom the Software  is furnished to do so, subject to
 * the following conditions:
 *
 * The  above  copyright  notice  and  this permission  notice  shall  be
 * included in all copies or substantial portions of the Software.
 *
 * THE  SOFTWARE IS  PROVIDED  "AS  IS", WITHOUT  WARRANTY  OF ANY  KIND,
 * EXPRESS OR  IMPLIED, INCLUDING  BUT NOT LIMITED  TO THE  WARRANTIES OF
 * MERCHANTABILITY,    FITNESS    FOR    A   PARTICULAR    PURPOSE    AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE,  ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package org.slf4j;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Every.everyItem;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.helpers.NOP_FallbackServiceProvider;
import org.slf4j.spi.SLF4JServiceProvider;

public class LoggerFactoryTest {

    private static String SLF4J_PROVIDER = SLF4JServiceProvider.class.getName();
    private static String NOP_PROVIDER = NOP_FallbackServiceProvider.class.getName();

    private WebappClassloader child;

    @Before
    public void setUp() {
        final URL apiUrl = getLocation(LoggerFactory.class);
        final URL testUrl = getLocation(LoggerFactoryTest.class);
        child = new WebappClassloader(getClass().getClassLoader(), new URL[] { apiUrl, testUrl });
    }

    /**
     * Tests if SLF4J in a web application is not disturbed by the SLF4J providers of the servlet container.
     */
    @Test
    public void testNotASubtype() throws Exception {
        final ClassLoader tccl = Thread.currentThread().getContextClassLoader();
        // both the server and the webapp have `slf4j-api`, but with different providers:
        // NOP_FallbackServiceProvider only in the server classloader
        child.delegateToParent(NOP_PROVIDER);
        // SubstituteServiceProvider in the webapp
        child.addResource("META-INF/services/org.slf4j.spi.SLF4JServiceProvider", LoggerFactoryTest.class.getResource("substituteServiceProvider.txt"));
        try {
            Thread.currentThread().setContextClassLoader(child);
            final List<?> providers = findServiceProviders(child);
            final Class<?> slf4jProvider = child.loadClass(SLF4J_PROVIDER);
            assertThat(providers, everyItem(instanceOf(slf4jProvider)));
        } finally {
            Thread.currentThread().setContextClassLoader(tccl);
        }
    }

    /**
     * Tests if SLF4J in a servlet container's common classloader does not use the providers in the webapplications.
     */
    @Test
    public void testMemoryLeak() throws Exception {
        final ClassLoader tccl = Thread.currentThread().getContextClassLoader();
        // both the server and the webapp have `slf4j-api`, but with different providers:
        // NOP_FallbackServiceProvider only in the server classloader
        child.delegateToParent(NOP_PROVIDER);
        // SubstituteServiceProvider in the webapp
        child.addResource("META-INF/services/org.slf4j.spi.SLF4JServiceProvider", LoggerFactoryTest.class.getResource("substituteServiceProvider.txt"));
        try {
            Thread.currentThread().setContextClassLoader(child);
            final List<?> providers = LoggerFactory.findServiceProviders();
            assertThat(providers, everyItem(isAccessibleBy(LoggerFactory.class.getClassLoader())));
        } finally {
            Thread.currentThread().setContextClassLoader(tccl);
        }
    }

    static List<SLF4JServiceProvider> callFromUnprivilegedDomain() {
        return LoggerFactory.findServiceProviders();
    }

    private static List<?> findServiceProviders(final ClassLoader cl) throws Exception {
        try {
            final Class<?> loggerFactory = Class.forName("org.slf4j.LoggerFactory", true, cl);
            final Method findServiceProviders = loggerFactory.getDeclaredMethod("findServiceProviders");
            findServiceProviders.setAccessible(true);
            return (List<?>) findServiceProviders.invoke(null);
        } catch (InvocationTargetException e) {
            Throwable t = e.getCause();
            if (t instanceof Error) {
                throw (Error) t;
            }
            throw (Exception) t;
        }
    }

    private static URL getLocation(Class<?> clazz) {
        return clazz.getProtectionDomain().getCodeSource().getLocation();
    }

    private static <T> Matcher<T> isAccessibleBy(final ClassLoader cl) {
        return new BaseMatcher<T>() {

            @Override
            public boolean matches(Object item) {
                try {
                    Class<?> clazz = item.getClass();
                    return Class.forName(clazz.getName(), true, cl).equals(clazz);
                } catch (ClassNotFoundException e) {
                    return false;
                }
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("in accessible by").appendValue(cl);
            }
        };
    }

    /**
     * Classloader with the same classpath as the parent, but does not delegate to the parent if the class name starts with {@code childFirstPrefix}.
     *
     */
    private static class WebappClassloader extends URLClassLoader {

        private final List<String> delegateToParent = new ArrayList<>();
        private final Map<String, URL> resourceOverride = new HashMap<>();

        public WebappClassloader(ClassLoader parent, URL[] urls) {
            super(urls, parent);
        }

        public void addResource(String name, URL resource) {
            resourceOverride.put(name, resource);
        }

        public void delegateToParent(String prefix) {
            delegateToParent.add(prefix);
        }

        @Override
        public Enumeration<URL> findResources(String name) throws IOException {
            final URL resource = resourceOverride.get(name);
            if (resource != null) {
                return Collections.enumeration(Collections.singleton(resource));
            }
            return super.findResources(name);
        }

        @Override
        protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
            synchronized (getClassLoadingLock(name)) {
                // 1. Class already loaded
                Class<?> c = findLoadedClass(name);
                // 2. classes that we delegate to the parent classloader
                if (c == null) {
                    if (name.startsWith("java")) {
                        c = getParent().loadClass(name);
                    } else {
                        for (final String prefix : delegateToParent) {
                            if (name.startsWith(prefix)) {
                                c = getParent().loadClass(name);
                                break;
                            }
                        }
                    }
                }
                // 3. find in the classloader
                if (c == null) {
                    c = findClass(name);
                }
                // 4. delegate to parent
                if (c == null) {
                    c = getParent().loadClass(name);
                }
                if (resolve) {
                    resolveClass(c);
                }
                return c;
            }
        }

    }
}
