package info.iut.sae2.algorithm;

import info.iut.sae2.graphs.IGraph;
import info.iut.sae2.graphs.INode;
import info.iut.sae2.graphs.Color;
import info.iut.sae2.properties.ColorProperty;
import java.util.ArrayList;
import java.util.Map;

public class FiveColorationPlanarAlgorithm implements Algorithm<ColorProperty> {

    @Override
    public ColorProperty apply(IGraph g, Map<String, Object> parameters) {
        if (g == null) {
            throw new IllegalArgumentException("The graph cannot be null");
        }
        
        ColorProperty colors = new ColorProperty();
        
        if (g.numberOfNodes() == 0) {
            return colors;
        }

        IGraph localGraph = g.copy();
        
        // Lancement de l'algorithme récursif
        fiveColoration(localGraph, g, colors);
        
        return colors;
    }

    /**
     * Méthode récursive pour colorier le graphe en 5 couleurs maximum.
     * @param localGraph le graphe en cours de réduction
     * @param originalGraph le graphe original intact (pour retrouver les vrais voisins)
     * @param colors la propriété stockant les couleurs finales
     */
    private void fiveColoration(IGraph localGraph, IGraph originalGraph, ColorProperty colors) {
        Color[] palette = colors.getPalette();
        if (localGraph.numberOfNodes() <= 5) {
            int i = 0;
            for (INode node : localGraph.getNodes()) {
                colors.setNodeValue(node, palette[i]);
                i++;
            }
            return;
        }
        ArrayList<INode> removedSmallNodes = new ArrayList<>();
        boolean found;

        do {
            found = false;
            ArrayList<INode> nodes = localGraph.getNodes(); 
            int i = 0;
            while (i < nodes.size() && !found) {
                INode node = nodes.get(i);
                if (localGraph.degree(node) <= 4) {
                    removedSmallNodes.add(node);
                    localGraph.delNode(node);
                    found = true; 
                }
                i++;
            }
        }
        while (found && localGraph.numberOfNodes() > 5);
        if (localGraph.numberOfNodes() <= 5) {
             int i = 0;
             for (INode node : localGraph.getNodes()) {
                 colors.setNodeValue(node, palette[i]);
                 i++;
             }
        } else {
            INode target5 = null;
            found = false;
            ArrayList<INode> nodes = localGraph.getNodes(); 
            int i = 0;

            while (i < nodes.size() && !found) {
                INode node = nodes.get(i);

                if (localGraph.degree(node) == 5) {
                    target5 = node;
                    found = true;
                }

                i++;
            }
            if (target5 != null) {
                ArrayList<INode> neighbors = localGraph.getNeighbors(target5);
                INode u = null, v = null;
                found = false;

                int k = 0;
                while (k < neighbors.size() && !found) {
                    int j = k + 1;
                    while (j < neighbors.size() && !found) {
                        INode n1 = neighbors.get(k);
                        INode n2 = neighbors.get(j);
                        if (!localGraph.existEdge(n1, n2, false)) {
                            u = n1;
                            v = n2;
                            found = true;
                        }
                        j++; 
                    }
                    k++; 
                }
                if (u != null && v != null) {
                    ArrayList<INode> vNeighbors = localGraph.getNeighbors(v);
                    for (INode vn : vNeighbors) {
                        if (!vn.equals(u) && !localGraph.existEdge(u, vn, false)) {
                            localGraph.addEdge(u, vn);
                        }
                    }
                    localGraph.delNode(v);
                }
                localGraph.delNode(target5);
                fiveColoration(localGraph, originalGraph, colors);
                if (u != null && v != null) {
                    Color uColor = colors.getNodeValue(u);
                    colors.setNodeValue(v, uColor);
                }
                Color c = FirstFitAlgorithm.firstFit(originalGraph, target5, colors, palette);
                colors.setNodeValue(target5, c);
            }
        }
        for (int i = removedSmallNodes.size() - 1; i >= 0; i--) {
            INode n = removedSmallNodes.get(i);
            Color chosen = FirstFitAlgorithm.firstFit(originalGraph, n, colors,palette);
            colors.setNodeValue(n, chosen);
        }
    }
}