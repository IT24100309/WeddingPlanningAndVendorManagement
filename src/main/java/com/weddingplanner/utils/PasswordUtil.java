package com.weddingplanner.utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {
    public static String hash(String plaintext) {
        return BCrypt.hashpw(plaintext, BCrypt.gensalt(12));
    }

    public static boolean matches(String plaintext, String hashed) {
        return BCrypt.checkpw(plaintext, hashed);
    }
}
