package pl.data.model.classes;

import com.google.common.base.Objects;
import pl.data.model.interfaces.CellCoordinates;

/**
 * Created by talik on 19.11.15.
 */

public class Coords1D implements CellCoordinates{
    public int x;

    public Coords1D(int x){
        this.x = x;
    }


    @Override
    public Coords1D toclone() {
        return new Coords1D(this.x);
    }

    @Override
    public boolean equals(CellCoordinates coordinates) {
            return ((Coords1D) coordinates).getX() == this.getX();

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coords1D coords1D = (Coords1D) o;
        return x == coords1D.x;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(x);
    }

    public int getX(){
        return this.x;
    }
}
