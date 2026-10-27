import java.util.Scanner;

public class RSAGCD {

    // Calculate the greatest common divisor
    static long gcd(long a, long b) {
        while (b != 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter RSA modulus n: ");
        long n = sc.nextLong();

        System.out.print("Enter plaintext block M: ");
        long m = sc.nextLong();

        System.out.print("Enter public exponent e: ");
        long e = sc.nextLong();

        long g = gcd(m, n);

        System.out.println("GCD(M, n) = " + g);

        if (g > 1 && g < n) {
            long p = g;
            long q = n / g;

            System.out.println("A factor of n is: " + p);
            System.out.println("Other factor of n is: " + q);

            long phi = (p - 1) * (q - 1);
            System.out.println("Phi(n) = " + phi);

            // Find modular inverse of e modulo phi(n)
            long d = modularInverse(e, phi);

            if (d != -1) {
                System.out.println("Private exponent d = " + d);
                System.out.println("Private key = (" + d + ", " + n + ")");
            } else {
                System.out.println(
                    "The modular inverse does not exist.");
            }
        } else if (g == n) {
            System.out.println(
                "The plaintext block is divisible by n.");
            System.out.println(
                "This does not directly reveal a proper factor.");
        } else {
            System.out.println(
                "GCD is 1. This block does not reveal a factor of n.");
        }

        sc.close();
    }

    static long modularInverse(long e, long phi) {
        long t = 0, newT = 1;
        long r = phi, newR = e;

        while (newR != 0) {
            long quotient = r / newR;

            long temp = t;
            t = newT;
            newT = temp - quotient * newT;

            temp = r;
            r = newR;
            newR = temp - quotient * newR;
        }

        if (r != 1) {
            return -1;
        }

        return (t % phi + phi) % phi;
    }
}
