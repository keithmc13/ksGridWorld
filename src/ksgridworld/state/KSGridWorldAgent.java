package ksgridworld.state;

import ksgridworld.KSGridWorldDomain;

public class KSGridWorldAgent extends KSLocalObject {
    //TODO move the classname to domain
    //public static final String CLASS_NAME = "KSAgent";

    public KSGridWorldAgent(String name, int x, int y){
        super(name, x, y);
    }

    @Override
    public String className() {
        return KSGridWorldDomain.CLASS_AGENT;
    }

    @Override
    public KSGridWorldAgent copyWithName(String name) {
        return new KSGridWorldAgent(name, super.getX(), super.getY());
    }

    @Override
    public KSGridWorldAgent copy(){
        return copyWithName(this.name);
    }

    @Override
	public boolean equals(Object other){
        //TODO equals
        return false;
    }
	
	
	
}
