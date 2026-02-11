package vinux;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import vinux.task.Task;
import vinux.task.Todo;

/**
 * Enhanced test class for {@link TaskList}.
 * Tests task list operations including adding, deleting, and searching.
 */
public class TaskListTest {

    private TaskList taskList;

    @BeforeEach
    public void setUp() {
        taskList = new TaskList();
    }

    // ========== POSITIVE TEST CASES ==========

    /**
     * Tests that adding a task increases the list size correctly.
     * POSITIVE CASE: Adding one task should increase size from 0 to 1.
     */
    @Test
    public void addTask_singleTask_increasesSize() {
        Task task = new Todo("read book");
        taskList.addTask(task);
        assertEquals(1, taskList.getSize());
    }

    /**
     * Tests that adding multiple tasks maintains correct size.
     * POSITIVE CASE: Adding 3 tasks should result in size of 3.
     */
    @Test
    public void addTask_multipleTasks_correctSize() {
        taskList.addTask(new Todo("task 1"));
        taskList.addTask(new Todo("task 2"));
        taskList.addTask(new Todo("task 3"));
        assertEquals(3, taskList.getSize());
    }

    /**
     * Tests that deleting a task returns the correct task and reduces size.
     * POSITIVE CASE: Delete first task should return it and reduce size by 1.
     */
    @Test
    public void deleteTask_validIndex_returnsTaskAndReducesSize() {
        Task task1 = new Todo("task 1");
        Task task2 = new Todo("task 2");
        taskList.addTask(task1);
        taskList.addTask(task2);

        Task deleted = taskList.deleteTask(0);

        assertEquals(task1, deleted);
        assertEquals(1, taskList.getSize());
    }

    /**
     * Tests that getting a task returns the correct task object.
     * POSITIVE CASE: Get task at index 0 should return the first added task.
     */
    @Test
    public void getTask_validIndex_returnsCorrectTask() {
        Task task = new Todo("test task");
        taskList.addTask(task);
        assertEquals(task, taskList.getTask(0));
    }

    /**
     * Tests that finding tasks with matching keyword returns results.
     * POSITIVE CASE: Search for "book" should find tasks containing "book".
     */
    @Test
    public void findTasks_matchingKeyword_returnsResults() {
        taskList.addTask(new Todo("read book"));
        taskList.addTask(new Todo("buy groceries"));
        taskList.addTask(new Todo("return book"));

        String result = taskList.findTasks("book");

        assertTrue(result.contains("read book"));
        assertTrue(result.contains("return book"));
    }

    /**
     * Tests that finding tasks is case-insensitive.
     * POSITIVE CASE: Search for "BOOK" should match tasks with "book".
     */
    @Test
    public void findTasks_caseInsensitive_findsMatches() {
        taskList.addTask(new Todo("read book"));

        String result = taskList.findTasks("BOOK");

        assertTrue(result.contains("read book"));
    }

    /**
     * Tests that empty task list has size 0.
     * POSITIVE CASE: New TaskList should be empty.
     */
    @Test
    public void getSize_emptyList_returnsZero() {
        assertEquals(0, taskList.getSize());
    }

    // ========== NEGATIVE TEST CASES ==========

    /**
     * Tests that deleting from invalid index throws IndexOutOfBoundsException.
     * NEGATIVE CASE: Deleting index 5 when list has 1 item should throw exception.
     */
    @Test
    public void deleteTask_invalidIndex_throwsException() {
        taskList.addTask(new Todo("task"));
        assertThrows(IndexOutOfBoundsException.class, () -> {
            taskList.deleteTask(5);
        });
    }

    /**
     * Tests that getting task from empty list throws IndexOutOfBoundsException.
     * NEGATIVE CASE: Getting index 0 from empty list should throw exception.
     */
    @Test
    public void getTask_emptyList_throwsException() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            taskList.getTask(0);
        });
    }

    /**
     * Tests that getting task with negative index throws IndexOutOfBoundsException.
     * NEGATIVE CASE: Negative indices should be rejected.
     */
    @Test
    public void getTask_negativeIndex_throwsException() {
        taskList.addTask(new Todo("task"));
        assertThrows(IndexOutOfBoundsException.class, () -> {
            taskList.getTask(-1);
        });
    }

    /**
     * Tests that finding with non-matching keyword returns appropriate message.
     * NEGATIVE CASE: Search for keyword not in any task should return "No matching tasks".
     */
    @Test
    public void findTasks_noMatches_returnsNoMatchMessage() {
        taskList.addTask(new Todo("read book"));
        taskList.addTask(new Todo("buy groceries"));

        String result = taskList.findTasks("meeting");

        assertTrue(result.contains("No matching tasks"));
    }

    /**
     * Tests that finding in empty list returns appropriate message.
     * NEGATIVE CASE: Searching empty list should return "No matching tasks".
     */
    @Test
    public void findTasks_emptyList_returnsNoMatchMessage() {
        String result = taskList.findTasks("anything");
        assertTrue(result.contains("No matching tasks"));
    }
}
