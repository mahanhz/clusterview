package org.amhzing.clusterview.adapter.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class ObfuscatorTest {

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