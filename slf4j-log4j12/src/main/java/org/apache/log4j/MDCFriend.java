package org.apache.log4j;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.apache.log4j.MDC;
import org.apache.log4j.helpers.ThreadLocalMap;

public class MDCFriend {

    public static void fixForJava9() {
        try {
            Field mdcField = MDC.class.getDeclaredField("mdc");

            MDC mdcSingleton = (MDC) mdcField.get(null);
            Field tlmField = MDC.class.getDeclaredField("tlm");

            Field java1Field = MDC.class.getDeclaredField("java1");
            Object mdcSingleton_tlm = tlmField.get(mdcSingleton);

            if (mdcSingleton_tlm == null) {
                tlmField.set(mdcSingleton, new ThreadLocalMap());
                java1Field.setBoolean(mdcSingleton, false);
                setRemoveMethod(mdcSingleton);
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    private static void setRemoveMethod(org.apache.log4j.MDC mdc) {
        try {
            Method removeMethod = ThreadLocal.class.getMethod("remove");
            Field removeMethodField = MDC.class.getDeclaredField("removeMethod");
            removeMethodField.setAccessible(true);
            removeMethodField.set(mdc, removeMethod);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }
}
