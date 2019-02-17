package nl.kabisa.rpc.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;
import org.springframework.web.bind.annotation.*;

import nl.kabisa.rpc.VectorGenerator;
import nl.kabisa.rpc.models.Point;
import nl.kabisa.rpc.models.Vector;
import nl.kabisa.rpc.proto.base.VectorProto;

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

    private VectorProto.Point toProto(Point point) {
        return VectorProto.Point.newBuilder()
                .setX(point.getX())
                .setY(point.getY())
                .setZ(point.getZ())
                .build();
    }

    private VectorProto.Vector toProto(Vector vector) {
        return VectorProto.Vector.newBuilder()
                .setStart(toProto(vector.getStart()))
                .setEnd(toProto(vector.getEnd()))
                .build();
    }

    @GetMapping("/proto/vectors")
    public VectorProto.Vectors getProtoVectors(@RequestParam long seed, @RequestParam int numberOfVectors) {
        var vectorsBuilder = VectorProto.Vectors.newBuilder();
        vectorGenerator.generateRandomVectors(seed, numberOfVectors).forEach(vector -> vectorsBuilder.addVectors(toProto(vector)));
        return vectorsBuilder.build();
    }
}
