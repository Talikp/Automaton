package pl.data.model.classes;

import pl.data.model.interfaces.CellCoordinates;
import pl.data.model.interfaces.CellNeighborhood;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by talik on 19.11.15.
 */

/* sasiedztwo 4 komorek */
public class VonNeumanNeighborhood implements CellNeighborhood {
    private int width;
    private int height;
    private boolean condition;


    public VonNeumanNeighborhood(int width,int height,boolean condition)
    {
        this.width = width;
        this.height = height;
        this.condition = condition;

    }

    @Override
    public boolean getWrapping() {
        return condition;
    }

    /**
     * jeśli koordynaty są klasy Coords1D zwracam niezależnie 3 sąsiadów,
     * a jeśli klasy Coords2D to zależy od zmiennej condition która oznacza z zawijaniem lub bez zawijania
     *
     * @param cell bierzące koordynaty komórki
     * @return set z sąsiadami
     */
    public Set<CellCoordinates> cellNeighbors(CellCoordinates cell)
    {
        if(cell instanceof Coords2D)
        {
            Set<CellCoordinates> neighbors = new HashSet<CellCoordinates>();
            int x = ((Coords2D) cell).getX();
            int y = ((Coords2D) cell).getY();

            if(condition){
                if(x-1<0)
                    neighbors.add(new Coords2D(this.width-1,y));
                else
                    neighbors.add(new Coords2D(x-1,y));
                if(y-1>=0)
                    neighbors.add(new Coords2D(x,y-1));
                else
                    neighbors.add(new Coords2D(x,this.height-1));
                if(y+1<this.height)
                    neighbors.add(new Coords2D(x,y+1));
                else
                    neighbors.add(new Coords2D(x,0));
                if(x+1<this.width)
                    neighbors.add(new Coords2D(x+1,y));
                else
                    neighbors.add(new Coords2D(0,y));
            }
            else{
            if(x-1>=0)
                neighbors.add(new Coords2D(x-1,y));
            if(y-1>=0)
                neighbors.add(new Coords2D(x,y-1));
            if(y+1<this.height)
                neighbors.add(new Coords2D(x,y+1));
            if(x+1<this.width)
                neighbors.add(new Coords2D(x+1,y));
            }
            return neighbors;

        }
        else if(cell instanceof Coords1D)
        {
            int x = ((Coords1D) cell).getX();
            Set<CellCoordinates> neighbors = new HashSet<CellCoordinates>();

            neighbors.add(new Coords1D(x-1));
            neighbors.add(new Coords1D(x+1));
            neighbors.add(new Coords1D(x));
            return neighbors;
        }
        else
            return null;
    }
}
