package vinux;

import vinux.task.Task;
import vinux.task.Todo;
import vinux.task.Deadline;
import vinux.task.Event;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Handles loading and saving tasks to/from the file system.
 * Level-7: Automatically creates data directory and file if they don't exist.
 */
public class Storage {
    private String filePath;

    /**
     * Constructs a Storage object with the specified file path.
     *
     * @param filePath The path to the data file
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the file.
     * Creates the file and directory if they don't exist.
     *
     * @return ArrayList of tasks loaded from file
     * @throws VinuxException if there's an error loading the file
     */
    public ArrayList<Task> loadTasks() throws VinuxException {
        ArrayList<Task> tasks = new ArrayList<>();
        File dataDir = new File("./data");
        File dataFile = new File(filePath);

        //create directory if it doesn't exist
        if (!dataDir.exists()) {
            dataDir.mkdir();
        }

        //create file if it doesn't exist
        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
                return tasks; //return empty list for new file
            } catch (IOException ioException) {
                throw new VinuxException("Error creating data file: " + ioException.getMessage());
            }
        }

        //load tasks from file
        try {
            Scanner fileScanner = new Scanner(dataFile);
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                if (line.trim().isEmpty()) {
                    continue;
                }
                tasks.add(parseTaskFromFile(line));
            }
            fileScanner.close();
        } catch (IOException ioException) {
            throw new VinuxException("Error loading tasks: " + ioException.getMessage());
        }

        return tasks;
    }

    /**
     * Parses a line from the file and creates the appropriate Task object.
     * Format: "TODO ✓ description" or "DEADLINE ✗ description by 2019-12-31"
     *
     * @param line The line from the file
     * @return The Task object created from the line
     * @throws VinuxException if the line format is invalid
     */
    private Task parseTaskFromFile(String line) throws VinuxException {
        String[] parts = line.split(" ", 3);
        if (parts.length < 3) {
            throw new VinuxException("Invalid file format: " + line);
        }

        String taskType = parts[0];
        boolean isDone = parts[1].equals("✓");
        String details = parts[2];

        Task task;
        switch (taskType) {
            case "TODO":
                task = new Todo(details);
                break;
            case "DEADLINE":
                task = parseDeadline(details);
                break;
            case "EVENT":
                task = parseEvent(details);
                break;
            default:
                throw new VinuxException("Unknown task type: " + taskType);
        }

        if (isDone) {
            task.markAsDone();
        }

        return task;
    }

    /**
     * Parses deadline details from file format.
     * Format: "description by 2019-12-31"
     *
     * @param details The deadline details string
     * @return Deadline task object
     * @throws VinuxException if format is invalid
     */
    private Deadline parseDeadline(String details) throws VinuxException {
        int byIndex = details.indexOf(" by ");
        if (byIndex == -1) {
            throw new VinuxException("Invalid deadline format: " + details);
        }

        String description = details.substring(0, byIndex);
        String dateString = details.substring(byIndex + 4);

        try {
            LocalDate date = LocalDate.parse(dateString);
            return new Deadline(description, date);
        } catch (DateTimeParseException parseException) {
            throw new VinuxException("Invalid date format: " + dateString);
        }
    }

    /**
     * Parses event details from file format.
     * Format: "description from start to end"
     *
     * @param details The event details string
     * @return Event task object
     * @throws VinuxException if format is invalid
     */
    private Event parseEvent(String details) throws VinuxException {
        int fromIndex = details.indexOf(" from ");
        int toIndex = details.indexOf(" to ");

        if (fromIndex == -1 || toIndex == -1) {
            throw new VinuxException("Invalid event format: " + details);
        }

        String description = details.substring(0, fromIndex);
        String from = details.substring(fromIndex + 6, toIndex);
        String to = details.substring(toIndex + 4);

        return new Event(description, from, to);
    }

    /**
     * Saves all tasks to the file.
     *
     * @param tasks The TaskList to save
     * @throws VinuxException if there's an error saving
     */
    public void saveTasks(TaskList tasks) throws VinuxException {
        try {
            File directory = new File("./data");
            if (!directory.exists()) {
                directory.mkdir();
            }

            FileWriter writer = new FileWriter(filePath);
            for (Task task : tasks.getAllTasks()) {
                writer.write(task.toFileFormat() + "\n");
            }
            writer.close();
        } catch (IOException ioException) {
            throw new VinuxException("Error saving tasks: " + ioException.getMessage());
        }
    }

    // A-Cheer: Load cheer quotes
    public List<String> loadCheerQuotes() throws VinuxException {
        try {
            return Files.readAllLines(Paths.get("./data/cheer.txt"));
        } catch (IOException e) {
            throw new VinuxException("Failed to load cheer quotes: " + e.getMessage());
        }
    }

    public String getRandomCheer() throws VinuxException {
        List<String> quotes = loadCheerQuotes();
        if (quotes.isEmpty()) {
            throw new VinuxException("No cheer quotes found in cheer.txt!");
        }
        Random rand = new Random();
        return quotes.get(rand.nextInt(quotes.size()));
    }

}

