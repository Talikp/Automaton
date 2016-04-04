package pl.data.model.classes;

import pl.data.model.interfaces.CellCoordinates;
import pl.data.model.interfaces.CellNeighborhood;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by talik on 19.11.15.
 */

/* sasiedztwo 8 komorek, dookola */
public class MoorNieghborhood implements CellNeighborhood {
    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    private int width;
    private int height;
    private boolean condition;



    public MoorNieghborhood(int width,int height,boolean condition){
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
        if(cell instanceof Coords2D){
        Set<CellCoordinates> neighbors = new HashSet<CellCoordinates>();
        int x = ((Coords2D) cell).getX();
        int y = ((Coords2D) cell).getY();
        if(condition){
            int x_1,x1,y_1,y1;
            if(x-1>=0)
                x_1 = x-1;
            else
                x_1 = this.width-1;

            if(x+1<this.width)
                x1 = x+1;
            else
                x1 = 0;

            if(y-1>=0)
                y_1 = y-1;
            else
                y_1 = this.height-1;

            if(y+1<this.height)
                y1 = y+1;
            else
                y1 = 0;

            neighbors.add(new Coords2D(x_1,y_1));
            neighbors.add(new Coords2D(x_1,y));
            neighbors.add(new Coords2D(x_1,y1));
            neighbors.add(new Coords2D(x,y_1));
            neighbors.add(new Coords2D(x,y1));
            neighbors.add(new Coords2D(x1,y_1));
            neighbors.add(new Coords2D(x1,y));
            neighbors.add(new Coords2D(x1,y1));

        }
        else
        {
            if(x-1>=0){
                neighbors.add(new Coords2D(x-1,y));

                if(y-1>=0)
                    neighbors.add(new Coords2D(x-1,y-1));
                if(y+1<this.height)
                    neighbors.add(new Coords2D(x-1,y+1));
            }
            if(y-1>=0) {
                neighbors.add(new Coords2D(x, y - 1));
            }
            if(y+1<this.height)
                neighbors.add(new Coords2D(x,y+1));

            if(x+1<this.width) {
                neighbors.add(new Coords2D(x + 1, y));

                if(y-1>=0)
                    neighbors.add(new Coords2D(x + 1, y - 1));
                if(y+1<this.height)
                    neighbors.add(new Coords2D(x + 1, y + 1));
            }
        }

            return neighbors;

        }
        else if(cell instanceof Coords1D)
        {
            int x = ((Coords1D) cell).getX();

            Set<CellCoordinates> neighbors = new HashSet<CellCoordinates>();
            neighbors.add(new Coords1D(x));
            neighbors.add(new Coords1D(x-1));
            neighbors.add(new Coords1D(x+1));
            return neighbors;
        }
        else {
            return null;
        }
    }




}
