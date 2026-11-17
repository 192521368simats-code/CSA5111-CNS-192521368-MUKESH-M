import java.security.SecureRandom;

public class OneTimePad {

    public static void main(String[] args) {

        String plaintext = "HELLO";

        int n = plaintext.length();

        int[] key = new int[n];

        SecureRandom random = new SecureRandom();

        // Generate random key values (0 to 25)
        for (int i = 0; i < n; i++) {
            key[i] = random.nextInt(26);
        }

        StringBuilder ciphertext = new StringBuilder();
        StringBuilder decrypted = new StringBuilder();

        // Encryption
        for (int i = 0; i < n; i++) {

            int p = plaintext.charAt(i) - 'A';

            int c = (p + key[i]) % 26;

            ciphertext.append((char) (c + 'A'));
        }

        // Decryption
        for (int i = 0; i < n; i++) {

            int c = ciphertext.charAt(i) - 'A';

            int p = (c - key[i] + 26) % 26;

            decrypted.append((char) (p + 'A'));
        }

        // Display results
        System.out.println("Plaintext: " + plaintext);

        System.out.print("Key: ");
        for (int value : key) {
            System.out.print(value + " ");
        }

        System.out.println("\nCiphertext: " + ciphertext);

        System.out.println("Decrypted Text: " + decrypted);
    }
}

output:
Plaintext: HELLO
Key: 13 0 10 21 4 
Ciphertext: UEVGS
Decrypted Text: HELLO
 
