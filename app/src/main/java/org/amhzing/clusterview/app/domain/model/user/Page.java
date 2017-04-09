package org.amhzing.clusterview.app.domain.model.user;

import java.util.List;
import java.util.Objects;

import static org.apache.commons.lang3.Validate.notNull;

public class Page<T> {

    private final List<T> content;
    private final int totalPages;

    private Page(final List<T> content, final int totalPages) {
        this.content = notNull(content);
        this.totalPages = totalPages;
    }

    public static <T> Page<T> create(final List<T> content, final int totalPages) {
        return new Page<T>(content, totalPages);
    }

    public List<T> getContent() {
        return content;
    }

    public int getTotalPages() {
        return totalPages;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof Page)) return false;
        final Page<?> page = (Page<?>) o;
        return totalPages == page.totalPages &&
                Objects.equals(content, page.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content, totalPages);
    }

    @Override
    public String toString() {
        return "Page{" +
                "content=" + content +
                ", totalPages=" + totalPages +
                '}';
    }
}
