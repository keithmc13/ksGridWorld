package ksgridworld.state;


import java.util.Arrays;
import java.util.List;

public class KSGridWorldGoal extends KSLocalObject {
    public static final String CLASS_NAME = "KSGoal";
    public static final String ATTR_COLOR = "color";

    public KSGridWorldGoal(String name, int x, int y, String color){
        super(name, x, y);
        super.set(ATTR_COLOR, color);
    }
    private static List<Object> keys = Arrays.asList(
            KSLocalObject.keys.get(0), //X key
            KSLocalObject.keys.get(1), //Y key
            ATTR_COLOR
    );
    @Override
    public List<Object> variableKeys() {
        return keys;
    }

    @Override
    public String className() {
        return CLASS_NAME;
    }

    @Override
    public KSGridWorldGoal copyWithName(String s) {
        return new KSGridWorldGoal(s, super.getX(), super.getY(),this.getColor() );
    }

    protected String getColor(){
        return (String)super.get(ATTR_COLOR);
    }

    @Override
    public KSGridWorldGoal copy() {
        return copyWithName(super.name);
    }

}
