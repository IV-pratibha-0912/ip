package vinux;

/**
 * Represents exceptions specific to the Vinux application.
 * Used for handling errors in a Vinux-appropriate way.
 */
public class VinuxException extends Exception {

    /**
     * Constructs a VinuxException with the specified error message.
     *
     * @param message The error message
     */
    public VinuxException(String message) {
        super(message);
    }

    /**
     * Constructs a VinuxException from multiple message lines using varargs.
     * Lines are joined with newline characters.
     *
     * @param lines The lines of the error message
     */
    public VinuxException(String... lines) {
        super(String.join("\n", lines));
    }
}
