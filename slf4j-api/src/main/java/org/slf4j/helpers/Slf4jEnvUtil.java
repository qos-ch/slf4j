/*
 *  Copyright (c) 2004-2024 QOS.ch
 *  All rights reserved.
 *
 *  Permission is hereby granted, free  of charge, to any person obtaining
 *  a  copy  of this  software  and  associated  documentation files  (the
 *  "Software"), to  deal in  the Software without  restriction, including
 *  without limitation  the rights to  use, copy, modify,  merge, publish,
 *  distribute,  sublicense, and/or sell  copies of  the Software,  and to
 *  permit persons to whom the Software  is furnished to do so, subject to
 *  the following conditions:
 *
 *  The  above  copyright  notice  and  this permission  notice  shall  be
 *  included in all copies or substantial portions of the Software.
 *
 *  THE  SOFTWARE IS  PROVIDED  "AS  IS", WITHOUT  WARRANTY  OF ANY  KIND,
 *  EXPRESS OR  IMPLIED, INCLUDING  BUT NOT LIMITED  TO THE  WARRANTIES OF
 *  MERCHANTABILITY,    FITNESS    FOR    A   PARTICULAR    PURPOSE    AND
 *  NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 *  LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 *  OF CONTRACT, TORT OR OTHERWISE,  ARISING FROM, OUT OF OR IN CONNECTION
 *  WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.slf4j.helpers;

/**
 * Various utility methods
 *
 * @since 2.0.14
 */
public class Slf4jEnvUtil {


    /**
     * Returns the current version of slf4j, or null if data is not available.
     *
     * @return current version or null if missing version data
     * @since 2.0.14
     */
    static public String slf4jVersion() {
//        String moduleVersion = slf4jVersionByModule();
//        if(moduleVersion != null)
//            return moduleVersion;

        Package pkg = Slf4jEnvUtil.class.getPackage();
        if(pkg == null) {
            return null;
        }
        final String pkgVersion = pkg.getImplementationVersion();
        return pkgVersion;
    }

    /**
     * Returns the current version of slf4j via class.getModule()
     * or null if data is not available.
     *
     * @return current version or null if missing version data
     * @since 2.0.14
     */
//    static private String slf4jVersionByModule() {
//        Module module = Slf4jEnvUtil.class.getModule();
//        if (module == null)
//            return null;
//
//        ModuleDescriptor md = module.getDescriptor();
//        if (md == null)
//            return null;
//        Optional<String> opt = md.rawVersion();
//        return opt.orElse(null);
//    }

}
