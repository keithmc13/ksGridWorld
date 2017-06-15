package ksgridworld;


//TODO refactor imports
import burlap.behavior.policy.Policy;
import burlap.behavior.policy.PolicyUtils;
import burlap.behavior.singleagent.Episode;
import burlap.behavior.singleagent.auxiliary.EpisodeSequenceVisualizer;
import burlap.behavior.singleagent.planning.stochastic.rtdp.BoundedRTDP;
import burlap.behavior.valuefunction.ConstantValueFunction;
import burlap.debugtools.RandomFactory;
import burlap.mdp.auxiliary.DomainGenerator;
import burlap.mdp.auxiliary.common.GoalConditionTF;
import burlap.mdp.auxiliary.common.NullTermination;
import burlap.mdp.auxiliary.stateconditiontest.StateConditionTest;
import burlap.mdp.core.Domain;
import burlap.mdp.core.StateTransitionProb;
import burlap.mdp.core.TerminalFunction;
import burlap.mdp.core.action.Action;
import burlap.mdp.core.action.ActionType;
import burlap.mdp.core.action.UniversalActionType;
import burlap.mdp.core.oo.OODomain;
import burlap.mdp.core.oo.propositional.GroundedProp;
import burlap.mdp.core.oo.propositional.PropositionalFunction;
import burlap.mdp.core.oo.state.OOState;
import burlap.mdp.core.oo.state.ObjectInstance;
import burlap.mdp.core.state.MutableState;
import burlap.mdp.core.state.State;
import burlap.mdp.singleagent.common.GoalBasedRF;
import burlap.mdp.singleagent.common.UniformCostRF;
import burlap.mdp.singleagent.environment.EnvironmentOutcome;
import burlap.mdp.singleagent.model.FactoredModel;
import burlap.mdp.singleagent.model.RewardFunction;
import burlap.mdp.singleagent.model.statemodel.FullStateModel;
import burlap.mdp.singleagent.oo.OOSADomain;
import burlap.shell.visual.VisualExplorer;
import burlap.statehashing.simple.SimpleHashableStateFactory;
import burlap.visualizer.Visualizer;
import ksgridworld.state.KSGridWorldAgent;
import ksgridworld.state.KSGridWorldState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class KSGridWorldDomain implements DomainGenerator {

	public static final String ATT_X = "x";
	public static final String ATT_Y = "y";

	//TODO get rid of deprecated keys and add new ones
	public static final String ATT_DIR = "direction";
	
	public static final String CLASS_AGENT = "agent";
	public static final String CLASS_BLOCK = "block";
	
	public static final String ACTION_NORTH = "north";
	public static final String ACTION_SOUTH = "south";
	public static final String ACTION_EAST = "east";
	public static final String ACTION_WEST = "west";
	
	public static final String VAR_SUCCESS = "success";
	public static final String VAR_ILLEGAL = "illegal";
	/*
	 * In case we add diagonal directional movements
	 * 
	 * public static final String ACTION_NORTHEAST = "northeast";
	 * public static final String ACTION_NORTHEAST = "northwest";
	 * public static final String ACTION_SOUTHEAST = "southeast";
	 * public static final String ACTION_SOUTHEAST = "southwest";
	 * 
	 */
	
	
	//propositional functions
	
	
	//reward function 
	private RewardFunction rf;
	//terminal function
	private TerminalFunction tf;
	
	public KSGridWorldDomain(RewardFunction rf, TerminalFunction tf){
		
		this.rf = rf;
		this.tf = tf;
	}
	
	public KSGridWorldDomain(){};
	
	
	//generate Object-Oriented Single Agent Domain
	@Override
	public OOSADomain generateDomain() {
		// TODO implement
//		KSGridWorldDomain d;
//		OOSADomain ksd = d.generateDomain();
		
		
		
		
		
		
		if(rf == null){
			
			rf = new UniformCostRF();
		}
		
		if(tf == null){
			
			tf = new NullTermination();
		}
		
		
		
		


		return  null;
	}

	public RewardFunction getRf() {
		return rf;
	}

	public void setRf(RewardFunction rf) {
		this.rf = rf;
	}

	public TerminalFunction getTf() {
		return tf;
	}

	public void setTf(TerminalFunction tf) {
		this.tf = tf;
	}
	
	/*
	 * Objects to include:
	 * -Agent <X, Y>
	 * -Block <X, Y, Color>
	 * -Goal <X, Y, Color>
	 * 
	 */

	
	
	public static boolean isGoal(State s) {
		KSGridWorldAgent agent = ((KSGridWorldState)s).getAgent();
		if (agent == null || agent.get(VAR_SUCCESS) == null) { return false; }
		return (Boolean) agent.get(VAR_SUCCESS);
	}
	
	public static boolean isFailure(State s) {
		KSGridWorldAgent agent = ((KSGridWorldState)s).getAgent();
		if (agent == null || agent.get(VAR_ILLEGAL) == null) { return false; }
		return (Boolean) agent.get(VAR_ILLEGAL);
	}
	
};




