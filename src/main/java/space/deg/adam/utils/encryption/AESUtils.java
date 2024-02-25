package space.deg.adam.utils.encryption;

import java.security.spec.KeySpec;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import lombok.SneakyThrows;

public class AESUtils {
  private static final String AES_ALGORITHM = "AES";
  private static final String SECRET_KEY_ALGORITHM = "PBKDF2WithHmacSHA256";
  private static final String CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";

  @SneakyThrows
  public static SecretKey getKeyFromText(String text) {
    String password = text.repeat(5).toLowerCase();
    String salt = text.repeat(10).toUpperCase();
    SecretKeyFactory factory = SecretKeyFactory.getInstance(SECRET_KEY_ALGORITHM);
    KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 256);
    return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), AES_ALGORITHM);
  }

  public static IvParameterSpec generateIv() {
    return new IvParameterSpec(new byte[16]);
  }

  @SneakyThrows
  public static String encrypt(String input, String secret) {
    SecretKey key = getKeyFromText(secret);
    Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
    IvParameterSpec iv = generateIv();
    cipher.init(Cipher.ENCRYPT_MODE, key, iv);

    byte[] cipherText = cipher.doFinal(input.getBytes());

    return Base64.getEncoder().encodeToString(cipherText);
  }

  @SneakyThrows
  public static String decrypt(String cipherText, String secret) {
    SecretKey key = getKeyFromText(secret);
    Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
    IvParameterSpec iv = generateIv();
    cipher.init(Cipher.DECRYPT_MODE, key, iv);

    byte[] plainText = cipher.doFinal(Base64.getDecoder().decode(cipherText));

    return new String(plainText);
  }
}
