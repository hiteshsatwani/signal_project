package structures;

import com.data_management.PatientRecord;
import com.structures.strategy.AlertStrategy;
import com.structures.strategy.BloodPressureStrategy;
import com.structures.strategy.HeartRateStrategy;
import com.structures.strategy.OxygenSaturationStrategy;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AlertStrategyTest {

    @Test
    public void testBloodPressureStrategy() {
        AlertStrategy strategy = new BloodPressureStrategy();
        PatientRecord record = new PatientRecord(1, 190.0, "bloodPressure", System.currentTimeMillis());
        assertTrue(strategy.checkAlert(record));

        record = new PatientRecord(1, 120.0, "bloodPressure", System.currentTimeMillis());
        assertFalse(strategy.checkAlert(record));
    }

    @Test
    public void testHeartRateStrategy() {
        AlertStrategy strategy = new HeartRateStrategy();
        PatientRecord record = new PatientRecord(1, 30.0, "heartRate", System.currentTimeMillis());
        assertTrue(strategy.checkAlert(record));

        record = new PatientRecord(1, 80.0, "heartRate", System.currentTimeMillis());
        assertFalse(strategy.checkAlert(record));
    }

    @Test
    public void testOxygenSaturationStrategy() {
        AlertStrategy strategy = new OxygenSaturationStrategy();
        PatientRecord record = new PatientRecord(1, 85.0, "oxygenSaturation", System.currentTimeMillis());
        assertTrue(strategy.checkAlert(record));

        record = new PatientRecord(1, 95.0, "oxygenSaturation", System.currentTimeMillis());
        assertFalse(strategy.checkAlert(record));
    }
}
