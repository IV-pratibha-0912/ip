package vinux;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for {@link Vinux}.
 *
 * <p>Tests main application behavior and command handling.</p>
 */
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
