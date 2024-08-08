package com.AxeFu.pastebox.util;

public class StringUtils {

    public static String random(int size) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            builder.append((char)('A' + Math.random() * 'z'));
        }
        return builder.toString();
    }

}
