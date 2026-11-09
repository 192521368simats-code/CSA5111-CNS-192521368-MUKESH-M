 import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter text: ");
        String text = sc.nextLine();

        System.out.print("Enter key: ");
        int k = sc.nextInt();

        k = k % 26;

        StringBuilder result = new StringBuilder();

        for (char ch : text.toCharArray()) {
            if (ch >= 'A' && ch <= 'Z')
                result.append((char) ('A' + (ch - 'A' + k) % 26));
            else if (ch >= 'a' && ch <= 'z')
                result.append((char) ('a' + (ch - 'a' + k) % 26));
            else
                result.append(ch);
        }

        System.out.println("Encrypted text: " + result);
    }
}