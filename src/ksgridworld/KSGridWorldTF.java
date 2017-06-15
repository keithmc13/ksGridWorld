package ksgridworld;

import java.util.ArrayList;
import java.util.List;

import burlap.mdp.core.TerminalFunction;
import burlap.mdp.core.oo.propositional.PropositionalFunction;
import burlap.mdp.core.state.State;

public class KSGridWorldTF implements TerminalFunction{

	List<PropositionalFunction> pfs = new ArrayList<PropositionalFunction>();
	
	public KSGridWorldTF(){
	
	}
	
	@Override
	public boolean isTerminal(State s){
		
		boolean isTS = false;
		
		if(KSGridWorldDomain.isGoal(s) || KSGridWorldDomain.isFailure(s)){
			isTS = true;
		}
		
		return isTS;
	}
	
}
