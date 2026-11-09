
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;

public class CBCMACAttack {

    static byte[] xor(byte[] a, byte[] b) {
        byte[] result = new byte[a.length];

        for (int i = 0; i < a.length; i++) {
            result[i] = (byte) (a[i] ^ b[i]);
        }

        return result;
    }

    static byte[] encryptBlock(Cipher cipher, byte[] block)
            throws Exception {
        return cipher.doFinal(block);
    }

    static byte[] cbcMac(Cipher cipher, byte[] message)
            throws Exception {
        byte[] state = new byte[16];

        for (int i = 0; i < message.length; i += 16) {
            byte[] block = Arrays.copyOfRange(
                    message, i, i + 16);

            state = encryptBlock(cipher, xor(block, state));
        }

        return state;
    }

    static String toHex(byte[] data) {
        StringBuilder result = new StringBuilder();

        for (byte b : data) {
            result.append(String.format("%02X", b & 0xff));
        }

        return result.toString();
    }

    public static void main(String[] args) throws Exception {
        byte[] key = "0123456789ABCDEF".getBytes("UTF-8");
        byte[] X = "ABCDEFGHIJKLMNOP".getBytes("UTF-8");

        SecretKeySpec secretKey =
                new SecretKeySpec(key, "AES");

        Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        // MAC of the original one-block message
        byte[] T = cbcMac(cipher, X);

        // Construct second block: X XOR T
        byte[] secondBlock = xor(X, T);

        // Construct the two-block message X || (X XOR T)
        byte[] forgedMessage = new byte[32];

        System.arraycopy(X, 0, forgedMessage, 0, 16);
        System.arraycopy(secondBlock, 0, forgedMessage, 16, 16);

        // Calculate the MAC of the constructed message
        byte[] forgedTag = cbcMac(cipher, forgedMessage);

        System.out.println("Original message X: " + toHex(X));
        System.out.println("Original MAC T:     " + toHex(T));
        System.out.println("Second block X XOR T: "
                + toHex(secondBlock));
        System.out.println("Forged message MAC: " + toHex(forgedTag));
        System.out.println("Tags match: " +
                Arrays.equals(T, forgedTag));
    }
}
