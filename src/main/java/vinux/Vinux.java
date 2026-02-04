package vinux;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Vinux is a Personal Assistant Chatbot that helps manage tasks.
 * It supports todos, deadlines, and events with save/load functionality.
 */
public class Vinux {
    /**
     * Enum representing different types of tasks.
     * Each type has a symbol for display purposes.
     */
    enum TaskType {
        TODO("T"),
        DEADLINE("D"),
        EVENT("E");

        private final String symbol;

        TaskType(String symbol) {
            this.symbol = symbol;
        }

        /**
         * Gets the display symbol for this task type.
         *
         * @return The symbol representing this task type
         */
        public String getSymbol() {
            return symbol;
        }
    }

    //Level-7: file path for saving/loading tasks (relative path for cross-platform compatibility)
    private static final String DATA_FILE_PATH = "./data/vinux.txt";
    private static final int MAX_TASKS = 100;

    public static void main(String[] args) {
        //ASCII art logo of the chatbox: VINUX
        String logo = "              ________   __\n"
                + "   |\\      /| \\__  __/  ( (    /| |\\      /| |\\      /|\n"
                + "   | )    ( |    ) (    |  \\  ( | | )    ( | ( \\    / )\n"
                + "   | |    | |    | |    |   \\ | | | |    | |  \\ (__) /\n"
                + "   ( (    ) )    | |    | (\\ \\) | | |    | |   ) __ (\n"
                + "    \\ \\__/ /     | |    | | \\   | | |    | |  / (  ) \\\n"
                + "     \\    /   ___) (___ | )  \\  | | (____) | ( /    \\ )\n"
                + "      \\__/    \\_______/ |/    )_) (________) |/      \\|\n";

        //display welcome message to user
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println(logo);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("Hello! I am your favourite assistant Vinux.");
        System.out.println("I'm listening, unfortunately. Go on.");
        System.out.println("    ____________________________________________________________");

        //Level-2: arrays to store task data (maximum 100 tasks)
        String[] tasks = new String[MAX_TASKS]; //task descriptions
        boolean[] isDone = new boolean[MAX_TASKS]; //completion status
        TaskType[] taskTypes = new TaskType[MAX_TASKS]; //task types (TODO, DEADLINE, EVENT)
        int taskCount = 0; //number of tasks currently stored

        //Level-7: load existing tasks from file when program starts
        taskCount = loadTasks(tasks, isDone, taskTypes);

        //initialize scanner to read user input
        Scanner scanner = new Scanner(System.in);
        String userCommand = ""; //stores user's command

        //main program loop: process commands until user types "bye"
        while (!userCommand.equals("bye")) {
            userCommand = scanner.nextLine();

            //process the command (unless it's "bye" which exits the loop)
            if (!userCommand.equals("bye")) {
                System.out.println("    ____________________________________________________________");

                //handle different commands
                if (userCommand.equals("list")) {
                    listAllTasks(tasks, isDone, taskTypes, taskCount);
                } else if (userCommand.startsWith("mark ")) {
                    taskCount = handleMark(userCommand, tasks, isDone, taskTypes, taskCount);
                    saveTasks(tasks, isDone, taskTypes, taskCount); //Level-7: save after change
                } else if (userCommand.startsWith("unmark ")) {
                    taskCount = handleUnmark(userCommand, tasks, isDone, taskTypes, taskCount);
                    saveTasks(tasks, isDone, taskTypes, taskCount); //Level-7: save after change
                } else if (userCommand.startsWith("delete ")) {
                    taskCount = handleDelete(userCommand, tasks, isDone, taskTypes, taskCount);
                    saveTasks(tasks, isDone, taskTypes, taskCount); //Level-7: save after change
                } else if (userCommand.startsWith("todo")) {
                    taskCount = handleTodo(userCommand, tasks, isDone, taskTypes, taskCount);
                    saveTasks(tasks, isDone, taskTypes, taskCount); //Level-7: save after change
                } else if (userCommand.startsWith("deadline")) {
                    taskCount = handleDeadline(userCommand, tasks, isDone, taskTypes, taskCount);
                    saveTasks(tasks, isDone, taskTypes, taskCount); //Level-7: save after change
                } else if (userCommand.startsWith("event")) {
                    taskCount = handleEvent(userCommand, tasks, isDone, taskTypes, taskCount);
                    saveTasks(tasks, isDone, taskTypes, taskCount); //Level-7: save after change
                } else {
                    //Level-5: handle unknown commands
                    System.out.println("    OOPS!!! I'm sorry, but I don't know what that means :-(");
                    System.out.println("    Try: todo, deadline, event, list, mark, unmark, or delete");
                }

                System.out.println("    ____________________________________________________________");
            }
        }

        //display goodbye message
        System.out.println("Bye. Try not to miss me too much ;)");
        System.out.println("||~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~||");
        System.out.println("||~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~||");

        scanner.close();
    }

