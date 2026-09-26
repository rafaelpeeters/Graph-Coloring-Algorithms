package info.iut.sae2.properties;

import info.iut.sae2.graphs.Color;
import info.iut.sae2.graphs.IEdge;
import info.iut.sae2.graphs.INode;
import java.util.Map.Entry;

/**
 * @author rbourqui
 * @author Peeters
 * @author Carpentier
 */
public class ColorProperty extends AbstractProperty<Color, Color>{

    final public static Color DEFAULT_NODE_COL = new Color(255,0,0,255);
    
    final public static Color DEFAULT_EDGE_COL = new Color(0,0,0,60);
    
    
    private static final Color[] PALETTE = {
        new Color(255, 0, 0, 255), // Rouge
        new Color(0, 0, 255, 255), // Bleu
        new Color(0, 255, 0, 255), // Vert
        new Color(255, 255, 0, 255), // Jaune
        new Color(255, 165, 0, 255), // Orange
        new Color(128, 0, 128, 255), // Violet
        new Color(0, 255, 255, 255), // Cyan
        new Color(255, 0, 255, 255), // Magenta
        new Color(255, 192, 203, 255),// Rose
        new Color(139, 69, 19, 255) // Marron
    };
    
    /**
     * Default constructor of ColorProperty
     */
    public ColorProperty() { }

    /** 
     * Copy constructor. Each value of the given ColorProperty are copied before being associated to the nodes and edges.
     * 
     * @param color the ColorProperty to be copied
     */
    public ColorProperty(ColorProperty color) {
        for (Entry e : color.nodesValues.entrySet()) {
            nodesValues.put((INode) e.getKey(), new Color((Color) e.getValue()));
        }
        for (Entry e : color.edgesValues.entrySet()) {
            edgesValues.put((IEdge) e.getKey(), new Color((Color) e.getValue()));
        }
    }

    public Color[] getPalette(){
        return PALETTE;
    }
    
    @Override
    public void setNodeValue(INode n, Color col) {
        nodesValues.put(n, new Color(col));
    }

    @Override
    public void setEdgeValue(IEdge e, Color col) {
        edgesValues.put(e, new Color(col));
    }
    
}