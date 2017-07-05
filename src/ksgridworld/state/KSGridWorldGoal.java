package ksgridworld.state;


import java.util.Arrays;
import java.util.List;

import ksgridworld.KSGridWorldDomain;

public class KSGridWorldGoal extends KSLocalObject {
    //public static final String CLASS_NAME = "KSGoal";
    //public static final String ATTR_COLOR = "color";

    public KSGridWorldGoal() {
    }
    
    public KSGridWorldGoal(String name, int x, int y, String color){
        super(name, x, y);
        super.set(KSGridWorldDomain.ATT_C, color);
    }
    private static List<Object> keys = Arrays.asList(
            KSLocalObject.keys.get(0), //X key
            KSLocalObject.keys.get(1), //Y key
            KSGridWorldDomain.ATT_C
    );
    @Override
    public List<Object> variableKeys() {
        return keys;
    }

    @Override
    public String className() {
        return KSGridWorldDomain.CLASS_GOAL;
    }

    @Override
    public KSGridWorldGoal copyWithName(String s) {
        return new KSGridWorldGoal(s, super.getX(), super.getY(),this.getColor() );
    }

    protected String getColor(){
        return (String)super.get(KSGridWorldDomain.ATT_C);
    }

    @Override
    public KSGridWorldGoal copy() {
        return copyWithName(super.name);
    }

}
