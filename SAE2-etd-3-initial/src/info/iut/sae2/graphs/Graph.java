package info.iut.sae2.graphs;

import info.iut.sae2.properties.ColorProperty;
import info.iut.sae2.properties.LayoutProperty;
import info.iut.sae2.properties.SizeProperty;

import info.iut.sae2.algorithm.WelshAndPowell;
import info.iut.sae2.algorithm.SixColorationPlanarAlgorithm;
import info.iut.sae2.algorithm.FiveColorationPlanarAlgorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;


/**
 * Implementation of the Igraph interface.
 * We specifically referred to:
 * https://docs.oracle.com/javase/8/docs/api/java/lang/Throwable.html
 * @author carpentier, peeters
 */
public class Graph implements IGraph {

    private HashSet<IEdge> edgesSet;
    private HashSet<INode> nodesSet;

    private final HashMap<INode, HashSet<IEdge>> outEdgesMap;
    private final HashMap<INode, HashSet<IEdge>> inEdgesMap;

    private final SizeProperty sizes;
    private final LayoutProperty layout;
    private final ColorProperty colors;

    public Graph() {
        edgesSet = new HashSet<>();
        nodesSet = new HashSet<>();
        sizes = new SizeProperty();
        layout = new LayoutProperty();
        colors = new ColorProperty();
        outEdgesMap = new HashMap<>();
        inEdgesMap = new HashMap<>();
    }

    @Override
    public IGraph createGraph() {
        return new Graph();
    }


    @Override
    public IGraph copy() {
        Graph newGraph = new Graph();
        newGraph.nodesSet = new HashSet<>(this.nodesSet);
        newGraph.edgesSet = new HashSet<>(this.edgesSet);

        for (INode n : this.outEdgesMap.keySet()) {
            HashSet<IEdge> originalEdges = this.outEdgesMap.get(n);
            newGraph.outEdgesMap.put(n, new HashSet<>(originalEdges));
        }

        for (INode n : this.inEdgesMap.keySet()) {
            HashSet<IEdge> originalEdges = this.inEdgesMap.get(n);
            newGraph.inEdgesMap.put(n, new HashSet<>(originalEdges));
        }
        return newGraph;
    }

    @Override
    public INode addNode() {
        INode newNode = new Node();
        addNode(newNode);
        return newNode;
    }

    @Override
    public INode addNode(INode n) {
        if (n == null) {
            throw new IllegalArgumentException("Impossible d'ajouter un nœud null au graphe.");
        }
        nodesSet.add(n);
        if (!outEdgesMap.containsKey(n)) {
            outEdgesMap.put(n, new HashSet<>());
        }
        if (!inEdgesMap.containsKey(n)) {
            inEdgesMap.put(n, new HashSet<>());
        }

        return n;
    }

    @Override
    public IEdge addEdge(IEdge e) {

        if (e == null || e.source() == null || e.target() == null) {
            throw new IllegalArgumentException("L'arête ou ses nœuds ne peuvent pas être null.");
        }
        addNode(e.source());
        addNode(e.target());
 
        edgesSet.add(e);
        outEdgesMap.get(e.source()).add(e);
        inEdgesMap.get(e.target()).add(e);

        return e;
    }

    @Override
    public IEdge addEdge(INode src, INode tgt) {
        IEdge newEdge = new Edge(src, tgt);
        return addEdge(newEdge);
    }

    @Override
    public void delNode(INode n) {
        if (nodesSet.contains(n)) {
            ArrayList<IEdge> edges = getInOutEdges(n);
            for (IEdge e : edges) {
                delEdge(e);
            }
            nodesSet.remove(n);
            outEdgesMap.remove(n);
            inEdgesMap.remove(n);
        }
    }

    @Override
    public void delEdge(IEdge e) {
        if (edgesSet.contains(e)) {
            edgesSet.remove(e);
            outEdgesMap.get(e.source()).remove(e);
            inEdgesMap.get(e.target()).remove(e);
        }
    }

    @Override
    public int numberOfNodes() {
        return nodesSet.size();
    }

    @Override
    public int numberOfEdges() {
        return edgesSet.size();
    }

    @Override
    public ArrayList<INode> getNeighbors(INode n) {
        HashSet<INode> neighbors = new HashSet<>();
        neighbors.addAll(getSuccessors(n));
        neighbors.addAll(getPredecessors(n));
        return new ArrayList<>(neighbors);
    }

