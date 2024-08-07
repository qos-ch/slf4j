package org.slf4j.helpers;

import java.lang.module.ModuleDescriptor;
import java.util.Optional;

public class Slf4jEnvUtil {


    /**
     * <p>Returns the current version of logback, or null if data is not
     * available.
     * </p>
     *
     * @return current version or null if missing version data
     * @since 1.3.0
     */
    static public String slf4jVersion() {
        String moduleVersion = slf4jVersionByModule();
        if(moduleVersion != null)
            return moduleVersion;

        Package pkg = Slf4jEnvUtil.class.getPackage();
        if(pkg == null) {
            return null;
        }
        final String pkgVersion = pkg.getImplementationVersion();
        return pkgVersion;
    }

    /**
     * <p>Returns the current version of logback via class.getModule() or null if data is not
     * available.
     * </p>
     *
     * @since 1.3.0
     * @return current version or null if missing version data
     */
    static private String slf4jVersionByModule() {
        Module module = Slf4jEnvUtil.class.getModule();
        if (module == null)
            return null;

        ModuleDescriptor md = module.getDescriptor();
        if (md == null)
            return null;
        Optional<String> opt = md.rawVersion();
        return opt.orElse(null);
    }

}
