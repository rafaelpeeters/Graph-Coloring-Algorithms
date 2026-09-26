package graphTest;

import info.iut.sae2.graphs.Size;

import org.junit.Test;
import static org.junit.Assert.*;

public class SizeTest {

    @Test
    public void testConstructors() {
        // default constructor test
        Size s1 = new Size();
        assertEquals(0.0, s1.getW(), 0.001);
        assertEquals(0.0, s1.getH(), 0.001);

        // parameter constructor test
        Size s2 = new Size(1.0, 2.0);
        assertEquals(1.0, s2.getW(), 0.001);
        assertEquals(2.0, s2.getH(), 0.001);

        // copy constructor test
        Size s3 = new Size(s2);
        assertEquals(1.0, s3.getW(), 0.001);
        assertEquals(2.0, s3.getH(), 0.001);
    }
    
    @Test
    public void testToString() {
        Size s = new Size(1.0, 2.0);
        assertEquals("(1.0, 2.0)", s.toString());
    }
    
}