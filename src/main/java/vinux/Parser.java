package vinux;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import vinux.task.Deadline;
import vinux.task.Event;
import vinux.task.Task;
import vinux.task.Todo;

/**
 * Parses user input and creates appropriate Task objects.
 * Level-8: Handles date parsing for deadline tasks.
 */
public class Parser {
    private static final DateTimeFormatter INPUT_DATE_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Parses the user command and returns the command type.
     *
     * @param fullCommand The full command string from user
     * @return The command word (first word of the command)
     */
    public static String getCommandWord(String fullCommand) {
        return fullCommand.split(" ")[0];
    }

    /**
     * Parses a todo command and creates a Todo task.
     *
     * @param fullCommand The full command string
     * @return A new Todo task
     * @throws VinuxException if the command format is invalid
     */
    public static Task parseTodoCommand(String fullCommand) throws VinuxException {
        if (fullCommand.trim().equals("todo") || fullCommand.substring(4).trim().isEmpty()) {
            throw new VinuxException("Wake up! You are giving me an empty task?\n"
                    + "    Try this: todo buy apples");
        }

        String description = fullCommand.substring(5);
        return new Todo(description);
    }

    /**
     * Parses a deadline command and creates a Deadline task.
     * Level-8: Parses dates in yyyy-MM-dd format and validates them.
     *
     * @param fullCommand The full command string
     * @return A new Deadline task
     * @throws VinuxException if the command format is invalid
     */
    public static Task parseDeadlineCommand(String fullCommand) throws VinuxException {
        if (fullCommand.trim().equals("deadline")) {
            throw new VinuxException("Wake up! When is the deadline??\n"
                    + "    Try: deadline return book /by 2019-12-31");
        }

        String details = fullCommand.substring(9);
        int byIndex = details.indexOf("/by");

        if (byIndex == -1) {
            throw new VinuxException("Uhm, I need to know the deadline.\n"
                    + "    Format: deadline <task> /by <date>\n"
                    + "    Date format: yyyy-MM-dd (e.g., 2019-12-31)");
        }

        String description = details.substring(0, byIndex).trim();
        String dateString = "";
        if (byIndex + 3 < details.length()) {
            dateString = details.substring(byIndex + 3).trim();
        }

        if (description.isEmpty()) {
            throw new VinuxException("Excuse me? What task are you talking about?");
        }

        if (dateString.isEmpty()) {
            throw new VinuxException("Excuse me? When is the deadline?");
        }

        try {
            LocalDate date = LocalDate.parse(dateString, INPUT_DATE_FORMAT);
            return new Deadline(description, date);
        } catch (DateTimeParseException parseException) {
            throw new VinuxException("Wait!!! That does NOT look like a valid date...\n"
                    + "    Use this format: yyyy-MM-dd (e.g., 2019-12-31)");
        }
    }

    /**
     * Parses an event command and creates an Event task.
     *
     * @param fullCommand The full command string
     * @return A new Event task
     * @throws VinuxException if the command format is invalid
     */
    public static Task parseEventCommand(String fullCommand) throws VinuxException {
        if (fullCommand.trim().equals("event") || fullCommand.substring(5).trim().isEmpty()) {
            throw new VinuxException("Wake up! What is the event even?\n"
                    + "    Try: event meeting /from Mon 2pm /to 4pm");
        }

        String details = fullCommand.substring(6);

        if (!details.contains(" /from ")) {
            throw new VinuxException("Excuse me? When does the event start?\n"
                    + "    Format: event <task> /from <start> /to <end>");
        }

        if (!details.contains(" /to ")) {
            throw new VinuxException("Excuse me? When does the event end?\n"
                    + "    Format: event <task> /from <start> /to <end>");
        }

        int fromIndex = details.indexOf(" /from ");
        int toIndex = details.indexOf(" /to ");

        if (fromIndex >= toIndex) {
            throw new VinuxException("Uhm...the /to must come after /from!");
        }

        String description = details.substring(0, fromIndex);
        String from = details.substring(fromIndex + 7, toIndex);
        String to = details.substring(toIndex + 5);

        if (description.trim().isEmpty()) {
            throw new VinuxException("Wake up! What is the event even?");
        }

        if (from.trim().isEmpty()) {
            throw new VinuxException("Excuse me? When does the event start?");
        }

        if (to.trim().isEmpty()) {
            throw new VinuxException("Excuse me? When does the event end?");
        }

        return new Event(description, from, to);
    }

    /**
     * Parses the task index from commands like "mark 1", "delete 2", etc.
     *
     * @param fullCommand The full command string
     * @param commandLength The length of the command word (e.g., "mark " = 5)
     * @return The task index (0-based)
     * @throws VinuxException if the index is invalid
     */
    public static int parseTaskIndex(String fullCommand, int commandLength) throws VinuxException {
        try {
            return Integer.parseInt(fullCommand.substring(commandLength)) - 1;
        } catch (NumberFormatException formatException) {
            throw new VinuxException("Excuse me? Please provide a valid task number.");
        } catch (StringIndexOutOfBoundsException indexException) {
            throw new VinuxException("Excuse me? Tell me which task clearly.");
        }
    }

    /**
     * Parses the find command and returns the keyword.
     *
     * @param fullCommand The full command string
     * @return The keyword to search for
     * @throws VinuxException if no keyword is provided
     */
    public static String parseFindCommand(String fullCommand) throws VinuxException {
        String[] parts = fullCommand.split(" ", 2);
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new VinuxException("OOPS!!! The keyword for find cannot be empty.");
        }
        return parts[1].trim();
    }
}
