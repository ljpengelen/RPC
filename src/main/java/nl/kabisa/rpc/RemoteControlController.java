package nl.kabisa.rpc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import nl.kabisa.rpc.proto.VectorsServiceConsumer;
import nl.kabisa.rpc.rest.VectorControllerConsumer;

@RestController
public class RemoteControlController {

    private final VectorControllerConsumer vectorControllerConsumer;
    private final VectorGenerator vectorGenerator;
    private final VectorsServiceConsumer vectorsServiceConsumer;

    @Autowired
    public RemoteControlController(VectorControllerConsumer vectorControllerConsumer, VectorGenerator vectorGenerator, VectorsServiceConsumer vectorsServiceConsumer) {
        this.vectorControllerConsumer = vectorControllerConsumer;
        this.vectorGenerator = vectorGenerator;
        this.vectorsServiceConsumer = vectorsServiceConsumer;
    }

    private static class Statistics {

        private final String hostname;
        private final int port;
        private final int numberOfIterations;
        private final int numberOfVectors;
        private final long duration;

        public Statistics(String hostname, int port, int numberOfIterations, int numberOfVectors, long duration) {
            this.hostname = hostname;
            this.port = port;
            this.numberOfIterations = numberOfIterations;
            this.numberOfVectors = numberOfVectors;
            this.duration = duration;
        }

        public String getHostname() {
            return hostname;
        }

        public int getPort() {
            return port;
        }

        public int getNumberOfIterations() {
            return numberOfIterations;
        }

        public int getNumberOfVectors() {
            return numberOfVectors;
        }

        public long getDuration() {
            return duration;
        }
    }

    @GetMapping("/remote/rest/json/vectors")
    public Statistics getRestJsonVectors(
            @RequestParam String hostname, @RequestParam int port, @RequestParam int numberOfIterations, @RequestParam long seed, @RequestParam int numberOfVectors) {

        var startTime = System.currentTimeMillis();

        for (var i = 0; i < numberOfIterations; ++i) {
            vectorControllerConsumer.getJsonVectors(hostname, port, seed, numberOfVectors);
        }

        var duration = System.currentTimeMillis() - startTime;

        return new Statistics(hostname, port, numberOfIterations, numberOfVectors, duration);
    }

    @GetMapping("/remote/rest/proto/vectors")
    public Statistics getRestProtoVectors(
            @RequestParam String hostname, @RequestParam int port, @RequestParam int numberOfIterations, @RequestParam long seed, @RequestParam int numberOfVectors) {

        var startTime = System.currentTimeMillis();

        for (var i = 0; i < numberOfIterations; ++i) {
            vectorControllerConsumer.getProtoVectors(hostname, port, seed, numberOfVectors);
        }

        var duration = System.currentTimeMillis() - startTime;

        return new Statistics(hostname, port, numberOfIterations, numberOfVectors, duration);
    }

    @GetMapping("/remote/grpc/list/vectors")
    public Statistics getGrpcVectors(
            @RequestParam String hostname, @RequestParam int port, @RequestParam int numberOfIterations, @RequestParam long seed, @RequestParam int numberOfVectors) {

        var startTime = System.currentTimeMillis();

        for (var i = 0; i < numberOfIterations; ++i) {
            vectorsServiceConsumer.getVectors(hostname, port, seed, numberOfVectors);
        }

        var duration = System.currentTimeMillis() - startTime;

        return new Statistics(hostname, port, numberOfIterations, numberOfVectors, duration);
    }

    @GetMapping("/remote/grpc/stream/vectors")
    public Statistics getGrpcVectorStream(
            @RequestParam String hostname, @RequestParam int port, @RequestParam int numberOfIterations, @RequestParam long seed, @RequestParam int numberOfVectors) {

        var startTime = System.currentTimeMillis();

        for (var i = 0; i < numberOfIterations; ++i) {
            vectorsServiceConsumer.getVectorStream(hostname, port, seed, numberOfVectors);
        }

        var duration = System.currentTimeMillis() - startTime;

        return new Statistics(hostname, port, numberOfIterations, numberOfVectors, duration);
    }

    @GetMapping("/local/vectors")
    public Statistics getLocalVectors(@RequestParam int numberOfIterations, @RequestParam long seed, @RequestParam int numberOfVectors) {

        var startTime = System.currentTimeMillis();

        for (var i = 0; i < numberOfIterations; ++i) {
            vectorGenerator.generateRandomVectors(seed, numberOfVectors);
        }

        var duration = System.currentTimeMillis() - startTime;

        return new Statistics(null, 0, numberOfIterations, numberOfVectors, duration);
    }
}
