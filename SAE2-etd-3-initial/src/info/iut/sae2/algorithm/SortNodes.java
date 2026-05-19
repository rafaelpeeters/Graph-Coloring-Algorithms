package info.iut.sae2.algorithm;

import info.iut.sae2.graphs.IGraph;
import info.iut.sae2.graphs.INode;
import java.util.Comparator;


/*
*Source (idea & inspiration): stack OverFlow -> "Sort ArrayList of custom Objects by property"
*/
public class SortNodes implements Comparator<INode> {
    
    private IGraph graph; 

    public SortNodes(IGraph g) {
        this.graph = g;
    }

    @Override
    public int compare(INode node1, INode node2) {
        int degree1 = graph.getNeighbors(node1).size();
        int degree2 = graph.getNeighbors(node2).size();
        
        return Integer.compare(degree2, degree1);
    }
}
