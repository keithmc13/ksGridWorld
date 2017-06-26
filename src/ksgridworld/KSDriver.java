package ksgridworld;

import burlap.behavior.singleagent.Episode;
import burlap.behavior.singleagent.learning.LearningAgent;
import burlap.behavior.singleagent.learning.tdmethods.QLearning;
import burlap.behavior.singleagent.planning.deterministic.uninformed.bfs.BFS;
import burlap.mdp.auxiliary.stateconditiontest.StateConditionTest;
import burlap.mdp.core.TerminalFunction;
import burlap.mdp.core.oo.state.OOState;
import burlap.mdp.singleagent.environment.Environment;
import burlap.mdp.singleagent.environment.SimulatedEnvironment;
import burlap.mdp.singleagent.model.RewardFunction;
import burlap.mdp.singleagent.oo.OOSADomain;
import burlap.statehashing.HashableStateFactory;
import burlap.statehashing.simple.SimpleHashableStateFactory;
import ksgridworld.KSGridWorldTF;
import ksgridworld.state.KSGridWorldGoal;
import ksgridworld.state.KSGridWorldState;
import ksgridworld.KSGridWorldRF;
import ksgridworld.KSGridWorldDomain;
import ksgridworld.KSGStateConditionTest;

/**
 * Created by sparr on 6/13/17.
 */
public class KSDriver {


    public static void main(String [] args){

        //KSGridWorldDomain dgen = new KSGridWorldDomain();
        //OOSADomain domain = dgen.generateDomain();

		OOSADomain domain;
    	RewardFunction rf;
		TerminalFunction tf;
		KSGridWorldGoal goalCondition;
		//OOState initialState;
		KSGridWorldState initialState;
		HashableStateFactory hashingFactory;
		Environment env;

		KSGStateConditionTest atGoal = new KSGStateConditionTest();
		double rewardGoal = 1000;
		double rewardDefault = 0;
		double rewardNoop = 0;
		double rewardMove = 0;
		
		String name;
		int x;
		int y;
		String color;
		
		goalCondition = new KSGridWorldGoal();
		rf = new KSGridWorldRF(atGoal, rewardGoal, rewardDefault, rewardNoop, rewardMove);
		tf = new KSGridWorldTF();
		KSGridWorldDomain gen = new KSGridWorldDomain();
		gen.setRf(rf);
		gen.setTf(tf);
		domain = gen.generateDomain();
//		CleanupGoalDescription[] goals = new CleanupGoalDescription[]{
//				new CleanupGoalDescription(new String[]{"block0", "room1"}, domain.propFunction(PF_BLOCK_IN_ROOM)),
//				new CleanupGoalDescription(new String[]{"block1", "room1"}, domain.propFunction(PF_BLOCK_IN_ROOM)),
//				new CleanupGoalDescription(new String[]{"block2", "room0"}, domain.propFunction(PF_BLOCK_IN_ROOM))		};
//		goalCondition.setGoals(goals);
		initialState = (KSGridWorldState) gen.getInitialState(1, 0);
		hashingFactory = new SimpleHashableStateFactory();
		env = new SimulatedEnvironment(domain, initialState);
//
//		Visualizer v = CleanupVisualizer.getVisualizer(width, height);
//		VisualExplorer exp = new VisualExplorer(domain, v, initialState);
//		exp.addKeyAction("w", ACTION_NORTH, "");
//		exp.addKeyAction("s", ACTION_SOUTH, "");
//		exp.addKeyAction("d", ACTION_EAST, "");
//		exp.addKeyAction("a", ACTION_WEST, "");
//		exp.addKeyAction("r", ACTION_PULL, "");
//		exp.initGUI();
//		exp.requestFocus();
//		exp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		String outputPath = "./output/";
		double gamma = 0.9;
		double qInit = 0;
		double learningRate = 0.01;
		int nEpisodes = 100;
		int maxEpisodeSize = 1;
		int writeEvery = 1;
		
		LearningAgent agent = new QLearning(domain, gamma, hashingFactory, qInit, learningRate, maxEpisodeSize);
		for(int i = 0; i < nEpisodes; i++){
			Episode e = agent.runLearningEpisode(env, maxEpisodeSize);
			if (i % writeEvery == 0) {
				e.write(outputPath + "ql_" + i);
			}
			System.out.println(i + ": " + e.maxTimeStep());
			System.out.println(i + ": " + e.actionSequence);
			System.out.println(i + ": " + e.stateSequence);
			env.resetEnvironment();
		}
		/*
		LearningAgent agent = new BFS(domain, gamma, hashingFactory, qInit, learningRate, maxEpisodeSize);
		for(int i = 0; i < nEpisodes; i++){
			Episode e = agent.runLearningEpisode(env, maxEpisodeSize);
			if (i % writeEvery == 0) {
				e.write(outputPath + "ql_" + i);
			}
			System.out.println(i + ": " + e.maxTimeStep());
			System.out.println(i + ": " + e.actionSequence);
			env.resetEnvironment();
			
		}*/
//		Visualizer v = CleanupVisualizer.getVisualizer(width, height);
//		EpisodeSequenceVisualizer esv = new EpisodeSequenceVisualizer(v, domain, outputPath);
//		esv.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	
    }
    
}
