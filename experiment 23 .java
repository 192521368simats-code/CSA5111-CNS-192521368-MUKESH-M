
public class SDESCTR {

    static int[] P10 = {3, 5, 2, 7, 4, 10, 1, 9, 8, 6};
    static int[] P8 = {6, 3, 7, 4, 8, 5, 10, 9};
    static int[] IP = {2, 6, 3, 1, 4, 8, 5, 7};
    static int[] IP_INV = {4, 1, 3, 5, 7, 2, 8, 6};
    static int[] EP = {4, 1, 2, 3, 2, 3, 4, 1};
    static int[] P4 = {2, 4, 3, 1};

    static int[][] S0 = {
        {1, 0, 3, 2},
        {3, 2, 1, 0},
        {0, 2, 1, 3},
        {3, 1, 3, 2}
    };

    static int[][] S1 = {
        {0, 1, 2, 3},
        {2, 0, 1, 3},
        {3, 0, 1, 0},
        {2, 1, 0, 3}
    };

    static String permute(String bits, int[] table) {
        String result = "";
        for (int pos : table) {
            result += bits.charAt(pos - 1);
        }
        return result;
    }

    static String xor(String a, String b) {
        String result = "";
        for (int i = 0; i < a.length(); i++) {
            result += (a.charAt(i) == b.charAt(i)) ? '0' : '1';
        }
        return result;
    }

    static String leftShift(String bits, int n) {
        return bits.substring(n) + bits.substring(0, n);
    }

    static String[] generateKeys(String key) {
        String p = permute(key, P10);
        String left = p.substring(0, 5);
        String right = p.substring(5);

        left = leftShift(left, 1);
        right = leftShift(right, 1);
        String k1 = permute(left + right, P8);

        left = leftShift(left, 2);
        right = leftShift(right, 2);
        String k2 = permute(left + right, P8);

        return new String[]{k1, k2};
    }

    static String sBox(String bits, int[][] box) {
        int row = Integer.parseInt(
                "" + bits.charAt(0) + bits.charAt(3), 2);
        int col = Integer.parseInt(bits.substring(1, 3), 2);

        return String.format("%2s",
                Integer.toBinaryString(box[row][col]))
                .replace(' ', '0');
    }

    static String fk(String bits, String key) {
        String left = bits.substring(0, 4);
        String right = bits.substring(4, 8);

        String expanded = permute(right, EP);
        String x = xor(expanded, key);

        String s = sBox(x.substring(0, 4), S0)
                 + sBox(x.substring(4, 8), S1);

        String p4 = permute(s, P4);
        return xor(left, p4) + right;
    }

    static String encryptBlock(String block, String[] keys) {
        String bits = permute(block, IP);

        bits = fk(bits, keys[0]);
        bits = bits.substring(4) + bits.substring(0, 4);
        bits = fk(bits, keys[1]);

        return permute(bits, IP_INV);
    }

    static String incrementCounter(String counter) {
        int value = Integer.parseInt(counter, 2);
        value = (value + 1) % 256;

        return String.format("%8s",
                Integer.toBinaryString(value)).replace(' ', '0');
    }

    static String ctrProcess(String input, String counter,
                             String[] keys) {
        String result = "";
        String currentCounter = counter;

        for (int i = 0; i < input.length(); i += 8) {
            String block = input.substring(i, i + 8);

            // Encrypt the counter to generate the keystream
            String keystream = encryptBlock(currentCounter, keys);

            // XOR with plaintext or ciphertext
            result += xor(block, keystream);

            // Increment the counter for the next block
            currentCounter = incrementCounter(currentCounter);
        }

        return result;
    }

    static String formatBits(String bits) {
        String result = "";
        for (int i = 0; i < bits.length(); i++) {
            if (i > 0 && i % 4 == 0) {
                result += " ";
            }
            result += bits.charAt(i);
        }
        return result;
    }

    public static void main(String[] args) {
        String key = "0111111101";
        String counter = "00000000";
        String plaintext = "000000010000001000000100";

        String[] keys = generateKeys(key);

        String ciphertext = ctrProcess(plaintext, counter, keys);
        String decrypted = ctrProcess(ciphertext, counter, keys);

        System.out.println("S-DES Counter Mode (CTR)");
        System.out.println("Key        : " + formatBits(key));
        System.out.println("Counter    : " + formatBits(counter));
        System.out.println("Plaintext  : " + formatBits(plaintext));
        System.out.println("Ciphertext : " + formatBits(ciphertext));
        System.out.println("Decrypted  : " + formatBits(decrypted));
    }
}
