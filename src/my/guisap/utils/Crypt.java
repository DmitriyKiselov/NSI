package my.guisap.utils;

import javax.crypto.*;
import java.io.*;
import java.security.InvalidKeyException;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 *
 * @author DanchenkoUA
 */
public class Crypt {

    static byte[] keyBytes = "keykeykey3".getBytes(); // ключь шифрования

    public static String decrypt(String str) throws IOException, IllegalBlockSizeException, BadPaddingException, Exception {
        try {
            javax.crypto.spec.SecretKeySpec key = new javax.crypto.spec.SecretKeySpec(getRawKey(), "DES");
            Cipher decryptCipher = Cipher.getInstance("DES");
            decryptCipher.init(Cipher.DECRYPT_MODE, key);
            byte[] utf8 = toByte(str);
            // Descrypt
            byte[] dec = decryptCipher.doFinal(utf8);
            return new String(dec);

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException exc) {
        }
        return str;
    }

    public static String encrypt(String str) throws IOException, IllegalBlockSizeException, BadPaddingException, Exception {
        try {

            javax.crypto.spec.SecretKeySpec key = new javax.crypto.spec.SecretKeySpec(getRawKey(), "DES");
            Cipher encryptCipher = Cipher.getInstance("DES");
            encryptCipher.init(Cipher.ENCRYPT_MODE, key);

            byte[] utf8 = str.getBytes("UTF8");
            // Encrypt
            byte[] enc = encryptCipher.doFinal(utf8);
            return toHex(enc);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException exc) {
            System.out.print(exc);
        }
        return str;
    }

    private static byte[] getRawKey() throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("DES");
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        sr.setSeed(keyBytes);
        kgen.init(56, sr);
        SecretKey skey = kgen.generateKey();
        byte[] raw = skey.getEncoded();
        return raw;
    }

    // эти методы используются для конвертации байтов в ASCII символы
    public static String toHex(String txt) {
        return toHex(txt.getBytes());
    }

    public static String fromHex(String hex) {
        return new String(toByte(hex));
    }

    public static byte[] toByte(String hexString) {
        int len = hexString.length() / 2;
        byte[] result = new byte[len];
        for (int i = 0; i < len; i++) {
            result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2), 16).byteValue();
        }
        return result;
    }

    public static String toHex(byte[] buf) {
        if (buf == null) {
            return "";
        }
        StringBuffer result = new StringBuffer(2 * buf.length);
        for (int i = 0; i < buf.length; i++) {
            appendHex(result, buf[i]);
        }
        return result.toString();
    }
    private final static String HEX = "0123456789ABCDEF";

    private static void appendHex(StringBuffer sb, byte b) {
        sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
    }
}
