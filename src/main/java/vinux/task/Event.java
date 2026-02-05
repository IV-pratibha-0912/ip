package vinux.task;

/**
 * Represents an event task with a start time and end time.
 */
public class Event extends Task {
    protected String from;
    protected String to;

    /**
     * Constructs an Event task with the given description, start time, and end time.
     *
     * @param description The description of the event
     * @param from The start time of the event
     * @param to The end time of the event
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns the type icon for event tasks.
     *
     * @return "E" for event
     */
    @Override
    public String getTypeIcon() {
        return "E";
    }

    /**
     * Converts the event task to file storage format.
     * Format: "EVENT ✓ description from start to end"
     *
     * @return String representation for file storage
     */
    @Override
    public String toFileFormat() {
        String statusSymbol = isDone ? "✓" : "✗";
        return "EVENT " + statusSymbol + " " + description + " from " + from + " to " + to;
    }

    /**
     * Returns the string representation of the event for display.
     *
     * @return Formatted string for display
     */
    @Override
    public String toString() {
        return "[" + getTypeIcon() + "][" + getStatusIcon() + "] "
                + description + " (from: " + from + " to: " + to + ")";
    }
}