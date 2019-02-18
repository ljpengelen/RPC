package nl.kabisa.rpc.models;

import java.util.List;
import java.util.Objects;

public class Vectors {

    private List<Vector> vectors;

    protected Vectors() {
    }

    public Vectors(List<Vector> vectors) {
        this.vectors = vectors;
    }

    public List<Vector> getVectors() {
        return this.vectors;
    }

    protected void setVectors(List<Vector> vectors) {
        this.vectors = vectors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Vectors vectors1 = (Vectors) o;
        return vectors.equals(vectors1.vectors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vectors);
    }
}
