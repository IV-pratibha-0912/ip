package vinux;

import java.util.Scanner;

import vinux.task.Task;

/**
 * Handles interactions with the user.
 * Displays messages and reads user input.
 */
public class Ui {
    private static final String LINE = "    ____________________________________________________________";
    private static final String DOUBLE_LINE = "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~";

    private Scanner scanner;

    /**
     * Constructs a Ui object and initializes the scanner.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Displays the welcome message with Vinux logo.
     */
    public void showWelcome() {
        String logo = "              ________   __\n"
                + "   |\\      /| \\__  __/  ( (    /| |\\      /| |\\      /|\n"
                + "   | )    ( |    ) (    |  \\  ( | | )    ( | ( \\    / )\n"
                + "   | |    | |    | |    |   \\ | | | |    | |  \\ (__) /\n"
                + "   ( (    ) )    | |    | (\\ \\) | | |    | |   ) __ (\n"
                + "    \\ \\__/ /     | |    | | \\   | | |    | |  / (  ) \\\n"
                + "     \\    /   ___) (___ | )  \\  | | (____) | ( /    \\ )\n"
                + "      \\__/    \\_______/ |/    )_) (________) |/      \\|\n";

        System.out.println(DOUBLE_LINE);
        System.out.println(DOUBLE_LINE);
        System.out.println(logo);
        System.out.println(DOUBLE_LINE);
        System.out.println(DOUBLE_LINE);
        System.out.println("Hello! I am your favourite assistant Vinux.");
        System.out.println("I'm listening, unfortunately. Go on.");
        showLine();
    }

    /**
     * Displays the goodbye message.
     */
    public void showGoodbye() {
        System.out.println("Bye. Try not to miss me too much ;)");
        System.out.println("||" + DOUBLE_LINE.substring(2) + "||");
        System.out.println("||" + DOUBLE_LINE.substring(2) + "||");
    }

    /**
     * Displays a horizontal line.
     */
    public void showLine() {
        System.out.println(LINE);
    }

    /**
     * Displays an error message.
     *
     * @param message The error message to display
     */
    public void showError(String message) {
        System.out.println("    " + message);
    }

    /**
     * Displays a loading error message.
     *
     * @param message The error message
     */
    public void showLoadingError(String message) {
        System.out.println("    Error loading file: " + message);
    }

    /**
     * Reads a command from the user.
     *
     * @return The command entered by the user
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Displays all tasks in the task list.
     *
     * @param tasks The TaskList to display
     */
    public void showTaskList(TaskList tasks) {
        System.out.println("Why do you have so many things to do?");
        System.out.println("These are your tasks:");
        for (int i = 0; i < tasks.getSize(); i++) {
            System.out.println("    " + (i + 1) + "." + tasks.getTask(i));
        }
    }

    /**
     * Displays a message when a task is added.
     *
     * @param task The task that was added
     * @param taskCount The total number of tasks
     */
    public void showTaskAdded(Task task, int taskCount) {
        showMessages(
                "Gotcha. I have now added this task:",
                "      " + task,
                "Now you have " + taskCount + " task(s) in the list."
        );
    }

    /**
     * Displays a message when a task is marked as done.
     *
     * @param task The task that was marked
     */
    public void showTaskMarked(Task task) {
        showMessages(
                "Solid! This task is now done (FINALLY!):",
                "        [X] " + task.getDescription()
        );
    }

    /**
     * Displays a message when a task is unmarked.
     *
     * @param task The task that was unmarked
     */
    public void showTaskUnmarked(Task task) {
        showMessages(
                "Aw man! This task is still not done:",
                "        [ ] " + task.getDescription()
        );
    }

    /**
     * Displays a message when a task is deleted.
     *
     * @param task The task that was deleted
     * @param taskCount The remaining number of tasks
     */
    public void showTaskDeleted(Task task, int taskCount) {
        showMessages(
                "You sure? I've removed this task:",
                "    " + task,
                "Now you have " + taskCount + " task(s) in the list."
        );
    }

    /**
     * Displays a general message to the user.
     *
     * @param message The message to display
     */
    public void showMessage(String message) {
        System.out.println(message);
    }

    /**
     * Displays multiple messages using varargs.
     *
     * @param messages The messages to display
     */
    public void showMessages(String... messages) {
        for (String message : messages) {
            System.out.println(message);
        }
    }

    /**
     * Closes the scanner.
     */
    public void close() {
        scanner.close();
    }
}
