
import java.util.*;

public class FrequencyAttack {

    static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    // Approximate English letter frequencies
    static final double[] ENGLISH_FREQ = {
        8.2, 1.5, 2.8, 4.3, 12.7, 2.2, 2.0,
        6.1, 7.0, 0.15, 0.77, 4.0, 2.4, 6.7,
        7.5, 1.9, 0.095, 6.0, 6.3, 9.1, 2.8,
        0.98, 2.4, 0.15, 2.0, 0.074
    };

    static String decrypt(String cipher, int[] key) {
        StringBuilder result = new StringBuilder();

        for (char ch : cipher.toCharArray()) {
            if (Character.isLetter(ch)) {
                char upper = Character.toUpperCase(ch);
                int index = upper - 'A';

                char decoded = (char) ('A' + key[index]);

                result.append(Character.isLowerCase(ch)
                        ? Character.toLowerCase(decoded)
                        : decoded);
            } else {
                result.append(ch);
            }
        }

        return result.toString();
    }

    // Score likely English letter combinations
    static double score(String text) {
        String s = text.toUpperCase();
        double result = 0;

        String[] common = {
            "TH", "HE", "IN", "ER", "AN", "RE",
            "ON", "AT", "EN", "ND", "THE", "AND",
            "ING", "ION", "ENT", "HER", "FOR",
            "THAT", "WITH", "TION"
        };

        for (String pattern : common) {
            int index = 0;

            while ((index = s.indexOf(pattern, index)) != -1) {
                result += pattern.length() * 2;
                index++;
            }
        }

        // Penalize unusual letter frequency
        int[] count = new int[26];
        int total = 0;

        for (char ch : s.toCharArray()) {
            if (ch >= 'A' && ch <= 'Z') {
                count[ch - 'A']++;
                total++;
            }
        }

        if (total > 0) {
            for (int i = 0; i < 26; i++) {
                double actual = (count[i] * 100.0) / total;
                result -= Math.abs(actual - ENGLISH_FREQ[i]) * 0.1;
            }
        }

        return result;
    }

    // Generate a random substitution key
    static int[] randomKey(Random random) {
        List<Integer> values = new ArrayList<>();

        for (int i = 0; i < 26; i++) {
            values.add(i);
        }

        Collections.shuffle(values, random);

        int[] key = new int[26];

        for (int i = 0; i < 26; i++) {
            key[i] = values.get(i);
        }

        return key;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("MONOALPHABETIC SUBSTITUTION CIPHER");
        System.out.print("Enter ciphertext: ");
        String cipher = sc.nextLine();

        System.out.print("How many possible plaintexts do you want? ");
        int top = sc.nextInt();

        if (top <= 0) {
            System.out.println("Please enter a positive number.");
            sc.close();
            return;
        }

        int attempts = 100000;
        Random random = new Random();

        Map<String, Double> candidates = new HashMap<>();

        for (int i = 0; i < attempts; i++) {

            int[] key = randomKey(random);
            String plain = decrypt(cipher, key);

            double value = score(plain);

            candidates.put(plain, value);
        }

        List<Map.Entry<String, Double>> results =
                new ArrayList<>(candidates.entrySet());

        results.sort((a, b) ->
                Double.compare(b.getValue(), a.getValue()));

        System.out.println("\nTOP " + top + " POSSIBLE PLAINTEXTS:");

        int limit = Math.min(top, results.size());

        for (int i = 0; i < limit; i++) {
            System.out.println("\n" + (i + 1) + ". "
                    + results.get(i).getKey());
            System.out.println("Score: "
                    + results.get(i).getValue());
        }

        sc.close();
    }
}
 