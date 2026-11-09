import java.util.*;

public class Main {
    static String encrypt(String text, int[] key) {
        StringBuilder result = new StringBuilder();
        int j = 0;

        for (char ch : text.toUpperCase().toCharArray()) {
            if (ch >= 'A' && ch <= 'Z') {
                int p = ch - 'A';
                result.append((char) ('A' + (p + key[j]) % 26));
                j++;
            }
        }
        return result.toString();
    }

    static int[] findKey(String cipher, String plain) {
        int[] key = new int[plain.length()];

        for (int i = 0; i < plain.length(); i++) {
            int c = cipher.charAt(i) - 'A';
            int p = plain.charAt(i) - 'A';
            key[i] = (c - p + 26) % 26;
        }
        return key;
    }

    public static void main(String[] args) {
        String plaintext = "SEND MORE MONEY";
        int[] key1 = {9, 0, 1, 7, 23, 15, 21, 14, 11, 11, 2, 8, 9};

        String cipher = encrypt(plaintext, key1);

        System.out.println("Ciphertext: " + cipher);

        String target = "CASH NOT NEEDED";
        target = target.replaceAll("[^A-Za-z]", "").toUpperCase();

        int[] key2 = findKey(cipher, target);

        System.out.print("Required key: ");
        for (int k : key2)
            System.out.print(k + " ");
        System.out.println();
    }
}