    @Override
    public ArrayList<INode> getSuccessors(INode n) {
        checkContainsNode(n);
        HashSet<INode> successors = new HashSet<>();
        for (IEdge e : getOutEdges(n)) {
            successors.add(e.target());
        }
        return new ArrayList<>(successors);
    }

    @Override
    public ArrayList<INode> getPredecessors(INode n) {
        checkContainsNode(n);
        HashSet<INode> predecessors = new HashSet<>();
        for (IEdge e : getInEdges(n)) {
            predecessors.add(e.source());
        }
        return new ArrayList<>(predecessors);
    }

    @Override
    public ArrayList<IEdge> getInOutEdges(INode n) {
        HashSet<IEdge> inOutEdges = new HashSet<>();
        inOutEdges.addAll(getInEdges(n));
        inOutEdges.addAll(getOutEdges(n));
        return new ArrayList<>(inOutEdges);
    }

    @Override
    public ArrayList<IEdge> getInEdges(INode n) {
        checkContainsNode(n);
        HashSet<IEdge> findEdge = inEdgesMap.get(n);
        return new ArrayList<>(findEdge);
    }

    @Override
    public ArrayList<IEdge> getOutEdges(INode n) {
        checkContainsNode(n);
        HashSet<IEdge> findEdge = outEdgesMap.get(n);
        return new ArrayList<>(findEdge);

    }

    @Override
    public ArrayList<INode> getNodes() {
        return new ArrayList<>(nodesSet);
    }

    @Override
    public ArrayList<IEdge> getEdges() {
        return new ArrayList<>(edgesSet);
    }

    @Override
    public INode source(IEdge e) {
        return e.source();
    }

    @Override
    public INode target(IEdge e) {
        return e.target();
    }

    @Override
    public int inDegree(INode n) {
        checkContainsNode(n);
        return inEdgesMap.get(n).size();
    }

    @Override
    public int outDegree(INode n) {
        checkContainsNode(n);
        return outEdgesMap.get(n).size();
    }

    @Override
    public int degree(INode n) {
        return inDegree(n) + outDegree(n);
    }

    @Override
    public boolean existEdge(INode src, INode tgt, boolean oriented) {
        return getEdge(src, tgt, oriented) != null;
    }

    @Override
    public IEdge getEdge(INode src, INode tgt, boolean oriented) {
        checkContainsNode(src); 
        checkContainsNode(tgt);
        if (outEdgesMap.containsKey(src)) {
            for (IEdge e : outEdgesMap.get(src)) {
                if (e.target().equals(tgt)) {
                    return e;
                }
            }
        }
        if (!oriented && outEdgesMap.containsKey(tgt)) {
            for (IEdge e : outEdgesMap.get(tgt)) {
                if (e.target().equals(src)) {
                    return e;
                }
            }
        }
        return null;
    }

    @Override
    public SizeProperty getSizes() {
        return sizes;
    }

    @Override
    public Size getNodeSize(INode n) {
        checkContainsNode(n);
        Size s = sizes.getNodeValue(n);
        if (s == null) {
            return SizeProperty.DEFAULT_NODE_SIZE;
        }
        return s;
    }

    @Override
    public Double getEdgeWidth(IEdge e) {
        checkContainsEdge(e);
        Double w = sizes.getEdgeValue(e);
        if (w == null) {
            return SizeProperty.DEFAULT_EDGE_WIDTH;
        }
        return w;
    }

    @Override
    public void setNodeSize(INode n, Size s) {
        checkContainsNode(n);
        sizes.setNodeValue(n, s);
    }

    @Override
    public void setEdgeWidth(IEdge e, Double width) {
        checkContainsEdge(e);
        sizes.setEdgeValue(e, width);
    }

    //REVOIR
    @Override
    public void setAllNodesSizes(Size s) {
        for (INode n : nodesSet) {
            sizes.setNodeValue(n, s);
        }
    }

    //REVOIR
    @Override
    public void setAllEdgesWidths(Double width) {
        for (IEdge e : edgesSet) {
            sizes.setEdgeValue(e, width);
        }
    }

    @Override
    public LayoutProperty getLayout() {
        return layout;
    }

    @Override
    public Coord getNodePosition(INode n) {
        checkContainsNode(n);
        Coord c = layout.getNodeValue(n);
        if (c == null) {
            return LayoutProperty.DEFAULT_NODE_POS;
        }
        return c;
    }

