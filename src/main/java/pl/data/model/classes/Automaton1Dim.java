package pl.data.model.classes;

import pl.data.model.interfaces.CellCoordinates;
import pl.data.model.interfaces.CellStateFactory;

/**
 * Created by talik on 20.11.15.
 */

//finished
public abstract class Automaton1Dim extends Automaton{
    private int size;


    /**
     * w konstruktorze nie podaje CellNeighbor ponieważ używam domyślengo cellNeighbor, bez zawijania
     * @param x rozmiar
     *
     * */
    public Automaton1Dim(CellStateFactory stateFactory, int x){
        super(stateFactory,new MoorNieghborhood(x,1,false));
        this.size = x;
    }

    @Override
    protected CellCoordinates initialCoordinates() {
        return new Coords1D(-1);
    }

    protected boolean hasNextCoordinates(CellCoordinates coord){
        if(coord instanceof Coords1D)
        {
            if(((Coords1D) coord).x<size)
                return true;
            else
                return false;
        }

        return false;
    }



    protected CellCoordinates nextCoordinates(CellCoordinates coord){
        if(coord instanceof  Coords1D){
            if(this.hasNextCoordinates(coord))
            {
                int x = ((Coords1D) coord).x;
                return new Coords1D(x+1);
            }
            return null;

        }
        return null;
    }

    public int getSize() {
        return this.size;
    }
}
