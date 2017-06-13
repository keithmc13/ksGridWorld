package ksgridworld.state;

import burlap.mdp.core.oo.state.MutableOOState;
import burlap.mdp.core.oo.state.OOStateUtilities;
import burlap.mdp.core.oo.state.ObjectInstance;
import burlap.mdp.core.oo.state.exceptions.UnknownClassException;
import burlap.mdp.core.state.MutableState;
import burlap.mdp.core.state.State;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class KSGridWorldState implements MutableOOState {

	private KSGridWorldAgent agent;
	private List<KSGridWorldBlock> blocks;
	private KSGridWorldGoal goal;
    private static final List<Object> keys= Arrays.asList(
            KSGridWorldGoal.CLASS_NAME,
            KSGridWorldBlock.CLASS_NAME,
            KSGridWorldAgent.CLASS_NAME);

	public KSGridWorldState(KSGridWorldAgent agent,
                            List<KSGridWorldBlock> blocks,
                            KSGridWorldGoal goal){
		this.agent = agent;
		this.blocks = blocks;
		this.goal = goal;
	}


	@Override
	public MutableOOState addObject(ObjectInstance o) {
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
	public MutableOOState removeObject(String s) {
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
	public MutableOOState renameObject(String s, String s1) {
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
	    else{
	        for(KSGridWorldBlock b : blocks)
	            if(s.equals(b.name()))
	                return b;
        }
        //not sure if return null makes sense here, would rather throw error
		return null;
	    //throw new ObjectNotFoundException("Name given wasn't used");
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
    //I'd rather suppress just the one List<> cast, not sure how
    @SuppressWarnings("unchecked")
    public MutableState set(Object key, Object value) {

	    if(key.equals(KSGridWorldAgent.CLASS_NAME))
	        agent =(KSGridWorldAgent)value;
        else if(key.equals(KSGridWorldGoal.CLASS_NAME))
            goal = (KSGridWorldGoal)value;
        else if(key.equals(KSGridWorldBlock.CLASS_NAME))
            blocks = (List<KSGridWorldBlock>)value;

        //again, another null?
        return null;
        //throw new ObjectNotFoundException("key given not in state keys");
    }

	@Override
	public List<Object> variableKeys() {
		return OOStateUtilities.flatStateKeys(this);
	}

	@Override
    //I'm letting this return null if goal is null, just because
    //likely burlap will pick up on the goal key regardless of state
	public Object get(Object key) {
	    if(key.equals(KSGridWorldAgent.CLASS_NAME))
	        return agent;
	    else if(key.equals(KSGridWorldGoal.CLASS_NAME))
	        return goal;
	    else if(key.equals(KSGridWorldGoal.CLASS_NAME))
	        return blocks;
	    //yet another null
		return null;
		//throw new ObjectNotFoundException("key given not in state keys");
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
