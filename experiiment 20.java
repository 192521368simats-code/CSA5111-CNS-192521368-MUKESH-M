import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.util.Arrays;

public class ECBvsCBC {

    public static void main(String[] args) throws Exception {

        byte[] keyBytes = new byte[16];
        byte[] iv = new byte[16];

        Arrays.fill(keyBytes, (byte) 1);
        Arrays.fill(iv, (byte) 2);

        SecretKeySpec key =
            new SecretKeySpec(keyBytes, "AES");

        String message =
            "BlockOne12345678BlockTwo12345678BlockThree123456";

        byte[] plaintext = message.getBytes("UTF-8");

        // ECB Encryption
        Cipher ecb = Cipher.getInstance("AES/ECB/NoPadding");
        ecb.init(Cipher.ENCRYPT_MODE, key);
        byte[] ecbCipher = ecb.doFinal(plaintext);

        // Introduce one-bit error in ciphertext block 1
        byte[] damagedECB = ecbCipher.clone();
        damagedECB[0] ^= 1;

        byte[] ecbResult = ecb.doFinal(plaintext);
        ecb.init(Cipher.DECRYPT_MODE, key);
        byte[] ecbPlain = ecb.doFinal(damagedECB);

        // CBC Encryption
        Cipher cbc = Cipher.getInstance("AES/CBC/NoPadding");
        cbc.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));
        byte[] cbcCipher = cbc.doFinal(plaintext);

        // Introduce one-bit error in ciphertext block 1
        byte[] damagedCBC = cbcCipher.clone();
        damagedCBC[0] ^= 1;

        cbc.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
        byte[] cbcPlain = cbc.doFinal(damagedCBC);

        System.out.println("Original: " + message);
        System.out.println("\nECB mode:");
        System.out.println("Block 1 is corrupted.");
        System.out.println("Other plaintext blocks remain unchanged.");

        System.out.println("\nCBC mode:");
        System.out.println("Block 1 is corrupted.");
        System.out.println("Block 2 has a corresponding bit error.");
        System.out.println("Block 3 and later blocks remain unchanged.");

        System.out.println("\nECB decrypted bytes: "
            + Arrays.toString(ecbPlain));
        System.out.println("CBC decrypted bytes: "
            + Arrays.toString(cbcPlain));
    }
}
