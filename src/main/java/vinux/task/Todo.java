package vinux.task;

/**
 * Represents a todo task without any date/time attached.
 */
public class Todo extends Task {

    /**
     * Constructs a Todo task with the given description.
     *
     * @param description The description of the todo task
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Returns the type icon for todo tasks.
     *
     * @return "T" for todo
     */
    @Override
    public String getTypeIcon() {
        return "T";
    }

    /**
     * Converts the todo task to file storage format.
     * Format: "TODO ✓ description" or "TODO ✗ description"
     *
     * @return String representation for file storage
     */
    @Override
    public String toFileFormat() {
        String statusSymbol = isDone ? "✓" : "✗";
        return "TODO " + statusSymbol + " " + description;
    }
}
