package info.iut.sae2.graphs;

import java.util.ArrayList;

public class Graph {

    public ArrayList<IEdge> edgesList;
    public ArrayList<INode> nodesList;
    public Graph() {
        edgesList = new ArrayList<>();
        nodesList = new ArrayList<>();
    }
    
}
