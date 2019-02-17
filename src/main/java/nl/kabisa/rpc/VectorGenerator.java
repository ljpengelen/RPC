package nl.kabisa.rpc;

import java.util.*;

import org.springframework.stereotype.Service;

import nl.kabisa.rpc.models.Point;
import nl.kabisa.rpc.models.Vector;

@Service
public class VectorGenerator {

    public List<Vector> generateRandomVectors(long seed, int numberOfVectors) {
        var random = new Random(seed);

        var vectors = new ArrayList<Vector>(numberOfVectors);
        for (var i = 0; i < numberOfVectors; ++i) {
            var start = new Point(random.nextDouble(), random.nextDouble(), random.nextDouble());
            var end = new Point(random.nextDouble(), random.nextDouble(), random.nextDouble());
            var vector = new Vector(start, end);

            vectors.add(vector);
        }

        return vectors;
    }
}
