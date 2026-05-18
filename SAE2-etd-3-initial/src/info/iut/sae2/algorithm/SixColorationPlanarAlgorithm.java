package info.iut.sae2.algorithm;

import info.iut.sae2.graphs.IGraph;
import info.iut.sae2.graphs.Color;
import info.iut.sae2.properties.ColorProperty;
import java.util.Map;

public class SixColorationPlanarAlgorithm implements Algorithm<ColorProperty> {

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

    @Override
    public ColorProperty apply(IGraph g, Map<String, Object> parameters) {
        ColorProperty result = new ColorProperty();
        return result;
    }
}
