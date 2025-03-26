package iplm.utility;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

public class CryptoUtility {
    /* Максимальная длинна генерируемого хеша */
    public static int MAX_LENGTH_HASH = 32;
    /* Алгоритм шифрования */
    private static final String ALGO = "AES";

    public static String toHashV2(String text) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        int iterations = 65536;
        PBEKeySpec spec = new PBEKeySpec(text.toCharArray(), salt, iterations, 256);
        SecretKeyFactory factory = null;
        try { factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256"); }
        catch (NoSuchAlgorithmException e) { e.printStackTrace(); }

        byte[] hash = new byte[0];
        try { hash = factory.generateSecret(spec).getEncoded(); }
        catch (InvalidKeySpecException e) { e.printStackTrace(); }
        String saltBase64 = Base64.getEncoder().encodeToString(salt);
        String hashBase64 = Base64.getEncoder().encodeToString(hash);

        return String.format("{PBKDF2WithHmacSHA256}%s:%s:%d", hashBase64, saltBase64, iterations);
    }
    /* Преобразование строки пароля в хэш для хранения в БД */
    public static String toHash(String text) {
        MessageDigest message_digest = null;
        try { message_digest = MessageDigest.getInstance("SHA-256"); }
        catch (NoSuchAlgorithmException e) { e.printStackTrace(); }
        byte[] hash = message_digest.digest(text.getBytes());
        String encoded_hash = Base64.getEncoder().encodeToString(hash);
        return encoded_hash.substring(0, MAX_LENGTH_HASH);
    }

    /* Шифрование строки */
    public static byte[] encrypt(String message, Key key)
    {
        try {
            Cipher cipher = Cipher.getInstance(ALGO);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            cipher.update(message.getBytes(StandardCharsets.UTF_8));
            byte[] ciphertext = cipher.doFinal();
            return ciphertext;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
//            Log4j2.LOGGER.error(e.getMessage());
        } catch (IllegalBlockSizeException e) {
//            Log4j2.LOGGER.error(e.getMessage());
        } catch (BadPaddingException e) {
//            Log4j2.LOGGER.error(e.getMessage());
        }
        return null;
    }

    /* Расшифрование строки */
    public static String decrypt(byte[] cipher_text, Key key)
    {
        try {
            Cipher cipher = Cipher.getInstance(ALGO);
            cipher.init(Cipher.DECRYPT_MODE, key);
            cipher.update(cipher_text);
            byte[] decrypted = cipher.doFinal();
            return new String(decrypted, "UTF8");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
//            Log4j2.LOGGER.error(e.getMessage());
        } catch (IllegalBlockSizeException e) {
//            Log4j2.LOGGER.error(e.getMessage());
        } catch (BadPaddingException e) {
//            Log4j2.LOGGER.error(e.getMessage());
        } catch (UnsupportedEncodingException e) {
//            Log4j2.LOGGER.error(e.getMessage());
        }
        return null;
    }

//    /* Чтение ключа шифрования */
//    public static Key readKey()
//    {
//        byte[] buffer = new byte[1024];
//        int length = FileUtils.read_file(AccessManager.SECRET_KEY_PATH,buffer);
//        byte[] key = Arrays.copyOf(buffer,length);
//
//        Key result = null;
//        if (length != 0)
//        {
//            result = new SecretKeySpec(key,0,length,ALGO);
//        }
//        return result;
//    }

    /* Генерирование ключа шифрования */
    public static Key generateKey()
    {
        KeyGenerator key_gen = null;
        try {
            key_gen = KeyGenerator.getInstance(ALGO);
            key_gen.init(128);
            Key key = key_gen.generateKey();
            return key;
        } catch (NoSuchAlgorithmException e) {
//            Log4j2.LOGGER.error(e.getMessage());
        }
        return null;
    }
}
