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
	
	public static boolean satisfies(State s) {
		
		/*
		 * Goal g = s.getGoal;
		 * check if the agent is at the goal
		 * if g.x = agent.x
		 * if g.y = agent. y
		 */
		KSGridWorldGoal location;
		
		//if agent is in the goal
		if(/*agent x coordinate is same as goal x coordinate*/){
			if(/*if agent y coordinate is same as goal*/){
			}
		}
		
		else{
			return false;
		}
	}
}
