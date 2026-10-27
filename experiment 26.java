import java.math.BigInteger;

public class RSAKeyLeak {

    public static void main(String[] args) {

        // Bob's original RSA parameters (small values for demonstration)
        BigInteger p = BigInteger.valueOf(61);
        BigInteger q = BigInteger.valueOf(53);

        BigInteger n = p.multiply(q);
        BigInteger phi = p.subtract(BigInteger.ONE)
                .multiply(q.subtract(BigInteger.ONE));

        BigInteger e = BigInteger.valueOf(17);
        BigInteger d = e.modInverse(phi);

        System.out.println("Original RSA Key Pair");
        System.out.println("Modulus n = " + n);
        System.out.println("Public exponent e = " + e);
        System.out.println("Private exponent d = " + d);

        // Demonstrate encryption and decryption
        BigInteger message = BigInteger.valueOf(65);
        BigInteger ciphertext = message.modPow(e, n);
        BigInteger decrypted = ciphertext.modPow(d, n);

        System.out.println("\nOriginal message = " + message);
        System.out.println("Ciphertext = " + ciphertext);
        System.out.println("Decrypted message = " + decrypted);

        // Bob changes e and d but keeps the same modulus n
        BigInteger newE = BigInteger.valueOf(7);

        if (!newE.gcd(phi).equals(BigInteger.ONE)) {
            System.out.println(
                "\nNew public exponent is invalid.");
            return;
        }

        BigInteger newD = newE.modInverse(phi);

        System.out.println("\nNew RSA Key Pair (same modulus)");
        System.out.println("New public exponent = " + newE);
        System.out.println("New private exponent = " + newD);
        System.out.println("Modulus remains = " + n);

        System.out.println(
            "\nIs reusing the modulus safe after key leakage? NO");
        System.out.println(
            "Bob should generate a new modulus and key pair.");
    }
}
