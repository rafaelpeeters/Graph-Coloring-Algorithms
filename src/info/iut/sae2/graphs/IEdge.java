package info.iut.sae2.graphs;

/**
 * @author rbourqui
 */
public interface IEdge {
    /**
     * Returns the source of the edge
     * 
     * @return the source of the edge
     */
    public INode source();
    
    /**
     * Returns the target of the edge
     * 
     * @return the target of the edge
     */
    public INode target();

}