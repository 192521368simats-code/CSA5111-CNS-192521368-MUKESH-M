import java.util.*;

public class Main {

    static char[][] matrix = {
        {'R', 'O', 'Y', 'A', 'L'},
        {'N', 'E', 'W', 'Z', 'D'},
        {'V', 'B', 'C', 'F', 'G'},
        {'H', 'I', 'K', 'M', 'P'},
        {'Q', 'S', 'T', 'U', 'X'}
    };

    static int[] findPosition(char ch) {
        if (ch == 'J')
            ch = 'I';

        for (int r = 0; r < 5; r++) {
            for (int c = 0; c < 5; c++) {
                if (matrix[r][c] == ch)
                    return new int[]{r, c};
            }
        }
        return null;
    }

    static String decrypt(String cipher) {
        cipher = cipher.toUpperCase()
                       .replaceAll("[^A-Z]", "")
                       .replace('J', 'I');

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < cipher.length(); i += 2) {
            char a = cipher.charAt(i);
            char b = cipher.charAt(i + 1);

            int[] p1 = findPosition(a);
            int[] p2 = findPosition(b);

            // Same row: move left
            if (p1[0] == p2[0]) {
                result.append(matrix[p1[0]][(p1[1] + 4) % 5]);
                result.append(matrix[p2[0]][(p2[1] + 4) % 5]);
            }

            // Same column: move up
            else if (p1[1] == p2[1]) {
                result.append(matrix[(p1[0] + 4) % 5][p1[1]]);
                result.append(matrix[(p2[0] + 4) % 5][p2[1]]);
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

        System.out.print("Enter ciphertext: ");
        String cipher = sc.nextLine();

        System.out.println("\nPlayfair Matrix:");
        for (char[] row : matrix)
            System.out.println(Arrays.toString(row));

        String plain = decrypt(cipher);

        System.out.println("\nDecrypted text:");
        System.out.println(plain);
    }
}