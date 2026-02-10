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
 * Test class for {@link Parser}.
 *
 * <p>This class contains unit tests for Parser methods to ensure correct
 * parsing of user commands and input strings.</p>
 */
public class ParserTest {

    @Test
    public void testGetCommandWord_todo() {
        String result = Parser.getCommandWord("todo read book");
        assertEquals("todo", result);
    }

    @Test
    public void testGetCommandWord_deadline() {
        String result = Parser.getCommandWord("deadline return book /by 2024-12-31");
        assertEquals("deadline", result);
    }

    @Test
    public void testGetCommandWord_event() {
        String result = Parser.getCommandWord("event meeting /from 2pm /to 4pm");
        assertEquals("event", result);
    }

    @Test
    public void testGetCommandWord_list() {
        String result = Parser.getCommandWord("list");
        assertEquals("list", result);
    }

    @Test
    public void testParseTodoCommand() throws VinuxException {
        Task task = Parser.parseTodoCommand("todo read book");
        assertTrue(task instanceof Todo);
        assertEquals("read book", task.getDescription());
    }

    @Test
    public void testParseTodoCommand_emptyDescription_throwsException() {
        assertThrows(VinuxException.class, () -> {
            Parser.parseTodoCommand("todo");
        });
    }

    @Test
    public void testParseDeadlineCommand() throws VinuxException {
        Task task = Parser.parseDeadlineCommand("deadline return book /by 2024-12-31");
        assertTrue(task instanceof Deadline);
        assertEquals("return book", task.getDescription());
    }

    @Test
    public void testParseDeadlineCommand_missingBy_throwsException() {
        assertThrows(VinuxException.class, () -> {
            Parser.parseDeadlineCommand("deadline return book");
        });
    }

    @Test
    public void testParseDeadlineCommand_invalidDate_throwsException() {
        assertThrows(VinuxException.class, () -> {
            Parser.parseDeadlineCommand("deadline return book /by tomorrow");
        });
    }

    @Test
    public void testParseEventCommand() throws VinuxException {
        Task task = Parser.parseEventCommand("event meeting /from Mon 2pm /to 4pm");
        assertTrue(task instanceof Event);
        assertEquals("meeting", task.getDescription());
    }

    @Test
    public void testParseEventCommand_missingFrom_throwsException() {
        assertThrows(VinuxException.class, () -> {
            Parser.parseEventCommand("event meeting /to 4pm");
        });
    }

    @Test
    public void testParseEventCommand_missingTo_throwsException() {
        assertThrows(VinuxException.class, () -> {
            Parser.parseEventCommand("event meeting /from 2pm");
        });
    }

    @Test
    public void testParseTaskIndex() throws VinuxException {
        int index = Parser.parseTaskIndex("mark 2", 5);
        assertEquals(1, index); // 0-based index
    }

    @Test
    public void testParseTaskIndex_invalidFormat_throwsException() {
        assertThrows(VinuxException.class, () -> {
            Parser.parseTaskIndex("mark abc", 5);
        });
    }
}
