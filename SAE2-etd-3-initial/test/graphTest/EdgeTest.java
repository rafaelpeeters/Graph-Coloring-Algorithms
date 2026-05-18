package graphTest;

import info.iut.sae2.graphs.Graph;
import info.iut.sae2.graphs.Node;
import info.iut.sae2.graphs.Edge;

import org.junit.Test;
import static org.junit.Assert.*;

public class EdgeTest {

    @Test
    public void testConstructor() {
        // default construcotr test
        Edge e1 = new Edge();
        assertNull(e1.source());
        assertNull(e1.target());
        
        // parameter constructor test
        Node n1 = new Node();
        Node n2 = new Node();
        Edge e2 = new Edge(n1, n2);
        assertEquals(n1, e2.source());
        assertEquals(n2, e2.target());
    }

    @Test
    public void testBehaviour() {
        Graph g = new Graph();
        Node n1 = new Node();
        Node n2 = new Node();

        //making sure the edges are equal if they have the same source and target
        Edge e1 = new Edge(n1, n2);
        Edge e2 = new Edge(n1, n2);
        assertEquals(e1, e2);

        //making sure it doesn't add a duplicate in the edge list
        g.addEdge(e1);
        g.addEdge(e2);
        assertEquals(1, g.numberOfEdges());
    }

}