import java.util.*;

public class Main {

    static int[][] K = {
        {9, 4},
        {5, 7}
    };

    // Inverse of K mod 26
    static int[][] Kinv = {
        {5, 12},
        {15, 25}
    };

    static String process(String text, int[][] key) {
        StringBuilder result = new StringBuilder();

        text = text.toUpperCase().replaceAll("[^A-Z]", "");

        if (text.length() % 2 != 0)
            text += "X";

        for (int i = 0; i < text.length(); i += 2) {
            int x = text.charAt(i) - 'A';
            int y = text.charAt(i + 1) - 'A';

            int a = (key[0][0] * x + key[0][1] * y) % 26;
            int b = (key[1][0] * x + key[1][1] * y) % 26;

            result.append((char) ('A' + a));
            result.append((char) ('A' + b));
        }

        return result.toString();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter plaintext: ");
        String plaintext = sc.nextLine();

        String ciphertext = process(plaintext, K);
        String decrypted = process(ciphertext, Kinv);

        System.out.println("Encrypted text: " + ciphertext);
        System.out.println("Decrypted text: " + decrypted);
    }
}