package vinux;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class VinuxExceptionTest {

    @Test
    public void testExceptionMessage() {
        VinuxException exception = new VinuxException("Test error");
        assertEquals("Test error", exception.getMessage());
    }

    @Test
    public void testThrowException() {
        VinuxException exception = assertThrows(VinuxException.class, () -> {
            throw new VinuxException("Error occurred");
        });
        assertEquals("Error occurred", exception.getMessage());
    }

    @Test
    public void testEmptyDescriptionException() {
        VinuxException exception = new VinuxException("The description cannot be empty.");
        assertEquals("The description cannot be empty.", exception.getMessage());
    }

    @Test
    public void testInvalidCommandException() {
        VinuxException exception = new VinuxException("Invalid command");
        assertEquals("Invalid command", exception.getMessage());
    }
}