    @Override
    public ArrayList<Coord> getEdgePosition(IEdge e) {
        checkContainsEdge(e);
        ArrayList<Coord> bends = layout.getEdgeValue(e);
        if (bends == null) {
            return new ArrayList<>();
        }
        return bends;
    }

    @Override
    public void setNodePosition(INode n, Coord c) {
        checkContainsNode(n);
        layout.setNodeValue(n, c);

    }

    @Override
    public void setEdgePosition(IEdge e, ArrayList<Coord> bends) {
        checkContainsEdge(e);
        layout.setEdgeValue(e, bends);
    }

    @Override
    public void setAllNodesPositions(Coord c) {
        layout.setAllNodesValues(c);
    }

    @Override
    public void setAllEdgesPositions(ArrayList<Coord> bends) {
        layout.setAllEdgesValues(bends);
    }

    @Override
    public ArrayList<Coord> getBoundingBox() {
        if (nodesSet.isEmpty()) {
            throw new IllegalStateException("Impossible de calculer la Bounding Box : le graphe est vide.");
        }
        double minX = Double.MAX_VALUE;
        double maxX = Double.MIN_VALUE;
        double minY = Double.MAX_VALUE;
        double maxY = Double.MIN_VALUE;
        for (INode n : nodesSet) {
            Coord pos = getNodePosition(n);
            if (pos.getX() < minX) {
                minX = pos.getX();
            }
            if (pos.getX() > maxX) {
                maxX = pos.getX();
            }
            if (pos.getY() < minY) {
                minY = pos.getY();
            }
            if (pos.getY() > maxY) {
                maxY = pos.getY();
            }
        }
        ArrayList<Coord> boundingBox = new ArrayList<>();
        boundingBox.add(new Coord(minX - 10, minY - 10));
        boundingBox.add(new Coord(maxX + 10, maxY + 10));
        return boundingBox;
    }

    @Override
    public ColorProperty getColor() {
        return colors;
    }

    @Override
    public Color getNodeColor(INode n) {
        checkContainsNode(n);
        Color c = colors.getNodeValue(n);
        if (c == null) {
            return ColorProperty.DEFAULT_NODE_COL;
        }
        return c;
    }

    @Override
    public Color getEdgeColor(IEdge e) {
        checkContainsEdge(e);
        Color c = colors.getEdgeValue(e);
        if (c == null) {
            return ColorProperty.DEFAULT_EDGE_COL;
        }
        return c;
    }

    @Override
    public void setNodeColor(INode n, Color c) {
        checkContainsNode(n);
        colors.setNodeValue(n, c);
    }

    @Override
    public void setEdgeColor(IEdge e, Color c) {
        checkContainsEdge(e);
        colors.setEdgeValue(e, c);
    }

    @Override
    public void setAllNodesColors(Color c) {
        colors.setAllNodesValues(c);
    }

    @Override
    public void setAllEdgesColors(Color c) {
        colors.setAllEdgesValues(c);
    }

    @Override
    public void welshAndPowell() {
        WelshAndPowell algo = new WelshAndPowell();
        ColorProperty color = algo.apply(this, null);
        for (INode n : this.getNodes()) {
            Color c = color.getNodeValue(n);
            if (c != null) {
                this.setNodeColor(n, c);
            }
        }
    }

    @Override
    public void sixColors() {
        SixColorationPlanarAlgorithm algo = new SixColorationPlanarAlgorithm();
        ColorProperty color = algo.apply(this, null);
        for (INode n : this.getNodes()) {
            Color c = color.getNodeValue(n);
            if (c != null) {
                this.setNodeColor(n, c);
            }
        }
    }

    @Override
    public void fiveColors() {
        FiveColorationPlanarAlgorithm algo = new FiveColorationPlanarAlgorithm();
        ColorProperty color = algo.apply(this, null);
        for (INode n : this.getNodes()) {
            Color c = color.getNodeValue(n);
            if (c != null) {
                this.setNodeColor(n, c);
            }
        }
    }
    
    private void checkContainsNode(INode n) {
        if (n == null || !nodesSet.contains(n)) {
        throw new IllegalArgumentException("Le nœud n'appartient pas à ce graphe.");
        }
    }
    private void checkContainsEdge(IEdge e) {
        if (e == null || !edgesSet.contains(e)) {
        throw new IllegalArgumentException("L'arrete n'appartient pas à ce graphe.");
        }
    }
}

