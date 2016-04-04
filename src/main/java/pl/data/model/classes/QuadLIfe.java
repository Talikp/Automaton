package pl.data.model.classes;

import pl.data.model.enums.QuadState;
import pl.data.model.interfaces.CellNeighborhood;
import pl.data.model.interfaces.CellState;
import pl.data.model.interfaces.CellStateFactory;

import java.util.Set;

/**
 * Created by talik on 11.12.15.
 */
public class QuadLIfe extends GameOfLife {


    /**
     *
     * QuadLife używa standardowej zasady przeżycia 23/3
     */
    public QuadLIfe(CellStateFactory stateFactory, CellNeighborhood cellNeighbors, int x, int y) {
        super(stateFactory, cellNeighbors, x, y);
    }

    @Override
    protected CellState newState(Cell currentState, Set<Cell> neighborsStates) {
        int countAlive = this.countAlive(neighborsStates);
        if((currentState.getState()!=QuadState.DEAD) && (this.find(alive,countAlive)))
            return currentState.getState();
        else if((currentState.getState()==QuadState.DEAD) && (this.find(dead,countAlive)))
        {
            return this.getHighestState(neighborsStates);
        }
        else
        return QuadState.DEAD;

    }

    @Override
    /**
     * zliczam żywych sąsiadów
     * */
    protected int countAlive(Set<Cell> neighbors) {
        int i = 0;
        for(Cell cell:neighbors)
        {
            if(cell.state != QuadState.DEAD)
                i++;
        }
        return i;
    }

    @Override
    protected Automaton newInstance(CellStateFactory state, CellNeighborhood cellNeighbors) {
        return new QuadLIfe(state,cellNeighbors,getWidth(),getHeight());
    }

    /**
     * sprawdzam jaki następny kolor przyjmie komórka która ma ożyć,
     * jeśli przynajmniej 2 sąsiadów jest tego samego koloru to ustawiam ten kolor,
     * w przeciwnym razie zwracam ten kolor który nie mają sąsiedzi
     *
     * @param neighborsStates sąsiedzi
     * @return
     */
    private QuadState getHighestState(Set<Cell> neighborsStates)
    {
        int red = 0;
        int yellow = 0;
        int blue = 0;
        int green = 0;

        for(Cell cell:neighborsStates)
        {
            QuadState cs = (QuadState) cell.getState();
            if(cs==QuadState.RED)
                red++;
            else if(cs == QuadState.BLUE)
                blue++;
            else if(cs == QuadState.GREEN)
                green++;
            else if(cs == QuadState.YELLOW)
                yellow++;

        }
        if(red>1)
            return QuadState.RED;
        if(blue>1)
            return QuadState.BLUE;
        if(yellow>1)
            return QuadState.YELLOW;
        if(green>1)
            return QuadState.GREEN;

        if(green==0)
            return QuadState.GREEN;
        if(blue == 0)
            return QuadState.BLUE;
        if(red == 0)
            return QuadState.RED;
        if(yellow == 0)
            return QuadState.YELLOW;

        return QuadState.RED;
    }
}
