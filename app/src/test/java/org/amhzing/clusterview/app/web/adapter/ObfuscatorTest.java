package org.amhzing.clusterview.app.web.adapter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class ObfuscatorTest {

    @Before
    public void setUp() throws Exception {
        User user = new User("me@example.com", "Nopass" , emptyList());
        Authentication auth = new UsernamePasswordAuthenticationToken(user, null);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Test
    public void should_obfuscate() {
        final String obfuscatedValue = Obfuscator.obfuscate(1234L);

        assertThat(obfuscatedValue).isNotEqualTo(1234L);
    }

    @Test
    public void should_deobfuscate() {
        final String obfuscatedValue = Obfuscator.obfuscate(1234L);
        final long deobfuscatedValue = Obfuscator.deobfuscate(obfuscatedValue);

        assertThat(deobfuscatedValue).isEqualTo(1234L);
    }
}