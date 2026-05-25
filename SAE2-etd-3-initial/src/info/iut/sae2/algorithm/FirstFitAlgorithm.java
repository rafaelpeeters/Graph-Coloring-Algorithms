package info.iut.sae2.algorithm;

import info.iut.sae2.graphs.Color;
import info.iut.sae2.graphs.IGraph;
import info.iut.sae2.graphs.INode;
import info.iut.sae2.properties.ColorProperty;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * A class implementing the First Fit Algorithm.
 *
 * @authors Peeters, Carpentier
 */
public class FirstFitAlgorithm {

    /**
     * Finds the first available color not used by any neighbor.
     *
     * @param g the graph of interest
     * @param node the node being colored
     * @param colors the colorProperty containing already assigned colors
     * @param palette the array of available colors
     * @return the chosen color for the node
     */
    public static Color firstFit(IGraph g, INode node, ColorProperty colors, Color[] palette) {
        ArrayList<INode> neighbors = g.getNeighbors(node);
        HashSet<Color> forbidenColors = new HashSet<>();
        for (INode neighbor : neighbors) {
            Color neighborColor = colors.getNodeValue(neighbor);
            if (neighborColor != null) {
                forbidenColors.add(neighborColor);
            }
        }
        for (Color color : palette) {
            if (!forbidenColors.contains(color)) {
                return color; 
            }
        }
        return palette[0];
    }

}
