package structures;

import com.structures.factory.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AlertFactoryTest {

    @Test
    public void testBloodPressureAlertCreation() {
        AlertFactory factory = new BloodPressureAlertFactory();
        Alert alert = factory.createAlert("Patient001", "High Blood Pressure", 1234567890L);
        assertNotNull(alert);
        assertTrue(alert instanceof BloodPressureAlert);
    }

    @Test
    public void testBloodOxygenAlertCreation() {
        AlertFactory factory = new BloodOxygenAlertFactory();
        Alert alert = factory.createAlert("Patient002", "Low Blood Oxygen", 1234567890L);
        assertNotNull(alert);
        assertTrue(alert instanceof BloodOxygenAlert);
    }

    @Test
    public void testECGAlertCreation() {
        AlertFactory factory = new ECGAlertFactory();
        Alert alert = factory.createAlert("Patient003", "Irregular Heartbeat", 1234567890L);
        assertNotNull(alert);
        assertTrue(alert instanceof ECGAlert);
    }

    @Test
    public void testAlertTrigger() {
        AlertFactory factory = new BloodPressureAlertFactory();
        Alert alert = factory.createAlert("Patient001", "High Blood Pressure", 1234567890L);
        assertDoesNotThrow(() -> alert.triggerAlert());
    }
}
