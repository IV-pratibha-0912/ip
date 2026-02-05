package vinux;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import vinux.task.Todo;
import vinux.task.Task;
import java.time.LocalDate;
import vinux.task.Deadline;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Test class for {@link TaskList}.
 *
 * <p>Tests the management of a list of tasks, including adding, deleting,
 * and marking tasks.</p>
 */
public class TaskListTest {

    private TaskList taskList;

    @BeforeEach
    public void setUp() {
        taskList = new TaskList();
    }

    @Test
    public void testAddTask() {
        Task task = new Todo("read book");
        taskList.addTask(task);

        assertEquals(1, taskList.getSize());
    }

    @Test
    public void testAddMultipleTasks() {
        taskList.addTask(new Todo("task 1"));
        taskList.addTask(new Todo("task 2"));
        taskList.addTask(new Todo("task 3"));

        assertEquals(3, taskList.getSize());
    }

    @Test
    public void testDeleteTask() {
        Task task1 = new Todo("task 1");
        Task task2 = new Todo("task 2");

        taskList.addTask(task1);
        taskList.addTask(task2);

        Task deleted = taskList.deleteTask(0);

        assertEquals(task1, deleted);
        assertEquals(1, taskList.getSize());
    }

    @Test
    public void testDeleteTask_invalidIndex_throwsException() {
        taskList.addTask(new Todo("task"));

        assertThrows(IndexOutOfBoundsException.class, () -> {
            taskList.deleteTask(5);
        });
    }

    @Test
    public void testGetTask() {
        Task task = new Todo("test task");
        taskList.addTask(task);

        assertEquals(task, taskList.getTask(0));
    }

    @Test
    public void testGetTask_invalidIndex_throwsException() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            taskList.getTask(0);
        });
    }

    @Test
    public void testGetSize_emptyList() {
        assertEquals(0, taskList.getSize());
    }

    @Test
    public void testGetSize_withTasks() {
        taskList.addTask(new Todo("task 1"));
        taskList.addTask(new Todo("task 2"));

        assertEquals(2, taskList.getSize());
    }

    @Test
    public void testGetAllTasks() {
        Task task1 = new Todo("task 1");
        Task task2 = new Todo("task 2");

        taskList.addTask(task1);
        taskList.addTask(task2);

        assertEquals(2, taskList.getAllTasks().size());
        assertEquals(task1, taskList.getAllTasks().get(0));
    }
}