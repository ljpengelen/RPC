package nl.kabisa.rpc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.List;

import org.junit.Test;

import nl.kabisa.rpc.VectorGenerator;
import nl.kabisa.rpc.models.Vector;

public class VectorGeneratorTest {

    private final VectorGenerator vectorGenerator = new VectorGenerator();

    @Test
    public void generates_given_number_of_vectors() {
        assertEquals(10, vectorGenerator.generateRandomVectors(0, 10).size());
        assertEquals(101, vectorGenerator.generateRandomVectors(0, 101).size());
    }

    @Test
    public void generates_random_vectors() {
        var numberOfVectors = 100;
        List<Vector> vectors = vectorGenerator.generateRandomVectors(0, numberOfVectors);

        for (var i = 0; i < numberOfVectors; ++i) {
            for (var j = i + 1; j < numberOfVectors; ++j) {
                assertNotEquals(vectors.get(i), vectors.get(j));
            }
        }
    }
}
