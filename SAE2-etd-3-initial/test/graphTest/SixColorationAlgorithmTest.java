package graphTest;

import info.iut.sae2.algorithm.SixColorationPlanarAlgorithm;
import info.iut.sae2.graphs.Color;
import info.iut.sae2.graphs.Graph;
import info.iut.sae2.graphs.IGraph;
import info.iut.sae2.graphs.INode;
import info.iut.sae2.properties.ColorProperty;
import org.junit.Test;
import static org.junit.Assert.*;

public class SixColorationAlgorithmTest {

    /**
     * Initial situation : null graph Expected result : IllegalArgumentException
     */
    @Test(expected = IllegalArgumentException.class)
    public void testApplyNullGraph() {
        SixColorationPlanarAlgorithm algo = new SixColorationPlanarAlgorithm();
        algo.apply(null, null);
    }

    /**
     * Initial situation : empty graph Expected result : empty ColorProperty
     */
    @Test
    public void testApplyEmptyGraph() {
        IGraph g = new Graph();
        SixColorationPlanarAlgorithm algo = new SixColorationPlanarAlgorithm();
        ColorProperty colors = algo.apply(g, null);
        assertNotNull(colors);
        assertTrue(g.getNodes().isEmpty());
        for (INode n : g.getNodes()) {
            assertNull(colors.getNodeValue(n));
        }
    }

    /**
     * Initial situation : single node graph Expected result : node gets first
     * color
     */
    @Test
    public void testApplySingleNode() {
        IGraph g = new Graph();
        INode n = g.addNode();
        SixColorationPlanarAlgorithm algo = new SixColorationPlanarAlgorithm();
        ColorProperty colors = algo.apply(g, null);
        Color[] palette = colors.getPalette();
        assertEquals(palette[0], colors.getNodeValue(n));
    }

    /**
     * Initial situation : Graph with n1 (degree 2) and n2,n3 (degree 1)
     * Expected result : n1 gets first color, n2 and n3 get second color
     */
    @Test
    public void testApplySimpleGraph() {
            IGraph g = new Graph();
        INode n1 = g.addNode();
        INode n2 = g.addNode();
        INode n3 = g.addNode();
        g.addEdge(n1, n2);
        g.addEdge(n1, n3);

        SixColorationPlanarAlgorithm algo = new SixColorationPlanarAlgorithm();
        ColorProperty colors = algo.apply(g, null);
        Color[] palette = colors.getPalette();

        // n1 has highest degree so gets first color
        assertEquals(palette[0], colors.getNodeValue(n1));
        // n2 and n3 are not neighbors so get same color
        assertEquals(colors.getNodeValue(n2), colors.getNodeValue(n3));
    }

    /**
     * Initial situation : Graph with n1, n2 and n3 of degree 2 Expected result
     * : They all get different colors
     */
    @Test
    public void testApplyTriangleGraph() {
        IGraph g = new Graph();
        INode n1 = g.addNode();
        INode n2 = g.addNode();
        INode n3 = g.addNode();
        g.addEdge(n1, n2);
        g.addEdge(n2, n3);
        g.addEdge(n1, n3);
        SixColorationPlanarAlgorithm algo = new SixColorationPlanarAlgorithm();
        ColorProperty colors = algo.apply(g, null);
        // triangle -> 3 different colors needed
        assertNotEquals(colors.getNodeValue(n1), colors.getNodeValue(n2));
        assertNotEquals(colors.getNodeValue(n2), colors.getNodeValue(n3));
        assertNotEquals(colors.getNodeValue(n1), colors.getNodeValue(n3));

    }
}
