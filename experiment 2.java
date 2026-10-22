import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter text: ");
        String text = sc.nextLine();

        System.out.print("Enter key: ");
        String key = sc.nextLine();

        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder result = new StringBuilder();

        for (char ch : text.toUpperCase().toCharArray()) {
            int index = alphabet.indexOf(ch);

            if (index != -1)
                result.append(key.toUpperCase().charAt(index));
            else
                result.append(ch);
        }

        System.out.println("Encrypted text: " + result);
    }
}