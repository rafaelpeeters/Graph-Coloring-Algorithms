package info.iut.sae2.graphs;

import info.iut.sae2.properties.ColorProperty;
import info.iut.sae2.properties.LayoutProperty;
import info.iut.sae2.properties.SizeProperty;
import java.util.ArrayList;
import java.util.HashSet;

public class Graph implements IGraph {

    private ArrayList<IEdge> edgesList;
    private ArrayList<INode> nodesList;
    private final SizeProperty sizes;
    private final LayoutProperty layout;
    private final ColorProperty colors;

    public Graph() {
        edgesList = new ArrayList<>();
        nodesList = new ArrayList<>();
        sizes = new SizeProperty();
        layout = new LayoutProperty();
        colors = new ColorProperty();
    }

    @Override
    public IGraph createGraph() {
        return new Graph();
    }

    @Override
    public IGraph copy() {
        Graph newGraph = new Graph();
        newGraph.edgesList = new ArrayList<>(this.edgesList);
        newGraph.nodesList = new ArrayList<>(this.nodesList);
        return newGraph;
    }

    @Override
    public INode addNode() {
        INode newNode = new Node();
        nodesList.add(newNode);
        return newNode;
    }

    @Override
    public INode addNode(INode n) {
        nodesList.add(n);
        return n;
    }

    @Override
    public IEdge addEdge(IEdge e) {
        edgesList.add(e);
        return e;
    }

    @Override
    public IEdge addEdge(INode src, INode tgt) {
        IEdge newEdge = new Edge(src, tgt);
        edgesList.add(newEdge);
        return newEdge;
    }

    @Override
    public void delNode(INode n) {
        nodesList.remove(n);
    }

    @Override
    public void delEdge(IEdge e) {
        edgesList.remove(e);
    }

    @Override
    public int numberOfNodes() {
        return nodesList.size();
    }

    @Override
    public int numberOfEdges() {
        return edgesList.size();
    }

    @Override
    public ArrayList<INode> getNeighbors(INode n) {
        HashSet<INode> neighbors = new HashSet<>();
        neighbors.addAll(getSuccesors(n));
        neighbors.addAll(getPredecessors(n));
        return new ArrayList<>(neighbors);
    }

    @Override
    public ArrayList<INode> getSuccesors(INode n) {
        ArrayList<INode> successors = new ArrayList<>();
        for (IEdge e : edgesList) {
            if (e.source().equals(n)) {
                successors.add(e.target());
            }
        }
        return successors;
    }

    @Override
    public ArrayList<INode> getPredecessors(INode n) {
        ArrayList<INode> predecessors = new ArrayList<>();
        for (IEdge e : edgesList) {
            if (e.target().equals(n)) {
                predecessors.add(e.source());
            }
        }
        return predecessors;
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
        ArrayList<IEdge> inEdges = new ArrayList<>();
        for (IEdge e : edgesList) {
            if (e.target().equals(n)) {
                inEdges.add(e);
            }
        }
        return inEdges;
    }

    @Override
    public ArrayList<IEdge> getOutEdges(INode n) {
        ArrayList<IEdge> outEdges = new ArrayList<>();
        for (IEdge e : edgesList) {
            if (e.source().equals(n)) {
                outEdges.add(e);
            }
        }
        return outEdges;
    }

    @Override
    public ArrayList<INode> getNodes() {
        return nodesList;
    }

    @Override
    public ArrayList<IEdge> getEdges() {
        return edgesList;
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
        return getInEdges(n).size();
    }

    @Override
    public int outDegree(INode n) {
        return getOutEdges(n).size();
    }

    @Override
    public int degree(INode n) {
        return getInOutEdges(n).size();
    }

    @Override
    public boolean existEdge(INode src, INode tgt, boolean oriented) {
        boolean trouve = false;
        int i = 0;
        while (!trouve && i < edgesList.size()) {
            IEdge e = edgesList.get(i);
            if (oriented && e.source().equals(src) && e.target().equals(tgt)) {
                trouve = true;
            } else if (!oriented && e.source().equals(tgt) && e.target().equals(src)) {
                trouve = true;
            }
            i++;
        }
        return trouve;
    }

