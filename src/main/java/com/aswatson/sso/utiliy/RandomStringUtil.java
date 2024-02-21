package com.aswatson.sso.utiliy;

import java.util.UUID;
public class RandomStringUtil {
    public static String generateRandomValue() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replace("-", "");
    }

}
