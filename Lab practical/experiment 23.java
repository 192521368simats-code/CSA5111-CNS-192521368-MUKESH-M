
public class SDES_CTR {

    static int[] P10 = {3,5,2,7,4,10,1,9,8,6};
    static int[] P8 = {6,3,7,4,8,5,10,9};
    static int[] IP = {2,6,3,1,4,8,5,7};
    static int[] IP_INV = {4,1,3,5,7,2,8,6};
    static int[] EP = {4,1,2,3,2,3,4,1};
    static int[] P4 = {2,4,3,1};

    static int[][] S0 = {
        {1,0,3,2},
        {3,2,1,0},
        {0,2,1,3},
        {3,1,3,2}
    };

    static int[][] S1 = {
        {0,1,2,3},
        {2,0,1,3},
        {3,0,1,0},
        {2,1,0,3}
    };

    static String permute(String s, int[] p) {
        String result = "";
        for (int i : p)
            result += s.charAt(i - 1);
        return result;
    }

    static String shift(String s, int n) {
        return s.substring(n) + s.substring(0, n);
    }

    static String xor(String a, String b) {
        String result = "";
        for (int i = 0; i < a.length(); i++)
            result += (a.charAt(i) == b.charAt(i)) ? "0" : "1";
        return result;
    }

    static String[] generateKeys(String key) {
        String p = permute(key, P10);
        String left = p.substring(0, 5);
        String right = p.substring(5);

        left = shift(left, 1);
        right = shift(right, 1);
        String k1 = permute(left + right, P8);

        left = shift(left, 2);
        right = shift(right, 2);
        String k2 = permute(left + right, P8);

        return new String[]{k1, k2};
    }

    static String sbox(String bits, int[][] box) {
        int row = Integer.parseInt(
            "" + bits.charAt(0) + bits.charAt(3), 2);
        int col = Integer.parseInt(bits.substring(1, 3), 2);

        return String.format("%2s",
            Integer.toBinaryString(box[row][col]))
            .replace(' ', '0');
    }

    static String fk(String bits, String key) {
        String left = bits.substring(0, 4);
        String right = bits.substring(4);

        String temp = xor(permute(right, EP), key);
        String s = sbox(temp.substring(0, 4), S0)
                 + sbox(temp.substring(4), S1);

        return xor(left, permute(s, P4)) + right;
    }

    static String encrypt(String plaintext, String key) {
        String[] keys = generateKeys(key);

        String s = permute(plaintext, IP);
        s = fk(s, keys[0]);

        s = s.substring(4) + s.substring(0, 4);
        s = fk(s, keys[1]);

        return permute(s, IP_INV);
    }

    // CTR encryption and decryption use the same operation.
    static String ctr(String data, String key) {
        String result = "";

        for (int i = 0; i < data.length(); i += 8) {
            String block = data.substring(i, i + 8);

            // Counter starts at 00000000
            int counter = i / 8;
            String counterBits =
                String.format("%8s",
                    Integer.toBinaryString(counter))
                    .replace(' ', '0');

            String stream = encrypt(counterBits, key);
            result += xor(block, stream);
        }

        return result;
    }

    static void printBlocks(String label, String data) {
        System.out.println(label);
        for (int i = 0; i < data.length(); i += 8)
            System.out.print(data.substring(i, i + 8) + " ");
        System.out.println();
    }

    public static void main(String[] args) {
        String key = "0111111101";

        String plaintext =
            "000000010000001000000100";

        printBlocks("Plaintext:", plaintext);

        String ciphertext = ctr(plaintext, key);
        printBlocks("Encrypted ciphertext:", ciphertext);

        String decrypted = ctr(ciphertext, key);
        printBlocks("Decrypted plaintext:", decrypted);
    }
}
