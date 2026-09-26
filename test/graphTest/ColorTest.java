package graphTest;

import info.iut.sae2.graphs.Color;

import org.junit.Test;
import static org.junit.Assert.*;

public class ColorTest {

    @Test
    public void testConstructors() {
        // default constructor test
        Color c1 = new Color();
        assertEquals(0, c1.getR());
        assertEquals(0, c1.getG());
        assertEquals(0, c1.getB());
        assertEquals(0, c1.getA());

        // parameter constructor test
        Color c2 = new Color(50, 100, 150, 200);
        assertEquals(50, c2.getR());
        assertEquals(100, c2.getG());
        assertEquals(150, c2.getB());
        assertEquals(200, c2.getA());

        // copy constructor test
        Color c3 = new Color(c2);
        assertEquals(50, c3.getR());
        assertEquals(100, c3.getG());
        assertEquals(150, c3.getB());
        assertEquals(200, c3.getA());
    }

    @Test
    public void testInterpolate() {
        Color start = new Color(50, 100, 150, 200);
        Color end = new Color(150, 200, 250, 100);

        Color result = Color.interpolate(start, end, 1, 2);

        // R : 50+(150-50)*1/2 = 100
        assertEquals(100, result.getR());
        // G : 100+(200-100)*1/2 = 150
        assertEquals(150, result.getG());

        // B : 150+(250-150)*1/2 = 200
        assertEquals(200, result.getB());

        // A : 200+(100-200)*1/2 = 150
        assertEquals(150, result.getA());
    }

    @Test
    public void testToString() {
        Color c = new Color(50, 100, 150, 200);
        assertEquals("(50, 100, 150, 200)", c.toString());
    }

}