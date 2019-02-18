package nl.kabisa.rpc.proto;

import static nl.kabisa.rpc.proto.DomainToProto.toProto;
import static org.junit.Assert.*;

import org.junit.*;
import org.junit.runner.RunWith;
import org.lognet.springboot.grpc.context.LocalRunningGrpcPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import nl.kabisa.rpc.models.Point;
import nl.kabisa.rpc.models.Vector;
import nl.kabisa.rpc.proto.base.VectorProto;
import nl.kabisa.rpc.proto.base.VectorServiceGrpc;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class VectorsServiceIT {

    private ManagedChannel managedChannel;
    private VectorServiceGrpc.VectorServiceBlockingStub blockingStub;

    @LocalRunningGrpcPort
    private int port;

    @Before
    public void setUpStub() {
        managedChannel = ManagedChannelBuilder.forAddress("localhost", port).usePlaintext().build();
        blockingStub = VectorServiceGrpc.newBlockingStub(managedChannel);
    }

    @After
    public void shutDownChannel() {
        managedChannel.shutdown();
    }

    @Test
    public void returns_vectors() {
        var vectorsRequest = VectorProto.VectorsRequest.newBuilder()
                .setNumberOfVectors(1)
                .setSeed(0)
                .build();

        var response = blockingStub.getVectors(vectorsRequest);

        assertTrue(response.hasNext());

        var vector = response.next();
        Point start = new Point(0.730967787376657, 0.24053641567148587, 0.6374174253501083);
        Point end = new Point(0.5504370051176339, 0.5975452777972018, 0.3332183994766498);
        assertEquals(toProto(new Vector(start, end)), vector);

        assertFalse(response.hasNext());
    }
}
