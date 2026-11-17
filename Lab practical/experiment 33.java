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

output:
DSA Signature 1: MDwCHDuiYDk4cHgQMqwHKJbmFeTGbh4C/2g8z056Fb8CHCTNOVgCCQRyWM1mj9rXvs5yK7/k4jWIiLgNiCQ=
DSA Signature 2: MD0CHDQcKEV7X/M1rO9XGPxRYaq4Dr6mgpZ6i4jPkNMCHQCV4jfXjrA28uM8H8Ec2gKTLtbwp6Q4CN/wDgcN
DSA Signatures Same: false

RSA Signature 1: lMPq52LkLJCADnEDRp2pVUFR77Om/LGtPy9u+5x8h3OyGguzKT6Jh2bXZG4V4ioMcA+33E2cy17mXOf417Knq6ZQQoVVUad9y0nZlDvqqOLzprXo4NW8p61aX+FbdHxvg269r8vmIiNWUhL7vsfsW13VB5gBYB5R7q8eyJ4yNwUisiEu1/P/KEvYDEYSe0TN+GYsBpUIwCLWJ+/cubQnhTqmYaS96POP1sj5EQ3irik1h/5t7PVYtS5GzmiO27pi2zYCDewQYubQ95y70sPkr8WcVKAQHwB2/6TmYPd4lvYbPRaZoPs6ooFGbVedYTMZh6JZG8vMQBy8HCQsYY513g==
RSA Signature 2: lMPq52LkLJCADnEDRp2pVUFR77Om/LGtPy9u+5x8h3OyGguzKT6Jh2bXZG4V4ioMcA+33E2cy17mXOf417Knq6ZQQoVVUad9y0nZlDvqqOLzprXo4NW8p61aX+FbdHxvg269r8vmIiNWUhL7vsfsW13VB5gBYB5R7q8eyJ4yNwUisiEu1/P/KEvYDEYSe0TN+GYsBpUIwCLWJ+/cubQnhTqmYaS96POP1sj5EQ3irik1h/5t7PVYtS5GzmiO27pi2zYCDewQYubQ95y70sPkr8WcVKAQHwB2/6TmYPd4lvYbPRaZoPs6ooFGbVedYTMZh6JZG8vMQBy8HCQsYY513g==
RSA Signatures Same: true














