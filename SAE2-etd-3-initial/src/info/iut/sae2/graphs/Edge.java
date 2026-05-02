package info.iut.sae2.graphs;

public class Edge implements IEdge{

    private INode source;
    private INode target;

    public Edge(){
        source = null;
        target = null;
    }

    public Edge(INode source, INode target){
        source = source;
        target = target;
    }

    @Override
    public INode source() {
        return source;
    }

    @Override
    public INode target() {
        return target;
    }

}
