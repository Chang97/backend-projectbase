package com.base.shared.core.util;

public final class StringNormalizer {

    private StringNormalizer() {
    }

    public static String trimToNull(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
