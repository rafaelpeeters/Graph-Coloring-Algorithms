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
        
        // On travaille sur une copie pour pouvoir modifier la structure (suppression/fusion)
        IGraph localGraph = g.copy();
        
        // Lancement de l'algorithme récursif
        recursiveColor(localGraph, g, colors);
        
        return colors;
    }

    /**
     * Méthode récursive pour colorier le graphe en 5 couleurs maximum.
     * * @param localGraph le graphe en cours de réduction
     * @param originalG le graphe original intact (pour retrouver les vrais voisins)
     * @param colors la propriété stockant les couleurs finales
     */
    private void recursiveColor(IGraph localGraph, IGraph originalG, ColorProperty colors) {
        // 1. Condition d'arrêt
        if (localGraph.numberOfNodes() <= 5) {
            Color[] palette = colors.getPalette();
            int i = 0;
            for (INode node : localGraph.getNodes()) {
                colors.setNodeValue(node, palette[i]);
                i++;
            }
            return;
        }

        // 2. ASTUCE : Regrouper les suppressions des sommets de degré <= 4
        ArrayList<INode> removedSmallNodes = new ArrayList<>();
        boolean found;

        do {
            found = false;

            // On récupère les nœuds (on suppose que c'est une List pour l'index)
            ArrayList<INode> nodes = localGraph.getNodes(); 
            int i = 0;

            // Boucle interne qui s'arrête si on arrive à la fin de la liste OU si on a trouvé un nœud
            while (i < nodes.size() && !found) {
                INode node = nodes.get(i);

                if (localGraph.degree(node) <= 4) {
                    removedSmallNodes.add(node);
                    localGraph.delNode(node);
                    found = true; // Cela va stopper ce while sans utiliser de "break"
                }

                i++;
            }
        } while (found && localGraph.numberOfNodes() > 5);

        // 3. Si après avoir retiré les petits sommets, on est à <= 5, on traite la fin
        if (localGraph.numberOfNodes() <= 5) {
             Color[] palette = colors.getPalette();
             int i = 0;
             for (INode node : localGraph.getNodes()) {
                 colors.setNodeValue(node, palette[i]);
                 i++;
             }
        } else {
            // 4. Cas B : Il n'y a plus de sommets <= 4, on cherche un sommet de degré 5
            INode target5 = null;
            found = false;
            ArrayList<INode> nodes = localGraph.getNodes(); 
            int i = 0;

            while (i < nodes.size() && !found) {
                INode node = nodes.get(i);

                if (localGraph.degree(node) == 5) {
                    target5 = node;
                    found = true; // Permet de sortir de la boucle, remplaçant ainsi le "break"
                }

                i++;
            }

            if (target5 != null) {
                ArrayList<INode> neighbors = localGraph.getNeighbors(target5);
                INode u = null, v = null;
                boolean foundPair = false;

                // Chercher la paire (u, v) non reliée
                for (int k = 0; k < neighbors.size() && !foundPair; k++) {
                    for (int j = k + 1; j < neighbors.size() && !foundPair; j++) {
                        INode n1 = neighbors.get(k);
                        INode n2 = neighbors.get(j);
                        if (!localGraph.existEdge(n1, n2, false)) {
                            u = n1;
                            v = n2;
                            foundPair = true;
                        }
                    }
                }

                // Fusion
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

                // L'APPEL RÉCURSIF (Unique pour ce niveau)
                recursiveColor(localGraph, originalG, colors);

                // À la remontée : on affecte à v la même couleur que u
                if (u != null && v != null) {
                    Color uColor = colors.getNodeValue(u);
                    colors.setNodeValue(v, uColor);
                }

                // Colorier le sommet de degré 5
                Color chosen = firstFit(originalG, target5, colors);
                colors.setNodeValue(target5, chosen);
            }
        }

        // 5. À la remontée finale : on colorie tous les sommets de degré <= 4 
        // qu'on avait mis de côté, dans l'ordre inverse de leur suppression.
        for (int i = removedSmallNodes.size() - 1; i >= 0; i--) {
            INode n = removedSmallNodes.get(i);
            Color chosen = firstFit(originalG, n, colors);
            colors.setNodeValue(n, chosen);
        }
    }

    /**
     * Trouve la première couleur disponible non utilisée par le voisinage.
     * * @param originalG le graphe original intact
     * @param node le sommet en cours de coloration
     * @param colors la ColorProperty contenant les couleurs déjà attribuées
     * @return la couleur choisie pour le sommet
     */
    private Color firstFit(IGraph originalG, INode node, ColorProperty colors) {
        // On récupère les voisins depuis le graphe original, car les nœuds ont été supprimés de la copie
        ArrayList<INode> neighbors = originalG.getNeighbors(node);
        ArrayList<Color> forbiddenColors = new ArrayList<>();       
        Color[] palette = colors.getPalette();
        
        for (INode neighbor : neighbors) {
            Color neighborColor = colors.getNodeValue(neighbor);
            if (neighborColor != null) {
                forbiddenColors.add(neighborColor);
            }
        }

        Color selectedColor = palette[0]; // On initialise avec la couleur par défaut au cas où
        boolean found = false;
        int i = 0;

        while (!found && i < palette.length) {
            Color color = palette[i];

            if (!forbiddenColors.contains(color)) {
                selectedColor = color; // On sauvegarde la couleur valide
                found = true;          // On passe le booléen à true pour casser la boucle
            }
            i++;
        }

        // 4. On retourne la couleur trouvée (ou palette[0] si found est resté false)
        return selectedColor;
    }
}