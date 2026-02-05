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
}