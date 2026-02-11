package vinux;

import java.util.List;
import java.util.Random;

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
                    case "find":
                        handleFind(fullCommand);
                        break;
                    case "cheer":
                        handleCheer();
                        break;
                    default:
                        ui.showError("OOPS!!! I'm sorry, but I don't know what that means :-(\n"
                                + "    Try: todo, deadline, event, list, mark, unmark, delete, or find");
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
     * Handles the find command.
     * Searches for tasks matching the keyword.
     *
     * @param fullCommand The full command string
     * @throws VinuxException if command parsing fails
     */
    private void handleFind(String fullCommand) throws VinuxException {
        String keyword = Parser.parseFindCommand(fullCommand);
        String result = tasks.findTasks(keyword);
        System.out.println(result);
    }

    /**
     * Handles the cheer command.
     * Displays a random cheer quote to motivate the user.
     */
    private void handleCheer() {
        try {
            List<String> quotes = storage.loadCheerQuotes();
            if (quotes.isEmpty()) {
                ui.showMessage("No quotes found!");
                return;
            }
            Random rand = new Random();
            String quote = quotes.get(rand.nextInt(quotes.size()));
            ui.showMessage(quote);
        } catch (VinuxException vinuxException) {
            ui.showError("Oops! Could not load cheer quotes: " + vinuxException.getMessage());
        }
    }

    /**
     * Generates a response for the user's input for the GUI.
     *
     * @param input The user's input string
     * @return The response string to display
     */
    public String getResponse(String input) {
        try {
            String commandWord = Parser.getCommandWord(input);
            switch (commandWord) {
                case "bye":
                    return "Bye. Try not to miss me too much ;)";
                case "list":
                    return getListResponse();
                case "mark":
                    return getMarkResponse(input);
                case "unmark":
                    return getUnmarkResponse(input);
                case "delete":
                    return getDeleteResponse(input);
                case "todo":
                    return getTodoResponse(input);
                case "deadline":
                    return getDeadlineResponse(input);
                case "event":
                    return getEventResponse(input);
                case "find":
                    return getFindResponse(input);
                case "cheer":
                    return getCheerResponse();
                default:
                    return "OOPS!!! I'm sorry, but I don't know what that means :-(\n"
                            + "Try: todo, deadline, event, list, mark, unmark, delete, or find";
            }
        } catch (VinuxException vinuxException) {
            return vinuxException.getMessage();
        }
    }

    /**
     * Returns the welcome message for display in the GUI.
     *
     * @return Welcome message string
     */
    public String getWelcomeMessage() {
        return "Hello! I am your favourite assistant Vinux.\nI'm listening, unfortunately. Go on.";
    }

    private String getListResponse() {
        if (tasks.getSize() == 0) {
            return "You have no tasks! Lucky you.";
        }
        StringBuilder sb = new StringBuilder("Why do you have so many things to do?\n");
        sb.append("These are your tasks:\n");
        for (int i = 0; i < tasks.getSize(); i++) {
            sb.append(i + 1).append(".").append(tasks.getTask(i)).append("\n");
        }
        return sb.toString().trim();
    }

    private String getMarkResponse(String input) throws VinuxException {
        int index = Parser.parseTaskIndex(input, 5);
        if (index < 0 || index >= tasks.getSize()) {
            throw new VinuxException("Sleepy, much? Task number " + (index + 1)
                    + " doesn't exist!\nYou only have " + tasks.getSize() + " task(s).");
        }
        tasks.getTask(index).markAsDone();
        storage.saveTasks(tasks);
        return "Solid! This task is now done (FINALLY!):\n    [X] "
                + tasks.getTask(index).getDescription();
    }

    private String getUnmarkResponse(String input) throws VinuxException {
        int index = Parser.parseTaskIndex(input, 7);
        if (index < 0 || index >= tasks.getSize()) {
            throw new VinuxException("Sleepy, much? Task number " + (index + 1)
                    + " doesn't exist!\nYou only have " + tasks.getSize() + " task(s).");
        }
        tasks.getTask(index).markAsNotDone();
        storage.saveTasks(tasks);
        return "Aw man! This task is still not done:\n    [ ] "
                + tasks.getTask(index).getDescription();
    }

    private String getDeleteResponse(String input) throws VinuxException {
        int index = Parser.parseTaskIndex(input, 7);
        if (index < 0 || index >= tasks.getSize()) {
            throw new VinuxException("Sleepy, much? Task number " + (index + 1)
                    + " doesn't exist!\nYou only have " + tasks.getSize() + " task(s).");
        }
        Task deletedTask = tasks.deleteTask(index);
        storage.saveTasks(tasks);
        return "You sure? I've removed this task:\n" + deletedTask
                + "\nNow you have " + tasks.getSize() + " task(s) in the list.";
    }

    private String getTodoResponse(String input) throws VinuxException {
        Task task = Parser.parseTodoCommand(input);
        tasks.addTask(task);
        storage.saveTasks(tasks);
        return "Gotcha. I have now added this task:\n  " + task
                + "\nNow you have " + tasks.getSize() + " task(s) in the list.";
    }

    private String getDeadlineResponse(String input) throws VinuxException {
        Task task = Parser.parseDeadlineCommand(input);
        tasks.addTask(task);
        storage.saveTasks(tasks);
        return "Gotcha. I have now added this task:\n  " + task
                + "\nNow you have " + tasks.getSize() + " task(s) in the list.";
    }

    private String getEventResponse(String input) throws VinuxException {
        Task task = Parser.parseEventCommand(input);
        tasks.addTask(task);
        storage.saveTasks(tasks);
        return "Gotcha. I have now added this task:\n  " + task
                + "\nNow you have " + tasks.getSize() + " task(s) in the list.";
    }

    private String getFindResponse(String input) throws VinuxException {
        String keyword = Parser.parseFindCommand(input);
        return tasks.findTasks(keyword);
    }

    private String getCheerResponse() {
        try {
            List<String> quotes = storage.loadCheerQuotes();
            if (quotes.isEmpty()) {
                return "No quotes found!";
            }
            Random rand = new Random();
            return quotes.get(rand.nextInt(quotes.size()));
        } catch (VinuxException vinuxException) {
            return "Oops! Could not load cheer quotes: " + vinuxException.getMessage();
        }
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
