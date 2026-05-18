package graphTest;

import info.iut.sae2.algorithm.WelshAndPowell;
import info.iut.sae2.graphs.Graph;
import info.iut.sae2.graphs.INode;
import info.iut.sae2.graphs.Color;
import info.iut.sae2.properties.ColorProperty;
import java.util.HashMap;

import org.junit.Test;
import static org.junit.Assert.*;

public class WelshAndPowellTest {

    @Test
    public void testDeuxNoeudsVoisins() {
        Graph g = new Graph();
        
        INode n1 = g.addNode();
        INode n2 = g.addNode();
        
        g.addEdge(n1, n2);
        
        WelshAndPowell algo = new WelshAndPowell();
        ColorProperty result = algo.apply(g, new HashMap<>());
        
        Color c1 = result.getNodeValue(n1);
        Color c2 = result.getNodeValue(n2);
        
        assertNotNull(c1);
        assertNotNull(c2);
        assertNotEquals(c1, c2);
    }

    @Test
    public void testDeuxNoeudsDistant() {
        Graph g = new Graph();
        
        INode n1 = g.addNode();
        INode n2 = g.addNode();
        
        WelshAndPowell algo = new WelshAndPowell();
        ColorProperty result = algo.apply(g, new HashMap<>());
        
        Color c1 = result.getNodeValue(n1);
        Color c2 = result.getNodeValue(n2);
        
        assertNotNull(c1);
        assertNotNull(c2);
        assertEquals(c1, c2);
    }
    
}