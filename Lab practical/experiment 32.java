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
DSA Signature 1: MD0CHF39CjICYxk71A1cPy85t6OmB60xMUSQMuTGrq0CHQCVtXH0YyQKp5YMsPsOPEj5k6eDPeIkqmr9mwtl
DSA Signature 2: MDwCHFH6FsqeaXE+N8erl+bVfgdJ6CUOvKDUN6k6tBkCHBX36Q60+WcVmWc4Mq7JDs2VGtgGonexRN5jRz0=
DSA Signatures Same: false

RSA Signature 1: HL6fjQ6tNtA7VYPsfSM3zT5VtcrUEb8VZ7q1JFr6ifrOqNlkmdHOJ3VdiC5tJVbuauWBRaCf+g4NUxzI4jwCqicC8BJUTek4mv5gfw+5ZZACxYEe0/pgSVCBFFn+a48C6smFMmHwvAd9XN9TIplCoLdGZk+tHzH0/npa/DqA4LMJqBZVSOWKjvROf7G/fFWV0K4d/Kq7gE/h8KTnFn8XCQMjlGLT0x4mU5HEGarGBt5eA36+JDZbLihW7rRsWis/NVYfFEw7RFe7B6jyw3YholjjWUuSkhqCmkhl0YSF1ReoDZ7ptKwSDyhl4u4Qf2cX5SqIf74ORr30KlCn0n/Wrg==
RSA Signature 2: HL6fjQ6tNtA7VYPsfSM3zT5VtcrUEb8VZ7q1JFr6ifrOqNlkmdHOJ3VdiC5tJVbuauWBRaCf+g4NUxzI4jwCqicC8BJUTek4mv5gfw+5ZZACxYEe0/pgSVCBFFn+a48C6smFMmHwvAd9XN9TIplCoLdGZk+tHzH0/npa/DqA4LMJqBZVSOWKjvROf7G/fFWV0K4d/Kq7gE/h8KTnFn8XCQMjlGLT0x4mU5HEGarGBt5eA36+JDZbLihW7rRsWis/NVYfFEw7RFe7B6jyw3YholjjWUuSkhqCmkhl0YSF1ReoDZ7ptKwSDyhl4u4Qf2cX5SqIf74ORr30KlCn0n/Wrg==
RSA Signatures Same: true








