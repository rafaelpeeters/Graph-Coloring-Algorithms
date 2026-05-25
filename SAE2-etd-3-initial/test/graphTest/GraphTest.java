package graphTest;

import info.iut.sae2.graphs.Graph;
import info.iut.sae2.graphs.IGraph;
import info.iut.sae2.graphs.Node;
import info.iut.sae2.graphs.INode;
import info.iut.sae2.graphs.IEdge;
import info.iut.sae2.graphs.Edge;
import info.iut.sae2.graphs.Coord;
import java.util.ArrayList;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * 
 * @author carpentier
 */
public class GraphTest {

    @Test
    public void testConstructors() {
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
        cg.addNode();
        assertEquals(1, g.numberOfNodes());
        assertEquals(2, cg.numberOfNodes());
        
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
    
    @Test
    public void testDelNodeRemovesAssociatedEdges() {
        IGraph g = new Graph();
        INode n1 = g.addNode();
        INode n2 = g.addNode();
        
        IEdge e = g.addEdge(n1, n2);
        assertEquals(1, g.numberOfEdges());
        
        // Supprimer un nœud doit supprimer l'arête qui y est attachée
        g.delNode(n1);
        
        assertEquals(1, g.numberOfNodes());
        assertEquals(0, g.numberOfEdges());
        assertFalse(g.getEdges().contains(e));
    }

    @Test
    public void testSuccessorsAndPredecessors() {
        IGraph g = new Graph();
        INode n1 = g.addNode();
        INode n2 = g.addNode();
        INode n3 = g.addNode();
        
        g.addEdge(n1, n2); 
        g.addEdge(n2, n3); 

        ArrayList<INode> succN2 = g.getSuccessors(n2);
        assertEquals(1, succN2.size());
        assertTrue(succN2.contains(n3));

        ArrayList<INode> predN2 = g.getPredecessors(n2);
        assertEquals(1, predN2.size());
        assertTrue(predN2.contains(n1));

        ArrayList<INode> neighbors = g.getNeighbors(n2);
        assertEquals(2, neighbors.size());
        assertTrue(neighbors.contains(n1));
        assertTrue(neighbors.contains(n3));
    }

    @Test
    public void testDegrees() {
        IGraph g = new Graph();
        INode n1 = g.addNode();
        INode n2 = g.addNode();
        INode n3 = g.addNode();
        
        g.addEdge(n1, n2);
        g.addEdge(n2, n3);
        
        assertEquals(1, g.inDegree(n2)); 
        assertEquals(1, g.outDegree(n2));
        assertEquals(2, g.degree(n2));   
    }

    @Test
    public void testExistAndGetEdge() {
        IGraph g = new Graph();
        INode n1 = g.addNode();
        INode n2 = g.addNode();
        
        IEdge e = g.addEdge(n1, n2);
        
        // Tests orientés
        assertTrue(g.existEdge(n1, n2, true));
        assertFalse(g.existEdge(n2, n1, true)); 
        assertEquals(e, g.getEdge(n1, n2, true));
        assertNull(g.getEdge(n2, n1, true));
        // Tests non-orientés
        assertTrue(g.existEdge(n1, n2, false));
        assertTrue(g.existEdge(n2, n1, false)); 
        assertEquals(e, g.getEdge(n2, n1, false));
    }

    @Test
    public void testNodeDefaultValue() {
        IGraph g = new Graph();
        INode n = g.addNode();
        assertNotNull(g.getNodeColor(n));
        assertNotNull(g.getNodeSize(n));
    }
    
    @Test
    public void testBoundingBox() {
        IGraph g = new Graph();
        INode n1 = g.addNode();
        INode n2 = g.addNode();

        g.setNodePosition(n1, new Coord(10, 20));
        g.setNodePosition(n2, new Coord(50, 80));
        
        ArrayList<Coord> box = g.getBoundingBox();
        assertEquals(0.0, box.get(0).getX(), 0.001);
        assertEquals(10.0, box.get(0).getY(), 0.001);
 
        assertEquals(60.0, box.get(1).getX(), 0.001);
        assertEquals(90.0, box.get(1).getY(), 0.001);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testAddNullNode() {
        IGraph g = new Graph();
        g.addNode(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNullEdge() {
        IGraph g = new Graph();
        g.addEdge(null);
    }

    @Test(expected = IllegalStateException.class)
    public void testBoundingBoxOnEmptyGraph() {
        IGraph g = new Graph();
        g.getBoundingBox();
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testGetEdgeWithUnregisteredNode() {
        IGraph g = new Graph();
        INode n1 = new Node();
        INode n2 = new Node();
        g.getEdge(n1, n2, true); 
    }
    
    
}