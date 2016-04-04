package pl.data.model.classes;

import pl.data.model.enums.WireElectronState;
import pl.data.model.interfaces.CellNeighborhood;
import pl.data.model.interfaces.CellState;
import pl.data.model.interfaces.CellStateFactory;

import java.util.Set;

/**
 * Created by talik on 20.11.15.
 */

public class WireWorld extends Automaton2Dim {

    public WireWorld(CellStateFactory stateFactory, boolean condition,int x,int y){
        super(stateFactory, new MoorNieghborhood(x,y,condition), x, y);
        for(int i=0; i<getWidth() ;i++)
        {
            for(int j=0; j<getHeight() ;j++)
            {
                Coords2D coord = new Coords2D(i,j);
                this.cells.put(coord,stateFactory.initialState(coord));
            }
        }
    }

    /**
     * liczba sąsiadujących główek elektronu
     * */

    private int countElectronHeads(Set<Cell> neighborsStates){
        int i = 0;
        for(Cell cell: neighborsStates){
            if(cell.state == WireElectronState.ELECTRON_HEAD)
                i++;
        }
        return i;

    }
    @Override
    protected Automaton newInstance(CellStateFactory state, CellNeighborhood cellNeighbors) {
        WireWorld game = new WireWorld(state,cellNeighbors.getWrapping(),super.getWidth(),super.getHeight());
        return game;
    }


    @Override
    /**
     * jeśli obecnie komórka jest przewodnikiem(WIRE) to sprawdzam liczbę sąsiadujących Electron_Head
     * wprzeciwnym wypadku bezsprawdzania zmienniam stan według zasad
     * */
    protected CellState newState(Cell currentState, Set<Cell> neighborsStates)
    {
        if(currentState.getState() == WireElectronState.VOID)
            return WireElectronState.VOID;
        if(currentState.getState() == WireElectronState.ELECTRON_HEAD)
            return WireElectronState.ELECTRON_TAIL;
        if(currentState.getState() == WireElectronState.ELECTRON_TAIL)
            return WireElectronState.WIRE;
        if(currentState.getState() == WireElectronState.WIRE){
            int electronHeads = this.countElectronHeads(neighborsStates);
            if((electronHeads ==1) || (electronHeads == 2))
                return WireElectronState.ELECTRON_HEAD;
            else
                return WireElectronState.WIRE;
        }
        return WireElectronState.VOID;
    }


}
