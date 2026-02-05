package vinux.task;

/**
 * Represents an abstract task with a description and completion status
 * This is the base class for all task types (Todo, Deadline, Event)
 */

public abstract class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Constructs a Task with the given description
     * The task is initially not done
     *
     * @param description The description of the task
     */

    public Task (String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the status icon of the task
     *
     * @return "X" if task is done, " " otherwise
     */
    public String getStatusIcon() {
        return isDone ? "X" : " ";
    }

    /**
     * Marks the task as done
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks the task as not done
     */
    public void markAsNotDone() {
        this.isDone = false;
    }

    /**
     * Returns the description of the task.
     *
     * @return Task description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns whether the task is done
     *
     * @return true if done, false otherwise
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * Returns the type icon of the task (to be implemented by subclasses
     *
     * @return Single character representing the task type
     */
    public abstract String getTypeIcon();

    /**
     * Converts the task to a format suitable for saving to file
     *
     * @return String representation for file storage
     */
    public abstract String toFileFormat();

    /**
     * Returns the string representation of the task for display
     *
     * @return Formatted string for display
     */
    @Override
    public String toString() {
        return "[" + getTypeIcon() + "][" + getStatusIcon() + "] " + description;
    }
}
