import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.util.Arrays;
import java.util.Base64;

public class EncryptionModes {

    static final int BLOCK_SIZE = 16;

    // Padding: 1 bit followed by zero bits
    static byte[] pad(byte[] data) {
        int padding = BLOCK_SIZE - (data.length % BLOCK_SIZE);

        // Always add padding, even if block is complete
        byte[] result = Arrays.copyOf(
            data, data.length + padding);

        result[data.length] = (byte) 0x80;

        return result;
    }

    // Remove padding
    static byte[] unpad(byte[] data) {
        int i = data.length - 1;

        while (i >= 0 && data[i] == 0) {
            i--;
        }

        if (i < 0 || (data[i] & 0xFF) != 0x80) {
            throw new IllegalArgumentException("Invalid padding");
        }

        return Arrays.copyOf(data, i);
    }

    // Encryption and decryption
    static void process(String mode, SecretKey key,
                        byte[] iv, byte[] plaintext)
                        throws Exception {

        String algorithm = "AES/" + mode + "/NoPadding";

        Cipher cipher = Cipher.getInstance(algorithm);

        byte[] padded = pad(plaintext);

        if (mode.equals("ECB")) {
            cipher.init(Cipher.ENCRYPT_MODE, key);
        } else {
            cipher.init(Cipher.ENCRYPT_MODE, key,
                        new IvParameterSpec(iv));
        }

        byte[] encrypted = cipher.doFinal(padded);

        System.out.println("\n" + mode + " Mode");
        System.out.println("Encrypted: " +
            Base64.getEncoder().encodeToString(encrypted));

        // Decryption
        if (mode.equals("ECB")) {
            cipher.init(Cipher.DECRYPT_MODE, key);
        } else {
            cipher.init(Cipher.DECRYPT_MODE, key,
                        new IvParameterSpec(iv));
        }

        byte[] decrypted = cipher.doFinal(encrypted);
        byte[] original = unpad(decrypted);

        System.out.println("Decrypted: " +
            new String(original,
                java.nio.charset.StandardCharsets.UTF_8));
    }

    public static void main(String[] args) throws Exception {

        String message = "HELLO WORLD";

        // Generate AES key
        KeyGenerator keyGen =
            KeyGenerator.getInstance("AES");

        keyGen.init(128);
        SecretKey key = keyGen.generateKey();

        // Generate IV
        byte[] iv = new byte[BLOCK_SIZE];
        new java.security.SecureRandom().nextBytes(iv);

        byte[] plaintext = message.getBytes(
            java.nio.charset.StandardCharsets.UTF_8);

        System.out.println("Original Message: " + message);

        // ECB Mode
        process("ECB", key, iv, plaintext);

        // CBC Mode
        process("CBC", key, iv, plaintext);

        // CFB Mode (128-bit feedback segment)
        process("CFB", key, iv, plaintext);
    }
}
