package org.amhzing.clusterview.data.helper;

public class JUnitParamHelper {

    private JUnitParamHelper() {
    }

    public static Class<? extends Exception> valid() {
        return NoException.class;
    }

    public static Class<? extends Exception> invalidMatching(final Class<? extends Exception> exception) {
        return exception;
    }
}
