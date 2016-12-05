package org.amhzing.clusterview;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.stream.Stream;

public final class QuickPasswordGenerator {

    public static void main(String[] args) {
        final String[] passwords = {"guest123" , "admin123", "superadmin123", "inactive123"};
        final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(11);

        Stream.of(passwords)
              .forEach(password -> System.out.println("Encoded password for " + password + " is >" + passwordEncoder.encode(password) + "<"));
    }
}
