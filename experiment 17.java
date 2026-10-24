
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Scanner;

public class DESDecryption {

    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter 16-character key: ");
        String key = sc.nextLine();

        System.out.print("Enter ciphertext (hex): ");
        String hex = sc.nextLine();

        byte[] keyBytes = key.getBytes("UTF-8");

        if (keyBytes.length != 8) {
            System.out.println("DES key must be exactly 8 bytes.");
            sc.close();
            return;
        }

        if (hex.length() == 0 || hex.length() % 16 != 0) {
            System.out.println(
                "Ciphertext must contain complete 8-byte blocks in hex.");
            sc.close();
            return;
        }

        byte[] cipherBytes = new byte[hex.length() / 2];

        for (int i = 0; i < hex.length(); i += 2) {
            cipherBytes[i / 2] =
                (byte) Integer.parseInt(hex.substring(i, i + 2), 16);
        }

        SecretKeySpec secretKey =
            new SecretKeySpec(keyBytes, "DES");

        Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");

        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        byte[] plainBytes = cipher.doFinal(cipherBytes);

        System.out.println("Decrypted plaintext (hex):");
        for (byte b : plainBytes) {
            System.out.printf("%02X", b & 0xFF);
        }

        System.out.println("\nDecrypted plaintext (text):");
        System.out.println(new String(plainBytes, "UTF-8"));

        sc.close();
    }
}
