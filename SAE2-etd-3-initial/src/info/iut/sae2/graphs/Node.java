package info.iut.sae2.graphs;

public class Node implements INode{
    private static int cpt = 0;
    private final int id;

    public Node() {
        this.id = cpt++;
    }

    public int getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Node n°" + id;
    }
}
