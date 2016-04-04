package pl.data.model.classes;

import pl.data.model.interfaces.CellCoordinates;
import pl.data.model.interfaces.CellState;
import pl.data.model.interfaces.CellStateFactory;

/**
 * Created by talik on 19.11.15.
 */
public class UniformStateFactory implements CellStateFactory {
    private CellState state;

    public UniformStateFactory(CellState state)
    {
        this.state = state;
    }

    @Override
    public CellState initialState(CellCoordinates cord)
    {
        return state;
    }
}
