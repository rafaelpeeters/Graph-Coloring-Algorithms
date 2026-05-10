package graphTest;

import info.iut.sae2.graphs.Graph;
import info.iut.sae2.graphs.IGraph;
import info.iut.sae2.graphs.Node; 
import info.iut.sae2.graphs.INode; 
import info.iut.sae2.graphs.IEdge;
import info.iut.sae2.graphs.Edge;
import info.iut.sae2.graphs.Coord;

import org.junit.Test;
import static org.junit.Assert.*;


public class GraphTest {

    @Test 
    public void testConstructors(){
        IGraph g = new Graph();
        assertEquals(0, g.numberOfNodes());
        assertEquals(0, g.numberOfEdges());
        
        assertNotNull(g.getSizes());
        assertNotNull(g.getLayout());
        assertNotNull(g.getColor());
    }
    
    @Test
    public void testCopy() {
        IGraph g = new Graph();
        INode n = g.addNode();
        IGraph cg = g.copy();
        assertEquals(1, cg.numberOfNodes());
        assertTrue(cg.getNodes().contains(n));
    }
    
    @Test
    public void testAddAndDelNode() {
        IGraph g = new Graph();
        INode n1 = g.addNode();
        
        assertEquals(1, g.numberOfNodes());
        assertTrue(g.getNodes().contains(n1));
        
        INode n2 = new Node();
        g.addNode(n2);
        assertEquals(2, g.numberOfNodes());
        
        g.delNode(n1);
        assertEquals(1, g.numberOfNodes());
        assertFalse(g.getNodes().contains(n1));
    }

    @Test
    public void testAddAndDelEdge() {
        IGraph g = new Graph();
        INode src = g.addNode();
        INode tgt = g.addNode();
        
        IEdge e1 = g.addEdge(src, tgt);
        assertEquals(1, g.numberOfEdges());
        assertTrue(g.getEdges().contains(e1));
        assertEquals(src, g.source(e1));
        assertEquals(tgt, g.target(e1));
        
        g.delEdge(e1);
        assertEquals(0, g.numberOfEdges());
        assertFalse(g.getEdges().contains(e1));
    }

   
}
