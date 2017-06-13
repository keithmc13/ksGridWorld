package ksgridworld.state;

import java.util.Arrays;
import java.util.List;

import burlap.mdp.core.oo.state.ObjectInstance;
import burlap.mdp.core.state.State;
import burlap.mdp.core.state.annotations.DeepCopyState;
import ksgridworld.KSGridWorldDomain;
import utils.MutableObject;

public class KSGridWorldAgent extends MutableObject {
    //TODO move the classname to domain
    private static final String CLASS_NAME = "KSAgent";
    private final String name;

	private KSGridWorldAgent(String name){
        this.name = name;
    }

    public KSGridWorldAgent(String name, int x, int y){
    this(name);
    super.set(KSGridWorldDomain.ATT_X, x);
    super.set(KSGridWorldDomain.ATT_Y, y);
    }
	
	private final static List<Object> keys = Arrays.asList(
			KSGridWorldDomain.ATT_X,
			KSGridWorldDomain.ATT_Y);

    @Override
    public String className(){
       return CLASS_NAME;
    }

    @Override
    public String name(){
        return name;
    }

    @Override
    public List<Object> variableKeys(){
        return keys;
    }

    @Override
    public ObjectInstance copyWithName(String name) {
        return new KSGridWorldAgent(
                name,
                (Integer)super.get(KSGridWorldDomain.ATT_X),
                (Integer)super.get(KSGridWorldDomain.ATT_Y));
    }

    @Override
    public State copy(){
        return copyWithName(this.name);
    }

    @Override
	public boolean equals(Object other){
        //TODO equals
        return false;
    }
	
	
	
}
