package info.iut.sae2.algorithm;

import info.iut.sae2.graphs.Color;
import info.iut.sae2.graphs.IEdge;
import info.iut.sae2.graphs.IGraph;
import info.iut.sae2.graphs.INode;
import info.iut.sae2.properties.ColorProperty;

import java.util.ArrayList;
import java.util.Map;

/**
 * Algorithme de 5-coloration pour les graphes planaires.
 *
 * Repose sur deux propriétés fondamentales des graphes planaires :
 *   (Prop 1) Tout graphe planaire possède au moins un sommet de degré <= 5.
 *   (Prop 3) Pour tout sommet de degré 5, au moins 2 de ses voisins ne sont pas reliés.
 *
 * Référence : Chiba, Nishizeki et Saito, "A linear 5-coloring algorithm of planar graphs",
 *             Journal of Algorithms, 2(4), 317-327, 1981.
 */
public class FiveColorationPlanarAlgorithm implements Algorithm<ColorProperty> {

    /** Les 5 couleurs disponibles, récupérées depuis la palette de ColorProperty. */
    private Color[] palette;

    /**
     * Point d'entrée de l'algorithme.
     * Travaille sur une COPIE du graphe afin de ne pas modifier l'original.
     *
     * @param graph  le graphe planaire à colorier
     * @param params paramètre non utilisé (pour compatibilité d'interface)
     * @return un ColorProperty associant une couleur à chaque nœud du graphe original
     */
    public ColorProperty apply(IGraph graph, Map<String, Object> parameters) {

        // Création du résultat et récupération de la palette de 5 couleurs
        ColorProperty coloring = new ColorProperty();
        palette = coloring.getPalette();

        // On travaille sur une copie pour ne pas altérer le graphe original
        // Les nœuds de la copie sont les MÊMES objets que dans l'original (copie superficielle),
        // donc les couleurs posées sur les nœuds de la copie s'appliquent bien à l'original.
        IGraph graphCopy = graph.copy();

        colorRecursive(graphCopy, coloring);

        return coloring;
    }

    // -------------------------------------------------------------------------
    //  Algorithme récursif principal
    // -------------------------------------------------------------------------

    /**
     * Colorie récursivement le graphe g en utilisant au plus 5 couleurs.
     *
     * @param g        le graphe (ou sous-graphe) à colorier, modifié en place
     * @param coloring le résultat dans lequel on stocke les couleurs des nœuds
     */
    private void colorRecursive(IGraph g, ColorProperty coloring) {

        // ----- Cas de base : 5 sommets ou moins -----
        // On peut affecter naïvement une couleur différente à chaque sommet.
        if (g.numberOfNodes() <= 5) {
            ArrayList<INode> nodes = g.getNodes();
            for (int i = 0; i < nodes.size(); i++) {
                coloring.setNodeValue(nodes.get(i), palette[i]);
            }
            return;
        }

        // ----- Étape 1 : choisir un sommet à retirer -----
        // On cherche d'abord un sommet de degré <= 4.
        // S'il n'en existe pas (tous à degré >= 5), on prend un sommet de degré 5.
        INode chosen = findNodeWithDegreeAtMost4(g);
        if (chosen == null) {
            chosen = findNodeWithDegree5(g);
        }

        // ----- Branche selon le degré du sommet choisi -----
        if (g.degree(chosen) <= 4) {
            caseDegreeFourOrLess(g, coloring, chosen);
        } else {
            caseDegreeFive(g, coloring, chosen);
        }
    }

    // -------------------------------------------------------------------------
    //  Cas 1 : sommet de degré <= 4
    // -------------------------------------------------------------------------

    /**
     * Traite le cas où le sommet choisi a un degré inférieur ou égal à 4.
     *
     * Principe :
     *   1. Mémoriser les voisins de chosen.
     *   2. Supprimer chosen du graphe.
     *   3. Colorier récursivement le graphe réduit.
     *   4. Affecter à chosen la plus petite couleur non utilisée par ses voisins.
     *      (au plus 4 voisins → on trouvera toujours une couleur parmi les 5)
     */
    private void caseDegreeFourOrLess(IGraph g, ColorProperty coloring, INode chosen) {

        // Sauvegarder les voisins avant suppression
        ArrayList<INode> neighbors = g.getNeighbors(chosen);

        // Supprimer le sommet (et toutes ses arêtes) du graphe
        g.delNode(chosen);

        // Appel récursif sur le graphe réduit d'un sommet
        colorRecursive(g, coloring);

        // Réaffecter chosen : plus petite couleur absente chez ses voisins
        Color color = smallestAvailableColor(neighbors, coloring);
        coloring.setNodeValue(chosen, color);
    }

