package graphTest;

import info.iut.sae2.graphs.Node;

import org.junit.Test;
import static org.junit.Assert.*;

public class NodeTest {

    @Test
    public void testId() {
        Node n1 = new Node();
        Node n2 = new Node();
        //making sure the id increments properly
        assertNotEquals(n1.getId(), n2.getId());
        assertTrue(n2.getId() > n1.getId());
    }
    
}