package pl.data.model.classes;

import pl.data.model.enums.BinaryState;
import pl.data.model.interfaces.CellCoordinates;
import pl.data.model.interfaces.CellNeighborhood;
import pl.data.model.interfaces.CellState;
import pl.data.model.interfaces.CellStateFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by talik on 19.11.15.
 */

public abstract class Automaton {
    protected Map<CellCoordinates,CellState> cells;
    private CellNeighborhood neighborsStrategy;
    private CellStateFactory stateFactory;

    public Automaton nextState(){
        Automaton game = this.newInstance(stateFactory,neighborsStrategy);
        game.cells.clear();

        CellIterator cellIterator = this.cellIterator();
        for (CellCoordinates coord: cells.keySet()) {
            Set<Cell> cell = this.mapCoordinates(neighborsStrategy.cellNeighbors(coord));
            CellState newState = this.newState(new Cell(cells.get(coord),coord),cell);
            cellIterator.setState(newState);
            game.cells.put(coord,newState);
        }
        return game;
    }

    /**
     * sprawdzam na początku czy wszystkie koordynaty znajdują się obszarze Automatu
     *
     * */
    public boolean insertStructure(Map<? extends  CellCoordinates, ? extends  CellState> structure){
        boolean condition = true;
        for (CellCoordinates coord: structure.keySet()) {
            if(!this.cells.containsKey(coord))
                condition = false;
        }
        if(condition)
        {
            for(CellCoordinates coord: structure.keySet())
            {
                this.cells.replace(coord,structure.get(coord));
            }
            return true;

        }
        return false;

    }

    public Automaton(CellStateFactory state,CellNeighborhood cellNeighbors){
        this.neighborsStrategy = cellNeighbors;
        this.stateFactory = state;
        cells = new HashMap<CellCoordinates,CellState>();


    }

    public Automaton(CellStateFactory stateFactory){
        this.stateFactory = stateFactory;
        cells = new HashMap<CellCoordinates,CellState>();

    }

    protected final Set<Cell> mapCoordinates(Set<CellCoordinates> coordinates){

        if(coordinates==null)
            return null;
        Set<Cell> cells = new HashSet<Cell>();
        CellState state = null;
        for (CellCoordinates coord: coordinates) {
            if(this.cells.get(coord)!=null)
                cells.add(new Cell(this.cells.get(coord),coord));
            else
                cells.add(new Cell(BinaryState.DEAD,coord));
        }
        return cells;

    }



    public CellIterator cellIterator(){
        return new CellIterator();
    }

    class CellIterator {
        private CellCoordinates currentState;

        public CellIterator(){
            Automaton.this.initialCoordinates();
        }

        public  boolean hasNext(){
            return Automaton.this.hasNextCoordinates(currentState);
        }

        public CellCoordinates next(){
            if(this.hasNext()){
                currentState = Automaton.this.nextCoordinates(currentState);
                return Automaton.this.nextCoordinates(currentState);
            }
            return null;
        }

        public void setState(CellState state){
            Automaton.this.getCells().replace(currentState,state);


        }
    }

    protected abstract CellCoordinates initialCoordinates();

    protected abstract Automaton newInstance(CellStateFactory state, CellNeighborhood cellNeighbors);

    protected abstract boolean hasNextCoordinates(CellCoordinates coord);


    protected abstract CellCoordinates nextCoordinates(CellCoordinates coord);

    protected  abstract CellState newState(Cell currentState, Set<Cell> neighborsStates);

    public CellNeighborhood getNeighborsStrategy() {
        return neighborsStrategy;
    }

    public CellStateFactory getStateFactory(){
        return stateFactory;
    }

    public Map<CellCoordinates,CellState> getCells(){
        return this.cells;
    }


}
