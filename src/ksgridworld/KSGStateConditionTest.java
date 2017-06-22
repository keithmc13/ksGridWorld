package ksgridworld;

import burlap.mdp.auxiliary.stateconditiontest.StateConditionTest;
import burlap.mdp.core.action.Action;
import burlap.mdp.core.state.State;
import burlap.mdp.singleagent.common.GoalBasedRF;
import ksgridworld.KSGridWorldDomain;
import ksgridworld.state.KSGridWorldGoal;
import ksgridworld.state.KSGridWorldAgent;

public class KSGStateConditionTest implements StateConditionTest {
	
	public KSGStateConditionTest(){
		
		//some KSGStateConditionTest fields here
	}
	
	@Override
	public boolean satisfies(State s) {
		
		/*
		 * Goal g = s.getGoal;
		 * check if the agent is at the goal
		 * if g.x = agent.x
		 * if g.y = agent. y
		 */
	
		
		KSGridWorldGoal location = null;
		//if agent is in the goal
		KSGridWorldAgent ksa = null;
		
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
