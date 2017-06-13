package ksgridworld.state;

import burlap.mdp.core.oo.state.MutableOOState;
import burlap.mdp.core.oo.state.OOStateUtilities;
import burlap.mdp.core.oo.state.ObjectInstance;
import burlap.mdp.core.state.MutableState;
import burlap.mdp.core.state.State;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class KSGridWorldState implements MutableOOState {

	private KSGridWorldAgent agent;
	private List<KSGridWorldBlock> blocks;
	private KSGridWorldGoal goal;

	public KSGridWorldState(KSGridWorldAgent agent,
                            List<KSGridWorldBlock> blocks,
                            KSGridWorldGoal goal){
		this.agent = agent;
		this.blocks = blocks;
		this.goal = goal;


	}


	@Override
	public MutableOOState addObject(ObjectInstance objectInstance) {
		return null;
	}

	@Override
	public MutableOOState removeObject(String s) {
		return null;
	}

	@Override
	public MutableOOState renameObject(String s, String s1) {
		return null;
	}

	@Override
    //this is only valid in states with an agent and a goal
	public int numObjects() {
		return 2+blocks.size();
	}

	@Override
	public ObjectInstance object(String s) {
		return null;
	}

	@Override
	public List<ObjectInstance> objects() {
        List<ObjectInstance> objects = new ArrayList<>();
        objects.add(agent);
        objects.add(goal);
        objects.addAll(blocks);
		return objects;
	}

	@Override
	public List<ObjectInstance> objectsOfClass(String s) {
	    List<ObjectInstance> objectsOfClass = new ArrayList<>();
	    switch(s){
	        case (KSGridWorldBlock.CLASS_NAME):
                objectsOfClass.addAll(blocks);  break;
            case (KSGridWorldAgent.CLASS_NAME):
                objectsOfClass.add(agent);      break;
            case (KSGridWorldGoal .CLASS_NAME):
                objectsOfClass.add(goal);       break;
        }
        return objectsOfClass;
	}

	@Override
	public MutableState set(Object o, Object o1) {
		return null;
	}

	@Override
	public List<Object> variableKeys() {
		return OOStateUtilities.flatStateKeys(this);
	}

	@Override
	public Object get(Object o) {
		return null;
	}

	@Override
	public State copy() {
	    List<KSGridWorldBlock> newblocks = new ArrayList<>();
	    for(KSGridWorldBlock b : blocks)
	        newblocks.add(b.copy());
		return new KSGridWorldState(agent.copy(),newblocks,goal.copy());
	}
	@Override
    public String toString(){
	    return OOStateUtilities.ooStateToString(this);
    }

}
