package info.iut.sae2.algorithm;

import info.iut.sae2.graphs.IGraph;
import info.iut.sae2.graphs.INode;
import info.iut.sae2.graphs.Color;
import info.iut.sae2.properties.ColorProperty;
import java.util.ArrayList;
import java.util.Map;

/**
 * A class implementing the Six Coloration Algorithm.
 *
 * @author Peeters
 * @author Carpentier
 */
public class SixColorationPlanarAlgorithm implements Algorithm<ColorProperty> {

    @Override
    public ColorProperty apply(IGraph g, Map<String, Object> parameters) {
        if (g == null) {
            throw new IllegalArgumentException("Le graphe ne peut pas etre null");
        }
        if (g.numberOfNodes() == 0) {
            return new ColorProperty();
        }
        IGraph localGraph = g.copy();
        ArrayList<INode> removalOrder = removalOrder(localGraph);
        return colorNodes(g, removalOrder);
    }

    /**
     * Builds the removal order list by removing vertices of degree lower or
     * equal to 5.
     *
     * @param g a copy of the graph of interest
     * @return the list of nodes in their elimination order
     */
    private ArrayList<INode> removalOrder(IGraph g) {
        ArrayList<INode> nodesList = new ArrayList<>();
        ArrayList<INode> availableNodes = g.getNodes(); 
        while (!availableNodes.isEmpty()) {
            INode targetNode = null;
            for (INode node : availableNodes) {
                if (g.degree(node) <= 5) {
                    targetNode = node;
                    break;
                }
            }
            if (targetNode == null) {
                targetNode = availableNodes.get(0);
            }
            nodesList.add(targetNode);
            availableNodes.remove(targetNode);
            g.delNode(targetNode);            
        }
        return nodesList;
    }

    /**
     * Assigns the colors to each node in reverse order.
     *
     * @param g the graph of interest
     * @param nodesList the list of nodes
     * @return a ColorProperty associating each node to its color
     */
    private ColorProperty colorNodes(IGraph g, ArrayList<INode> nodesList) {
        ColorProperty colors = new ColorProperty();
        Color[] palette = colors.getPalette();
        while (!nodesList.isEmpty()) {
            int lastIndex = nodesList.size() - 1;
            INode n = nodesList.get(lastIndex);
            nodesList.remove(lastIndex);
            colors.setNodeValue(n, FirstFitAlgorithm.firstFit(g, n, colors, palette));
        }
        return colors;
    }

}