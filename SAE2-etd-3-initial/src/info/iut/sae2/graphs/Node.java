package info.iut.sae2.graphs;

public class Node implements INode{

    private final Coord coord;
    private final Size size;
    private final Color color;
    private final int degree;

    public Node(Coord coord, Size size, Color color, int degree){
        this.coord = coord;
        this.size = size;
        this.color = color;
        this.degree = degree;
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

    public int degree() {
        return this.degree;
    }
}
