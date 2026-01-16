package uk.co.deveroonie.hyvotejava.util;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class Keys {
    public static SecretKey generateKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256);
        return keyGenerator.generateKey();
    }

    public static byte[] stripPublicKeyHeaders(String key) {
        String pem = key
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", ""); // removes all whitespace including newlines
        byte[] encoded = Base64.getDecoder().decode(pem);

        return encoded;
    }

    public static PublicKey pemToKey(byte[] key) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(key);
        return keyFactory.generatePublic(keySpec);
    }
}
