package shinchan.parsers;

import shinchan.exceptions.IllegalDateFormatException;
import shinchan.exceptions.TaskMissingDateException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateParser {
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    private DateParser() {}

    public static LocalDateTime parse(String date) throws IllegalDateFormatException, TaskMissingDateException {
        if (date == null) {
            throw new TaskMissingDateException("Please enter a date!");
        }
        try {
            return LocalDateTime.parse(date.trim(), FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalDateFormatException("Invalid date format! Expected yyyy-MM-dd HHmm, e.g, 2025-09-23 2359.");
        }
    }
}
