package ksgridworld.state;

import burlap.mdp.core.oo.state.MutableOOState;
import burlap.mdp.core.oo.state.OOStateUtilities;
import burlap.mdp.core.oo.state.ObjectInstance;
import burlap.mdp.core.oo.state.exceptions.UnknownClassException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static ksgridworld.KSGridWorldDomain.*;

public class KSGridWorldState implements MutableOOState {

	private KSGridWorldAgent agent;
	private List<KSGridWorldBlock> blocks;
	private KSGridWorldGoal goal;

    private static final List<Object> keys=
			Arrays.asList(CLASS_GOAL, CLASS_AGENT, CLASS_BLOCK);

	public KSGridWorldState(KSGridWorldAgent agent,
                            List<KSGridWorldBlock> blocks,
                            KSGridWorldGoal goal){
		this.agent = agent;
		this.blocks = blocks;
		this.goal = goal;
	}


	@Override
	public KSGridWorldState addObject(ObjectInstance o) {
	    if(o instanceof KSGridWorldBlock)
            this.blocks.add((KSGridWorldBlock)o);
	    else if(o instanceof KSGridWorldAgent)
            this.agent = (KSGridWorldAgent)o;
	    else if(o instanceof KSGridWorldGoal)
	        this.goal  = (KSGridWorldGoal) o;
	    else
	        throw new UnknownClassException(o.className());

		return this;
	}

	@Override
    //be careful with removing the agent and goal and stuff
	public KSGridWorldState removeObject(String s) {

	    if(s.equals(agent.name()))
	        agent = null;
	    else if(s.equals(goal.name()))
	        goal = null;
	    else{
	        //use explicit iterator for in iteration removal
	        for(Iterator<KSGridWorldBlock> i=blocks.iterator();i.hasNext();)
	            if(s.equals(i.next().name()))
	                i.remove();
        }
		return this;
	}

	@Override
	public KSGridWorldState renameObject(String s, String s1) {
		ObjectInstance o = this.object(s);
		o = o.copyWithName(s1);
		this.removeObject(s1);

	    return this.addObject(o);
	}

	@Override
	public int numObjects() {
	    int count = 0;
	    if(agent!=null)count++;
	    if(goal !=null)count++;
		return count+blocks.size();
	}

	@Override
	public ObjectInstance object(String s) {
	    if(agent!=null&&s.equals(agent.name()))
	        return agent;
	    else if(goal!=null&&s.equals(goal.name()))
	        return goal;
	    else
	        for(KSGridWorldBlock b : blocks)
	            if(s.equals(b.name()))
	                return b;

        throw new ObjectNotFoundException("No object of name: "+s+" found");
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
            case(CLASS_AGENT):
                objectsOfClass.add(agent);    break;
            case (CLASS_GOAL):
                objectsOfClass.add(goal);     break;
	        case(CLASS_BLOCK):
                objectsOfClass.addAll(blocks);break;

        }
        return objectsOfClass;
	}

	@Override
    //I'd rather suppress just the one List<> cast, not sure how
    @SuppressWarnings("unchecked")
    public KSGridWorldState set(Object key, Object value) {
	    //so are keys always strings? I've never seen one that wasn't
		String immutableKey = (String)key;
		switch(immutableKey) {
			case(CLASS_AGENT):
				agent  = (KSGridWorldAgent)value;break;
			case (CLASS_GOAL):
				goal   = (KSGridWorldGoal) value;break;
			case(CLASS_BLOCK):
				blocks = (List<KSGridWorldBlock>)value;break;
		}
        return this;
    }

	@Override
	public List<Object> variableKeys() {
		return OOStateUtilities.flatStateKeys(this);
	}

	@Override
    //I'm letting this return null if goal is null, just because
    //likely burlap will pick up on the goal key regardless of state
	public Object get(Object key) {
        String immutableKey = (String)key;
        switch(immutableKey) {
            case(CLASS_AGENT):
                return agent;
            case (CLASS_GOAL):
                return goal;
            case(CLASS_BLOCK):
                return blocks;
        }
		throw new ObjectNotFoundException("key: "+(immutableKey)+" given not in state keys");
	}

	@Override
	public KSGridWorldState copy() {
	    List<KSGridWorldBlock> newblocks = new ArrayList<>();
	    for(KSGridWorldBlock b : blocks)
	        newblocks.add(b.copy());
		return new KSGridWorldState(agent.copy(), newblocks, goal.copy());
	}

	@Override
    public String toString(){
	    return OOStateUtilities.ooStateToString(this);
    }

}
