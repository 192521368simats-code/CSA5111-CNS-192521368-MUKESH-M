import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter text: ");
        String text = sc.nextLine().toUpperCase();

        System.out.print("Enter a: ");
        int a = sc.nextInt();

        System.out.print("Enter b: ");
        int b = sc.nextInt();

        if (gcd(a, 26) != 1) {
            System.out.println("Invalid value of a. gcd(a, 26) must be 1.");
            return;
        }

        StringBuilder result = new StringBuilder();

        for (char ch : text.toCharArray()) {
            if (ch >= 'A' && ch <= 'Z') {
                int p = ch - 'A';
                int c = (a * p + b) % 26;
                result.append((char) ('A' + c));
            } else {
                result.append(ch);
            }
        }

        System.out.println("Encrypted text: " + result);
    }

    static int gcd(int a, int b) {
        while (b != 0) {
            int t = a % b;
            a = b;
            b = t;
        }
        return Math.abs(a);
    }
}