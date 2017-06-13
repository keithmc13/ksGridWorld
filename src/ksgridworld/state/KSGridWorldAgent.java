package ksgridworld.state;

import java.util.Arrays;
import java.util.List;

import burlap.mdp.core.oo.state.ObjectInstance;
import burlap.mdp.core.state.annotations.DeepCopyState;
import ksgridworld.KSGridWorldDomain;
import utils.MutableObject;

public class KSGridWorldAgent extends MutableObject {

	private String name;
	
	public KSGridWorldAgent(){
		
	}
	
	private final static List<Object> keys = Arrays.<Object>asList(
			KSGridWorldAgent.ATT_X, 
			KSGridWorldAgent.ATT_Y, 
			KSGridWorldAgent.ATT_DIR, 
			KSGridWorldAgent.SHAPE);

	//need to give the agent a set of coordinates when initializing from the constructor
	public KSGridWorldAgent(int x, int y, String direction){
		
	}
	
	
	
	
}
