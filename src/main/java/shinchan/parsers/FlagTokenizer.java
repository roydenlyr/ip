package shinchan.parsers;

import java.util.HashMap;
import java.util.Map;

public class FlagTokenizer {
    private FlagTokenizer() {}

    public static Map<String, String> tokenize(String input) {
        Map<String, String> map = new HashMap<>();
        String[] parts = input.split("\\s+/");
        map.put("description", parts.length > 0 ? parts[0].trim() : "");

        for (int i = 1; i < parts.length; i++) {
            String[] keyValue = parts[i].trim().split("\\s+", 2);
            if (keyValue.length == 2) {
                map.put(keyValue[0].toLowerCase(), keyValue[1].trim());
            }
        }
        return map;
    }
}
