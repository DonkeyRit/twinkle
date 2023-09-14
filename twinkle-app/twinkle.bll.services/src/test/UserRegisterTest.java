import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterTest;

public class UserRegisterTest {
    @BeforeClass
    private void setUp() {
        System.out.println("Before");
    }

    @AfterTest
    void tearDown() {
        System.out.println("After");
    }

    @Test
    public void test() {
        System.out.println("Test");
    }
}