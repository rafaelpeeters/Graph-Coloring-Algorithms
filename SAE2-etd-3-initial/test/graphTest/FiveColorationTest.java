package graphTest;

import org.junit.Test;
import static org.junit.Assert.*;

import info.iut.sae2.algorithm.FiveColorationPlanarAlgorithm;
import info.iut.sae2.graphs.Graph;
import info.iut.sae2.graphs.IGraph;
import info.iut.sae2.graphs.INode;
import info.iut.sae2.graphs.Color;
import info.iut.sae2.properties.ColorProperty;


import java.util.HashMap;
import java.util.ArrayList;

public class FiveColorationTest {


    @Test(expected = IllegalArgumentException.class)
    public void nullGraphTest() {
        FiveColorationPlanarAlgorithm algo = new FiveColorationPlanarAlgorithm();
        algo.apply(null, null);
    }


    @Test
    public void emptyGraphTest() {
        IGraph emptyGraph = new Graph(); 
        FiveColorationPlanarAlgorithm algo = new FiveColorationPlanarAlgorithm();     
        ColorProperty result = algo.apply(emptyGraph, new HashMap<>());
        assertNotNull(result);
        assertEquals(0, emptyGraph.numberOfNodes());
    }

    @Test
    public void differentColorGraphTest() {
        IGraph graph = new Graph();
        FiveColorationPlanarAlgorithm algo = new FiveColorationPlanarAlgorithm();
        
        INode nA = graph.addNode();
        INode nB = graph.addNode();
        INode nC = graph.addNode();
        
        //creat the graph
        graph.addEdge(nA, nB);
        graph.addEdge(nB, nC);
        graph.addEdge(nC, nA);

        ColorProperty result = algo.apply(graph, new HashMap<>());

        Color colorA = result.getNodeValue(nA);
        Color colorB = result.getNodeValue(nB);
        Color colorC = result.getNodeValue(nC);

        // check all color is different
        assertNotNull(colorA);
        assertNotNull(colorB);
        assertNotNull(colorC);
        assertNotEquals(colorA, colorB);
        assertNotEquals(colorB, colorC);
        assertNotEquals(colorA, colorC);
    }


    @Test
    public void planarGraphTest() {
        IGraph graph = new Graph();
        FiveColorationPlanarAlgorithm algo = new FiveColorationPlanarAlgorithm();
        
        INode nA = graph.addNode();
        INode nB = graph.addNode();
        INode nC = graph.addNode();
        INode nD = graph.addNode();
        INode nE = graph.addNode();
        INode nF = graph.addNode();

        //creat the graph
        graph.addEdge(nA, nB);
        graph.addEdge(nA, nC);
        graph.addEdge(nA, nD);
        graph.addEdge(nB, nE);
        graph.addEdge(nC, nE);
        graph.addEdge(nD, nF);
        graph.addEdge(nE, nF);

        ColorProperty result = algo.apply(graph, new HashMap<>());

        //check no neighbors have same color
        ArrayList<INode> nodes = graph.getNodes();
        ArrayList<Color> usedColors = new ArrayList<>();

        for (INode node : nodes) {
            Color nodeColor = result.getNodeValue(node);
            assertNotNull(nodeColor);
            
            if (!usedColors.contains(nodeColor)) {
                usedColors.add(nodeColor);
            }

            // for each Neighbors check color is different
            for (INode neighbor : graph.getNeighbors(node)) {
                Color neighborColor = result.getNodeValue(neighbor);
                assertNotEquals(nodeColor, neighborColor); 
            }
        }
        assertTrue(usedColors.size() <= 5);
    }
}