package shinchan.parsers;

import shinchan.exceptions.InvalidItemNumberException;
import shinchan.exceptions.MissingItemNumberException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class for parsing item numbers from user input.
 * <p>
 * The {@code IndexParser} extracts a trailing integer from a command string
 * (e.g., "delete 3") and converts it into a zero-based index for internal use.
 */
public class IndexParser {
    private static final Pattern TRAILING_INT = Pattern.compile("([-+]?\\d+)\\s*$");

    private IndexParser() {
        // Prevent instantiation
    }

    /**
     * Extracts a trailing integer from the input string and converts it
     * into a zero-based index.
     * <p>
     * Example:
     * <pre>
     *   "delete 3" → 2
     *   "mark 1"   → 0
     * </pre>
     *
     * @param input the raw user input containing an item number
     * @return the zero-based index corresponding to the provided item number
     * @throws MissingItemNumberException if no number is found in the input
     * @throws InvalidItemNumberException if the number is not a positive integer
     */
    public static int toZeroBased(String input)
            throws MissingItemNumberException, InvalidItemNumberException {
        Matcher matcher = TRAILING_INT.matcher(input == null ? "" : input.trim());
        if (!matcher.find()) {
            throw new MissingItemNumberException("Please include item number!");
        }
        int oneBased = Integer.parseInt(matcher.group(1));
        if (oneBased <= 0) {
            throw new InvalidItemNumberException("Item number must be a non-negative integer!");
        }
        return oneBased - 1;
    }
}
