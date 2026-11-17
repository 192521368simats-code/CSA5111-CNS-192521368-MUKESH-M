import java.util.*;

public class AdditiveCipherAttack {

    static double[] englishFrequency = {
        8.2, 1.5, 2.8, 4.3, 12.7, 2.2,
        2.0, 6.1, 7.0, 0.15, 0.77, 4.0,
        2.4, 6.7, 7.5, 1.9, 0.095, 6.0,
        6.3, 9.1, 2.8, 0.98, 2.4, 0.15,
        2.0, 0.074
    };

    // Decryption
    static String decrypt(String text, int key) {

        StringBuilder result = new StringBuilder();

        for (char ch : text.toCharArray()) {

            if (ch >= 'A' && ch <= 'Z') {

                int p = (ch - 'A' - key + 26) % 26;

                result.append((char)(p + 'A'));

            } else {
                result.append(ch);
            }
        }

        return result.toString();
    }

    // Calculate frequency score
    static double calculateScore(String text) {

        int[] count = new int[26];
        int total = 0;

        for (char ch : text.toCharArray()) {

            if (ch >= 'A' && ch <= 'Z') {
                count[ch - 'A']++;
                total++;
            }
        }

        if (total == 0)
            return Double.MAX_VALUE;

        double score = 0;

        for (int i = 0; i < 26; i++) {

            double actual =
                (count[i] * 100.0) / total;

            score += Math.abs(
                actual - englishFrequency[i]
            );
        }

        return score;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter ciphertext: ");
        String ciphertext = sc.nextLine().toUpperCase();

        System.out.print("Enter number of top results: ");
        int top = sc.nextInt();

        if (top <= 0) {
            System.out.println("Enter a positive number.");
            sc.close();
            return;
        }

        List<String> plaintexts = new ArrayList<>();

        // Try all 26 possible keys
        for (int key = 0; key < 26; key++) {

            String plaintext = decrypt(ciphertext, key);

            plaintexts.add(plaintext);
        }

        // Sort according to frequency score
        plaintexts.sort(
            Comparator.comparingDouble(
                AdditiveCipherAttack::calculateScore
            )
        );

        // Display top possible plaintexts
        System.out.println("\nTop Possible Plaintexts:");

        for (int i = 0;
             i < Math.min(top, plaintexts.size());
             i++) {

            System.out.println(
                (i + 1) + ". " + plaintexts.get(i)
            );
        }

        sc.close();
    }
}
 output:
 Enter ciphertext: yogeshwaran
Enter number of top results: 10

Top Possible Plaintexts:
1. YOGESHWARAN
2. CSKIWLAEVER
3. LBTRFUJNENA
4. KASQETIMDMZ
5. UKCAODSWNWJ
6. OEWUIXMQHQD
7. ZPHFTIXBSBO
8. GWOMAPEIZIV
9. SIAYMBQULUH
10. NDVTHWLPGPC
