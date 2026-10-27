
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class EncryptionModes {
    public static void main(String[] args) throws Exception {
        byte[] key = "1234567890123456"
                .getBytes(StandardCharsets.UTF_8);
        byte[] iv = "abcdefghijklmnop"
                .getBytes(StandardCharsets.UTF_8);

        String plaintext = "HELLO WORLD 1234";
        byte[] data = plaintext.getBytes(StandardCharsets.UTF_8);

        SecretKeySpec secretKey =
                new SecretKeySpec(key, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        String[] modes = {
            "AES/ECB/PKCS5Padding",
            "AES/CBC/PKCS5Padding",
            "AES/CFB128/NoPadding"
        };

        for (String mode : modes) {
            Cipher cipher = Cipher.getInstance(mode);

            if (mode.startsWith("AES/ECB")) {
                cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            } else {
                cipher.init(Cipher.ENCRYPT_MODE,
                        secretKey, ivSpec);
            }

            byte[] encrypted = cipher.doFinal(data);

            if (mode.startsWith("AES/ECB")) {
                cipher.init(Cipher.DECRYPT_MODE, secretKey);
            } else {
                cipher.init(Cipher.DECRYPT_MODE,
                        secretKey, ivSpec);
            }

            byte[] decrypted = cipher.doFinal(encrypted);

            System.out.println("Mode: " + mode);
            System.out.println("Ciphertext: " +
                    Arrays.toString(encrypted));
            System.out.println("Decrypted: " +
                    new String(decrypted,
                            StandardCharsets.UTF_8));
            System.out.println();
        }
    }
}
