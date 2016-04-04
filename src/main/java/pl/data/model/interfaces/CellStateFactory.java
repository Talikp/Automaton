package pl.data.model.interfaces;

/**
 * Created by talik on 19.11.15.
 */
public interface CellStateFactory {

    public abstract CellState initialState(CellCoordinates cord);
}
