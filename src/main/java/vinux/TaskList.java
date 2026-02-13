package vinux;

import java.util.ArrayList;

import vinux.task.Task;

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
     * Finds tasks whose descriptions contain the given keyword.
     *
     * @param keyword The keyword to search for
     * @return A formatted string of matching tasks
     */
    public String findTasks(String keyword) {
        StringBuilder sb = new StringBuilder();
        sb.append("Here are the matching tasks in your list:\n");

        int index = 1;
        boolean foundAny = false;

        for (Task task : tasks) {
            if (task.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                sb.append(index).append(".").append(task).append("\n");
                foundAny = true;
            }
            index++;
        }

        if (!foundAny) {
            sb.append("No matching tasks found.");
        }

        return sb.toString();
    }

    /**
     * Clears all tasks from the list
     */
    public void clearTasks () {
        tasks.clear();
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
