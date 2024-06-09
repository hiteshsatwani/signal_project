package cardio_generator.generators;

import com.cardio_generator.generators.ECGDataGenerator;
import com.cardio_generator.outputs.TestOutputStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ECGDataGeneratorTest {

    private ECGDataGenerator dataGenerator;
    private TestOutputStrategy testOutputStrategy;

    @BeforeEach
    void setUp() {
        dataGenerator = new ECGDataGenerator(5);
        testOutputStrategy = new TestOutputStrategy();
    }

    @Test
    void testGenerate() {
        dataGenerator.generate(1, testOutputStrategy);

        assertFalse(testOutputStrategy.getOutputs().isEmpty());
        assertTrue(testOutputStrategy.getOutputs().stream().anyMatch(output -> output.contains("ECG")));
    }
}