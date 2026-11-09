import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter text: ");
        String text = sc.nextLine();

        System.out.print("Enter key: ");
        String key = sc.nextLine().toUpperCase();

        StringBuilder result = new StringBuilder();
        int j = 0;

        for (char ch : text.toUpperCase().toCharArray()) {
            if (ch >= 'A' && ch <= 'Z') {
                int p = ch - 'A';
                int k = key.charAt(j % key.length()) - 'A';

                result.append((char) ('A' + (p + k) % 26));
                j++;
            } else {
                result.append(ch);
            }
        }

        System.out.println("Encrypted text: " + result);
    }
}