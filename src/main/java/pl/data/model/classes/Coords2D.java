package pl.data.model.classes;

import com.google.common.base.Objects;
import pl.data.model.interfaces.CellCoordinates;

/**
 * Created by talik on 19.11.15.
 */
public class Coords2D implements CellCoordinates{
    public int x;
    public int y;

    public Coords2D(int x,int y){
        this.x = x;
        this.y = y;
    }


    public Coords2D toclone(){
        return new Coords2D(this.x,this.y);

    }

    @Override
    public boolean equals(CellCoordinates coordinates) {
        return ((Coords2D) coordinates).getX() == this.getX() && ((Coords2D) coordinates).getY() == this.getY();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coords2D coords2D = (Coords2D) o;
        return x == coords2D.x &&
                y == coords2D.y;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(x, y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
