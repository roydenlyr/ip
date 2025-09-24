package shinchan.commands;

import shinchan.ui.Persona;

public class Parser {
    public static Command parse (String input) {
//        try {
//            if (input == null || input.isBlank()) {
//                return Commands.EMPTY;
//            }
//
//            input = input.trim().toUpperCase().split("\\s", 2)[0];
//            return Commands.valueOf(input);
//        } catch (IllegalArgumentException e) {
//            return Commands.ERROR;
//        }

        Commands command;
        if (input == null || input.isBlank()) {
            command = Commands.EMPTY;
        }

        String[] parts = input.trim().split("\\s+", 2);
        command = Commands.valueOf(parts[0].trim().toUpperCase());
        String content = parts.length > 1 ? parts[1].trim() : "";

        switch (command) {
        case DELETE:
            return new DeleteCommand(IndexParser.toZeroBased(content));
        case LIST:
            return new ListCommand();
        case MARK:
            return new MarkCommand(IndexParser.toZeroBased(content));
        case UNMARK:
            return new UnmarkCommand(IndexParser.toZeroBased(content));
        default:
            Persona.printMessage("Invalid command");
            return null;
        }
    }
}
