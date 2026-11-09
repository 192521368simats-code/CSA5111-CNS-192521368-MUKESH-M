import java.util.*;

public class Main {

    static char[][] matrix = new char[5][5];

    static void createMatrix(String key) {
        String s = "";

        key = key.toUpperCase().replace("J", "I");

        for (char c : key.toCharArray())
            if (c >= 'A' && c <= 'Z' && s.indexOf(c) == -1)
                s += c;

        for (char c = 'A'; c <= 'Z'; c++) {
            if (c == 'J') continue;
            if (s.indexOf(c) == -1)
                s += c;
        }

        for (int i = 0; i < 25; i++)
            matrix[i / 5][i % 5] = s.charAt(i);
    }

    static int[] position(char c) {
        if (c == 'J') c = 'I';

        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; j++)
                if (matrix[i][j] == c)
                    return new int[]{i, j};

        return null;
    }

    static String prepare(String text) {
        text = text.toUpperCase().replace("J", "I").replaceAll("[^A-Z]", "");
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

            int[] p1 = position(a);
            int[] p2 = position(b);

            if (p1[0] == p2[0]) {
                result.append(matrix[p1[0]][(p1[1] + 1) % 5]);
                result.append(matrix[p2[0]][(p2[1] + 1) % 5]);
            } 
            else if (p1[1] == p2[1]) {
                result.append(matrix[(p1[0] + 1) % 5][p1[1]]);
                result.append(matrix[(p2[0] + 1) % 5][p2[1]]);
            } 
            else {
                result.append(matrix[p1[0]][p2[1]]);
                result.append(matrix[p2[0]][p1[1]]);
            }
        }

        return result.toString();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter keyword: ");
        String key = sc.nextLine();

        System.out.print("Enter plaintext: ");
        String plaintext = sc.nextLine();

        createMatrix(key);

        System.out.println("\nPlayfair Matrix:");
        for (char[] row : matrix)
            System.out.println(Arrays.toString(row));

        String prepared = prepare(plaintext);
        System.out.println("\nPrepared text: " + prepared);
        System.out.println("Encrypted text: " + encrypt(prepared));
    }
}