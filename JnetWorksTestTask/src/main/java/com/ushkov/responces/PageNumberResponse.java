package com.ushkov.responces;

import java.util.Objects;

public class PageNumberResponse {
    private String original;
    private String reduced;

    public PageNumberResponse() {
    }

    public PageNumberResponse(String original, String reduced) {
        this.original = original;
        this.reduced = reduced;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getReduced() {
        return reduced;
    }

    public void setReduced(String reduced) {
        this.reduced = reduced;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PageNumberResponse that = (PageNumberResponse) o;
        return original.equals(that.original) && reduced.equals(that.reduced);
    }

    @Override
    public int hashCode() {
        return Objects.hash(original, reduced);
    }

    @Override
    public String toString() {
        return "PageNumberResponse{" +
                "original='" + original + '\'' +
                ", reduced='" + reduced + '\'' +
                '}';
    }
}
