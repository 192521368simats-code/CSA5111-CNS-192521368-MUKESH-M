
import java.util.Scanner;

public class HillCipherAttack {

    static int mod(int x) {
        return (x % 26 + 26) % 26;
    }

    // Find modular inverse
    static int inverse(int a) {
        for (int i = 1; i < 26; i++) {
            if ((a * i) % 26 == 1)
                return i;
        }
        return -1;
    }

    // Recover Hill Cipher key
    static void recoverKey(int[][] P, int[][] C) {

        int det = mod(
            P[0][0] * P[1][1] -
            P[0][1] * P[1][0]
        );

        int detInv = inverse(det);

        if (detInv == -1) {
            System.out.println(
                "Plaintext matrix is not invertible!"
            );
            return;
        }

        // Calculate inverse of P
        int[][] invP = new int[2][2];

        invP[0][0] = mod(P[1][1] * detInv);
        invP[0][1] = mod(-P[0][1] * detInv);
        invP[1][0] = mod(-P[1][0] * detInv);
        invP[1][1] = mod(P[0][0] * detInv);

        // K = C * inverse(P)
        int[][] K = new int[2][2];

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                K[i][j] = 0;

                for (int k = 0; k < 2; k++) {
                    K[i][j] += C[i][k] * invP[k][j];
                }

                K[i][j] = mod(K[i][j]);
            }
        }

        System.out.println("\nRecovered Key Matrix:");

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                System.out.print(K[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int[][] P = new int[2][2];
        int[][] C = new int[2][2];

        System.out.println(
            "Enter Plaintext Matrix (2x2):"
        );

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                P[i][j] = sc.nextInt();
            }
        }

        System.out.println(
            "Enter Ciphertext Matrix (2x2):"
        );

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                C[i][j] = sc.nextInt();
            }
        }

        recoverKey(P, C);

        sc.close();
    }
}

output:
Enter Plaintext Matrix (2x2):
5 6
7 9
Enter Ciphertext Matrix (2x2):
6 8
5 3

Recovered Key Matrix:
8 10 
8 21 
