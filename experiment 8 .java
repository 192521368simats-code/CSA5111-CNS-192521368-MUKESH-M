import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter text: ");
        String text = sc.nextLine().toUpperCase();

        System.out.print("Enter keyword: ");
        String key = sc.nextLine().toUpperCase();

        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder cipher = new StringBuilder();

        // Create cipher alphabet from keyword
        for (char c : key.toCharArray()) {
            if (alphabet.indexOf(c) >= 0 && cipher.indexOf(String.valueOf(c)) == -1)
                cipher.append(c);
        }

        // Add remaining unused letters
        for (char c : alphabet.toCharArray()) {
            if (cipher.indexOf(String.valueOf(c)) == -1)
                cipher.append(c);
        }

        StringBuilder result = new StringBuilder();

        // Encrypt
        for (char c : text.toCharArray()) {
            int pos = alphabet.indexOf(c);

            if (pos >= 0)
                result.append(cipher.charAt(pos));
            else
                result.append(c);
        }

        System.out.println("Cipher alphabet: " + cipher);
        System.out.println("Encrypted text: " + result);
    }
}