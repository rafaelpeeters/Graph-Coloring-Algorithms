package info.iut.sae2.algorithm;

import info.iut.sae2.graphs.IGraph;
import info.iut.sae2.graphs.Color;
import info.iut.sae2.properties.ColorProperty;
import java.util.Map;

public class SixColorationPlanarAlgorithm implements Algorithm<ColorProperty> {

    @Override
    public ColorProperty apply(IGraph g, Map<String, Object> parameters) {
        ColorProperty result = new ColorProperty();
        Color[] palette = result.getPalette();
        return result;
    }
}
