import java.util.*;

public class Main {
    public static void main(String[] args) {

        String cipher =
            "53‡‡†305))6*;4826)4‡.)4‡);806*;48†8¶60))85;;]8*;:‡*8†83" +
            "(88)5*†;46(;88*96*?;8)*‡(;485);5*†2:*‡(;4956*2(5*—4)8¶8*" +
            ";4069285);)6†8)4‡‡;1(‡9;48081;8:8‡1;48†85;4)485†528806*81" +
            "(‡9;48;(88;4(‡?34;48)4‡;161;:188;‡?;";

        String symbols =
            "53‡†0)6*;4826.8¶] :‡(5?—;1(9?"; // not used directly

        Map<Character, Character> map = new HashMap<>();

        map.put('5', 'A');
        map.put('3', 'G');
        map.put('‡', 'O');
        map.put('†', 'D');
        map.put('0', 'L');
        map.put(')', 'R');
        map.put('6', 'N');
        map.put('*', 'S');
        map.put(';', 'T');
        map.put('4', 'H');
        map.put('8', 'E');
        map.put('2', 'F');
        map.put(')', 'R');
        map.put('(', 'D');
        map.put('1', 'I');
        map.put('9', 'B');
        map.put(']', 'U');
        map.put(':', 'C');
        map.put('.', 'P');
        map.put('¶', 'V');
        map.put('?', 'Y');
        map.put('—', 'M');

        StringBuilder result = new StringBuilder();

        for (char c : cipher.toCharArray()) {
            result.append(map.getOrDefault(c, c));
        }

        System.out.println("Decrypted text:");
        System.out.println(result);
    }
}