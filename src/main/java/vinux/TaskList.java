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
        assert task != null : "Task to add should not be null";
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
        assert index >= 0 : "Index should not be negative: " + index;
        assert index < tasks.size() : "Index should be within list size: " + index;
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
        assert index >= 0 : "Index should not be negative: " + index;
        assert index < tasks.size() : "Index should be within list size: " + index;
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
     * Finds tasks whose descriptions contain the given keyword using streams.
     *
     * @param keyword The keyword to search for
     * @return A formatted string of matching tasks
     */
    public String findTasks(String keyword) {
        assert keyword != null : "Keyword should not be null";

        StringBuilder sb = new StringBuilder();
        sb.append("Here are the matching tasks in your list:\n");

        long matchCount = tasks.stream()
                .filter(task -> task.getDescription().toLowerCase()
                        .contains(keyword.toLowerCase()))
                .count();

        if (matchCount == 0) {
            return "No matching tasks found.";
        }

        // Build numbered list of matching tasks using streams
        int[] index = {1};
        tasks.stream()
                .filter(task -> task.getDescription().toLowerCase()
                        .contains(keyword.toLowerCase()))
                .forEach(task -> {
                    sb.append(index[0]).append(".").append(task).append("\n");
                    index[0]++;
                });

        return sb.toString().trim();
    }

    /**
     * Clears all tasks from the list
     */
    public void clearTasks () {
        tasks.clear();
    }


    /**
     * Returns all tasks as an unmodifiable list using streams.
     *
     * @return The list of all tasks
     */
    public ArrayList<Task> getAllTasks() {
        return tasks.stream().collect(java.util.stream.Collectors.toCollection(ArrayList::new));
    }

    /**
     * Returns a summary of tasks by type using streams.
     *
     * @return A formatted string showing task type counts
     */
    public String getTaskSummary() {
        long todoCount = tasks.stream()
                .filter(task -> task.getTypeIcon().equals("T"))
                .count();

        long deadlineCount = tasks.stream()
                .filter(task -> task.getTypeIcon().equals("D"))
                .count();

        long eventCount = tasks.stream()
                .filter(task -> task.getTypeIcon().equals("E"))
                .count();

        long doneCount = tasks.stream()
                .filter(Task::isDone)
                .count();

        return "Task summary:\n"
                + "  Todos: " + todoCount + "\n"
                + "  Deadlines: " + deadlineCount + "\n"
                + "  Events: " + eventCount + "\n"
                + "  Completed: " + doneCount + "/" + tasks.size();
    }
}
