package pl.data.model.classes;

import pl.data.model.enums.AntState;
import pl.data.model.enums.BinaryState;
import pl.data.model.interfaces.CellNeighborhood;
import pl.data.model.interfaces.CellState;
import pl.data.model.interfaces.CellStateFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by talik on 20.11.15.
 */
public class LangtonAnt extends Automaton2Dim {

    public LangtonAnt(CellStateFactory stateFactory, boolean condition,int x,int y){
        super(stateFactory, new VonNeumanNeighborhood(x,y,condition), x, y);
        for(int i=0; i<getWidth() ;i++)
        {
            for(int j=0; j<getHeight() ;j++)
            {
                Coords2D coord = new Coords2D(i,j);
                this.cells.put(coord,stateFactory.initialState(coord));
            }
        }
    }


    //zlicza sąsiednie mrówki

    /**
     * @param neighborsStates
     * @return zwraca sąsiadujące mrówki
     */
    private List<Cell> countAnts(Set<Cell> neighborsStates){
        List<Cell> ants = new ArrayList<Cell>();

        for(Cell cell: neighborsStates){
            if(cell.state instanceof LangtonCell)
            {
                if (((LangtonCell) cell.state).antState != AntState.NONE) {
                   ants.add(cell);
                }
            }
        }
        return ants;
    }



    @Override
    protected Automaton newInstance(CellStateFactory state, CellNeighborhood cellNeighbors)
    {
        LangtonAnt game = new LangtonAnt(state,cellNeighbors.getWrapping(),super.getWidth(),super.getHeight());
        return game;
    }

    @Override
    /**
     * jeśli nie ma mrówek w pobliżu to zwracam nowy stan zależnie od nextState
     * jeśli są mrówki to sprawdzam czy ich następne koordynaty nie pokrywają się z koordynatami obecnej komórki,
     * i czy nie jest więcej niż 1 takich mrówek, w przeciwnym wypdaku pozostaje tylko 1 mrówka na nowym polu
     */
    protected CellState newState(Cell currentState, Set<Cell> neighborsStates)
    {
        List<Cell> ants = this.countAnts(neighborsStates);
        int antsNeighborCount = ants.size();
        LangtonCell currentCellState = (LangtonCell) currentState.getState();
        if(antsNeighborCount == 0)
        {
            return new LangtonCell(this.nextState(currentCellState),0,AntState.NONE);
        }

        int i=0;
        Coords2D myCoords =(Coords2D) currentState.getCoords();
        Cell lastAnt = new Cell(currentCellState,myCoords);
        for (Cell ant: ants) {
            if(myCoords.equals(this.antsNextCoords(ant))) {
                lastAnt = ant;
                i++;
            }
        }
        if(i%2==0)
        {
            return new LangtonCell(this.nextState(currentCellState),0,AntState.NONE);
        }
        else {
            LangtonCell ant = (LangtonCell) lastAnt.state;
            return new LangtonCell(this.nextState(currentCellState),ant.antId,ant.nextState());
        }

    }

    /**
     * zależnie czy na komórce jest obecnie mrówka czy jej nie ma tak ndaje stan
     * @param state obecny stan
     * @return przyszły stan
     */
    private BinaryState nextState(CellState state){
        if(state instanceof LangtonCell)
        {
            if(((LangtonCell) state).antState == AntState.NONE)
            {
                return ((LangtonCell) state).cellState;
            }
            if(((LangtonCell) state).cellState == BinaryState.ALIVE)
                return BinaryState.DEAD;
            else
                return BinaryState.ALIVE;
        }
        return null;
    }


    /**
     * Sprawdzam następne koordynaty mrówki, a sprawdzić czy nie przesunie sie na badane pole
     * @param ant badana mrówka
     * @return następne koordynaty mrówki
     */
    private Coords2D antsNextCoords(Cell ant){
    if((ant.state instanceof LangtonCell) && (ant.coords instanceof Coords2D)){
        Coords2D antCoords = (Coords2D) ant.coords.toclone();

        if(((LangtonCell) ant.state).cellState == BinaryState.DEAD)
        {
            switch (((LangtonCell) ant.state).antState) {
                case NORTH:antCoords.x++;
                    break;
                case EAST:antCoords.y++;
                    break;
                case SOUTH:antCoords.x--;
                    break;
                case WEST:antCoords.y--;
                    break;

            }
        }
        else
        {
            switch (((LangtonCell) ant.state).antState) {
                case NORTH:antCoords.x--;
                    break;
                case EAST:antCoords.y--;
                    break;
                case SOUTH:antCoords.x++;
                    break;
                case WEST:antCoords.y++;
                    break;

            }
        }
        if(antCoords.getX()<0)
            antCoords.x = this.getWidth()-1;
        if(antCoords.getY()<0)
            antCoords.y = this.getHeight()-1;
        if(antCoords.getX()>=this.getWidth())
            antCoords.x = 0;
        if(antCoords.getY()>=this.getHeight())
            antCoords.y = 0;
        return antCoords;

    }
        return null;
}


}
