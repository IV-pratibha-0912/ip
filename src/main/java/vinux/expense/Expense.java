package vinux.expense;

/**
 * Represents an expense with a description, amount, and category.
 * An expense tracks spending in different categories like food, transport, etc.
 */
public class Expense {
    private String description;
    private double amount;
    private String category;

    /**
     * Constructs an Expense with the specified details.
     *
     * @param description A brief description of what was purchased
     * @param amount The amount spent in dollars
     * @param category The category of expense (e.g., food, transport, books)
     */
    public Expense(String description, double amount, String category) {
        assert description != null : "Description should not be null";
        assert amount >= 0 : "Amount should not be negative";
        assert category != null : "Category should not be null";

        this.description = description;
        this.amount = amount;
        this.category = category.toLowerCase();
    }

    /**
     * Returns the description of this expense.
     *
     * @return The expense description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the amount of this expense.
     *
     * @return The expense amount in dollars
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Returns the category of this expense.
     *
     * @return The expense category
     */
    public String getCategory() {
        return category;
    }

    /**
     * Returns a string representation of this expense for display.
     *
     * @return Formatted string showing category, description, and amount
     */
    @Override
    public String toString() {
        return String.format("[%s] %s - $%.2f",
                category.toUpperCase(), description, amount);
    }

    /**
     * Converts this expense to a format suitable for saving to file.
     *
     * @return Pipe-separated string of category, description, and amount
     */
    public String toFileString() {
        return category + " | " + description + " | " + amount;
    }
}
