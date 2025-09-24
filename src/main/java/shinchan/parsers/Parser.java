package shinchan.parsers;

import shinchan.commands.AddDeadlineCommand;
import shinchan.commands.AddEventCommand;
import shinchan.commands.AddTodoCommand;
import shinchan.commands.ByeCommand;
import shinchan.commands.Command;
import shinchan.commands.Commands;
import shinchan.commands.DeleteCommand;
import shinchan.commands.EmptyCommand;
import shinchan.commands.ErrorCommand;
import shinchan.commands.FindDateCommand;
import shinchan.commands.FindWordCommand;
import shinchan.commands.ListCommand;
import shinchan.commands.MarkCommand;
import shinchan.commands.UnmarkCommand;
import shinchan.exceptions.IllegalDateFormatException;
import shinchan.exceptions.InvalidItemNumberException;
import shinchan.exceptions.MissingItemNumberException;
import shinchan.exceptions.TaskMissingDateException;
import shinchan.exceptions.TaskNumberOutOfBoundException;

import java.util.Map;

public class Parser {

    private static final String TASK_DESCRIPTION = "description";
    private static final String DEADLINE_BY = "by";
    private static final String EVENT_FROM = "from";
    private static final String EVENT_TO = "to";

    public static Command parse (String input)
            throws IllegalDateFormatException, TaskMissingDateException,
            MissingItemNumberException, TaskNumberOutOfBoundException, InvalidItemNumberException {
        Commands command;
        if (input == null || input.isBlank()) {
            return new EmptyCommand();
        }

        String[] parts = input.trim().split("\\s+", 2);
        try {
            command = Commands.valueOf(parts[0].trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return new ErrorCommand();
        }
        String content = parts.length > 1 ? parts[1].trim() : "";
        Map<String, String> map;
        switch (command) {
        case DELETE:
            return new DeleteCommand(IndexParser.toZeroBased(content));
        case LIST:
            return new ListCommand();
        case MARK:
            return new MarkCommand(IndexParser.toZeroBased(content));
        case UNMARK:
            return new UnmarkCommand(IndexParser.toZeroBased(content));
        case TODO:
            map = FlagTokenizer.tokenize(content);
            return new AddTodoCommand(map.get(TASK_DESCRIPTION));
        case DEADLINE:
            map = FlagTokenizer.tokenize(content);
            return new AddDeadlineCommand(map.get(TASK_DESCRIPTION), DateParser.parse(map.get(DEADLINE_BY)));
        case EVENT:
            map = FlagTokenizer.tokenize(content);
            return new AddEventCommand(map.get(TASK_DESCRIPTION),
                    DateParser.parse(map.get(EVENT_FROM)),
                    DateParser.parse(map.get(EVENT_TO)));
        case FIND:
            return new FindWordCommand(content.trim());
        case DATE:
            return new FindDateCommand(DateParser.parse(content));
        case BYE:
            return new ByeCommand();
        default:
            return new ErrorCommand();
        }
    }
}
