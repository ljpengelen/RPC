package nl.kabisa.rpc.proto;

import nl.kabisa.rpc.models.Point;
import nl.kabisa.rpc.models.Vector;
import nl.kabisa.rpc.proto.base.VectorProto;

public class DomainToProto {

    private DomainToProto() {
    }

    public static VectorProto.Point toProto(Point point) {
        return VectorProto.Point.newBuilder()
                .setX(point.getX())
                .setY(point.getY())
                .setZ(point.getZ())
                .build();
    }

    public static VectorProto.Vector toProto(Vector vector) {
        return VectorProto.Vector.newBuilder()
                .setStart(toProto(vector.getStart()))
                .setEnd(toProto(vector.getEnd()))
                .build();
    }
}