    /**
     * Loads tasks from the data file using Vinux's custom format.
     * Format: "TODO ✓ read book" or "DEADLINE ✗ return book by June 6th"
     * Level-7: Handles the case where file doesn't exist by creating it.
     *
     * @param tasks Array to store task descriptions
     * @param isDone Array to store completion status
     * @param taskTypes Array to store task types
     * @return Number of tasks successfully loaded
     */
    private static int loadTasks(String[] tasks, boolean[] isDone, TaskType[] taskTypes) {
        int count = 0;
        File dataDir = new File("./data");
        File dataFile = new File(DATA_FILE_PATH);

        //Level-7 WARNING HANDLING: create directory if it doesn't exist
        if (!dataDir.exists()) {
            dataDir.mkdir();
        }

        //Level-7 WARNING HANDLING: create file if it doesn't exist
        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
                return 0; //new file, no tasks to load
            } catch (IOException ioException) {
                System.out.println("    Error creating data file: " + ioException.getMessage());
                return 0;
            }
        }

        //file exists, proceed to load tasks
        try {
            Scanner fileScanner = new Scanner(dataFile);
            while (fileScanner.hasNextLine() && count < MAX_TASKS) {
                String line = fileScanner.nextLine();

                //skip empty lines
                if (line.trim().isEmpty()) {
                    continue;
                }

                //parse the line: format is "TODO ✓ read book" or "DEADLINE ✗ return book by..."
                //first word is task type
                String[] words = line.split(" ", 3); //split into max 3 parts

                if (words.length >= 3) {
                    //extract task type (TODO, DEADLINE, EVENT)
                    String typeWord = words[0];
                    taskTypes[count] = parseTaskTypeFromWord(typeWord);

                    //extract done status (✓ = done, ✗ = not done)
                    String statusSymbol = words[1];
                    isDone[count] = statusSymbol.equals("✓");

                    //extract task description (everything after status symbol)
                    tasks[count] = words[2];

                    count++;
                }
            }
            fileScanner.close();
        } catch (IOException ioException) {
            System.out.println("    Error loading tasks: " + ioException.getMessage());
        }

        return count;
    }

    /**
     * Saves all current tasks to the data file using Vinux's custom format.
     * Format: "TODO ✓ read book" or "DEADLINE ✗ return book by June 6th"
     * Level-7: Automatically saves whenever task list changes.
     *
     * @param tasks Array of task descriptions
     * @param isDone Array of completion status
     * @param taskTypes Array of task types
     * @param taskCount Number of tasks to save
     */
    private static void saveTasks(String[] tasks, boolean[] isDone,
                                  TaskType[] taskTypes, int taskCount) {
        try {
            //create data directory if it doesn't exist (safety check)
            File directory = new File("./data");
            if (!directory.exists()) {
                directory.mkdir();
            }

            //write tasks to file in Vinux's format: "TODO ✓ read book"
            FileWriter writer = new FileWriter(DATA_FILE_PATH);
            for (int i = 0; i < taskCount; i++) {
                //get task type as full word
                String typeWord = getTaskTypeWord(taskTypes[i]);

                //get status symbol (✓ = done, ✗ = not done)
                String statusSymbol = isDone[i] ? "✓" : "✗";

                //write in format: "TODO ✓ read book"
                writer.write(typeWord + " " + statusSymbol + " " + tasks[i] + "\n");
            }
            writer.close();
        } catch (IOException ioException) {
            System.out.println("    Error saving tasks: " + ioException.getMessage());
        }
    }

    /**
     * Converts a task type word (TODO, DEADLINE, EVENT) to TaskType enum.
     *
     * @param typeWord The word representing the task type
     * @return Corresponding TaskType enum value
     */
    private static TaskType parseTaskTypeFromWord(String typeWord) {
        switch (typeWord) {
            case "TODO":
                return TaskType.TODO;
            case "DEADLINE":
                return TaskType.DEADLINE;
            case "EVENT":
                return TaskType.EVENT;
            default:
                return TaskType.TODO; //default to TODO if unknown
        }
    }

    /**
     * Converts a TaskType enum to its word representation.
     *
     * @param type The TaskType enum
     * @return The word representing the task type (TODO, DEADLINE, or EVENT)
     */
    private static String getTaskTypeWord(TaskType type) {
        switch (type) {
            case TODO:
                return "TODO";
            case DEADLINE:
                return "DEADLINE";
            case EVENT:
                return "EVENT";
            default:
                return "TODO";
        }
    }

    /**
     * Displays all tasks in the task list.
     *
     * @param tasks Array of task descriptions
     * @param isDone Array of completion status
     * @param taskTypes Array of task types
     * @param taskCount Number of tasks to display
     */
    private static void listAllTasks(String[] tasks, boolean[] isDone,
                                     TaskType[] taskTypes, int taskCount) {
        System.out.println("    Why do you have so many things to do?");
        System.out.println("    These are your tasks:");
        for (int i = 0; i < taskCount; i++) {
            String status = isDone[i] ? "[X]" : "[ ]"; //[X] = done, [ ] = not done
            System.out.println("    " + (i + 1) + ".[" + taskTypes[i].getSymbol() + "]"
                    + status + " " + tasks[i]);
        }
    }

    /**
     * Handles the 'mark' command to mark a task as done.
     * Level-5: Includes error handling for invalid input.
     *
     * @param userCommand User's command input
     * @param tasks Array of task descriptions
     * @param isDone Array of completion status
     * @param taskTypes Array of task types
     * @param taskCount Current number of tasks
     * @return Updated task count (unchanged for mark)
     */
    private static int handleMark(String userCommand, String[] tasks, boolean[] isDone,
                                  TaskType[] taskTypes, int taskCount) {
        try {
            //extract task number from command (e.g., "mark 2" -> 2)
            int taskNumber = Integer.parseInt(userCommand.substring(5)) - 1; //convert to 0-indexed

            //validate task number
            if (taskNumber < 0 || taskNumber >= taskCount) {
                System.out.println("    Sleepy, much? Task number " + (taskNumber + 1)
                        + " doesn't exist!");
                System.out.println("    You only have " + taskCount + " task(s) in the list.");
            } else {
                //mark task as done
                isDone[taskNumber] = true;
                System.out.println("    Solid! This task is now done (FINALLY!):");
                System.out.println("        [X] " + tasks[taskNumber]);
            }
        } catch (NumberFormatException formatException) {
            //handle non-numeric userCommand (e.g., "mark abc")
            System.out.println("    Excuse me? Please provide a valid task number.");
            System.out.println("    Try this: mark 1");
        } catch (StringIndexOutOfBoundsException indexException) {
            //handle missing task number (e.g., just "mark")
            System.out.println("    Excuse me? Tell me which task you want to mark clearly.");
            System.out.println("    Try this: mark 1");
        }
        return taskCount;
    }

    /**
     * Handles the 'unmark' command to mark a task as not done.
     * Level-5: Includes error handling for invalid input.
     *
     * @param userCommand User's command input
     * @param tasks Array of task descriptions
     * @param isDone Array of completion status
     * @param taskTypes Array of task types
     * @param taskCount Current number of tasks
     * @return Updated task count (unchanged for unmark)
     */
    private static int handleUnmark(String userCommand, String[] tasks, boolean[] isDone,
                                    TaskType[] taskTypes, int taskCount) {
        try {
            //extract task number from command
            int taskNumber = Integer.parseInt(userCommand.substring(7)) - 1;

            //validate task number
            if (taskNumber < 0 || taskNumber >= taskCount) {
                System.out.println("    Sleepy, much? Task number " + (taskNumber + 1)
                        + " doesn't exist!");
                System.out.println("    You only have " + taskCount + " task(s) in the list.");
            } else {
                //mark task as not done
                isDone[taskNumber] = false;
                System.out.println("    Aw man! This task is still not done:");
                System.out.println("        [ ] " + tasks[taskNumber]);
            }
        } catch (NumberFormatException formatException) {
            System.out.println("    Excuse me? Please provide a valid task number.");
            System.out.println("    Try this: unmark 1");
        } catch (StringIndexOutOfBoundsException indexException) {
            System.out.println("    Excuse me? Tell me which task you want to mark clearly.");
            System.out.println("    Try this: unmark 1");
        }
        return taskCount;
    }

    /**
     * Handles the 'delete' command to remove a task.
     * Level-6: Deletes task and shifts remaining tasks.
     *
     * @param userCommand User's command input
     * @param tasks Array of task descriptions
     * @param isDone Array of completion status
     * @param taskTypes Array of task types
     * @param taskCount Current number of tasks
     * @return Updated task count after deletion
     */
    private static int handleDelete(String userCommand, String[] tasks, boolean[] isDone,
                                    TaskType[] taskTypes, int taskCount) {
        try {
            //extract task number from command
            int taskNumber = Integer.parseInt(userCommand.substring(7)) - 1;

            //validate task number
            if (taskNumber < 0 || taskNumber >= taskCount) {
                System.out.println("    Sleepy, much? Task number " + (taskNumber + 1)
                        + " doesn't exist!");
                System.out.println("    You only have " + taskCount + " task(s) in the list.");
            } else {
                //store task info before deletion (for confirmation message)
                String deletedTask = tasks[taskNumber];
                TaskType deletedType = taskTypes[taskNumber];
                boolean deletedStatus = isDone[taskNumber];
                String status = deletedStatus ? "[X]" : "[ ]";

                //shift all subsequent tasks left to fill the gap
                for (int i = taskNumber; i < taskCount - 1; i++) {
                    tasks[i] = tasks[i + 1];
                    isDone[i] = isDone[i + 1];
                    taskTypes[i] = taskTypes[i + 1];
                }
                taskCount--; //decrease total count

                //display confirmation
                System.out.println("    You sure? I've removed this task:");
                System.out.println("    [" + deletedType.getSymbol() + "]" + status + " "
                        + deletedTask);
                System.out.println("    Now you have " + taskCount + " task(s) in the list.");
            }
        } catch (NumberFormatException formatException) {
            System.out.println("    Excuse me? Please provide a valid task number.");
            System.out.println("    Try this: delete 1");
        } catch (StringIndexOutOfBoundsException indexException) {
            System.out.println("    Excuse me? Tell me which task you want to delete clearly.");
            System.out.println("    Try this: delete 1");
        }
        return taskCount;
    }

    /**
     * Handles the 'todo' command to add a todo task.
     * Level-4: Creates a task without any date/time.
     * Level-5: Includes error handling for empty descriptions.
     *
     * @param userCommand User's command input
     * @param tasks Array of task descriptions
     * @param isDone Array of completion status
     * @param taskTypes Array of task types
     * @param taskCount Current number of tasks
     * @return Updated task count after addition
     */
    private static int handleTodo(String userCommand, String[] tasks, boolean[] isDone,
                                  TaskType[] taskTypes, int taskCount) {
        //Level-5: check for empty description
        if (userCommand.trim().equals("todo") || userCommand.substring(4).trim().isEmpty()) {
            System.out.println("    Wake up! You are giving me an empty task?");
            System.out.println("    Try this: todo buy apples");
        } else {
            //extract task description (everything after "todo ")
            String description = userCommand.substring(5);

            //add task to arrays
            tasks[taskCount] = description;
            taskTypes[taskCount] = TaskType.TODO;
            isDone[taskCount] = false;
            taskCount++;

            //display confirmation
            System.out.println("    Gotcha. I have now added this task:");
            System.out.println("        [T][ ] " + description);
            System.out.println("    Now you have " + taskCount + " task(s) in the list.");
        }
        return taskCount;
    }

    /**
     * Handles the 'deadline' command to add a deadline task.
     * Level-4: Creates a task with a deadline date/time.
     * Level-5: Includes error handling for missing or invalid parts.
     *
     * @param userCommand User's command input
     * @param tasks Array of task descriptions
     * @param isDone Array of completion status
     * @param taskTypes Array of task types
     * @param taskCount Current number of tasks
     * @return Updated task count after addition
     */
    private static int handleDeadline(String userCommand, String[] tasks, boolean[] isDone,
                                      TaskType[] taskTypes, int taskCount) {
        //Level-5: check for empty description
        if (userCommand.trim().equals("deadline") || userCommand.substring(8).trim().isEmpty()) {
            System.out.println("    Wake up! When is the deadline??");
            System.out.println("    Try: deadline return book /by Sunday");
        } else {
            String details = userCommand.substring(9); //everything after "deadline "

            //Level-5: check if "/by" is present
            if (!details.contains(" /by ")) {
                System.out.println("    Uhm, I need to know the deadline.");
                System.out.println("    Format: deadline <task> /by <date>");
            } else {
                try {
                    //split by "/by" to separate description and deadline
                    String[] parts = details.split(" /by ");

                    //Level-5: validate parts
                    if (parts[0].trim().isEmpty()) {
                        System.out.println("    Excuse me? What task are you talking about?");
                    } else if (parts.length < 2 || parts[1].trim().isEmpty()) {
                        System.out.println("    Excuse me? When is the deadline?");
                    } else {
                        String description = parts[0];
                        String by = parts[1];

                        //add task with deadline info in the format: "return book by June 6th"
                        tasks[taskCount] = description + " by " + by;
                        taskTypes[taskCount] = TaskType.DEADLINE;
                        isDone[taskCount] = false;
                        taskCount++;

                        //display confirmation
                        System.out.println("    Gotcha. I have now added this task:");
                        System.out.println("        [D][ ] " + description + " (by: " + by + ")");
                        System.out.println("    Now you have " + taskCount
                                + " task(s) in the list.");
                    }
                } catch (Exception generalException) {
                    System.out.println("    Wait...something went wrong with the deadline...");
                    System.out.println("    Try: deadline return book /by Sunday");
                }
            }
        }
        return taskCount;
    }

    /**
     * Handles the 'event' command to add an event task.
     * Level-4: Creates a task with start and end times.
     * Level-5: Includes error handling for missing or invalid parts.
     *
     * @param userCommand User's command input
     * @param tasks Array of task descriptions
     * @param isDone Array of completion status
     * @param taskTypes Array of task types
     * @param taskCount Current number of tasks
     * @return Updated task count after addition
     */
    private static int handleEvent(String userCommand, String[] tasks, boolean[] isDone,
                                   TaskType[] taskTypes, int taskCount) {
        //Level-5: check for empty description
        if (userCommand.trim().equals("event") || userCommand.substring(5).trim().isEmpty()) {
            System.out.println("    Wake up! What is the event even?");
            System.out.println("    Try: event meeting /from Mon 2pm /to 4pm");
        } else {
            String details = userCommand.substring(6); //everything after "event "

            //Level-5: check if "/from" is present
            if (!details.contains(" /from ")) {
                System.out.println("    Excuse me? When does the event start?");
                System.out.println("    Format: event <task> /from <start> /to <end>");
            } else if (!details.contains(" /to ")) {
                //Level-5: check if "/to" is present
                System.out.println("    Excuse me? When does the event end?");
                System.out.println("    Format: event <task> /from <start> /to <end>");
            } else {
                try {
                    //find positions of "/from" and "/to"
                    int fromIndex = details.indexOf(" /from ");
                    int toIndex = details.indexOf(" /to ");

                    //Level-5: validate order of /from and /to
                    if (fromIndex >= toIndex) {
                        System.out.println("    Uhm...the /to must come after /from!");
                    } else {
                        //extract description, start time, and end time
                        String description = details.substring(0, fromIndex);
                        String from = details.substring(fromIndex + 7, toIndex);
                        String to = details.substring(toIndex + 5);

                        //Level-5: validate each part
                        if (description.trim().isEmpty()) {
                            System.out.println("    Wake up! What is the event even?");
                        } else if (from.trim().isEmpty()) {
                            System.out.println("    Excuse me? When does the event start?");
                        } else if (to.trim().isEmpty()) {
                            System.out.println("    Excuse me? When does the event end?");
                        } else {
                            //add event task in format: "meeting from Aug 6th 2pm to 4pm"
                            tasks[taskCount] = description + " from " + from + " to " + to;
                            taskTypes[taskCount] = TaskType.EVENT;
                            isDone[taskCount] = false;
                            taskCount++;

                            //display confirmation
                            System.out.println("    Gotcha. I have now added this task:");
                            System.out.println("    [E][ ] " + description + " (from: "
                                    + from + " to: " + to + ")");
                            System.out.println("    Now you have " + taskCount
                                    + " task(s) in the list.");
                        }
                    }
                } catch (Exception generalException) {
                    System.out.println("    Wait...something went wrong with the event...");
                    System.out.println("    Try: event meeting /from Mon 2pm /to 4pm");
                }
            }
        }
        return taskCount;
    }
}