    // -------------------------------------------------------------------------
    //  Cas 2 : sommet de degré exactement 5
    // -------------------------------------------------------------------------

    /**
     * Traite le cas où le sommet choisi a un degré égal à 5.
     *
     * Principe (appuyé sur Prop 3 : parmi les 5 voisins, au moins 2 ne sont pas adjacents) :
     *   1. Mémoriser les voisins de chosen.
     *   2. Trouver une paire (u, v) de voisins non adjacents.
     *   3. Fusionner v dans u : ajouter une arête u–w pour chaque voisin w de v (w ≠ u).
     *   4. Supprimer v  → chosen perd un voisin, son degré passe de 5 à 4.
     *   5. Supprimer chosen (degré 4 à ce stade).
     *   6. Appel récursif sur le graphe réduit de 2 sommets.
     *   7. Affecter à v la même couleur que u (ils n'étaient pas adjacents → valide).
     *   8. Affecter à chosen la plus petite couleur non utilisée par ses voisins.
     *      (u et v partagent la même couleur → au plus 4 couleurs distinctes → on en trouve une 5e)
     */
    private void caseDegreeFive(IGraph g, ColorProperty coloring, INode chosen) {

        // Sauvegarder les 5 voisins de chosen avant toute modification
        ArrayList<INode> neighbors = g.getNeighbors(chosen);

        // ----- Trouver une paire (u, v) de voisins non adjacents -----
        // Garantie par Prop 3 : une telle paire existe toujours.
        INode u = null;
        INode v = null;

        outerLoop:
        for (int i = 0; i < neighbors.size(); i++) {
            for (int j = i + 1; j < neighbors.size(); j++) {
                INode ni = neighbors.get(i);
                INode nj = neighbors.get(j);
                if (!g.existEdge(ni, nj, false)) {
                    u = ni;
                    v = nj;
                    break outerLoop;
                }
            }
        }

        // ----- Fusionner v dans u -----
        // Pour chaque voisin de v, on crée une arête vers u si elle n'existe pas encore.
        ArrayList<INode> vNeighbors = g.getNeighbors(v);
        for (INode neighbor : vNeighbors) {
            boolean isSelfLoop       = neighbor.equals(u);
            boolean alreadyConnected = g.existEdge(u, neighbor, false);
            if (!isSelfLoop && !alreadyConnected) {
                g.addEdge(u, neighbor);
            }
        }

        // Supprimer v : chosen perd le voisin v → son degré passe de 5 à 4
        g.delNode(v);

        // Supprimer chosen (degré 4 désormais)
        g.delNode(chosen);

        // Appel récursif sur le graphe réduit de 2 sommets
        colorRecursive(g, coloring);

        // Affecter à v la même couleur qu'u (non-adjacents → pas de conflit)
        coloring.setNodeValue(v, coloring.getNodeValue(u));

        // Affecter à chosen la plus petite couleur non utilisée par ses voisins
        // u et v partagent la même couleur → au plus 4 couleurs distinctes parmi les 5 voisins
        Color color = smallestAvailableColor(neighbors, coloring);
        coloring.setNodeValue(chosen, color);
    }

    // -------------------------------------------------------------------------
    //  Méthodes utilitaires
    // -------------------------------------------------------------------------

    /**
     * Retourne le premier nœud du graphe ayant un degré <= 4, ou null s'il n'en existe pas.
     */
    private INode findNodeWithDegreeAtMost4(IGraph g) {
        for (INode n : g.getNodes()) {
            if (g.degree(n) <= 4) {
                return n;
            }
        }
        return null;
    }

    /**
     * Retourne le premier nœud du graphe ayant un degré exactement égal à 5, ou null s'il n'en existe pas.
     */
    private INode findNodeWithDegree5(IGraph g) {
        for (INode n : g.getNodes()) {
            if (g.degree(n) == 5) {
                return n;
            }
        }
        return null;
    }

    /**
     * Retourne la première couleur de la palette qui n'est utilisée par aucun des voisins donnés.
     *
     * @param neighbors la liste des nœuds voisins dont on veut éviter les couleurs
     * @param coloring  le coloriage courant
     * @return la plus petite couleur disponible (au sens de l'ordre de la palette)
     */
    private Color smallestAvailableColor(ArrayList<INode> neighbors, ColorProperty coloring) {

        for (Color candidate : palette) {

            boolean usedByANeighbor = false;

            for (INode neighbor : neighbors) {
                Color neighborColor = coloring.getNodeValue(neighbor);
                if (candidate.equals(neighborColor)) {
                    usedByANeighbor = true;
                    break;
                }
            }

            if (!usedByANeighbor) {
                return candidate;
            }
        }

        // Ce cas ne devrait jamais se produire pour un graphe planaire valide
        return palette[0];
    }
}