package nl.kabisa.rpc.proto;

import static nl.kabisa.rpc.proto.DomainToProto.toProto;

import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

import io.grpc.stub.StreamObserver;
import nl.kabisa.rpc.VectorGenerator;
import nl.kabisa.rpc.proto.base.VectorProto;
import nl.kabisa.rpc.proto.base.VectorServiceGrpc;

@GRpcService
public class VectorsService extends VectorServiceGrpc.VectorServiceImplBase {

    private final VectorGenerator vectorGenerator;

    @Autowired
    public VectorsService(VectorGenerator vectorGenerator) {
        this.vectorGenerator = vectorGenerator;
    }

    @Override
    public void getVectors(VectorProto.VectorsRequest request, StreamObserver<VectorProto.Vector> responseObserver) {
        vectorGenerator.generateRandomVectors(request.getSeed(), request.getNumberOfVectors()).forEach(vector -> responseObserver.onNext(toProto(vector)));
        responseObserver.onCompleted();
    }
}
