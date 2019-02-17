package nl.kabisa.rpc.models;

import java.util.Objects;

public class Vector {

    private final Point start;
    private final Point end;

    public Vector(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Vector vector = (Vector) o;
        return start.equals(vector.start) &&
               end.equals(vector.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }
}
