package pl.data.model.classes;

import pl.data.model.interfaces.CellCoordinates;
import pl.data.model.interfaces.CellState;
import pl.data.model.interfaces.CellStateFactory;

import java.util.Map;

/**
 * Created by talik on 19.11.15.
 */
public class GeneralStateFactory implements CellStateFactory {
    private Map<CellCoordinates,CellState> states;


    public GeneralStateFactory(Map<CellCoordinates,CellState> state)
    {
        this.states = state;
    }

    public CellState initialState(CellCoordinates cord){

        for(CellCoordinates cc: states.keySet())
        {
            if(cord.equals(cc))
                return this.states.get(cc);
        }

        return null;
    }
}
