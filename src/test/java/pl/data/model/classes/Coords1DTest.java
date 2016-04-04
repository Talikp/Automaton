package pl.data.model.classes;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by talik on 07.12.15.
 */
public class Coords1DTest {

    @Test
    public void testEqualsAndToClone() throws Exception {
        Coords1D coord1 = new Coords1D(2);
        Coords1D coord2 = new Coords1D(2);
        Coords1D coord3 = coord1.toclone();
        assertEquals(true,coord1.equals(coord2));
        assertEquals(true,coord1.equals(coord3));
    }
}