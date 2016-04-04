package pl.data.model.classes;

import pl.data.model.enums.BinaryState;
import pl.data.model.interfaces.CellNeighborhood;
import pl.data.model.interfaces.CellState;
import pl.data.model.interfaces.CellStateFactory;

import java.util.Set;

/**
 * Created by talik on 09.12.15.
 */
public class OneDimension extends Automaton1Dim{
    /**
     * zmienna w której przechowuje zasady Automatu
     *
     * */
    private int[] rule;


    public OneDimension(CellStateFactory stateFactory, int size,int rule)
    {
        super(stateFactory,size);
        this.rule = makeRule(rule);
        for (int i = 0; i <size ; i++) {
            Coords1D coord = new Coords1D(i);
            this.cells.put(coord,stateFactory.initialState(coord));

        }
    }

    @Override
    protected Automaton newInstance(CellStateFactory state, CellNeighborhood cellNeighbors) {
        return new OneDimension(state,this.getSize(),this.getRule());
    }

    @Override
    /**
     * z metody idNumber pobieram numer sąsiedztwa, a potem porównuje go z wartością w rule
     *
     * */
    protected CellState newState(Cell currentState, Set<Cell> neighborsStates) {
        int id = this.idNumber(neighborsStates);
        if(this.rule[id]==0)
            return BinaryState.DEAD;
        else
            return BinaryState.ALIVE;
    }

    /**
     * przekształca int na tablice int o rozmiarze 8, które odpowiednią kodują sąsiedztwo,
     * np. AAA daje z binarnego 8, co odpowiada indexowi 7, a jeśli w tablicy rule o tym indexie jest 1 to komórka pozostanie żywa
     * */
    private int[] makeRule(int rule){
        int[] tab = new int[8];

        for(int i=0;i<8;i++)
        {
            tab[i]=rule%2;
            rule=rule/2;
        }

        return  tab;
    }


    /**
     * na początku licze koordynaty dla której liczone jest sąsiedztwo
     * a poźniej licze numer sąsiedztwa i zwracam ten numer
     * */
    public int idNumber(Set<Cell> neighborsStates) {
        int suma = 0;
        int sumaCyfr=0;
        for (Cell cell : neighborsStates)
        {
            if(cell.getCoords() instanceof Coords1D)
                suma +=((Coords1D) cell.getCoords()).getX();
        }

        suma/=3;
        for(Cell cell : neighborsStates)
        {
            for(int i=0;i<=2;i++){
                if(cell.getCoords().equals(new Coords1D(suma-1+i)) && cell.getState()==BinaryState.ALIVE)
                {
                    sumaCyfr+=Math.pow(2,2-i);
                }
            }
        }
        return sumaCyfr;
    }

    /**
     * metoda użyta do zamiany z liczbny binarnej zmiennej rule na dziesiętną użytej w newInstance
     * */
    public int getRule(){
        int rules = 0;

        for (int i = 0; i < 8 ; i++) {
            if(this.rule[i]!=0)
            {
                rules+=Math.pow(2,i);
            }
        }

        return rules;
    }

}
