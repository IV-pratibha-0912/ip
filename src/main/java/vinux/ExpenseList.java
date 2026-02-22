package vinux;

import java.util.ArrayList;
import java.util.stream.Collectors;

import vinux.expense.Expense;

/**
 * Represents a list of expenses.
 * Handles operations like adding, deleting, and calculating totals.
 */
public class ExpenseList {
    private ArrayList<Expense> expenses;

    /**
     * Constructs an empty ExpenseList.
     */
    public ExpenseList() {
        this.expenses = new ArrayList<>();
    }

    /**
     * Constructs an ExpenseList with the given list of expenses.
     *
     * @param expenses The list of expenses to initialize with
     */
    public ExpenseList(ArrayList<Expense> expenses) {
        assert expenses != null : "Expenses list should not be null";
        this.expenses = expenses;
    }

    /**
     * Adds an expense to the list.
     *
     * @param expense The expense to add
     */
    public void addExpense(Expense expense) {
        assert expense != null : "Expense to add should not be null";
        expenses.add(expense);
    }

    /**
     * Deletes an expense from the list at the specified index.
     *
     * @param index The index of the expense to delete (0-based)
     * @return The deleted expense
     */
    public Expense deleteExpense(int index) {
        assert index >= 0 : "Index should not be negative: " + index;
        assert index < expenses.size() : "Index should be within list size: " + index;
        return expenses.remove(index);
    }

    /**
     * Returns the number of expenses in the list.
     *
     * @return The size of the expense list
     */
    public int getSize() {
        return expenses.size();
    }

    /**
     * Gets an expense at the specified index.
     *
     * @param index The index of the expense (0-based)
     * @return The expense at the specified index
     */
    public Expense getExpense(int index) {
        assert index >= 0 : "Index should not be negative: " + index;
        assert index < expenses.size() : "Index should be within list size: " + index;
        return expenses.get(index);
    }

    /**
     * Returns all expenses as an ArrayList.
     *
     * @return The list of all expenses
     */
    public ArrayList<Expense> getAllExpenses() {
        return new ArrayList<>(expenses);
    }

    /**
     * Returns a formatted list of all expenses.
     *
     * @return A string showing all expenses with their total
     */
    public String listExpenses() {
        if (expenses.isEmpty()) {
            return "You have no expenses recorded! Lucky you.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Here are your expenses:\n");
        for (int i = 0; i < expenses.size(); i++) {
            sb.append(i + 1).append(". ").append(expenses.get(i)).append("\n");
        }
        sb.append("\n");
        sb.append(String.format("Total spent: $%.2f", getTotalAmount()));
        return sb.toString().trim();
    }

    /**
     * Calculates the total amount of all expenses using streams.
     *
     * @return The total amount spent across all expenses
     */
    public double getTotalAmount() {
        return expenses.stream()
                .mapToDouble(Expense::getAmount)
                .sum();
    }

    /**
     * Calculates the total amount spent in a specific category using streams.
     *
     * @param category The category to filter by
     * @return A formatted string showing the category total
     */
    public String getTotalByCategory(String category) {
        assert category != null : "Category should not be null";

        String lowerCategory = category.toLowerCase();
        double total = expenses.stream()
                .filter(e -> e.getCategory().equals(lowerCategory))
                .mapToDouble(Expense::getAmount)
                .sum();

        long count = expenses.stream()
                .filter(e -> e.getCategory().equals(lowerCategory))
                .count();

        if (count == 0) {
            return "No expenses found in category: " + category;
        }

        return String.format("Total spent on %s: $%.2f (%d expense%s)",
                category, total, count, count == 1 ? "" : "s");
    }

    /**
     * Returns a summary of expenses grouped by category using streams.
     *
     * @return A formatted string showing spending breakdown by category
     */
    public String getCategorySummary() {
        if (expenses.isEmpty()) {
            return "No expenses to summarize!";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Expense summary by category:\n");

        expenses.stream()
                .map(Expense::getCategory)
                .distinct()
                .sorted()
                .forEach(category -> {
                    double total = expenses.stream()
                            .filter(e -> e.getCategory().equals(category))
                            .mapToDouble(Expense::getAmount)
                            .sum();
                    sb.append(String.format("  %s: $%.2f\n",
                            category.toUpperCase(), total));
                });

        sb.append(String.format("\nTotal: $%.2f", getTotalAmount()));
        return sb.toString().trim();
    }
}
