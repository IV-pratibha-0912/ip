package vinux;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import vinux.task.Todo;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for {@link Ui}.
 *
 * <p>Tests user interface outputs and formatting.</p>
 */
public class UiTest {

    private Ui ui;
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;

    @BeforeEach
    public void setUp() {
        ui = new Ui();
        outputStream = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    public void testShowWelcome() {
        ui.showWelcome();
        String output = outputStream.toString();
        assertTrue(output.contains("Vinux") || output.contains("Hello"));
    }

    @Test
    public void testShowGoodbye() {
        ui.showGoodbye();
        String output = outputStream.toString();
        assertTrue(output.contains("Bye"));
    }

    @Test
    public void testShowLine() {
        ui.showLine();
        String output = outputStream.toString();
        assertTrue(output.contains("_"));
    }

    @Test
    public void testShowError() {
        ui.showError("Test error message");
        String output = outputStream.toString();
        assertTrue(output.contains("Test error message"));
    }

    @Test
    public void testShowTaskAdded() {
        Todo task = new Todo("test task");
        ui.showTaskAdded(task, 1);
        String output = outputStream.toString();
        assertTrue(output.contains("added"));
        assertTrue(output.contains("test task"));
    }
}