package ksgridworld;


import burlap.mdp.core.StateTransitionProb;
import burlap.mdp.core.action.Action;
import burlap.mdp.core.oo.state.ObjectInstance;
import burlap.mdp.core.state.State;
import burlap.mdp.singleagent.model.statemodel.FullStateModel;
//import burlap.mdp.singleagent.model.FactoredModel;
import ksgridworld.state.KSGridWorldAgent;
import ksgridworld.state.KSGridWorldBlock;
import ksgridworld.state.KSGridWorldState;
import ksgridworld.state.KSLocalObject;
import static ksgridworld.KSGridWorldDomain.*;

//import java.lang.Object;
import java.util.Collections;
import java.util.List;


/**
 * Created by sparr on 6/13/17.
 */
public class KSModel implements FullStateModel {
	
	protected int minX;
	protected int minY;
	protected int maxX;
	protected int maxY;
	protected double [][] transitionProbs;
	
	public KSModel() {
		
	}
	
	public KSModel(int minX, int minY, int maxX, int maxY, int numActions) {
		this.minX = minX;
		this.minY = minY;
		this.maxX = maxX;
		this.maxY = maxY;
		this.transitionProbs = new double[numActions][numActions];
		for(int i = 0; i < numActions; i++){
			for(int j = 0; j < numActions; j++){
				double p = i != j ? 0 : 1;
				transitionProbs[i][j] = p;
			}
		}
	}
	
	
	@Override
	public List<StateTransitionProb> stateTransitions(State s, Action a){
		return FullStateModel.Helper.deterministicTransition(this, s, a);
	}

	@Override
	public State sample(State s, Action a){
		
		s = s.copy();
		String actionName = a.actionName();
		if(actionName.equals(KSGridWorldDomain.ACTION_NORTH) || actionName.equals(KSGridWorldDomain.ACTION_SOUTH)
		||actionName.equals(KSGridWorldDomain.ACTION_EAST) || actionName.equals(KSGridWorldDomain.ACTION_WEST)){
			
			return move(s, actionName);
		}
		
		else{
			throw new RuntimeException("Unknown action" + actionName);
		}
		
	}
	
	public State move(State s, String actionName){
		
		KSGridWorldState kws = (KSGridWorldState)s;
		KSGridWorldAgent agent = kws.getAgent();
		int direction = actionDir(actionName);
		int curX = (Integer) agent.get(KSGridWorldDomain.ATT_X);
		int curY = (Integer) agent.get(KSGridWorldDomain.ATT_Y);
		//first get change in x and y from direction using 0: north; 1: south; 2:east; 3: west
		int xdelta = 0;
		int ydelta = 0;
		if(direction == 0){
			ydelta = 1;
		} else if(direction == 1){
			ydelta = -1;
		} else if(direction == 2){
			xdelta = 1;
		} else{
			xdelta = -1;
		}
		int nx = curX + xdelta;
		int ny = curY + ydelta;
		//int nbx = nx;
		//int nby = ny;

		boolean agentCanMove = false;
		
		//be sure that the width is less than the new x
		//and the height is less than the new y
		if(kws.getWidth() > nx && kws.getHeight() > ny && nx >= 0 && ny >= 0){
			//agent is able to move within the boundaries
			agentCanMove = true;
			//check if the agent's coordinates are the same as a block object
			//if so, agentCanMove is false
			List<ObjectInstance> blocks = kws.objectsOfClass(CLASS_BLOCK);
			for(ObjectInstance block: blocks){
				KSGridWorldBlock b = (KSGridWorldBlock) block;
				if(nx == b.getX()){
					if(ny == b.getY()){
						agentCanMove = false;
						break;
					}
				}
				
			}
			
		}
		
			
		
		KSGridWorldAgent nAgent = kws.touchAgent();
		if (agentCanMove){
			
			nAgent.set(KSGridWorldDomain.ATT_X, nx);
			nAgent.set(KSGridWorldDomain.ATT_Y, ny);
		}
		
		return s;
	}
	
	
	protected static int actionDir(String actionName) {
		int direction = -1;
		if (actionName.equals(KSGridWorldDomain.ACTION_NORTH)) {
			direction = 0;
		} else if (actionName.equals(KSGridWorldDomain.ACTION_SOUTH)) {
			direction = 1;
		} else if (actionName.equals(KSGridWorldDomain.ACTION_EAST)) {
			direction = 2;
		} else if (actionName.equals(KSGridWorldDomain.ACTION_WEST)) {
			direction = 3;
		} else {
			throw new RuntimeException("ERROR: not a valid direction for " + actionName);
		}
		return direction;
	}
	
	protected int actionDir(Action a){
		return actionDir(a.actionName());
	}
}
