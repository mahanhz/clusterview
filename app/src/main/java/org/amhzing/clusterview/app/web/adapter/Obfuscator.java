package org.amhzing.clusterview.app.web.adapter;

import org.amhzing.clusterview.app.user.UserUtil;
import org.amhzing.clusterview.app.exception.NotFoundException;
import org.hashids.Hashids;

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

        if (decode.length != 1) {
            throw new NotFoundException("Could not find decoded value for " + obfuscatedValue);
        }

        return decode[0];
    }

    private static Hashids hashids() {
        return new Hashids(UserUtil.username(), MIN_HASH_LENGTH);
    }
}
