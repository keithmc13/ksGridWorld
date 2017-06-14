package ksgridworld;

import burlap.mdp.auxiliary.stateconditiontest.StateConditionTest;
import burlap.mdp.core.action.Action;
import burlap.mdp.core.state.State;
import burlap.mdp.singleagent.common.GoalBasedRF;
import ksgridworld.KSGridWorldDomain;
import ksgridworld.state.KSGridWorldGoal;


public class KSGridWorldRF extends GoalBasedRF {

	private double goalReward;
	private double moveReward;
	private double no_opReward;

	public KSGridWorldRF(StateConditionTest atGoal, double rewardGoal, double rewardDefault, double rewardNoop, double rewardMove) {
		super(atGoal, rewardGoal, rewardDefault);
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
		double r = super.reward(s, a, sprime);
		if (s.equals(sprime))
			r += no_opReward;
		else
			r += moveReward;
		return r;
	}
	
}
