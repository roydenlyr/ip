package shinchan.parsers;

import shinchan.exceptions.IllegalDateFormatException;
import shinchan.exceptions.TaskMissingDateException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Utility class for parsing date-time strings into {@link LocalDateTime} objects.
 * <p>
 * The accepted format is:
 * <pre>
 *   yyyy-MM-dd HHmm
 * </pre>
 * Example: {@code 2025-09-23 2359}
 */
public class DateParser {
    /**
     * The date-time format used for parsing user input.
     * Pattern: {@code yyyy-MM-dd HHmm}
     */
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    private DateParser() {
        // Prevent instantiation
    }

    /**
     * Parses the given date string into a {@link LocalDateTime} using the predefined format.
     *
     * @param date the input string to parse
     * @return the corresponding {@link LocalDateTime} object
     * @throws TaskMissingDateException   if the input is {@code null} or blank
     * @throws IllegalDateFormatException if the input does not match the required format
     */
    public static LocalDateTime parse(String date) throws IllegalDateFormatException, TaskMissingDateException {
        if (date == null || date.isBlank()) {
            throw new TaskMissingDateException("Please enter a date!");
        }
        String normalizedDate = date.strip().replaceAll("\\s+", " ").trim();
        try {
            return LocalDateTime.parse(normalizedDate, FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalDateFormatException("Invalid date format! Expected yyyy-MM-dd HHmm, e.g, 2025-09-23 2359.\n" +
                    "Make sure you date is valid: MM(1-12) | dd(1-31) | HH(00-23) | mm(00-59)");
        }
    }
}
