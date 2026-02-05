package vinux.task;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for {@link Deadline}.
 *
 * <p>Tests the behavior of Deadline tasks, including toString format and
 * completion status.</p>
 */
public class DeadlineTest {

    @Test
    public void testDeadlineCreation() {
        LocalDate date = LocalDate.of(2024, 12, 31);
        Deadline deadline = new Deadline("return book", date);
        assertEquals("return book", deadline.getDescription());
    }

    @Test
    public void testGetTypeIcon() {
        LocalDate date = LocalDate.of(2024, 12, 31);
        Deadline deadline = new Deadline("test", date);
        assertEquals("D", deadline.getTypeIcon());
    }

    @Test
    public void testToString_notDone() {
        LocalDate date = LocalDate.of(2024, 12, 31);
        Deadline deadline = new Deadline("submit report", date);
        assertEquals("[D][ ] submit report (by: Dec 31 2024)", deadline.toString());
    }

    @Test
    public void testToString_done() {
        LocalDate date = LocalDate.of(2024, 1, 15);
        Deadline deadline = new Deadline("return book", date);
        deadline.markAsDone();
        assertEquals("[D][X] return book (by: Jan 15 2024)", deadline.toString());
    }

    @Test
    public void testToFileFormat_notDone() {
        LocalDate date = LocalDate.of(2024, 12, 31);
        Deadline deadline = new Deadline("test task", date);
        assertEquals("DEADLINE ✗ test task by 2024-12-31", deadline.toFileFormat());
    }

    @Test
    public void testToFileFormat_done() {
        LocalDate date = LocalDate.of(2024, 12, 31);
        Deadline deadline = new Deadline("test task", date);
        deadline.markAsDone();
        assertEquals("DEADLINE ✓ test task by 2024-12-31", deadline.toFileFormat());
    }
}