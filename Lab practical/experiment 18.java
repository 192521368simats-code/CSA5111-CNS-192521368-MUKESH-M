
public class DESKeyGeneration {

    static int[] PC1 = {
        57,49,41,33,25,17,9,
        1,58,50,42,34,26,18,
        10,2,59,51,43,35,27,
        19,11,3,60,52,44,36,
        63,55,47,39,31,23,15,
        7,62,54,46,38,30,22,
        14,6,61,53,45,37,29,
        21,13,5,28,20,12,4
    };

    static int[] shifts = {
        1,1,2,2,2,2,2,2,
        1,2,2,2,2,2,2,1
    };

    static int[] PC2 = {
        14,17,11,24,1,5,
        3,28,15,6,21,10,
        23,19,12,4,26,8,
        16,7,27,20,13,2,
        41,52,31,37,47,55,
        30,40,51,45,33,48,
        44,49,39,56,34,53,
        46,42,50,36,29,32
    };

    static String permute(String key, int[] table) {
        String result = "";
        for (int position : table) {
            result += key.charAt(position - 1);
        }
        return result;
    }

    static String leftShift(String half, int n) {
        return half.substring(n) + half.substring(0, n);
    }

    public static void main(String[] args) {

        String key =
            "0001001100110100010101110111100110011011101111001101111111110001";

        String permutedKey = permute(key, PC1);

        String C = permutedKey.substring(0, 28);
        String D = permutedKey.substring(28, 56);

        System.out.println("Initial Key: " + key);
        System.out.println("\nDES Subkeys:");

        for (int i = 0; i < 16; i++) {
            C = leftShift(C, shifts[i]);
            D = leftShift(D, shifts[i]);

            String combined = C + D;
            String subkey = permute(combined, PC2);

            System.out.println("K" + (i + 1) + ": " + subkey);
            System.out.println("  First 24 bits : "
                               + subkey.substring(0, 24));
            System.out.println("  Second 24 bits: "
                               + subkey.substring(24, 48));
        }
    }
}
