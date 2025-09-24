package shinchan.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IndexParser {
    private static final Pattern TRAILING_INT = Pattern.compile("(\\\\d+)\\\\s*$");

    private IndexParser() {}

    public static int toZeroBased(String input) {
        Matcher matcher = TRAILING_INT.matcher(input == null ? "" : input);
        if (!matcher.find()) {
            return -1;
        }
        return Integer.parseInt(matcher.group(1)) - 1;
    }
}
