import java.util.*;

public class Main {

    static char[][] matrix = {
        {'M', 'F', 'H', 'I', 'K'},
        {'U', 'N', 'O', 'P', 'Q'},
        {'Z', 'V', 'W', 'X', 'Y'},
        {'E', 'L', 'A', 'R', 'G'},
        {'D', 'S', 'T', 'B', 'C'}
    };

    static int[] pos(char ch) {
        if (ch == 'J')
            ch = 'I';

        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; j++)
                if (matrix[i][j] == ch)
                    return new int[]{i, j};

        return null;
    }

    static String prepare(String text) {
        text = text.toUpperCase()
                   .replaceAll("[^A-Z]", "")
                   .replace('J', 'I');

        StringBuilder s = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char a = text.charAt(i);

            if (i + 1 < text.length()) {
                char b = text.charAt(i + 1);

                if (a == b) {
                    s.append(a).append('X');
                } else {
                    s.append(a).append(b);
                    i++;
                }
            } else {
                s.append(a).append('X');
            }
        }

        return s.toString();
    }

    static String encrypt(String text) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < text.length(); i += 2) {
            char a = text.charAt(i);
            char b = text.charAt(i + 1);

            int[] p1 = pos(a);
            int[] p2 = pos(b);

            // Same row
            if (p1[0] == p2[0]) {
                result.append(matrix[p1[0]][(p1[1] + 1) % 5]);
                result.append(matrix[p2[0]][(p2[1] + 1) % 5]);
            }

            // Same column
            else if (p1[1] == p2[1]) {
                result.append(matrix[(p1[0] + 1) % 5][p1[1]]);
                result.append(matrix[(p2[0] + 1) % 5][p2[1]]);
            }

            // Rectangle rule
            else {
                result.append(matrix[p1[0]][p2[1]]);
                result.append(matrix[p2[0]][p1[1]]);
            }
        }

        return result.toString();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter plaintext: ");
        String plaintext = sc.nextLine();

        String prepared = prepare(plaintext);

        System.out.println("Prepared text: " + prepared);
        System.out.println("Encrypted text: " + encrypt(prepared));
    }
}