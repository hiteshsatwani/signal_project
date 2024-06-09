package cardio_generator.generators;

import com.cardio_generator.generators.BloodLevelsDataGenerator;
import com.cardio_generator.outputs.TestOutputStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BloodLevelsDataGeneratorTest {

    private BloodLevelsDataGenerator dataGenerator;
    private TestOutputStrategy testOutputStrategy;

    @BeforeEach
    void setUp() {
        dataGenerator = new BloodLevelsDataGenerator(5);
        testOutputStrategy = new TestOutputStrategy();
    }

    @Test
    void testGenerate() {
        dataGenerator.generate(1, testOutputStrategy);

        assertFalse(testOutputStrategy.getOutputs().isEmpty());
        assertTrue(testOutputStrategy.getOutputs().stream().anyMatch(output -> output.contains("Cholesterol")));
        assertTrue(testOutputStrategy.getOutputs().stream().anyMatch(output -> output.contains("WhiteBloodCells")));
        assertTrue(testOutputStrategy.getOutputs().stream().anyMatch(output -> output.contains("RedBloodCells")));
    }
}