    @Override
    public IEdge getEdge(INode src, INode tgt, boolean oriented) {
        IEdge edge = null;
        int i = 0;
        while (edge == null && i < edgesList.size()) {
            IEdge e = edgesList.get(i);
            if (e.source().equals(src) && e.target().equals(tgt)) {
                edge = e;
            } else if (!oriented && e.source().equals(tgt) && e.target().equals(src)) {
                edge = e;
            }
            i++;
        }
        return edge;
    }

    @Override
    public SizeProperty getSizes() {
        return sizes;
    }

    @Override
    public Size getNodeSize(INode n) {
        Size s = sizes.getNodeValue(n);
        if (s == null) {
            return SizeProperty.DEFAULT_NODE_SIZE;
        }
        return s;
    }

    @Override
    public Double getEdgeWidth(IEdge e) {
        Double w = sizes.getEdgeValue(e);
        if (w == null) {
            return SizeProperty.DEFAULT_EDGE_WIDTH;
        }
        return w;
    }

    @Override
    public void setNodeSize(INode n, Size s) {
        sizes.setNodeValue(n, s);
    }

    @Override
    public void setEdgeWidth(IEdge e, Double width) {
        sizes.setEdgeValue(e, width);
    }
    
    //REVOIR
    @Override
    public void setAllNodesSizes(Size s) {
        for (INode n : nodesList) {
            sizes.setNodeValue(n, s);
        }
    }
    
    //REVOIR
    @Override
    public void setAllEdgesWidths(Double width) {
        for (IEdge e : edgesList) {
            sizes.setEdgeValue(e, width);
        }
    }

    @Override
    public LayoutProperty getLayout() {
        return layout;
    }

    @Override
    public Coord getNodePosition(INode n) {
        Coord c = layout.getNodeValue(n);
        if (c == null) {
            return LayoutProperty.DEFAULT_NODE_POS;
        }
        return c;
    }

    @Override
    public ArrayList<Coord> getEdgePosition(IEdge e) {
        ArrayList<Coord> bends = layout.getEdgeValue(e);
        if (bends == null) {
            return new ArrayList<>();
        }
        return bends;
    }

    @Override
    public void setNodePosition(INode n, Coord c) {
        layout.setNodeValue(n, c);

    }

    @Override
    public void setEdgePosition(IEdge e, ArrayList<Coord> bends) {
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
        double minX = Double.MAX_VALUE;
        double maxX = Double.MIN_VALUE;
        double minY = Double.MAX_VALUE;
        double maxY = Double.MIN_VALUE;

        for (INode n : nodesList) {
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
        boundingBox.add(new Coord(minX-10, minY-10));
        boundingBox.add(new Coord(maxX+10, maxY+10));
        return boundingBox;
    }

    @Override
    public ColorProperty getColor() {
        return colors;
    }

    @Override
    public Color getNodeColor(INode n) {
        Color c = colors.getNodeValue(n);
        if (c == null) {
            return ColorProperty.DEFAULT_NODE_COL;
        }
        return c;
    }

    @Override
    public Color getEdgeColor(IEdge e) {
        Color c = colors.getEdgeValue(e);
        if (c == null) {
            return ColorProperty.DEFAULT_EDGE_COL;
        }
        return c;
    }

    @Override
    public void setNodeColor(INode n, Color c) {
        colors.setNodeValue(n, c);
    }

    @Override
    public void setEdgeColor(IEdge e, Color c) {
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

    // ALGORITHMES ///
    @Override
    public void welshAndPowell() {
        System.out.println("HelloWord");
    }

    @Override
    public void sixColors() {
        System.out.println("HelloWord");
    }

    @Override
    public void fiveColors() {
        System.out.println("HelloWord");
    }
}
