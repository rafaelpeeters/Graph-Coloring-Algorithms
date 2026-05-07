package graphTest;

import info.iut.sae2.graphs.Graph;
import info.iut.sae2.graphs.IGraph;
import org.junit.Test;
import static org.junit.Assert.*;


public class GraphTest {

    @Test 
    public void testConstructors(){
        IGraph g = new Graph();
        assertEquals(0, g.numberOfNodes());
        assertEquals(0, g.numberOfEdges());
        assertEquals(0, g.getSizes());
        assertEquals(null,g.getLayout());
        
    }

}
