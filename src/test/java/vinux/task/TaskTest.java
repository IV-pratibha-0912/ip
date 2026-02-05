package vinux.task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TaskTest {

    @Test
    public void testTodoCreation() {
        Todo todo = new Todo("read book");
        assertEquals("read book", todo.getDescription());
        assertFalse(todo.isDone());
    }

    /**
     * Tests that marking a task as done changes its status correctly.
     */
    @Test
    public void testMarkAsDone() {
        Todo task = new Todo("return book");
        assertFalse(task.isDone());

        task.markAsDone();
        assertTrue(task.isDone());
    }

    @Test
    public void testMarkAsNotDone() {
        Todo task = new Todo("buy bread");
        task.markAsDone();
        assertTrue(task.isDone());

        task.markAsNotDone();
        assertFalse(task.isDone());
    }

    @Test
    public void testGetStatusIcon_notDone() {
        Todo task = new Todo("test task");
        assertEquals(" ", task.getStatusIcon());
    }

    @Test
    public void testGetStatusIcon_done() {
        Todo task = new Todo("test task");
        task.markAsDone();
        assertEquals("X", task.getStatusIcon());
    }

    @Test
    public void testGetDescription() {
        Todo task = new Todo("test description");
        assertEquals("test description", task.getDescription());
    }
}