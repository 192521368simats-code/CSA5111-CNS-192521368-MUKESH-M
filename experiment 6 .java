import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter ciphertext: ");
        String text = sc.nextLine().toUpperCase();

        int a = 3;
        int b = 15;

        int aInv = 9; // 3 × 9 mod 26 = 1
        StringBuilder result = new StringBuilder();

        for (char ch : text.toCharArray()) {
            if (ch >= 'A' && ch <= 'Z') {
                int c = ch - 'A';
                int p = (aInv * (c - b + 26)) % 26;
                result.append((char) ('A' + p));
            } else {
                result.append(ch);
            }
        }

        System.out.println("Decrypted text: " + result);
    }
}