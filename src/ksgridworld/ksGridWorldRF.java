package ksgridworld;

import burlap.mdp.auxiliary.stateconditiontest.StateConditionTest;
import burlap.mdp.core.action.Action;
import burlap.mdp.core.state.State;
import burlap.mdp.singleagent.common.GoalBasedRF;
import ksgridworld.KSGridWorldDomain;
import ksgridworld.state.KSGridWorldGoal;


public class ksGridWorldRF extends GoalBasedRF {

	private double goalReward;
	private double moveReward;
	private double no_opReward;

	public ksGridWorldRF(KSGridWorldGoal goal, double rewardGoal, double rewardDefault, double rewardNoop, double rewardMove) {
		super((StateConditionTest) goal, rewardGoal, rewardDefault);
		this.goalReward = rewardGoal;
		this.no_opReward = rewardNoop;
		this.moveReward = rewardMove;
	}
	
	public double getGoalReward() {
		return goalReward;
	}

	public void setGoalReward(double goal) {
		this.goalReward = goal;
	}
	
	@Override
	public double reward(State s, Action a, State sprime) {
		double superR = super.reward(s, a, sprime);
		double r = superR;
		if(a.actionName().equals(KSGridWorldDomain.ACTION_NORTH)){
			r += moveReward;
		}
		else if(a.actionName().equals(KSGridWorldDomain.ACTION_SOUTH)){
			r += moveReward;
		}
		else if(a.actionName().equals(KSGridWorldDomain.ACTION_EAST)){
			r += moveReward;
		}
		else if(a.actionName().equals(KSGridWorldDomain.ACTION_WEST)){
			r += moveReward;
		}
		if (s.equals(sprime)) {
			r += no_opReward;
		}
		return r;
	}
	
}
