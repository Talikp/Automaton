package pl.data.model.classes;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by talik on 07.12.15.
 */
public class Coords2DTest {


    @Test
    public void testEqualsAndToClone() throws Exception {
        Coords2D coord1 = new Coords2D(2,3);
        Coords2D coord2 = new Coords2D(2,3);
        Coords2D coord3 = coord1.toclone();
        assertEquals(true,coord1.equals(coord2));
        assertEquals(true,coord1.equals(coord3));
    }
}