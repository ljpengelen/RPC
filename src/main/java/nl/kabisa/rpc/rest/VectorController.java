package nl.kabisa.rpc.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;
import org.springframework.web.bind.annotation.*;

import nl.kabisa.rpc.VectorGenerator;
import nl.kabisa.rpc.models.Point;
import nl.kabisa.rpc.models.Vector;
import nl.kabisa.rpc.protos.VectorProtos;

@RestController
public class VectorController {

    private final VectorGenerator vectorGenerator;

    @Bean
    ProtobufHttpMessageConverter protobufHttpMessageConverter() {
        return new ProtobufHttpMessageConverter();
    }

    @Autowired
    public VectorController(VectorGenerator vectorGenerator) {
        this.vectorGenerator = vectorGenerator;
    }

    @GetMapping("/json/vectors")
    public List<Vector> getJsonVectors(@RequestParam long seed, @RequestParam int numberOfVectors) {
        return vectorGenerator.generateRandomVectors(seed, numberOfVectors);
    }

    private VectorProtos.Point toProto(Point point) {
        return VectorProtos.Point.newBuilder()
                .setX(point.getX())
                .setY(point.getY())
                .setZ(point.getZ())
                .build();
    }

    private VectorProtos.Vector toProto(Vector vector) {
        return VectorProtos.Vector.newBuilder()
                .setStart(toProto(vector.getStart()))
                .setEnd(toProto(vector.getEnd()))
                .build();
    }

    @GetMapping("/proto/vectors")
    public VectorProtos.Vectors getProtoVectors(@RequestParam long seed, @RequestParam int numberOfVectors) {
        var vectorsBuilder = VectorProtos.Vectors.newBuilder();
        vectorGenerator.generateRandomVectors(seed, numberOfVectors).forEach(vector -> vectorsBuilder.addVectors(toProto(vector)));
        return vectorsBuilder.build();
    }
}