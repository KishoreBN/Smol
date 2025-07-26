package com.primeengineer.smol.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Utility class containing helper methods used across the application.
 */
@Component
public class Utility {
    @Value("${frontend.url}")
    private String frontendUrl;
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

    public String getRandomUUID() {
        return UUID.randomUUID().toString();
    }

    public String getPasswordResetMessage(String token, String registeredEmail) {
        StringBuilder str = new StringBuilder();
        str.append(Constants.RESET_MESSAGE_PREFIX)
                .append(frontendUrl)
                .append("/passwordReset?token=")
                .append(token)
                .append("?email=")
                .append(registeredEmail)
                .append(Constants.RESET_MESSAGE_SUFFIX);
        return str.toString();
    }
}
