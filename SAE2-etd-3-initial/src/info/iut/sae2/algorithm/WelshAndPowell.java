package info.iut.sae2.algorithm;

import info.iut.sae2.graphs.IGraph;
import info.iut.sae2.graphs.INode;
import info.iut.sae2.graphs.Color;
import info.iut.sae2.properties.ColorProperty;
import java.util.ArrayList;
import java.util.Map;

/**
 * A class implementing the Welsh and Powell Algorithm.
 * 
 * @authors Peeters
 * @authors Carpentier
 */
public class WelshAndPowell implements Algorithm<ColorProperty> {

    @Override
    public ColorProperty apply(IGraph g, Map<String, Object> parameters) {
        if (g == null) {
            throw new IllegalArgumentException("Le graphe ne peut pas être null");
        }
        if (g.numberOfNodes() == 0) {
            return new ColorProperty();
        }
        ArrayList<INode> sortedNodes = sortByDegree(g);
        return colorNodes(g, sortedNodes);
    }

    /**
     * Sorts the nodes by ordering them by descending degree.
     *
     * @param g the graph of interest
     * @return the list of nodes ordered by descending degree
     */
    private ArrayList<INode> sortByDegree(IGraph g) {
        ArrayList<INode> nodes = g.getNodes();
        nodes.sort((n1, n2) -> g.degree(n2) - g.degree(n1));
        return nodes;
    }

    /**
     * Assigns the colors to each node.
     *
     * @param g the graph of interest
     * @param nodesList the list of nodes
     * @return a ColorProperty associating each node to its computed color
     */
    private ColorProperty colorNodes(IGraph g, ArrayList<INode> nodesList) {
        ColorProperty colors = new ColorProperty();
        Color[] palette = colors.getPalette();
        for (INode n : nodesList) {
            colors.setNodeValue(n, FirstFitAlgorithm.firstFit(g, n, colors, palette));
        }
        return colors;
    }

}