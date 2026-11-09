import java.math.BigInteger;

public class Main {
    public static void main(String[] args) {

        BigInteger keys = BigInteger.ONE;

        // Number of Playfair keys = 25!
        for (int i = 1; i <= 25; i++)
            keys = keys.multiply(BigInteger.valueOf(i));

        double power = 25 * Math.log(25) / Math.log(2);

        System.out.println("Number of possible keys = " + keys);
        System.out.printf("Approximately 2^%.2f%n", power);
        System.out.println("Approximate power of 2 = 2^84");
    }
}