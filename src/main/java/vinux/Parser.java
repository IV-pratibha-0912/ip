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

    // Command length constants (command word + 1 space)
    private static final int TODO_PREFIX_LENGTH = 5;         // "todo "
    private static final int DEADLINE_PREFIX_LENGTH = 9;     // "deadline "
    private static final int EVENT_PREFIX_LENGTH = 6;        // "event "
    private static final int FROM_KEYWORD_LENGTH = 7;        // " /from "
    private static final int TO_KEYWORD_LENGTH = 5;          // " /to "
    private static final int BY_KEYWORD_LENGTH = 3;          // "/by"
    private static final int EXPENSE_PREFIX_LENGTH = 8;     // "expense "
    private static final int AMOUNT_KEYWORD_LENGTH = 9;     // " /amount "

    /**
     * Parses the user command and returns the command type.
     *
     * @param fullCommand The full command string from user
     * @return The command word (first word of the command)
     */
    public static String getCommandWord(String fullCommand) {
        assert fullCommand != null : "Command should not be null";
        assert !fullCommand.isEmpty() : "Command should not be empty";
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
        assert fullCommand != null : "Command should not be null";
        assert fullCommand.startsWith("todo") : "Command should start with todo";

        if (fullCommand.trim().equals("todo") || fullCommand.substring(TODO_PREFIX_LENGTH - 1).trim().isEmpty()) {
            throw new VinuxException(
                    "Wake up! You are giving me an empty task?",
                    "Try this: todo buy apples"
            );
        }

        String description = fullCommand.substring(TODO_PREFIX_LENGTH);
        assert !description.trim().isEmpty() : "Description should not be empty after parsing";
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
        assert fullCommand != null : "Command should not be null";
        assert fullCommand.startsWith("deadline") : "Command should start with deadline";

        if (fullCommand.trim().equals("deadline")) {
            throw new VinuxException(
                    "Wake up! When is the deadline??",
                    "Try: deadline return book /by 2019-12-31"
            );
        }

        String details = fullCommand.substring(DEADLINE_PREFIX_LENGTH);
        int byIndex = details.indexOf("/by");

        if (byIndex == -1) {
            throw new VinuxException(
                    "Uhm, I need to know the deadline.",
                    "Format: deadline <task> /by <date>",
                    "Date format: yyyy-MM-dd (e.g., 2019-12-31)"
            );
        }

        String description = details.substring(0, byIndex).trim();
        String dateString = "";
        if (byIndex + BY_KEYWORD_LENGTH < details.length()) {
            dateString = details.substring(byIndex + BY_KEYWORD_LENGTH).trim();
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
            throw new VinuxException(
                    "Wait!!! That does NOT look like a valid date...",
                    "Use this format: yyyy-MM-dd (e.g., 2019-12-31)"
            );
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
        assert fullCommand != null : "Command should not be null";
        assert fullCommand.startsWith("event") : "Command should start with event";

        if (fullCommand.trim().equals("event")
                || fullCommand.substring(EVENT_PREFIX_LENGTH - 1).trim().isEmpty()) {
            throw new VinuxException(
                    "Wake up! What is the event even?",
                    "Format: event <task> /from <start> /to <end>"
            );
        }

        String details = fullCommand.substring(EVENT_PREFIX_LENGTH);

        // Check if description exists BEFORE looking for /from and /to
        // If details starts with /from or /to, there is no description
        if (details.trim().startsWith("/from") || details.trim().startsWith("/to")) {
            throw new VinuxException("Wake up! What is the event even?",
                    "Format: event <task> /from <start> /to <end>");
        }

        int fromIndex = details.indexOf(" /from ");
        int toIndex = details.indexOf(" /to ");

        if (fromIndex == -1 && toIndex == -1) {
            throw new VinuxException("Wake up! What is the event even?",
                    "Format: event <task> /from <start> /to <end>");
        }

        if (fromIndex == -1) {
            throw new VinuxException(
                    "Excuse me? When does the event start?",
                    "Format: event <task> /from <start> /to <end>"
            );
        }

        if (toIndex == -1) {
            throw new VinuxException(
                    "Excuse me? When does the event end?",
                    "Format: event <task> /from <start> /to <end>"
            );
        }

        String description = details.substring(0, fromIndex).trim();

        if (description.isEmpty()) {
            throw new VinuxException("Wake up! What is the event even?",
                    "Format: event <task> /from <start> /to <end>");
        }

        if (fromIndex >= toIndex) {
            throw new VinuxException("Uhm...the /to must come after /from!");
        }

        String from = details.substring(fromIndex + FROM_KEYWORD_LENGTH, toIndex).trim();
        String to = details.substring(toIndex + TO_KEYWORD_LENGTH).trim();

        if (from.isEmpty()) {
            throw new VinuxException("Excuse me? When does the event start?");
        }

        if (to.isEmpty()) {
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
        assert fullCommand != null : "Command should not be null";
        assert commandLength > 0 : "Command length should be positive";

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

    /**
     * Parses an expense command and creates an Expense object.
     * Format: expense <category> <description> /amount <amount>
     *
     * @param fullCommand The full command string
     * @return A new Expense object
     * @throws VinuxException if the command format is invalid
     */
    public static vinux.expense.Expense parseExpenseCommand(String fullCommand) throws VinuxException {
        assert fullCommand != null : "Command should not be null";
        assert fullCommand.startsWith("expense") : "Command should start with expense";

        if (fullCommand.trim().equals("expense")
                || fullCommand.substring(EXPENSE_PREFIX_LENGTH - 1).trim().isEmpty()) {
            throw new VinuxException(
                    "Format: expense <category> <description> /amount <amount>",
                    "Example: expense food chicken rice /amount 4.50"
            );
        }

        String details = fullCommand.substring(EXPENSE_PREFIX_LENGTH);
        int amountIndex = details.indexOf(" /amount ");

        if (amountIndex == -1) {
            throw new VinuxException(
                    "Missing /amount keyword!",
                    "Format: expense <category> <description> /amount <amount>",
                    "Example: expense food chicken rice /amount 4.50"
            );
        }

        String beforeAmount = details.substring(0, amountIndex).trim();
        String[] parts = beforeAmount.split(" ", 2);

        if (parts.length < 2) {
            throw new VinuxException(
                    "Please provide both category and description!",
                    "Format: expense <category> <description> /amount <amount>"
            );
        }

        String category = parts[0];
        String description = parts[1];
        String amountString = details.substring(amountIndex + AMOUNT_KEYWORD_LENGTH).trim();

        if (amountString.isEmpty()) {
            throw new VinuxException("Please provide an amount!");
        }

        double amount;
        try {
            amount = Double.parseDouble(amountString);
            if (amount < 0) {
                throw new VinuxException("Amount cannot be negative!");
            }
        } catch (NumberFormatException e) {
            throw new VinuxException(
                    "Invalid amount: " + amountString,
                    "Please provide a valid number like 4.50"
            );
        }

        return new vinux.expense.Expense(description, amount, category);
    }
}



