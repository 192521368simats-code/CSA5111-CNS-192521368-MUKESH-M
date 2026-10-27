
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class RSACharacterAttack {

    public static void main(String[] args) {

        // Small RSA values for demonstration only
        BigInteger p = BigInteger.valueOf(61);
        BigInteger q = BigInteger.valueOf(53);
        BigInteger n = p.multiply(q);
        BigInteger phi = p.subtract(BigInteger.ONE)
                .multiply(q.subtract(BigInteger.ONE));

        BigInteger e = BigInteger.valueOf(17);
        BigInteger d = e.modInverse(phi);

        String message = "HELLO";

        // Encrypt each character separately
        BigInteger[] ciphertext = new BigInteger[message.length()];

        System.out.println("Original message: " + message);
        System.out.print("Ciphertext: ");

        for (int i = 0; i < message.length(); i++) {
            int value = message.charAt(i) - 'A';

            ciphertext[i] = BigInteger.valueOf(value)
                    .modPow(e, n);

            System.out.print(ciphertext[i] + " ");
        }

        // Attacker creates a codebook using the public key
        Map<BigInteger, Character> codebook = new HashMap<>();

        for (int value = 0; value < 26; value++) {
            BigInteger c = BigInteger.valueOf(value)
                    .modPow(e, n);

            codebook.put(c, (char) ('A' + value));
        }

        // Recover the message without the private key
        StringBuilder recovered = new StringBuilder();

        for (BigInteger c : ciphertext) {
            recovered.append(codebook.get(c));
        }

        System.out.println("\nRecovered message: " + recovered);
        System.out.println("Private key was not used in the attack.");
    }
}
