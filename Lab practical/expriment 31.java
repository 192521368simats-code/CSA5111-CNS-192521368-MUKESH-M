import java.math.BigInteger;
import java.util.Scanner;

public class CMACSubkey {
    static BigInteger generateSubkey(BigInteger input, int blockSize) {
        BigInteger R;

        if (blockSize == 64)
            R = new BigInteger("1B", 16);
        else
            R = new BigInteger("87", 16);

        BigInteger mask = BigInteger.ONE.shiftLeft(blockSize)
                .subtract(BigInteger.ONE);

        boolean msb = input.testBit(blockSize - 1);

        BigInteger result = input.shiftLeft(1).and(mask);

        if (msb)
            result = result.xor(R);

        return result;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter block size (64 or 128): ");
        int size = sc.nextInt();

        if (size != 64 && size != 128) {
            System.out.println("Invalid block size!");
            sc.close();
            return;
        }

        System.out.print("Enter L in hexadecimal: ");
        String hex = sc.next();

        BigInteger L = new BigInteger(hex, 16);

        BigInteger K1 = generateSubkey(L, size);
        BigInteger K2 = generateSubkey(K1, size);

        System.out.println("L  = " + L.toString(16).toUpperCase());
        System.out.println("K1 = " + K1.toString(16).toUpperCase());
        System.out.println("K2 = " + K2.toString(16).toUpperCase());

        sc.close();
    }
}