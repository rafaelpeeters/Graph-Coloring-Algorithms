package info.iut.sae2.graphs;

import java.util.ArrayList;

import info.iut.sae2.properties.ColorProperty;
import info.iut.sae2.properties.LayoutProperty;
import info.iut.sae2.properties.SizeProperty;

public class Graph implements IGraph{

    public ArrayList<IEdge> edgesList;
    public ArrayList<INode> nodesList;

    public Graph() {
        edgesList = new ArrayList<>();
        nodesList = new ArrayList<>();
    }

    @Override
    public IGraph createGraph() {
        return new Graph(); 
    }

    @Override
    public IGraph copy() {
        return new Graph();
    }

    @Override
    public INode addNode(){
        return nodesList.get(0);
    }
    
    @Override
    public INode addNode(INode n){
        nodesList.add(n);
        return n;
    }

    @Override
    public IEdge addEdge(IEdge e){
        edgesList.add(e);
        return e;
    }

    @Override
    public IEdge addEdge(INode src, INode tgt){
        return edgesList.get(0);
    }
    @Override
    public void delNode(INode n){
        nodesList.remove(n);
    }
    @Override
    public void delEdge(IEdge e){
        edgesList.remove(e);
    }

    @Override
    public int numberOfNodes(){
        return nodesList.size();
        }

    @Override
    public int numberOfEdges(){
        return edgesList.size();
    }

    @Override
    public ArrayList<INode> getNeighbors(INode n){
        return nodesList;
    }

    @Override
    public ArrayList<INode> getSuccesors(INode n){
        return nodesList;
    }

    @Override
    public ArrayList<INode> getPredecessors(INode n){
        return nodesList;
    }

    @Override
    public ArrayList<IEdge> getInOutEdges(INode n){
        return edgesList;
    }

    @Override
    public ArrayList<IEdge> getInEdges(INode n){
        return edgesList;
    }

    @Override
    public ArrayList<IEdge> getOutEdges(INode n){
        return edgesList;
    }

    @Override
    public ArrayList<INode> getNodes(){
        return nodesList;
    }

    @Override
    public ArrayList<IEdge> getEdges(){
        return edgesList;
    }

    @Override
    public INode source(IEdge e){
        return nodesList.get(0);
    }

    @Override
    public INode target(IEdge e){
        return nodesList.get(0);
    }

    @Override
    public int inDegree(INode n){
        return 0;
    }

    @Override
    public int outDegree(INode n){
        return 0;
    }

    @Override
    public int degree(INode n){
        return 0;
    }

    @Override
    public boolean existEdge(INode src, INode tgt, boolean oriented){
        return true;
    }

    @Override
    public IEdge getEdge(INode src, INode tgt, boolean oriented){
        return edgesList.get(0);
    }

    @Override
    public SizeProperty getSizes(){
        return new SizeProperty();
    }

    @Override
    public Size getNodeSize(INode n){
        return new Size();
    }

    @Override
    public Double getEdgeWidth(IEdge e){
        return  0.0;
    }

    @Override
    public void setNodeSize(INode n, Size s){
        System.out.println("HelloWord");
    }

    @Override
    public void setEdgeWidth(IEdge e, Double width){
        System.out.println("HelloWord");
    }
    @Override
    public void setAllNodesSizes(Size s){
        System.out.println("HelloWord");
    }

    @Override
    public void setAllEdgesWidths(Double width){
        System.out.println("HelloWord");
    }

    @Override
    public LayoutProperty getLayout(){
        return new LayoutProperty();
    }

    @Override
    public Coord getNodePosition(INode n){
        return new Coord();
    }

    @Override
    public ArrayList<Coord> getEdgePosition(IEdge e){
        return new ArrayList<Coord>();
    }

    @Override
    public void setNodePosition(INode n, Coord c){
        System.out.println("HelloWord");

    }

    @Override
    public void setEdgePosition(IEdge e, ArrayList<Coord> bends){
        System.out.println("HelloWord");

    }

    @Override
    public void setAllNodesPositions(Coord c){
        System.out.println("HelloWord");
    }

    @Override
    public void setAllEdgesPositions(ArrayList<Coord> bends){
        System.out.println("HelloWord");
    }

    @Override
    public ArrayList<Coord> getBoundingBox(){
        return new ArrayList<Coord>();
    }

    @Override
    public ColorProperty getColor(){
        return new ColorProperty();
    }

    @Override
    public Color getNodeColor(INode n){
        return new Color();
    }

    @Override
    public Color getEdgeColor(IEdge e){
        return new Color();
    }

    @Override
    public void setNodeColor(INode n, Color c){
        System.out.println("HelloWord");
    }
    @Override
    public void setEdgeColor(IEdge e, Color c){
        System.out.println("HelloWord");
    }

    @Override
    public void setAllNodesColors(Color c){
        System.out.println("HelloWord");
    }

    @Override
    public void setAllEdgesColors(Color c){
        System.out.println("HelloWord");
    }
    @Override
    public void welshAndPowell(){
        System.out.println("HelloWord");
    }
    
    @Override
    public void sixColors(){
        System.out.println("HelloWord");
    }
    
    @Override 
    public void fiveColors(){
        System.out.println("HelloWord");
    }
}

