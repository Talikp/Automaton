package pl.data.model.interfaces;

/**
 * Created by talik on 19.11.15.
 */
public interface CellCoordinates {

    /**
     * metoda użyta w konstruktorze klasy Cell, aby nie rozróżniać na koordynaty 1 i 2 wymiarowe
     * @return
     */
    public CellCoordinates toclone();

    /**
     *  Sprawdzam czy koordynaty są równe
     * */
    public boolean equals(CellCoordinates coordinates);


}
