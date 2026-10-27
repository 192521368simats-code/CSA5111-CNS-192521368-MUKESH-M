
public class RSA {

    // Find p and q by trial division
    static long[] findFactors(long n) {
        for (long i = 2; i <= n / i; i++) {
            if (n % i == 0) {
                return new long[]{i, n / i};
            }
        }
        throw new IllegalArgumentException(
                "The modulus must be composite.");
    }

    // Extended Euclidean Algorithm
    static long modInverse(long e, long phi) {
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
            throw new IllegalArgumentException(
                    "The private key does not exist.");
        }

        return (t % phi + phi) % phi;
    }

    // Modular exponentiation
    static long modPow(long base, long exp, long mod) {
        long result = 1;
        base %= mod;

        while (exp > 0) {
            if (exp % 2 == 1) {
                result = (result * base) % mod;
            }

            base = (base * base) % mod;
            exp /= 2;
        }

        return result;
    }

    public static void main(String[] args) {
        long e = 31;
        long n = 3599;

        // Find prime factors
        long[] factors = findFactors(n);
        long p = factors[0];
        long q = factors[1];

        // Calculate Euler's totient
        long phi = (p - 1) * (q - 1);

        // Calculate private exponent
        long d = modInverse(e, phi);

        System.out.println("RSA Algorithm");
        System.out.println("Public exponent (e) = " + e);
        System.out.println("Modulus (n) = " + n);
        System.out.println("p = " + p);
        System.out.println("q = " + q);
        System.out.println("Phi(n) = " + phi);
        System.out.println("Private exponent (d) = " + d);
        System.out.println("Private key = (" + d + ", " + n + ")");

        // Demonstrate encryption and decryption
        long message = 65;
        long ciphertext = modPow(message, e, n);
        long decrypted = modPow(ciphertext, d, n);

        System.out.println("Original message = " + message);
        System.out.println("Encrypted message = " + ciphertext);
        System.out.println("Decrypted message = " + decrypted);
    }
}
