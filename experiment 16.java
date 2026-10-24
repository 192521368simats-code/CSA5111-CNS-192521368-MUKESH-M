import java.util.*;

public class FrequencyAttack {

    static final String ENGLISH_FREQ = "ETAOINSHRDLCUMWFGYPBVKJXQZ";

    static String decrypt(String cipher, Map<Character, Character> key) {
        StringBuilder result = new StringBuilder();

        for (char ch : cipher.toUpperCase().toCharArray()) {
            if (key.containsKey(ch)) {
                result.append(key.get(ch));
            } else {
                result.append(ch);
            }
        }
        return result.toString();
    }

    static Map<Character, Integer> countFrequency(String text) {
        Map<Character, Integer> freq = new HashMap<>();

        for (char ch : text.toUpperCase().toCharArray()) {
            if (ch >= 'A' && ch <= 'Z') {
                freq.put(ch, freq.getOrDefault(ch, 0) + 1);
            }
        }
        return freq;
    }

    static List<Character> sortByFrequency(Map<Character, Integer> freq) {
        List<Character> letters = new ArrayList<>(freq.keySet());

        letters.sort((a, b) ->
            Integer.compare(freq.get(b), freq.get(a)));

        return letters;
    }

    static void frequencyAttack(String cipher, int topN) {
        Map<Character, Integer> freq = countFrequency(cipher);
        List<Character> cipherLetters = sortByFrequency(freq);

        System.out.println("\nLetter Frequencies:");
        for (char ch : cipherLetters) {
            System.out.println(ch + " : " + freq.get(ch));
        }

        System.out.println("\nTop " + topN +
                           " Possible Plaintexts:");

        for (int shift = 0; shift < topN; shift++) {
            Map<Character, Character> key = new HashMap<>();

            for (int i = 0; i < cipherLetters.size(); i++) {
                char cipherChar = cipherLetters.get(i);
                char plainChar =
                    ENGLISH_FREQ.charAt((i + shift) % 26);

                key.put(cipherChar, plainChar);
            }

            System.out.println((shift + 1) + ". " +
                               decrypt(cipher, key));
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter ciphertext: ");
        String cipher = sc.nextLine();

        System.out.print("How many possible plaintexts? ");
        int topN = sc.nextInt();

        if (topN < 1 || topN > 26) {
            System.out.println("Enter a number from 1 to 26.");
        } else {
            frequencyAttack(cipher, topN);
        }

        sc.close();
    }
}
