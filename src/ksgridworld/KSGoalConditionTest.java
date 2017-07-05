package ksgridworld;

import burlap.mdp.auxiliary.stateconditiontest.StateConditionTest;
import burlap.mdp.core.action.Action;
import burlap.mdp.core.state.State;
import burlap.mdp.singleagent.common.GoalBasedRF;
import ksgridworld.KSGridWorldDomain;
import ksgridworld.state.KSGridWorldGoal;
import ksgridworld.state.KSGridWorldState;
import ksgridworld.state.KSGridWorldAgent;

public class KSGoalConditionTest implements StateConditionTest {
	
	public KSGoalConditionTest(){
		
		//some KSGStateConditionTest fields here
	}
	
	@Override
	public boolean satisfies(State s) {
		//checks if the agent is in the goal
	
		//create a state s
		KSGridWorldState state = (KSGridWorldState) s;
		//instantiate a location and agent object
		KSGridWorldGoal location = (KSGridWorldGoal) state.get(KSGridWorldDomain.CLASS_GOAL);
		//if agent is in the goal
		KSGridWorldAgent ksa = (KSGridWorldAgent) state.get(KSGridWorldDomain.CLASS_AGENT);
		
		if( ksa.getX() == location.getX()/*agent x coordinate is same as goal x coordinate*/) {
			if( ksa.getY() == location.getY()/*if agent y coordinate is same as goal*/){
				//agent is at the goal
				return true;
			}
		}
		
		//if not just return false
		else{
			return false;
		}
		
		//default to false
		return false;
	}
}
