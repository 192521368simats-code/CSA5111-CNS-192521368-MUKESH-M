
import java.math.BigInteger;

public class DiffieHellman {
    public static void main(String[] args) {
        BigInteger q = BigInteger.valueOf(23);
        BigInteger a = BigInteger.valueOf(5);

        // Alice's and Bob's secret numbers
        BigInteger x = BigInteger.valueOf(6);
        BigInteger y = BigInteger.valueOf(15);

        // Public values
        BigInteger A = a.modPow(x, q);
        BigInteger B = a.modPow(y, q);

        // Shared key calculations
        BigInteger keyAlice = B.modPow(x, q);
        BigInteger keyBob = A.modPow(y, q);

        System.out.println("Public q = " + q);
        System.out.println("Public a = " + a);
        System.out.println("Alice's public value = " + A);
        System.out.println("Bob's public value = " + B);
        System.out.println("Alice's shared key = " + keyAlice);
        System.out.println("Bob's shared key = " + keyBob);

        System.out.println("Keys match: "
                + keyAlice.equals(keyBob));
    }
}
