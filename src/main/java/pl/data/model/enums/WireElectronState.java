package pl.data.model.enums;

import pl.data.model.interfaces.CellState;

/**
 * Created by talik on 19.11.15.
 */
public enum WireElectronState implements CellState{
     VOID,
     WIRE,
     ELECTRON_HEAD,
     ELECTRON_TAIL;

}
