package info.iut.sae2.graphs;

public class Edge implements IEdge{

    private final Node source;
    private final Node target;

    public Edge(Node source, Node target){
        this.source = source;
        this.target = target;
    }

    @Override
    public Node source() {
        return this.source;
    }

    @Override
    public Node target() {
         return this.target;
    }

}
