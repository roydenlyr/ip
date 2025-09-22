package shinchan.commands;

public class Parser {
    public static Commands parse (String input) {
        try {
            if (input == null || input.isBlank()) {
                return Commands.EMPTY;
            }

            input = input.trim().toUpperCase().split("\\s", 2)[0];
            return Commands.valueOf(input);
        } catch (IllegalArgumentException e) {
            return Commands.ERROR;
        }
    }
}
