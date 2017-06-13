package ksgridworld.state;

import burlap.mdp.core.oo.state.ObjectInstance;
import burlap.mdp.core.state.State;


public class KSGridWorldAgent extends KSLocalObject {
    //TODO move the classname to domain
    private static final String CLASS_NAME = "KSAgent";

    public KSGridWorldAgent(String name, int x, int y){
        super(name, x, y);
    }

    @Override
    public String className() {
        return CLASS_NAME;
    }

    @Override
    public ObjectInstance copyWithName(String name) {
        return new KSGridWorldAgent(name, super.getX(), super.getY());
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
