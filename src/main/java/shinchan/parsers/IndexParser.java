package shinchan.parsers;

import shinchan.exceptions.InvalidItemNumberException;
import shinchan.exceptions.MissingItemNumberException;
import shinchan.exceptions.TaskNumberOutOfBoundException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IndexParser {
    private static final Pattern TRAILING_INT = Pattern.compile("([-+]?\\d+)\\s*$");

    private IndexParser() {}

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
