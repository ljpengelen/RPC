package nl.kabisa.rpc.proto;

import org.springframework.stereotype.Component;

import io.grpc.ManagedChannelBuilder;
import nl.kabisa.rpc.proto.base.VectorProto;
import nl.kabisa.rpc.proto.base.VectorServiceGrpc;

@Component
public class VectorsServiceConsumer {

    public void getVectors(String hostname, int port, long seed, int numberOfVectors) {
        var managedChannel = ManagedChannelBuilder.forAddress(hostname, port).usePlaintext().build();
        var blockingStub = VectorServiceGrpc.newBlockingStub(managedChannel);
        var vectorsRequest = VectorProto.VectorsRequest.newBuilder()
                .setNumberOfVectors(numberOfVectors)
                .setSeed(seed)
                .build();

        var response = blockingStub.getVectors(vectorsRequest);

        response.getVectorsList();

        managedChannel.shutdown();
    }

    public void getVectorStream(String hostname, int port, long seed, int numberOfVectors) {
        var managedChannel = ManagedChannelBuilder.forAddress(hostname, port).usePlaintext().build();
        var blockingStub = VectorServiceGrpc.newBlockingStub(managedChannel);
        var vectorsRequest = VectorProto.VectorsRequest.newBuilder()
                .setNumberOfVectors(numberOfVectors)
                .setSeed(seed)
                .build();

        var response = blockingStub.getVectorStream(vectorsRequest);

        while (response.hasNext()) {
            response.next();
        }

        managedChannel.shutdown();
    }
}
