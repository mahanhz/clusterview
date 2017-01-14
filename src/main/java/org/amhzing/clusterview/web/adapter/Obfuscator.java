package org.amhzing.clusterview.web.adapter;

import org.amhzing.clusterview.user.UserUtil;
import org.hashids.Hashids;

import static org.apache.commons.lang3.Validate.isTrue;

public final class Obfuscator {

    public static final int MIN_HASH_LENGTH = 8;

    private Obfuscator() {
        // Prevent instantiation
    }

    public static String obfuscate(final long value) {
        return hashids().encode(value);
    }

    public static long deobfuscate(final String obfuscatedValue) {
        final long[] decode = hashids().decode(obfuscatedValue);

        isTrue(decode.length == 1);

        return decode[0];
    }

    private static Hashids hashids() {
        return new Hashids(UserUtil.username(), MIN_HASH_LENGTH);
    }
}
