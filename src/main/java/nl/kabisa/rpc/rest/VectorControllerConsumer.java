package nl.kabisa.rpc.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import nl.kabisa.rpc.models.Vectors;
import nl.kabisa.rpc.proto.base.VectorProto;

@Component
public class VectorControllerConsumer {

    private final RestTemplate restTemplate;

    @Autowired
    public VectorControllerConsumer(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void getJsonVectors(String hostname, int port, long seed, int numberOfVectors) {
        String url = String.format("%s:%s/json/vectors?seed={seed}&numberOfVectors={numberOfVectors}", hostname, port);
        restTemplate.getForObject(url, Vectors.class, seed, numberOfVectors);
    }

    public void getProtoVectors(String hostname, int port, long seed, int numberOfVectors) {
        String url = String.format("%s:%s/proto/vectors?seed={seed}&numberOfVectors={numberOfVectors}", hostname, port);
        restTemplate.getForObject(url, VectorProto.Vectors.class, seed, numberOfVectors);
    }
}
