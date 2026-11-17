import java.security.*;
import java.util.Base64;

public class SignatureComparison {

    public static void main(String[] args) throws Exception {

        String message = "Hello World";

        // Generate DSA Key Pair
        KeyPairGenerator dsaGen =
                KeyPairGenerator.getInstance("DSA");
        dsaGen.initialize(2048);
        KeyPair dsaPair = dsaGen.generateKeyPair();

        // Generate RSA Key Pair
        KeyPairGenerator rsaGen =
                KeyPairGenerator.getInstance("RSA");
        rsaGen.initialize(2048);
        KeyPair rsaPair = rsaGen.generateKeyPair();

        // DSA: Sign the same message twice
        Signature dsa = Signature.getInstance("SHA256withDSA");

        dsa.initSign(dsaPair.getPrivate());
        dsa.update(message.getBytes());
        byte[] dsaSig1 = dsa.sign();

        dsa.initSign(dsaPair.getPrivate());
        dsa.update(message.getBytes());
        byte[] dsaSig2 = dsa.sign();

        // RSA: Sign the same message twice
        Signature rsa = Signature.getInstance("SHA256withRSA");

        rsa.initSign(rsaPair.getPrivate());
        rsa.update(message.getBytes());
        byte[] rsaSig1 = rsa.sign();

        rsa.initSign(rsaPair.getPrivate());
        rsa.update(message.getBytes());
        byte[] rsaSig2 = rsa.sign();

        // Display results
        System.out.println("DSA Signature 1: " +
                Base64.getEncoder().encodeToString(dsaSig1));

        System.out.println("DSA Signature 2: " +
                Base64.getEncoder().encodeToString(dsaSig2));

        System.out.println("DSA Signatures Same: " +
                MessageDigest.isEqual(dsaSig1, dsaSig2));

        System.out.println("\nRSA Signature 1: " +
                Base64.getEncoder().encodeToString(rsaSig1));

        System.out.println("RSA Signature 2: " +
                Base64.getEncoder().encodeToString(rsaSig2));

        System.out.println("RSA Signatures Same: " +
                MessageDigest.isEqual(rsaSig1, rsaSig2));
    }
}
