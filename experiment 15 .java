import java.util.*;

public class Main {

    // English letter frequencies in percentage
    static final double[] FREQ = {
        8.167, 1.492, 2.782, 4.253, 12.702, 2.228,
        2.015, 6.094, 6.966, 0.153, 0.772, 4.025,
        2.406, 6.749, 7.507, 1.929, 0.095, 5.987,
        6.327, 9.056, 2.758, 0.978, 2.360, 0.150,
        1.974, 0.074
    };

    static class Result {
        int key;
        double score;
        String text;

        Result(int key, double score, String text) {
            this.key = key;
            this.score = score;
            this.text = text;
        }
    }

    // Decrypt using additive cipher key
    static String decrypt(String cipher, int key) {
        StringBuilder result = new StringBuilder();

        for (char ch : cipher.toCharArray()) {
            if (ch >= 'A' && ch <= 'Z') {
                char p = (char) ('A' + (ch - 'A' - key + 26) % 26);
                result.append(p);
            } else if (ch >= 'a' && ch <= 'z') {
                char p = (char) ('a' + (ch - 'a' - key + 26) % 26);
                result.append(p);
            } else {
                result.append(ch);
            }
        }

        return result.toString();
    }

    // Chi-square score: smaller = more likely English
    static double score(String text) {
        int[] count = new int[26];
        int total = 0;

        for (char ch : text.toUpperCase().toCharArray()) {
            if (ch >= 'A' && ch <= 'Z') {
                count[ch - 'A']++;
                total++;
            }
        }

        if (total == 0)
            return Double.MAX_VALUE;

        double chi = 0.0;

        for (int i = 0; i < 26; i++) {
            double expected = total * FREQ[i] / 100.0;
            chi += Math.pow(count[i] - expected, 2) / expected;
        }

        return chi;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter ciphertext: ");
        String cipher = sc.nextLine();

        System.out.print("How many possible plaintexts? ");
        int n = sc.nextInt();

        n = Math.min(n, 26);

        List<Result> results = new ArrayList<>();

        // Try all 26 possible keys
        for (int key = 0; key < 26; key++) {
            String plain = decrypt(cipher, key);
            double s = score(plain);

            results.add(new Result(key, s, plain));
        }

        // Sort from most likely to least likely
        results.sort(Comparator.comparingDouble(r -> r.score));

        System.out.println("\nTop " + n + " possible plaintexts:");

        for (int i = 0; i < n; i++) {
            Result r = results.get(i);

            System.out.printf(
                "%d. Key = %d  Score = %.2f%n   %s%n%n",
                i + 1, r.key, r.score, r.text
            );
        }
    }
}