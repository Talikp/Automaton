package pl.data.model.classes;

import pl.data.model.enums.BinaryState;
import pl.data.model.interfaces.CellCoordinates;
import pl.data.model.interfaces.CellNeighborhood;
import pl.data.model.interfaces.CellState;
import pl.data.model.interfaces.CellStateFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by talik on 20.11.15.
 */
public class GameOfLife extends Automaton2Dim{

    /**
     * zmienne przechowują int dla odpowiednich zasad
     */
    protected int[] dead;
    protected int[] alive;

    /**
     * jeśli brak zasad to nadaje standardowe 23/3
     */
    public GameOfLife(CellStateFactory stateFactory,CellNeighborhood cellNeighbors,int x, int y){
        super(stateFactory,cellNeighbors,x,y);
        this.dead = new int[]{3};
        this.alive = new int[]{2,3};
        for(int i=0; i<x ;i++)
        {
            for(int j=0; j<y ;j++)
            {
                Coords2D coord = new Coords2D(i,j);
                this.cells.put(coord,stateFactory.initialState(coord));
            }
        }
    }

    public GameOfLife(CellStateFactory stateFactory,CellNeighborhood cellNeighbors, int x, int y,int[] alive, int[] dead){
        super(stateFactory,cellNeighbors,x,y);
        this.alive = alive.clone();
        this.dead = dead.clone();

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
     * metoda ta sprawdza czy liczba żywych sąsiadów się znajduje w którejś z tablicy,
     * dla pozostania żywą komórką lub zostania żywą
     *
     */
    protected boolean find(int [] tab,int x){
        for(int i:tab)
            if((i>0) &&(i==x))
                return true;
        return false;
    }

    /**
     * zliczenie żywych sąsiadów
     * */
    protected int countAlive(Set<Cell> neighbors){
        int i = 0;
        for(Cell cell:neighbors){
            if(cell.state== BinaryState.ALIVE)
                i++;
        }
        return i;
    }

    @Override
    protected Automaton newInstance(CellStateFactory state, CellNeighborhood cellNeighbors) {
        GameOfLife game = new GameOfLife(state,cellNeighbors,super.getWidth(),super.getHeight(),this.alive,this.dead);
        return game;
    }


    @Override
    /**
     * jeśli sprawdzana komórka jest żywa to sprawdzam warunek pozostania przy życią w tablicy alive,
     * jeśli jest martwa to sprawdzam w tablicy dead czy może ożyć w tej iteracji
     *
     * */
    protected CellState newState(Cell currentState, Set<Cell> neighborsStates) {
        int countAlive = this.countAlive(neighborsStates);
        if((currentState.getState()==BinaryState.ALIVE) && (this.find(this.alive,countAlive)))
            return BinaryState.ALIVE;
        else if((currentState.getState()==BinaryState.DEAD) && (this.find(this.dead,countAlive)))
            return BinaryState.ALIVE;
        else
            return BinaryState.DEAD;

    }






}
