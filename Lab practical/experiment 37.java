
import java.util.*;

public class FrequencyAttack {

    static String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    static double[] englishFrequency = {
        8.2, 1.5, 2.8, 4.3, 12.7, 2.2,
        2.0, 6.1, 7.0, 0.15, 0.77, 4.0,
        2.4, 6.7, 7.5, 1.9, 0.095, 6.0,
        6.3, 9.1, 2.8, 0.98, 2.4, 0.15,
        2.0, 0.074
    };

    static String decrypt(String text, int shift) {
        StringBuilder result = new StringBuilder();

        for (char ch : text.toCharArray()) {
            if (Character.isUpperCase(ch)) {
                int value = (ch - 'A' - shift + 26) % 26;
                result.append((char)(value + 'A'));
            } else {
                result.append(ch);
            }
        }

        return result.toString();
    }

    static double calculateScore(String text) {
        int[] count = new int[26];
        int total = 0;

        for (char ch : text.toCharArray()) {
            if (ch >= 'A' && ch <= 'Z') {
                count[ch - 'A']++;
                total++;
            }
        }

        double score = 0;

        if (total == 0)
            return Double.MAX_VALUE;

        for (int i = 0; i < 26; i++) {
            double actual = (count[i] * 100.0) / total;
            score += Math.abs(actual - englishFrequency[i]);
        }

        return score;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter ciphertext: ");
        String cipherText = sc.nextLine().toUpperCase();

        System.out.print("Enter number of possible plaintexts: ");
        int top = sc.nextInt();

        List<String> results = new ArrayList<>();

        for (int i = 0; i < 26; i++) {
            String plain = decrypt(cipherText, i);
            results.add(plain);
        }

        results.sort(
            Comparator.comparingDouble(
                FrequencyAttack::calculateScore
            )
        );

        System.out.println("\nTop " + top +
                           " Possible Plaintexts:");

        for (int i = 0; i < Math.min(top, results.size()); i++) {
            System.out.println((i + 1) + ". " + results.get(i));
        }

        sc.close();
    }
}
