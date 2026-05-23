package info.iut.sae2.algorithm;

import info.iut.sae2.graphs.IGraph;
import info.iut.sae2.graphs.INode;
import info.iut.sae2.graphs.Color;
import info.iut.sae2.properties.ColorProperty;
import java.util.ArrayList;
import java.util.Map;

public class WelshAndPowell implements Algorithm<ColorProperty> {

    @Override
    public ColorProperty apply(IGraph g, Map<String, Object> parameters) {
        ColorProperty resultColorProperty = new ColorProperty();
        Color[] palette = resultColorProperty.getPalette();
        ArrayList<INode> nodes = g.getNodes();
        if (nodes.isEmpty()) {
            return resultColorProperty;
        }  
        nodes.sort((node1, node2) -> g.degree(node2) - g.degree(node1));
        int colorIndex = 0;
        while (!nodes.isEmpty()) {
            Color currentColor = palette[colorIndex];
            ArrayList<INode> coloredInThisPass = new ArrayList<>();
            ArrayList<INode> toRemove = new ArrayList<>();
            for (INode current : nodes) {
                boolean hasColoredNeighbor = false;
                ArrayList<INode> neighbors = g.getNeighbors(current);
                int i = 0;
                while (i < coloredInThisPass.size() && !hasColoredNeighbor) {
                    INode coloredNode = coloredInThisPass.get(i);
                    if (neighbors.contains(coloredNode)) {
                        hasColoredNeighbor = true;
                    }
                    i++;
                }
                if (!hasColoredNeighbor) {
                    resultColorProperty.setNodeValue(current, currentColor);
                    coloredInThisPass.add(current);
                    toRemove.add(current);
                }
            }
            nodes.removeAll(toRemove);
            colorIndex++;
            if (colorIndex == palette.length) {
                colorIndex = 0;
            }
        }
        return resultColorProperty;
    }
}
