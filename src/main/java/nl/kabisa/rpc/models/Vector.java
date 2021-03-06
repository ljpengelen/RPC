package nl.kabisa.rpc.models;

import java.util.Objects;

public class Vector {

    private Point start;
    private Point end;

    protected Vector() {
    }

    public Vector(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    public Point getStart() {
        return start;
    }

    protected void setStart(Point start) {
        this.start = start;
    }

    public Point getEnd() {
        return end;
    }

    protected void setEnd(Point end) {
        this.end = end;
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
