package graphTest;

import info.iut.sae2.algorithm.FirstFitAlgorithm;
import info.iut.sae2.graphs.Color;
import info.iut.sae2.graphs.Graph;
import info.iut.sae2.graphs.IGraph;
import info.iut.sae2.graphs.INode;
import info.iut.sae2.properties.ColorProperty;
import org.junit.Test;
import static org.junit.Assert.*;

public class FirstFitAlgorithmTest {

    @Test
    public void testFirstFit() {
        IGraph g = new Graph();
        ColorProperty colors = new ColorProperty();
        Color[] palette = colors.getPalette();

        // no neighbors -> should get first color
        INode n1 = g.addNode();
        assertEquals(palette[0], FirstFitAlgorithm.firstFit(g, n1, colors, palette));

        // one colored neighbor -> should get second color
        INode n2 = g.addNode();
        g.addEdge(n1, n2);
        colors.setNodeValue(n1, palette[0]);
        assertEquals(palette[1], FirstFitAlgorithm.firstFit(g, n2, colors, palette));

        // one uncolored neighbor -> should get first color
        INode n3 = g.addNode();
        INode n4 = g.addNode();
        g.addEdge(n3, n4);
        assertEquals(palette[0], FirstFitAlgorithm.firstFit(g, n4, colors, palette));

        // non-adjacent colored node -> should get first color
        INode n5 = g.addNode();
        INode n6 = g.addNode();
        INode n7 = g.addNode();
        g.addEdge(n5, n6);
        colors.setNodeValue(n7, palette[0]);
        assertEquals(palette[0], FirstFitAlgorithm.firstFit(g, n6, colors, palette));

    }
}