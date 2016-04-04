package pl.data.model.classes;

import com.google.common.base.Objects;
import pl.data.model.interfaces.CellCoordinates;
import pl.data.model.interfaces.CellState;

/**
 * Created by talik on 19.11.15.
 */
public class Cell{

    public CellState state;
    public CellCoordinates coords;



    public CellCoordinates getCoords() {
        return coords;
    }

    public CellState getState() {
        return state;
    }

    public Cell(CellState state,CellCoordinates coord)
    {
        this.state = state;
        this.coords = coord.toclone();
    }

    public boolean equals(Cell another){

        return another.getCoords().equals(this.getCoords()) && another.getState()==this.getState();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return Objects.equal(state, cell.state) &&
                Objects.equal(coords, cell.coords);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(state, coords);
    }

}
