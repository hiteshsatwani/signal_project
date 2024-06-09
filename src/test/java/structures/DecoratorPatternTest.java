package structures;

import com.structures.decorator.Alert;
import com.structures.decorator.BasicAlert;
import com.structures.decorator.PriorityAlertDecorator;
import com.structures.decorator.RepeatedAlertDecorator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DecoratorPatternTest {
    @Test
    public void testBasicAlert() {
        Alert basicAlert = new BasicAlert();
        basicAlert.send("Basic alert test");
        // Capture output for assertion (you might need a custom OutputStream for this)
    }

    @Test
    public void testRepeatedAlertDecorator() {
        Alert basicAlert = new BasicAlert();
        Alert repeatedAlert = new RepeatedAlertDecorator(basicAlert);
        repeatedAlert.send("Repeated alert test");
        // Verify the output, checking it repeats 3 times
    }

    @Test
    public void testPriorityAlertDecorator() {
        Alert basicAlert = new BasicAlert();
        Alert priorityAlert = new PriorityAlertDecorator(basicAlert);
        priorityAlert.send("Priority alert test");
        // Verify the output, checking it adds [PRIORITY] tag
    }

    @Test
    public void testRepeatedPriorityAlertDecorator() {
        Alert basicAlert = new BasicAlert();
        Alert repeatedPriorityAlert = new PriorityAlertDecorator(new RepeatedAlertDecorator(basicAlert));
        repeatedPriorityAlert.send("Repeated priority alert test");
        // Verify the output, checking it repeats 3 times and adds [PRIORITY] tag
    }
}
