package pl.data.model.classes;

import org.junit.Test;
import pl.data.model.enums.AntState;
import pl.data.model.enums.BinaryState;

import static org.junit.Assert.assertEquals;

/**
 * Created by talik on 07.12.15.
 */
public class LangtonCellTest {

    @Test
    public void testNextState() throws Exception {
        LangtonCell langtonCell = new LangtonCell(BinaryState.ALIVE,1, AntState.NORTH);
        assertEquals(AntState.WEST,langtonCell.nextState());
    }
}