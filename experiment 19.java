
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class CBC3DES {
    public static void main(String[] args) throws Exception {

        String message = "HELLO WORLD";

        // 24-byte key for 3DES
        byte[] keyBytes = new byte[24];
        for (int i = 0; i < 24; i++) {
            keyBytes[i] = (byte) (i + 1);
        }

        SecretKey key = new SecretKeySpec(keyBytes, "DESede");

        // 8-byte IV for CBC mode
        byte[] iv = new byte[8];
        for (int i = 0; i < 8; i++) {
            iv[i] = (byte) (i + 1);
        }

        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);

        byte[] encrypted = cipher.doFinal(message.getBytes("UTF-8"));

        String encryptedText =
            Base64.getEncoder().encodeToString(encrypted);

        System.out.println("Original Text: " + message);
        System.out.println("Encrypted Text: " + encryptedText);
    }
}
