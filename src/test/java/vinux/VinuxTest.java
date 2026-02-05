package vinux;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import java.io.File;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class VinuxTest {

    @AfterEach
    public void cleanUp() {
        File file = new File("./data/test_vinux.txt");
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void testVinuxCreation() {
        Vinux vinux = new Vinux("./data/test_vinux.txt");
        assertNotNull(vinux);
    }
}