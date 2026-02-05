package vinux;

import vinux.task.Task;
import java.util.ArrayList;

/**
 * Represents a list of tasks.
 * Handles operations like adding, deleting, and retrieving tasks.
 */
public class TaskList {
    private ArrayList<Task> tasks;

    /**
     * Constructs an empty TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Constructs a TaskList with the given list of tasks.
     *
     * @param tasks The list of tasks to initialize with
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a task to the list.
     *
     * @param task The task to add
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Deletes a task from the list at the specified index.
     *
     * @param index The index of the task to delete (0-based)
     * @return The deleted task
     * @throws IndexOutOfBoundsException if index is invalid
     */
    public Task deleteTask(int index) {
        return tasks.remove(index);
    }

    /**
     * Gets a task at the specified index.
     *
     * @param index The index of the task (0-based)
     * @return The task at the specified index
     * @throws IndexOutOfBoundsException if index is invalid
     */
    public Task getTask(int index) {
        return tasks.get(index);
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return The size of the task list
     */
    public int getSize() {
        return tasks.size();
    }

    /**
     * Returns the ArrayList of all tasks.
     *
     * @return The list of all tasks
     */
    public ArrayList<Task> getAllTasks() {
        return tasks;
    }
}