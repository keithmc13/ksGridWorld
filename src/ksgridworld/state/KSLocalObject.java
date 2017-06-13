package ksgridworld.state;

import ksgridworld.KSGridWorldDomain;
import utils.MutableObject;

import java.util.Arrays;
import java.util.List;

/**
 * Created by sparr on 6/13/17.
 */
public abstract class KSLocalObject extends MutableObject {
    protected String name;
    protected KSLocalObject(String name, int x, int y) {
        this.name = name;
        super.set(KSGridWorldDomain.ATT_X, x);
        super.set(KSGridWorldDomain.ATT_Y, y);
    }

    protected final static List<Object> keys = Arrays.asList(
            KSGridWorldDomain.ATT_X,
            KSGridWorldDomain.ATT_Y);

    public List<Object> variableKeys() {
        return keys;
    }

    @Override
    public String name() {
        return this.name;
    }

    protected int getX(){
        return (Integer)super.get(KSGridWorldDomain.ATT_X);
    }

    protected int getY(){
        return (Integer)super.get(KSGridWorldDomain.ATT_Y);
    }
}
