package org.slf4j.simple;

import org.junit.Test;
import org.slf4j.helpers.Slf4jEnvUtil;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class Slf4jVersionTest {


    @Test
    public void slf4jVersionTest() {

        String version = Slf4jEnvUtil.slf4jVersion();
        assertNotNull(version);
        assertTrue(version.startsWith("2"));

    }

}
