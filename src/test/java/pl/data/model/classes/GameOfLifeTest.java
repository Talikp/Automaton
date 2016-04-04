package pl.data.model.classes;

import org.junit.Test;
import pl.data.model.enums.BinaryState;
import pl.data.model.interfaces.CellCoordinates;
import pl.data.model.interfaces.CellState;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Created by talik on 09.12.15.
 */
public class GameOfLifeTest {
    @Test
    public void newStateTest() throws Exception {   // testuje newState
        int width = 4;
        int height = 4;
        UniformStateFactory stateFactory = new UniformStateFactory(BinaryState.DEAD);
        MoorNieghborhood neighboorStrategy = new MoorNieghborhood(width,height,false);
        GameOfLife game = new GameOfLife(stateFactory,neighboorStrategy,width,height);
        Map<CellCoordinates,CellState> tab = new HashMap<CellCoordinates, CellState>();

        tab.put(new Coords2D(1,1),BinaryState.ALIVE);
        tab.put(new Coords2D(2,1),BinaryState.ALIVE);
        tab.put(new Coords2D(3,1),BinaryState.ALIVE);
        game.insertStructure(tab);
        Set<Cell> neighbors = game.mapCoordinates(game.getNeighborsStrategy().cellNeighbors(new Coords2D(2,2)));
        Set<Cell> expected = new HashSet<Cell>();
        expected.add(new Cell(BinaryState.ALIVE,new Coords2D(1,1)));
        expected.add(new Cell(BinaryState.ALIVE,new Coords2D(2,1)));

        expected.add(new Cell(BinaryState.DEAD,new Coords2D(1,2)));
        expected.add(new Cell(BinaryState.DEAD,new Coords2D(1,3)));
        expected.add(new Cell(BinaryState.DEAD,new Coords2D(2,3)));
        expected.add(new Cell(BinaryState.ALIVE,new Coords2D(3,1)));
        expected.add(new Cell(BinaryState.DEAD,new Coords2D(3,2)));
        expected.add(new Cell(BinaryState.DEAD,new Coords2D(3,3)));

        assertEquals(expected,neighbors);
        assertEquals(BinaryState.ALIVE,game.newState(new Cell(BinaryState.DEAD,new Coords2D(2,2)),neighbors));

    }

    @Test
    public void nextStateSquareTest() throws Exception {
        int width = 10;
        int height = 10;
        UniformStateFactory stateFactory = new UniformStateFactory(BinaryState.DEAD);
        MoorNieghborhood neighboorStrategy = new MoorNieghborhood(width,height,false);
        GameOfLife game = new GameOfLife(stateFactory,neighboorStrategy,width,height);
        Map<CellCoordinates,CellState> tab = new HashMap<CellCoordinates, CellState>();

        tab.put(new Coords2D(1,1),BinaryState.ALIVE);
        tab.put(new Coords2D(2,1),BinaryState.ALIVE);
        tab.put(new Coords2D(2,2),BinaryState.ALIVE);
        tab.put(new Coords2D(1,2),BinaryState.ALIVE);
        game.insertStructure(tab);

        GameOfLife newgame = (GameOfLife) game.nextState();


        assertEquals(game.cells,newgame.cells);


    }

    @Test
    public void nextStateOscillatorTest() throws Exception {
        int width = 10;
        int height = 10;
        UniformStateFactory stateFactory = new UniformStateFactory(BinaryState.DEAD);
        MoorNieghborhood neighboorStrategy = new MoorNieghborhood(width,height,false);
        GameOfLife game = new GameOfLife(stateFactory,neighboorStrategy,width,height);
        GameOfLife game1 = (GameOfLife) game.newInstance(stateFactory,neighboorStrategy);
        Map<CellCoordinates,CellState> tab = new HashMap<CellCoordinates, CellState>();

        tab.put(new Coords2D(1,2),BinaryState.ALIVE);
        tab.put(new Coords2D(2,2),BinaryState.ALIVE);
        tab.put(new Coords2D(3,2),BinaryState.ALIVE);
        game.insertStructure(tab);
        GameOfLife newgame = (GameOfLife) game.nextState();

        Map<CellCoordinates,CellState> tab1 = new HashMap<CellCoordinates, CellState>();
        tab1.put(new Coords2D(2,1),BinaryState.ALIVE);
        tab1.put(new Coords2D(2,2),BinaryState.ALIVE);
        tab1.put(new Coords2D(2,3),BinaryState.ALIVE);

        game1.insertStructure(tab1);


        assertEquals(game1.cells,newgame.cells);


    }
}