package info.iut.sae2.algorithm;

import info.iut.sae2.graphs.IGraph;
import info.iut.sae2.graphs.INode;
import info.iut.sae2.graphs.Color;
import info.iut.sae2.properties.ColorProperty;
import java.util.ArrayList;
import java.util.Map;

public class SixColorationPlanarAlgorithm implements Algorithm<ColorProperty> {

    @Override
    public ColorProperty apply(IGraph g, Map<String, Object> parameters) {
        if (g == null) {
            throw new IllegalArgumentException("The graph cannot be null");
        }
        if (g.numberOfNodes() == 0) {
            return new ColorProperty();
        }
        IGraph localGraph = g.copy();
        ArrayList<INode> eliminationOrder = buildEliminationList(localGraph);
        ColorProperty finalColors = colorNodesFromList(g, eliminationOrder);
        return finalColors;
    }

    /**
     * Reduces the graph by repeatedly finding and removing vertices of degree
     * <= 5.
     *
     * @param g the copy of the graph of interest
     * @return a list of nodes storing them in their elimination order
     */
    private ArrayList<INode> buildEliminationList(IGraph g) {
        ArrayList<INode> nodesList = new ArrayList<>();
        while (g.numberOfNodes() > 0) {
            ArrayList<INode> availableNodes = g.getNodes();
            INode targetNode = null;
            int i = 0;
            while (i < availableNodes.size() && targetNode == null) {
                INode node = availableNodes.get(i);
                if (g.degree(node) <= 5) {
                    targetNode = node;
                }
                i++;
            }
            if (targetNode == null) {
                targetNode = availableNodes.get(0);
            }
            nodesList.add(targetNode);
            g.delNode(targetNode);
        }
        return nodesList;
    }

    /**
     * Finds the first available color not used by any neighbor.
     *
     * @param g the graph of interest
     * @param node the node being colored
     * @param colors the colorProperty containing already assigned colors
     * @param palette the array of available colors
     * @return the chosen color for the node
     */
    private Color firstFit(IGraph g, INode node, ColorProperty colors, Color[] palette) {
        ArrayList<INode> neighbors = g.getNeighbors(node);
        ArrayList<Color> forbidenColors = new ArrayList<>();
        for (INode neighbor : neighbors) {
            Color neighborColor = colors.getNodeValue(neighbor);
            if (neighborColor != null) {
                forbidenColors.add(neighborColor);
            }
        }
        Color chosenColor = null;
        int i = 0;
        while (i < palette.length && chosenColor == null) {
            Color color = palette[i];
            if (!forbidenColors.contains(color)) {
                chosenColor = color;
            }
            i++;
        }
        if (chosenColor == null) {
            chosenColor = palette[0];
        }
        return chosenColor;
    }

    /**
     * Assign colors to each node according to the elimination order in reverse.
     *
     * @param g the graph of interest
     * @param eliminationOrder the list of nodes storing them in their
     * elimination order
     * @return a ColorProperty associating each node to its computed color
     */
    private ColorProperty colorNodesFromList(IGraph g, ArrayList<INode> eliminationOrder) {
        ColorProperty colors = new ColorProperty();
        Color[] palette = colors.getPalette();
        while (!eliminationOrder.isEmpty()) {
            int lastIndex = eliminationOrder.size() - 1;
            INode currentNode = eliminationOrder.get(lastIndex);
            eliminationOrder.remove(lastIndex);
            Color chosenColor = firstFit(g, currentNode, colors, palette);
            colors.setNodeValue(currentNode, chosenColor);
        }
        return colors;
    }

}
