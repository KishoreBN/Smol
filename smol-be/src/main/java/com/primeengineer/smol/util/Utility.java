package com.primeengineer.smol.util;

import org.springframework.stereotype.Component;

/**
 * Utility class containing helper methods used across the application.
 */
@Component
public class Utility {

    /**
     * Converts a given number to a Base62-encoded string.
     * Adds an offset based on the minimum length to ensure uniform length of the result.
     *
     * @param num           The number to be encoded (typically the ID of a URL mapping).
     * @param minimumLength The minimum desired length of the resulting short string.
     * @return A Base62-encoded string representation of the input number.
     */
    public String base62Encode(Long num, int minimumLength) {
        num = num + ((minimumLength + 1) * 62L);
        StringBuilder shortUrl = new StringBuilder();
        while (num > 0) {
            char c = Constants.URL_CHARS.charAt((int) (num%62));
            shortUrl.append(c);
            num = num/62;
        }
        return shortUrl.toString();
    }
}
