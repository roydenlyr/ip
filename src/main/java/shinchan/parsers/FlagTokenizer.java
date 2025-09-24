package shinchan.parsers;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for tokenizing user input into flags and their values.
 * <p>
 * A flag is indicated by a {@code /} prefix in the input string,
 * followed by a key and its associated value. The description
 * (text before the first flag) is always stored under the key {@code "description"}.
 */
public class FlagTokenizer {
    private FlagTokenizer() {}

    /**
     * Tokenizes the given input string into a map of keys and values.
     * <p>
     * Example:
     * <pre>
     *   "submit report /by 2025-09-30 /priority high"
     *
     *   → {
     *        "description" : "submit report",
     *        "by"          : "2025-09-30",
     *        "priority"    : "high"
     *     }
     * </pre>
     *
     * @param input the raw user input containing a description and optional flags
     * @return a map of flag keys (in lowercase) to their corresponding values,
     *         including {@code "description"}
     */
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
