package vinux.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a deadline task with a specific due date.
 * Level-8: Uses LocalDate for proper date handling.
 */
public class Deadline extends Task {
    private static final DateTimeFormatter OUTPUT_FORMAT =
            DateTimeFormatter.ofPattern("MMM dd yyyy");

    protected LocalDate by;

    /**
     * Constructs a Deadline task with the given description and due date.
     *
     * @param description The description of the deadline task
     * @param by The due date of the task
     */
    public Deadline(String description, LocalDate by) {
        super(description);
        this.by = by;
    }

    /**
     * Returns the type icon for deadline tasks.
     *
     * @return "D" for deadline
     */
    @Override
    public String getTypeIcon() {
        return "D";
    }

    /**
     * Converts the deadline task to file storage format.
     * Format: "DEADLINE ✓ description by 2019-12-31"
     *
     * @return String representation for file storage
     */
    @Override
    public String toFileFormat() {
        String statusSymbol = isDone ? "✓" : "✗";
        return "DEADLINE " + statusSymbol + " " + description + " by " + by;
    }

    /**
     * Returns the string representation of the deadline for display.
     * Shows the date in a human-readable format (e.g., Dec 31 2019).
     *
     * @return Formatted string for display
     */
    @Override
    public String toString() {
        return "[" + getTypeIcon() + "][" + getStatusIcon() + "] "
                + description + " (by: " + by.format(OUTPUT_FORMAT) + ")";
    }
}
