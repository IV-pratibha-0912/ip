package vinux;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import vinux.task.Todo;
import java.io.File;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StorageTest {

    private Storage storage;
    private String testFilePath = "./data/test_vinux.txt";

    @BeforeEach
    public void setUp() {
        storage = new Storage(testFilePath);
    }

    @AfterEach
    public void cleanUp() {
        File file = new File(testFilePath);
        if (file.exists()) {
            file.delete();
        }
        File dir = new File("./data");
        if (dir.exists() && dir.list().length == 0) {
            dir.delete();
        }
    }

    @Test
    public void testSaveAndLoad() throws VinuxException {
        TaskList taskList = new TaskList();
        taskList.addTask(new Todo("test task"));

        storage.saveTasks(taskList);
        TaskList loadedList = new TaskList(storage.loadTasks());

        assertEquals(1, loadedList.getSize());
        assertEquals("test task", loadedList.getTask(0).getDescription());
    }

    @Test
    public void testFileCreation() throws VinuxException {
        TaskList taskList = new TaskList();
        storage.saveTasks(taskList);

        File file = new File(testFilePath);
        assertTrue(file.exists());
    }

    @Test
    public void testLoadTasks_newFile() throws VinuxException {
        TaskList loadedList = new TaskList(storage.loadTasks());
        assertEquals(0, loadedList.getSize());
    }
}