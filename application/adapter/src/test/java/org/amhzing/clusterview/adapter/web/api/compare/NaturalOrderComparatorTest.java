package org.amhzing.clusterview.adapter.web.api.compare;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class NaturalOrderComparatorTest {

    private NaturalOrderComparator naturalOrderComparator;

    @Test
    public void test() {
        String[] strings = new String[]{"1-2", "1-02", "1-20", "10-20", "fred", "jane", "pic01",
                "pic2", "pic02", "pic02a", "pic3", "pic4", "pic 4 else", "pic 5", "pic05", "pic 5",
                "pic 5 something", "pic 6", "pic   7", "pic100", "pic100a", "pic120", "pic121",
                "pic02000", "tom", "x2-g8", "x2-y7", "x2-y08", "x8-y8"};

        List<String> orig = Arrays.asList(strings);

        List<String> scrambled = Arrays.asList(strings);
        Collections.shuffle(scrambled);

        assertThat(scrambled).containsSequence(strings);

        Collections.sort(scrambled, new NaturalOrderComparator());

        assertThat(scrambled).isSortedAccordingTo(new NaturalOrderComparator());
    }
}