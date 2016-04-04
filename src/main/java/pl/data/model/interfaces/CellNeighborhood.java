package pl.data.model.interfaces;

import java.util.Set;

/**
 * Created by talik on 19.11.15.
 */
public interface CellNeighborhood {

    public abstract Set<CellCoordinates> cellNeighbors(CellCoordinates cell);

    public abstract boolean getWrapping();

}
