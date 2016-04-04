package pl.data.model.classes;

import pl.data.model.interfaces.CellCoordinates;
import pl.data.model.interfaces.CellNeighborhood;
import pl.data.model.interfaces.CellState;
import pl.data.model.interfaces.CellStateFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by talik on 20.11.15.
 */

//finished
public abstract class Automaton2Dim extends Automaton{
    private int width;
    private int height;

    protected boolean hasNextCoordinates(CellCoordinates coord){
        if(coord instanceof Coords2D){
            int x = ((Coords2D) coord).x;
            int y = ((Coords2D) coord).y;
            if(x<this.width && y<this.height)
                return true;
            else return ((x == this.width) && (y < this.height)) || ((y == this.height) && (x < this.width));
        }
        return false;
    }

    public Automaton2Dim(CellStateFactory state,CellNeighborhood cellNeighbors,int x, int y){
        super(state,cellNeighbors);
        this.width = x;
        this.height = y;

    }

    /**
     *
     * @param old stary Automaton z którym porównuje różnice, aby niepotrzebnie nie rysować
     * @return zwraca zmapowane koordynaty
     * @see pl.data.view.GameComponent
     * */

    public Map<CellCoordinates,CellState> diffrent(Automaton2Dim old)
    {
        Map<CellCoordinates,CellState> tab = new HashMap<CellCoordinates, CellState>();
        for(CellCoordinates cc : this.cells.keySet())
        {
            if(this.cells.get(cc)!=old.cells.get(cc))
                tab.put(cc,this.cells.get(cc));
        }

        return tab;
    }

    protected CellCoordinates nextCoordinates(CellCoordinates coord){
        if(!this.hasNextCoordinates(coord))
            return null;
        else{
            if(coord instanceof Coords2D){
                int x = ((Coords2D) coord).x;
                int y = ((Coords2D) coord).y;
                if(y < this.height){
                    return new Coords2D(x,y+1);
                }
                else
                {
                    return new Coords2D(x+1,y);
                }
            }
        }
        return null;
    }

    @Override
    protected CellCoordinates initialCoordinates() {
        return new Coords2D(-1,0);
    }

    public int getWidth(){
        return this.width;
    }

    public int getHeight(){
        return this.height;
    }
}
