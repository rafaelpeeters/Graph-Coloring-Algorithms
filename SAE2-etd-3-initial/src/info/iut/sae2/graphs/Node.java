package info.iut.sae2.graphs;

public class Node implements INode{

    private final Coord coord;
    private final Size size;
    private final Color color;

    public Node(Coord coord, Size size, Color color){
        this.coord = coord;
        this.size = size;
        this.color = color;
    }

    public Coord coord() {
        return this.coord;
    }

    public Size size() {
        return this.size;
    }
    
    public Color color() {
        return this.color;
    }

}
