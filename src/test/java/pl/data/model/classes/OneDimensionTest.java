package pl.data.model.classes;

import org.junit.Test;
import pl.data.model.enums.BinaryState;
import pl.data.model.interfaces.CellCoordinates;
import pl.data.model.interfaces.CellState;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Created by talik on 10.12.15.
 */
public class OneDimensionTest {

    @Test
    public void makeRuleTest() throws Exception {
        int width = 4;
        UniformStateFactory stateFactory = new UniformStateFactory(BinaryState.DEAD);

        OneDimension game = new OneDimension(stateFactory,width,30);
        assertEquals(30,game.getRule());
    }

    @Test
    public void getIDNumberTest() throws Exception {
        int width = 4;
        UniformStateFactory stateFactory = new UniformStateFactory(BinaryState.DEAD);

        OneDimension game = new OneDimension(stateFactory,width,30);
        Map<CellCoordinates,CellState> tab = new HashMap<CellCoordinates, CellState>();
        tab.put(new Coords1D(1),BinaryState.ALIVE);
        tab.put(new Coords1D(0),BinaryState.ALIVE);
        game.insertStructure(tab);


        Set<CellCoordinates> neighborsCoords = game.getNeighborsStrategy().cellNeighbors(new Coords1D(1));
        Set<Cell> neighbors = game.mapCoordinates(neighborsCoords);
        assertEquals(6,game.idNumber(neighbors));
    }

    @Test
    public void newStateTest() throws Exception {
        int width = 4;
        UniformStateFactory stateFactory = new UniformStateFactory(BinaryState.DEAD);

        OneDimension game = new OneDimension(stateFactory,width,30);
        Map<CellCoordinates,CellState> tab = new HashMap<CellCoordinates, CellState>();
        tab.put(new Coords1D(1),BinaryState.ALIVE);
        tab.put(new Coords1D(0),BinaryState.ALIVE);
        game.insertStructure(tab);

        Set<Cell> neighbors = game.mapCoordinates(game.getNeighborsStrategy().cellNeighbors(new Coords1D(1)));
        Set<Cell> neighbors1 = game.mapCoordinates(game.getNeighborsStrategy().cellNeighbors(new Coords1D(2)));
        assertEquals(BinaryState.DEAD,game.newState(new Cell(game.cells.get(new Coords1D(1)),new Coords1D(1)),neighbors));
        assertEquals(BinaryState.ALIVE,game.newState(new Cell(game.cells.get(new Coords1D(2)),new Coords1D(2)),neighbors1));

    }

    @Test
    public void nextStateTest() throws Exception {
        int width = 4;
        UniformStateFactory stateFactory = new UniformStateFactory(BinaryState.DEAD);

        OneDimension game = new OneDimension(stateFactory,width,30);
        Map<CellCoordinates,CellState> tab = new HashMap<CellCoordinates, CellState>();
        tab.put(new Coords1D(1),BinaryState.ALIVE);
        tab.put(new Coords1D(3),BinaryState.ALIVE);
        game.insertStructure(tab);

        OneDimension newgame = (OneDimension) game.nextState();

        tab.put(new Coords1D(0),BinaryState.ALIVE);
        game.insertStructure(tab);
        assertEquals(game.cells,newgame.cells);
    }
}