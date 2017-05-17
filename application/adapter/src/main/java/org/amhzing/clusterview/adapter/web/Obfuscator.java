package org.amhzing.clusterview.adapter.web;

import com.fasterxml.uuid.Generators;
import org.hashids.Hashids;

public final class Obfuscator {

    public static final int MIN_HASH_LENGTH = 8;
    private static final String SALT = Generators.timeBasedGenerator().generate().toString();
    private static final Hashids HASH_IDS = new Hashids(SALT, MIN_HASH_LENGTH);

    private Obfuscator() {
        // Prevent instantiation
    }

    public static String obfuscate(final long value) {
        return HASH_IDS.encode(value);
    }

    public static long deobfuscate(final String obfuscatedValue) {
        final long[] decode = HASH_IDS.decode(obfuscatedValue);

        if (decode.length != 1) {
            throw new NotFoundException("Could not find decoded value for " + obfuscatedValue);
        }

        return decode[0];
    }
}
