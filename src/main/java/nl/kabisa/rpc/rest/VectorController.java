package nl.kabisa.rpc.rest;

import static nl.kabisa.rpc.proto.DomainToProto.toProto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import nl.kabisa.rpc.VectorGenerator;
import nl.kabisa.rpc.models.Vectors;
import nl.kabisa.rpc.proto.base.VectorProto;

@RestController
public class VectorController {

    private final VectorGenerator vectorGenerator;

    @Autowired
    public VectorController(VectorGenerator vectorGenerator) {
        this.vectorGenerator = vectorGenerator;
    }

    @GetMapping("/json/vectors")
    public Vectors getJsonVectors(@RequestParam long seed, @RequestParam int numberOfVectors) {
        return new Vectors(vectorGenerator.generateRandomVectors(seed, numberOfVectors));
    }

    @GetMapping("/proto/vectors")
    public VectorProto.Vectors getProtoVectors(@RequestParam long seed, @RequestParam int numberOfVectors) {
        var vectorsBuilder = VectorProto.Vectors.newBuilder();
        vectorGenerator.generateRandomVectors(seed, numberOfVectors).forEach(vector -> vectorsBuilder.addVectors(toProto(vector)));
        return vectorsBuilder.build();
    }
}
