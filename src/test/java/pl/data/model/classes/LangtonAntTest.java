package pl.data.model.classes;

import org.junit.Test;
import pl.data.model.enums.AntState;
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
public class LangtonAntTest {

    @Test
    public void newStateWith1AntTest() throws Exception {
        int width = 4;
        int height = 4;
        UniformStateFactory stateFactory = new UniformStateFactory(new LangtonCell(BinaryState.DEAD,0, AntState.NONE));
        LangtonAnt game = new LangtonAnt(stateFactory,false,width,height);
        Map<CellCoordinates,CellState> tab = new HashMap<CellCoordinates, CellState>();
        Coords2D checkedCoord = new Coords2D(2,1);
        Coords2D checkedCoord1 = new Coords2D(1,1);
        LangtonCell testedState1 = new LangtonCell(BinaryState.DEAD,1,AntState.NORTH);
        LangtonCell testedState = new LangtonCell(BinaryState.DEAD,0,AntState.NONE);
        tab.put(checkedCoord1,testedState1);

        game.insertStructure(tab);

        Set<Cell> neighbors = game.mapCoordinates(game.getNeighborsStrategy().cellNeighbors(checkedCoord));
        Set<Cell> neighbors1 = game.mapCoordinates(game.getNeighborsStrategy().cellNeighbors(checkedCoord1));

        assertEquals(new LangtonCell(BinaryState.DEAD,1,AntState.EAST),game.newState(new Cell(testedState,checkedCoord),neighbors));
        assertEquals(new LangtonCell(BinaryState.ALIVE,0,AntState.NONE),game.newState(new Cell(testedState1,checkedCoord1),neighbors1));
    }

    @Test
    public void newStateWithMoreThan1AntTest() throws Exception {
        int width = 4;
        int height = 4;
        UniformStateFactory stateFactory = new UniformStateFactory(new LangtonCell(BinaryState.DEAD,0, AntState.NONE));
        LangtonAnt game = new LangtonAnt(stateFactory,false,width,height);
        Map<CellCoordinates,CellState> tab = new HashMap<CellCoordinates, CellState>();
        Coords2D checkedCoord = new Coords2D(2,1);
        Coords2D checkedCoord1 = new Coords2D(1,1);
        Coords2D checkingAnt = new Coords2D(0,1);
        LangtonCell testedState1 = new LangtonCell(BinaryState.DEAD,1,AntState.NORTH);
        LangtonCell testedState = new LangtonCell(BinaryState.DEAD,3,AntState.SOUTH);
        LangtonCell testingAnt = new LangtonCell(BinaryState.DEAD,2,AntState.NORTH);
        tab.put(checkedCoord1,testedState1);
        tab.put(checkingAnt,testingAnt);
        tab.put(checkedCoord,testedState);
        game.insertStructure(tab);

        Set<Cell> neighbors = game.mapCoordinates(game.getNeighborsStrategy().cellNeighbors(checkedCoord));
        Set<Cell> neighbors1 = game.mapCoordinates(game.getNeighborsStrategy().cellNeighbors(checkedCoord1));
        Set<Cell> neighborsAnt = game.mapCoordinates(game.getNeighborsStrategy().cellNeighbors(checkingAnt));



        assertEquals(new LangtonCell(BinaryState.ALIVE,1,AntState.EAST),game.newState(new Cell(testedState,checkedCoord),neighbors));
        assertEquals(new LangtonCell(BinaryState.ALIVE,0,AntState.NONE),game.newState(new Cell(testedState1,checkedCoord1),neighbors1));
        assertEquals(new LangtonCell(BinaryState.ALIVE,0,AntState.NONE),game.newState(new Cell(testingAnt,checkingAnt),neighborsAnt));

    }

    @Test
    public void nextStateWith1AntTest() throws Exception {

        int width = 4;
        int height = 4;
        UniformStateFactory stateFactory = new UniformStateFactory(new LangtonCell(BinaryState.DEAD,0, AntState.NONE));
        LangtonAnt game = new LangtonAnt(stateFactory,false,width,height);
        Map<CellCoordinates,CellState> tab = new HashMap<CellCoordinates, CellState>();
        Coords2D checkedCoord = new Coords2D(2,1);
        Coords2D checkedCoord1 = new Coords2D(1,1);
        LangtonCell testedState1 = new LangtonCell(BinaryState.DEAD,1,AntState.NORTH);
        LangtonCell testedState = new LangtonCell(BinaryState.DEAD,0,AntState.NONE);
        tab.put(checkedCoord1,testedState1);

        game.insertStructure(tab);

        LangtonAnt newgame = (LangtonAnt) game.nextState();
        Map<CellCoordinates,CellState> tab1 = new HashMap<CellCoordinates, CellState>();
        tab1.put(checkedCoord,new LangtonCell(BinaryState.DEAD,1,AntState.EAST));
        tab1.put(checkedCoord1,new LangtonCell(BinaryState.ALIVE,0,AntState.NONE));
        game.insertStructure(tab1);

        assertEquals(game.cells,newgame.cells);

    }

    @Test
    public void nextStateWith2AntsTest() throws Exception {

        int width = 4;
        int height = 4;
        UniformStateFactory stateFactory = new UniformStateFactory(new LangtonCell(BinaryState.DEAD,0, AntState.NONE));
        LangtonAnt game = new LangtonAnt(stateFactory,false,width,height);
        Map<CellCoordinates,CellState> tab = new HashMap<CellCoordinates, CellState>();
        Coords2D checkedCoord = new Coords2D(2,1);
        Coords2D checkedCoord1 = new Coords2D(1,1);
        Coords2D checkingAnt = new Coords2D(0,1);
        LangtonCell testedState1 = new LangtonCell(BinaryState.DEAD,1,AntState.NORTH);
        LangtonCell testedState = new LangtonCell(BinaryState.DEAD,3,AntState.SOUTH);
        LangtonCell testingAnt = new LangtonCell(BinaryState.DEAD,2,AntState.NORTH);
        tab.put(checkedCoord1,testedState1);
        tab.put(checkingAnt,testingAnt);
        tab.put(checkedCoord,testedState);
        game.insertStructure(tab);

        LangtonAnt newgame = (LangtonAnt) game.nextState();
        Map<CellCoordinates,CellState> tab1 = new HashMap<CellCoordinates, CellState>();
        tab1.put(checkedCoord,new LangtonCell(BinaryState.ALIVE,1,AntState.EAST));
        tab1.put(checkedCoord1,new LangtonCell(BinaryState.ALIVE,0,AntState.NONE));
        tab1.put(checkingAnt,new LangtonCell(BinaryState.ALIVE,0,AntState.NONE));
        game.insertStructure(tab1);

        assertEquals(game.cells,newgame.cells);

    }
}