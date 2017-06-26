package ksgridworld;


//TODO re-factor imports
import burlap.behavior.policy.Policy;
import burlap.behavior.policy.PolicyUtils;
import burlap.behavior.singleagent.Episode;
import burlap.behavior.singleagent.auxiliary.EpisodeSequenceVisualizer;
import burlap.behavior.singleagent.auxiliary.performance.LearningAlgorithmExperimenter;
import burlap.behavior.singleagent.auxiliary.performance.PerformanceMetric;
import burlap.behavior.singleagent.auxiliary.performance.TrialMode;
import burlap.behavior.singleagent.learning.LearningAgent;
import burlap.behavior.singleagent.learning.LearningAgentFactory;
import burlap.behavior.singleagent.learning.tdmethods.QLearning;
import burlap.behavior.singleagent.learning.tdmethods.SarsaLam;
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
import burlap.mdp.singleagent.environment.Environment;
import burlap.mdp.singleagent.environment.EnvironmentOutcome;
import burlap.mdp.singleagent.model.FactoredModel;
import burlap.mdp.singleagent.model.RewardFunction;
import burlap.mdp.singleagent.model.statemodel.FullStateModel;
import burlap.mdp.singleagent.oo.OOSADomain;
import burlap.shell.visual.VisualExplorer;
import burlap.statehashing.HashableStateFactory;
import burlap.statehashing.simple.SimpleHashableStateFactory;
import burlap.visualizer.Visualizer;
import ksgridworld.state.KSGridWorldAgent;
import ksgridworld.state.KSGridWorldState;
import ksgridworld.state.KSGridWorldBlock;
import ksgridworld.state.KSGridWorldState;
import ksgridworld.state.KSGridWorldGoal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;


public class KSGridWorldDomain implements DomainGenerator {

	public static final String ATT_X = "x";
	public static final String ATT_Y = "y";
	public static final String ATT_C = "color";

	public static final String CLASS_AGENT = "agent";
	public static final String CLASS_BLOCK = "block";
	public static final String CLASS_GOAL = "goal";
	
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
	
	
	public KSGridWorldDomain(){};
	
	//generate Object-Oriented Single Agent Domain
	@Override
	public OOSADomain generateDomain() {
		// TODO implement
//		KSGridWorldDomain d;
		OOSADomain ksdomain = new OOSADomain();
		
		ksdomain.addStateClass(CLASS_AGENT, KSGridWorldAgent.class)
			.addStateClass(CLASS_BLOCK, KSGridWorldBlock.class)
			.addStateClass(CLASS_GOAL, KSGridWorldGoal.class);	
		
		ksdomain.addActionTypes(
				new UniversalActionType(ACTION_NORTH),
				new UniversalActionType(ACTION_SOUTH),
				new UniversalActionType(ACTION_EAST),
				new UniversalActionType(ACTION_WEST));
		
		
		if(rf == null){
			
			rf = new UniformCostRF();
		}
		
		if(tf == null){
			
			tf = new NullTermination();
		}
		
		KSModel ksgmodel = new KSModel();
		
		FactoredModel fmodel = new FactoredModel(ksgmodel, rf, tf);
		ksdomain.setModel(fmodel);
		
		return ksdomain;
	}
	
	

	public static void experimenterAndPlotter(final OOSADomain ksdomain, Environment env, final HashableStateFactory hashingFactory, RewardFunction rf){
		//different reward function for more structured performance plots
		((FactoredModel)ksdomain.getModel()).setRf(rf);

		LearningAgentFactory qLearningFactory = new LearningAgentFactory() {
			public String getAgentName() {
				return "Q-Learning";
			}
			public LearningAgent generateAgent() {
				return new QLearning(ksdomain, 0.99, hashingFactory, 0.3, 0.1);
			}
		};

		LearningAlgorithmExperimenter exp = new LearningAlgorithmExperimenter(
			env, 10, 100, qLearningFactory);
		//exp.setUpPlottingConfiguration(500, 250, 2, 1000,
		//		TrialMode.MOST_RECENT_AND_AVERAGE,
		//		PerformanceMetric.CUMULATIVE_STEPS_PER_EPISODE,
		//		PerformanceMetric.AVERAGE_EPISODE_REWARD);
		//exp.startExperiment();
		//exp.writeStepAndEpisodeDataToCSV("expData");
		//exp.plotter.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}


	public OOState getInitialState(int x, int y) {
		// TODO Set up initial state
		
		KSGridWorldAgent agent = new KSGridWorldAgent(CLASS_AGENT, x, y);
        List<KSGridWorldBlock> blocks = new ArrayList<>(Arrays.asList());
		//KSGridWorldBlock block = new KSGridWorldBlock(CLASS_BLOCK, 4, 4);
        KSGridWorldGoal goal = new KSGridWorldGoal(CLASS_GOAL, 2, 3, "red");
		
		KSGridWorldState s = new KSGridWorldState(agent, blocks, goal);
		
		//need to add in the objects as necessary
		s.addObject(new KSGridWorldBlock("block0", 0, 2));
		s.addObject(new KSGridWorldBlock("block1", 1, 2));
		s.addObject(new KSGridWorldBlock("block2", 2, 2));
		s.addObject(new KSGridWorldBlock("block3", 3, 0));
		s.addObject(new KSGridWorldBlock("block4", 2, 4));
		s.addObject(new KSGridWorldBlock("block5", 1, 3));
		//s.addObject(new KSGridWorldAgent("overallAgent", 1, 0));
		//s.addObject(new KSGridWorldGoal ("overallGoal", 2, 3, "red"));
		
		return s;
	}
	
	
}




