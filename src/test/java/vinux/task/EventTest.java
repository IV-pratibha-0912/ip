package vinux.task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for {@link Event}.
 *
 * <p>Tests the behavior of Event tasks, including date/time formatting and
 * completion status.</p>
 */
public class EventTest {

    @Test
    public void testEventCreation() {
        Event event = new Event("project meeting", "Mon 2pm", "4pm");
        assertEquals("project meeting", event.getDescription());
    }

    @Test
    public void testGetTypeIcon() {
        Event event = new Event("test", "start", "end");
        assertEquals("E", event.getTypeIcon());
    }

    @Test
    public void testToString_notDone() {
        Event event = new Event("team meeting", "Mon 2pm", "4pm");
        assertEquals("[E][ ] team meeting (from: Mon 2pm to: 4pm)", event.toString());
    }

    @Test
    public void testToString_done() {
        Event event = new Event("conference", "Jan 1st", "Jan 3rd");
        event.markAsDone();
        assertEquals("[E][X] conference (from: Jan 1st to: Jan 3rd)", event.toString());
    }

    @Test
    public void testToFileFormat_notDone() {
        Event event = new Event("meeting", "2pm", "4pm");
        assertEquals("EVENT ✗ meeting from 2pm to 4pm", event.toFileFormat());
    }

    @Test
    public void testToFileFormat_done() {
        Event event = new Event("meeting", "2pm", "4pm");
        event.markAsDone();
        assertEquals("EVENT ✓ meeting from 2pm to 4pm", event.toFileFormat());
    }
}