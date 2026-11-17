
import java.util.Scanner;

public class AffineCipher {

    // Find GCD
    static int gcd(int a, int b) {
        if (b == 0)
            return a;
        return gcd(b, a % b);
    }

    // Find modular inverse
    static int modInverse(int a) {
        for (int i = 1; i < 26; i++) {
            if ((a * i) % 26 == 1)
                return i;
        }
        return -1;
    }

    // Encryption
    static String encrypt(String text, int a, int b) {
        StringBuilder result = new StringBuilder();

        for (char ch : text.toCharArray()) {
            if (ch >= 'A' && ch <= 'Z') {
                int p = ch - 'A';
                int c = (a * p + b) % 26;
                result.append((char)(c + 'A'));
            } else {
                result.append(ch);
            }
        }

        return result.toString();
    }

    // Decryption
    static String decrypt(String text, int a, int b) {
        StringBuilder result = new StringBuilder();

        int inverse = modInverse(a);

        for (char ch : text.toCharArray()) {
            if (ch >= 'A' && ch <= 'Z') {
                int c = ch - 'A';
                int p = (inverse * (c - b + 26)) % 26;
                result.append((char)(p + 'A'));
            } else {
                result.append(ch);
            }
        }

        return result.toString();
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter plaintext: ");
        String text = sc.nextLine().toUpperCase();

        System.out.print("Enter key a: ");
        int a = sc.nextInt();

        System.out.print("Enter key b: ");
        int b = sc.nextInt();

        a = ((a % 26) + 26) % 26;
        b = ((b % 26) + 26) % 26;

        // Check one-to-one condition
        if (gcd(a, 26) != 1) {
            System.out.println("Invalid key!");
            System.out.println("GCD(a,26) must be 1.");
            System.out.println("Encryption is not one-to-one.");
            sc.close();
            return;
        }

        String encrypted = encrypt(text, a, b);
        String decrypted = decrypt(encrypted, a, b);

        System.out.println("Original Text: " + text);
        System.out.println("Encrypted Text: " + encrypted);
        System.out.println("Decrypted Text: " + decrypted);

        sc.close();
    }
}

output:
Enter plaintext: hukesh
Enter key a: 5
Enter key b: 8
Original Text: HUKESH
Encrypted Text: REGCUR
Decrypted Text: HUKESH
