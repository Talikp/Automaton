package pl.data.model.classes;

import com.google.common.base.Objects;
import pl.data.model.enums.AntState;
import pl.data.model.enums.BinaryState;
import pl.data.model.interfaces.CellState;

/**
 * Created by talik on 19.11.15.
 */
public class LangtonCell implements CellState{
    public BinaryState cellState;
    public int antId;
    public AntState antState;

    public LangtonCell(BinaryState cellState,int id, AntState antState){
        this.cellState = cellState;
        this.antId = id;
        this.antState = antState;
    }

    @Override
    public String toString() {
        return "LangtonCell{" +
                "cellState=" + cellState +
                ", antId=" + antId +
                ", antState=" + antState +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LangtonCell that = (LangtonCell) o;
        return antId == that.antId &&
                cellState == that.cellState &&
                antState == that.antState;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(cellState, antId, antState);
    }



    /**
     *
     * @return zwraca gdzie następnie mrówka będzie zwrócona
     * */
    public AntState nextState(){
        if(this.cellState == BinaryState.ALIVE)
        {
            switch (antState){
                case NORTH:
                    return AntState.WEST;

                case EAST:
                    return AntState.NORTH;

                case SOUTH:
                    return AntState.EAST;

                case WEST:
                    return AntState.SOUTH;
                case NONE:
                    return AntState.NONE;
            }
        }
        else
        {
            switch (antState){
                case NORTH:
                    return AntState.EAST;

                case EAST:
                    return AntState.SOUTH;

                case SOUTH:
                    return AntState.WEST;

                case WEST:
                    return AntState.NORTH;
                case NONE:
                    return AntState.NONE;

            }
        }
        return AntState.NONE;
    }


}
