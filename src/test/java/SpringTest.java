import core.AppConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

/**
 * Test spring context.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( classes = { AppConfig.class }, loader = AnnotationConfigContextLoader.class)
public class SpringTest {

    @Test
    public void whenSpringContextIsInstatiated_thenNoExceptions() {

    }
}
