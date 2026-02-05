package vinux;

import vinux.task.Task;

/**
 * Vinux is a Personal Assistant Chatbot that helps manage tasks.
 * Level-8: Now with proper date/time handling using LocalDate.
 * A-MoreOOP: Refactored with OOP design using Task, Storage, Ui, Parser classes.
 */
public class Vinux {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    /**
     * Constructs a Vinux chatbot with the specified file path for data storage.
     *
     * @param filePath The path to the data file
     */
    public Vinux(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.loadTasks());
        } catch (VinuxException vinuxException) {
            ui.showLoadingError(vinuxException.getMessage());
            tasks = new TaskList();
        }
    }

    /**
     * Runs the main program loop.
     * Displays welcome message, processes commands, and saves tasks.
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;

        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.showLine();
                String commandWord = Parser.getCommandWord(fullCommand);

                switch (commandWord) {
                    case "bye":
                        isExit = true;
                        break;
                    case "list":
                        handleList();
                        break;
                    case "mark":
                        handleMark(fullCommand);
                        break;
                    case "unmark":
                        handleUnmark(fullCommand);
                        break;
                    case "delete":
                        handleDelete(fullCommand);
                        break;
                    case "todo":
                        handleTodo(fullCommand);
                        break;
                    case "deadline":
                        handleDeadline(fullCommand);
                        break;
                    case "event":
                        handleEvent(fullCommand);
                        break;
                    default:
                        ui.showError("OOPS!!! I'm sorry, but I don't know what that means :-(\n"
                                + "    Try: todo, deadline, event, list, mark, unmark, or delete");
                }

                ui.showLine();
            } catch (VinuxException vinuxException) {
                ui.showLine();
                ui.showError(vinuxException.getMessage());
                ui.showLine();
            }
        }

        ui.showGoodbye();
        ui.close();
    }

    /**
     * Handles the list command.
     * Displays all tasks in the task list.
     */
    private void handleList() {
        ui.showTaskList(tasks);
    }

    /**
     * Handles the mark command.
     * Marks a task as done.
     *
     * @param fullCommand The full command string
     * @throws VinuxException if the task index is invalid
     */
    private void handleMark(String fullCommand) throws VinuxException {
        int index = Parser.parseTaskIndex(fullCommand, 5);

        if (index < 0 || index >= tasks.getSize()) {
            throw new VinuxException("Sleepy, much? Task number " + (index + 1)
                    + " doesn't exist!\n"
                    + "    You only have " + tasks.getSize() + " task(s) in the list.");
        }

        Task task = tasks.getTask(index);
        task.markAsDone();
        ui.showTaskMarked(task);
        storage.saveTasks(tasks);
    }

    /**
     * Handles the unmark command.
     * Marks a task as not done.
     *
     * @param fullCommand The full command string
     * @throws VinuxException if the task index is invalid
     */
    private void handleUnmark(String fullCommand) throws VinuxException {
        int index = Parser.parseTaskIndex(fullCommand, 7);

        if (index < 0 || index >= tasks.getSize()) {
            throw new VinuxException("Sleepy, much? Task number " + (index + 1)
                    + " doesn't exist!\n"
                    + "    You only have " + tasks.getSize() + " task(s) in the list.");
        }

        Task task = tasks.getTask(index);
        task.markAsNotDone();
        ui.showTaskUnmarked(task);
        storage.saveTasks(tasks);
    }

    /**
     * Handles the delete command.
     * Deletes a task from the task list.
     *
     * @param fullCommand The full command string
     * @throws VinuxException if the task index is invalid
     */
    private void handleDelete(String fullCommand) throws VinuxException {
        int index = Parser.parseTaskIndex(fullCommand, 7);

        if (index < 0 || index >= tasks.getSize()) {
            throw new VinuxException("Sleepy, much? Task number " + (index + 1)
                    + " doesn't exist!\n"
                    + "    You only have " + tasks.getSize() + " task(s) in the list.");
        }

        Task deletedTask = tasks.deleteTask(index);
        ui.showTaskDeleted(deletedTask, tasks.getSize());
        storage.saveTasks(tasks);
    }

    /**
     * Handles the todo command.
     * Creates and adds a todo task.
     *
     * @param fullCommand The full command string
     * @throws VinuxException if the command format is invalid
     */
    private void handleTodo(String fullCommand) throws VinuxException {
        Task task = Parser.parseTodoCommand(fullCommand);
        tasks.addTask(task);
        ui.showTaskAdded(task, tasks.getSize());
        storage.saveTasks(tasks);
    }

    /**
     * Handles the deadline command.
     * Creates and adds a deadline task.
     *
     * @param fullCommand The full command string
     * @throws VinuxException if the command format is invalid
     */
    private void handleDeadline(String fullCommand) throws VinuxException {
        Task task = Parser.parseDeadlineCommand(fullCommand);
        tasks.addTask(task);
        ui.showTaskAdded(task, tasks.getSize());
        storage.saveTasks(tasks);
    }

    /**
     * Handles the event command.
     * Creates and adds an event task.
     *
     * @param fullCommand The full command string
     * @throws VinuxException if the command format is invalid
     */
    private void handleEvent(String fullCommand) throws VinuxException {
        Task task = Parser.parseEventCommand(fullCommand);
        tasks.addTask(task);
        ui.showTaskAdded(task, tasks.getSize());
        storage.saveTasks(tasks);
    }

    /**
     * Main entry point of the Vinux application.
     *
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        new Vinux("./data/vinux.txt").run();
    }
}