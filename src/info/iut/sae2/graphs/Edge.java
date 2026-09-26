package info.iut.sae2.graphs;

import java.util.Objects;

/**
 * A class implementing the IEdge interface.
 *
 * @author Peeters 
 * @author Carpentier
 */
public class Edge implements IEdge{

    private final INode source;
    private final INode target;

    public Edge(){
        source = null;
        target = null;
    }

    public Edge(INode theSource, INode theTarget){
        source = theSource;
        target = theTarget;
    }

    @Override
    public INode source() {
        return source;
    }

    @Override
    public INode target() {
        return target;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.source);
        hash = 67 * hash + Objects.hashCode(this.target);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Edge other = (Edge) obj;
        if (!Objects.equals(this.source, other.source)) {
            return false;
        }
        return Objects.equals(this.target, other.target);
    }
}
