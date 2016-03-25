package ia.domain;

/**
 * Created by Dragos on 23.03.2016.
 */
public class State implements Cloneable{

    protected State parent

    State getParent() {
        return parent
    }

    void setParent(State parent) {
        this.parent = parent
    }
}
