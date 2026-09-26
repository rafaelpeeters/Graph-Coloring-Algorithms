package graphTest;

import info.iut.sae2.graphs.Coord;

import org.junit.Test;
import static org.junit.Assert.*;

public class CoordTest {

   @Test
    public void testConstructors() {
        // default constructor test
        Coord c1 = new Coord();
        assertEquals(0.0, c1.getX(), 0.001);
        assertEquals(0.0, c1.getY(), 0.001);

        // parameter constructor test
        Coord c2 = new Coord(1.0, 2.0);
        assertEquals(1.0, c2.getX(), 0.001);
        assertEquals(2.0, c2.getY(), 0.001);

        // copy constructor test
        Coord c3 = new Coord(c2);
        assertEquals(1.0, c3.getX(), 0.001);
        assertEquals(2.0, c3.getY(), 0.001);
    }
    
    @Test
    public void testDist() {
        Coord c1 = new Coord(1.0, 1.0);
        Coord c2 = new Coord(2.0, 2.0);
        assertEquals(0.0, c1.dist(c1), 0.001);
        // dist : sqrt((1.0-2.0) * (1.0-2.0) + (1.0-2.0) * (1.0-2.0)) ≈ 1.414
        assertEquals(1.414, c1.dist(c2), 0.001);
    }
    
    @Test
    public void testNorm() {
        Coord c1 = new Coord(0.0, 0.0);
        Coord c2 = new Coord(1.0, 2.0);
        assertEquals(0.0, c1.norm(), 0.001);
        // norm : sqrt((1.0*1.0) + (2.0*2.0)) ≈ 2.236
        assertEquals(2.236, c2.norm(), 0.001);
    }
    @Test
    public void testMult() {
        Coord c1 = new Coord(1.0, 2.0);
        c1.mult(2.0);
        // c1's values got modified 
        assertEquals(2.0, c1.getX(), 0.001);
        assertEquals(4.0, c1.getY(), 0.001);
        
        //new coord is returned
        Coord c2 = Coord.mult(c1, 2.0);
        assertEquals(4.0, c2.getX(), 0.001);
        assertEquals(8.0, c2.getY(), 0.001);
        
        // c1's values stayed the same
        assertEquals(2.0, c1.getX(), 0.001);
        assertEquals(4.0, c1.getY(), 0.001);
    }
    @Test
    public void testAdd() {
        Coord c1 = new Coord(1.0, 2.0);
        c1.add(1.0, 2.0);
        //c1's values got modified
        assertEquals(2.0, c1.getX(), 0.001);
        assertEquals(4.0, c1.getY(), 0.001);

        Coord c2 = new Coord(2.0, 4.0);
        c1.add(c2);
        //c1's values got modified again
        assertEquals(4.0, c1.getX(), 0.001);
        assertEquals(8.0, c1.getY(), 0.001);
        
        //new cord is returned
        Coord c3 = Coord.add(c1,c2);
        assertEquals(6.0, c3.getX(), 0.001);
        assertEquals(12.0, c3.getY(), 0.001);
        
        //c1's values stayed the same
        assertEquals(4.0, c1.getX(), 0.001);
        assertEquals(8.0, c1.getY(), 0.001);
        
    }
    
    @Test
    public void testMinus() {
        Coord c1 = new Coord(4.0, 2.0);
        Coord c2 = new Coord(2.0, 1.0);
        
        // new coord is returned
        Coord c3 = Coord.minus(c1, c2);
        assertEquals(2.0, c3.getX(), 0.001);
        assertEquals(1.0, c3.getY(), 0.001);

        // c1's values stayed the same
        assertEquals(4.0, c1.getX(), 0.001);
        assertEquals(2.0, c1.getY(), 0.001);
    }
    
    @Test
    public void testToString() {
        Coord c = new Coord(1.0, 2.0);
        assertEquals("(1.0, 2.0)", c.toString());
    }
    
}