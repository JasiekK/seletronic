package com.jkmiec.selectronic.controller.util;

public interface TestUtil {

    default Long getIdFromUri(String url) {
        return Long.parseLong(url.substring(url.lastIndexOf('/') + 1));
    }
}
