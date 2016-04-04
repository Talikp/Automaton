package pl.data.model.classes;

import org.junit.Test;
import pl.data.model.enums.WireElectronState;
import pl.data.model.interfaces.CellCoordinates;
import pl.data.model.interfaces.CellState;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Created by talik on 10.12.15.
 */
public class WireWorldTest {

    @Test
    public void newStateWireTest() throws Exception {
        int width = 4;
        int height = 4;
        UniformStateFactory stateFactory = new UniformStateFactory(WireElectronState.VOID);
        MoorNieghborhood neighboorStrategy = new MoorNieghborhood(width,height,false);
        WireWorld game = new WireWorld(stateFactory,false,width,height);
        Map<CellCoordinates,CellState> tab = new HashMap<CellCoordinates, CellState>();

        tab.put(new Coords2D(2,2),WireElectronState.ELECTRON_HEAD);
        tab.put(new Coords2D(2,1),WireElectronState.ELECTRON_HEAD);
        game.insertStructure(tab);
        Set<Cell> neighbors = game.mapCoordinates(game.getNeighborsStrategy().cellNeighbors(new Coords2D(1,1)));
        assertEquals(WireElectronState.ELECTRON_HEAD,game.newState(new Cell(WireElectronState.WIRE,new Coords2D(1,1)),neighbors));

    }

    @Test
    public void newStateHeadToTailTest() throws Exception {
        int width = 4;
        int height = 4;
        UniformStateFactory stateFactory = new UniformStateFactory(WireElectronState.VOID);
        MoorNieghborhood neighboorStrategy = new MoorNieghborhood(width,height,false);
        WireWorld game = new WireWorld(stateFactory,false,width,height);
        Set<Cell> neighbors = null;
        Map<CellCoordinates,CellState> tab = new HashMap<CellCoordinates, CellState>();

        tab.put(new Coords2D(2,2),WireElectronState.ELECTRON_HEAD);
        game.insertStructure(tab);

        assertEquals(WireElectronState.ELECTRON_TAIL,game.newState(new Cell(WireElectronState.ELECTRON_HEAD,new Coords2D(2,2)),neighbors));


    }

    @Test
    public void newStateTailToWireTest() throws Exception {
        int width = 4;
        int height = 4;
        UniformStateFactory stateFactory = new UniformStateFactory(WireElectronState.VOID);
        MoorNieghborhood neighboorStrategy = new MoorNieghborhood(width,height,false);
        WireWorld game = new WireWorld(stateFactory,false,width,height);
        Set<Cell> neighbors = null;
        Map<CellCoordinates,CellState> tab = new HashMap<CellCoordinates, CellState>();

        tab.put(new Coords2D(2,2),WireElectronState.ELECTRON_TAIL);
        game.insertStructure(tab);

        assertEquals(WireElectronState.WIRE,game.newState(new Cell(WireElectronState.ELECTRON_TAIL,new Coords2D(2,2)),neighbors));
    }

    @Test
    public void nextStateTest() throws Exception {
        int width = 4;
        int height = 4;
        UniformStateFactory stateFactory = new UniformStateFactory(WireElectronState.VOID);
        MoorNieghborhood neighboorStrategy = new MoorNieghborhood(width,height,false);
        WireWorld game = new WireWorld(stateFactory,false,width,height);
        Map<CellCoordinates,CellState> tab = new HashMap<CellCoordinates, CellState>();

        tab.put(new Coords2D(0,1),WireElectronState.WIRE);
        tab.put(new Coords2D(1,1),WireElectronState.WIRE);
        tab.put(new Coords2D(2,1),WireElectronState.WIRE);
        tab.put(new Coords2D(2,2),WireElectronState.ELECTRON_TAIL);
        tab.put(new Coords2D(2,3),WireElectronState.ELECTRON_HEAD);
        game.insertStructure(tab);
        WireWorld newgame = (WireWorld) game.nextState();
        Map<CellCoordinates,CellState> tab1 = new HashMap<CellCoordinates, CellState>();
        tab1.put(new Coords2D(2,2),WireElectronState.WIRE);
        tab1.put(new Coords2D(2,3),WireElectronState.ELECTRON_TAIL);
        game.insertStructure(tab1);

        assertEquals(game.cells,newgame.cells);


    }
}