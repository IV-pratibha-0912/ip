package vinux;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import vinux.expense.Expense;

/**
 * Handles loading and saving of expenses to/from a file.
 * Expenses are stored in a pipe-separated format.
 */
public class ExpenseStorage {
    private static final String FILE_PATH = "data/expenses.txt";

    /**
     * Loads expenses from the storage file.
     *
     * @return An ArrayList of expenses loaded from the file
     * @throws VinuxException if there is an error reading the file
     */
    public ArrayList<Expense> loadExpenses() throws VinuxException {
        ArrayList<Expense> expenses = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return expenses;
        }

        try {
            List<String> lines = Files.readAllLines(Paths.get(FILE_PATH));
            for (String line : lines) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                try {
                    Expense expense = parseExpenseLine(line);
                    expenses.add(expense);
                } catch (Exception e) {
                    throw new VinuxException("Error parsing expense: " + line);
                }
            }
        } catch (IOException ioException) {
            throw new VinuxException("Error reading expenses file: " + ioException.getMessage());
        }

        return expenses;
    }

    /**
     * Parses a line from the file into an Expense object.
     *
     * @param line The line to parse in format "category | description | amount"
     * @return An Expense object created from the line
     * @throws VinuxException if the line format is invalid
     */
    private Expense parseExpenseLine(String line) throws VinuxException {
        String[] parts = line.split(" \\| ");
        if (parts.length != 3) {
            throw new VinuxException("Invalid expense format: " + line);
        }

        String category = parts[0].trim();
        String description = parts[1].trim();
        double amount;

        try {
            amount = Double.parseDouble(parts[2].trim());
        } catch (NumberFormatException e) {
            throw new VinuxException("Invalid amount in expense: " + line);
        }

        return new Expense(description, amount, category);
    }

    /**
     * Saves the expenses to the storage file.
     *
     * @param expenseList The ExpenseList containing expenses to save
     * @throws VinuxException if there is an error writing to the file
     */
    public void saveExpenses(ExpenseList expenseList) throws VinuxException {
        assert expenseList != null : "ExpenseList should not be null";

        try {
            File file = new File(FILE_PATH);
            file.getParentFile().mkdirs();

            FileWriter writer = new FileWriter(file);
            for (int i = 0; i < expenseList.getSize(); i++) {
                Expense expense = expenseList.getExpense(i);
                writer.write(expense.toFileString() + System.lineSeparator());
            }
            writer.close();
        } catch (IOException ioException) {
            throw new VinuxException("Error saving expenses: " + ioException.getMessage());
        }
    }
}
