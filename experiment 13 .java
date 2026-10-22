import java.util.*;

public class Main {

    static int modInverse(int a) {
        a = (a % 26 + 26) % 26;

        for (int i = 1; i < 26; i++)
            if ((a * i) % 26 == 1)
                return i;

        return -1;
    }

    static int gcd(int a, int b) {
        while (b != 0) {
            int t = a % b;
            a = b;
            b = t;
        }
        return Math.abs(a);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter plaintext pair 1: ");
        String p1 = sc.nextLine().toUpperCase();

        System.out.print("Enter ciphertext pair 1: ");
        String c1 = sc.nextLine().toUpperCase();

        System.out.print("Enter plaintext pair 2: ");
        String p2 = sc.nextLine().toUpperCase();

        System.out.print("Enter ciphertext pair 2: ");
        String c2 = sc.nextLine().toUpperCase();

        if (p1.length() != 2 || p2.length() != 2 ||
            c1.length() != 2 || c2.length() != 2) {
            System.out.println("Each pair must contain exactly 2 letters.");
            return;
        }

        // P = [P1 P2]
        int[][] P = {
            {p1.charAt(0) - 'A', p2.charAt(0) - 'A'},
            {p1.charAt(1) - 'A', p2.charAt(1) - 'A'}
        };

        // C = [C1 C2]
        int[][] C = {
            {c1.charAt(0) - 'A', c2.charAt(0) - 'A'},
            {c1.charAt(1) - 'A', c2.charAt(1) - 'A'}
        };

        int det = (P[0][0] * P[1][1] - P[0][1] * P[1][0]) % 26;
        det = (det + 26) % 26;

        int inv = modInverse(det);

        if (inv == -1) {
            System.out.println("Cannot recover key.");
            System.out.println("Plaintext matrix is not invertible modulo 26.");
            return;
        }

        // P inverse
        int[][] Pinv = {
            {(inv * P[1][1]) % 26, (-inv * P[0][1]) % 26},
            {(-inv * P[1][0]) % 26, (inv * P[0][0]) % 26}
        };

        for (int i = 0; i < 2; i++)
            for (int j = 0; j < 2; j++)
                Pinv[i][j] = (Pinv[i][j] + 26) % 26;

        // K = C * P^-1
        int[][] K = new int[2][2];

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                K[i][j] = (C[i][0] * Pinv[0][j]
                         + C[i][1] * Pinv[1][j]) % 26;
            }
        }

        System.out.println("\nRecovered Key Matrix:");
        System.out.println(K[0][0] + " " + K[0][1]);
        System.out.println(K[1][0] + " " + K[1][1]);
    }
}