package vinux;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import vinux.task.Deadline;
import vinux.task.Event;
import vinux.task.Task;
import vinux.task.Todo;

/**
 * Enhanced test class for {@link Parser}.
 * Tests both positive cases (valid input) and negative cases (invalid input).
 */
public class ParserTest {

    // ========== POSITIVE TEST CASES ==========

    /**
     * Tests that a valid todo command creates a Todo task with correct description.
     * POSITIVE CASE: Valid input should create the correct task type.
     */
    @Test
    public void parseTodoCommand_validInput_createsTodoTask() throws VinuxException {
        Task task = Parser.parseTodoCommand("todo read book");
        assertTrue(task instanceof Todo);
        assertEquals("read book", task.getDescription());
    }

    /**
     * Tests that a valid deadline command with proper date format creates a Deadline task.
     * POSITIVE CASE: Valid date format (yyyy-MM-dd) should be accepted.
     */
    @Test
    public void parseDeadlineCommand_validDateFormat_createsDeadlineTask() throws VinuxException {
        Task task = Parser.parseDeadlineCommand("deadline submit report /by 2024-12-31");
        assertTrue(task instanceof Deadline);
        assertEquals("submit report", task.getDescription());
    }

    /**
     * Tests that a valid event command with all components creates an Event task.
     * POSITIVE CASE: Valid event with description, /from, and /to should be accepted.
     */
    @Test
    public void parseEventCommand_validInput_createsEventTask() throws VinuxException {
        Task task = Parser.parseEventCommand("event team meeting /from Mon 2pm /to 4pm");
        assertTrue(task instanceof Event);
        assertEquals("team meeting", task.getDescription());
    }

    /**
     * Tests that task index parsing correctly converts from 1-based to 0-based indexing.
     * POSITIVE CASE: "mark 3" should return index 2 (0-based).
     */
    @Test
    public void parseTaskIndex_validNumber_returnsCorrectZeroBasedIndex() throws VinuxException {
        int index = Parser.parseTaskIndex("mark 3", 5);
        assertEquals(2, index); // 3 - 1 = 2 (0-based)
    }

    /**
     * Tests that find command correctly extracts the search keyword.
     * POSITIVE CASE: "find book" should return "book" as keyword.
     */
    @Test
    public void parseFindCommand_validKeyword_returnsKeyword() throws VinuxException {
        String keyword = Parser.parseFindCommand("find homework");
        assertEquals("homework", keyword);
    }

    // ========== NEGATIVE TEST CASES ==========

    /**
     * Tests that empty todo description throws VinuxException.
     * NEGATIVE CASE: Todo command without description should be rejected.
     */
    @Test
    public void parseTodoCommand_emptyDescription_throwsException() {
        VinuxException exception = assertThrows(VinuxException.class, () -> {
            Parser.parseTodoCommand("todo");
        });
        assertTrue(exception.getMessage().contains("empty"));
    }

    /**
     * Tests that deadline without /by keyword throws VinuxException.
     * NEGATIVE CASE: Missing /by should be caught and rejected.
     */
    @Test
    public void parseDeadlineCommand_missingBy_throwsException() {
        VinuxException exception = assertThrows(VinuxException.class, () -> {
            Parser.parseDeadlineCommand("deadline return book");
        });
        assertTrue(exception.getMessage().contains("deadline"));
    }

    /**
     * Tests that invalid date format in deadline throws VinuxException.
     * NEGATIVE CASE: Date format "tomorrow" should be rejected (only yyyy-MM-dd accepted).
     */
    @Test
    public void parseDeadlineCommand_invalidDateFormat_throwsException() {
        VinuxException exception = assertThrows(VinuxException.class, () -> {
            Parser.parseDeadlineCommand("deadline submit /by tomorrow");
        });
        assertTrue(exception.getMessage().contains("valid date"));
    }

    /**
     * Tests that event without description throws VinuxException.
     * NEGATIVE CASE: Event starting with /from (no description) should be rejected.
     */
    @Test
    public void parseEventCommand_missingDescription_throwsException() {
        VinuxException exception = assertThrows(VinuxException.class, () -> {
            Parser.parseEventCommand("event /from 2pm /to 4pm");
        });
        assertTrue(exception.getMessage().contains("event even"));
    }

    /**
     * Tests that event missing /from keyword throws VinuxException.
     * NEGATIVE CASE: Event without start time should be rejected.
     */
    @Test
    public void parseEventCommand_missingFrom_throwsException() {
        VinuxException exception = assertThrows(VinuxException.class, () -> {
            Parser.parseEventCommand("event meeting /to 4pm");
        });
        assertTrue(exception.getMessage().contains("start"));
    }

    /**
     * Tests that event missing /to keyword throws VinuxException.
     * NEGATIVE CASE: Event without end time should be rejected.
     */
    @Test
    public void parseEventCommand_missingTo_throwsException() {
        VinuxException exception = assertThrows(VinuxException.class, () -> {
            Parser.parseEventCommand("event meeting /from 2pm");
        });
        assertTrue(exception.getMessage().contains("end"));
    }

    /**
     * Tests that invalid task index format throws VinuxException.
     * NEGATIVE CASE: Non-numeric index like "mark abc" should be rejected.
     */
    @Test
    public void parseTaskIndex_invalidFormat_throwsException() {
        VinuxException exception = assertThrows(VinuxException.class, () -> {
            Parser.parseTaskIndex("mark abc", 5);
        });
        assertTrue(exception.getMessage().contains("valid"));
    }

    /**
     * Tests that empty find keyword throws VinuxException.
     * NEGATIVE CASE: Find command without keyword should be rejected.
     */
    @Test
    public void parseFindCommand_emptyKeyword_throwsException() {
        VinuxException exception = assertThrows(VinuxException.class, () -> {
            Parser.parseFindCommand("find");
        });
        assertTrue(exception.getMessage().contains("empty"));
    }
}
