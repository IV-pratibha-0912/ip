package vinux.task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for {@link Todo}.
 *
 * <p>Tests the behavior of Todo tasks, including string representation
 * and marking tasks as done.</p>
 */
public class TodoTest {

    @Test
    public void testTodoCreation() {
        Todo todo = new Todo("read book");
        assertEquals("read book", todo.getDescription());
    }

    @Test
    public void testGetTypeIcon() {
        Todo todo = new Todo("test");
        assertEquals("T", todo.getTypeIcon());
    }

    @Test
    public void testToString_notDone() {
        Todo todo = new Todo("borrow book");
        assertEquals("[T][ ] borrow book", todo.toString());
    }

    @Test
    public void testToString_done() {
        Todo todo = new Todo("return book");
        todo.markAsDone();
        assertEquals("[T][X] return book", todo.toString());
    }

    @Test
    public void testToFileFormat_notDone() {
        Todo todo = new Todo("test task");
        assertEquals("TODO ✗ test task", todo.toFileFormat());
    }

    @Test
    public void testToFileFormat_done() {
        Todo todo = new Todo("test task");
        todo.markAsDone();
        assertEquals("TODO ✓ test task", todo.toFileFormat());
    }
}