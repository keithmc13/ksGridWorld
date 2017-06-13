package ksgridworld;


import burlap.mdp.core.StateTransitionProb;
import burlap.mdp.core.action.Action;
import burlap.mdp.core.state.State;
import burlap.mdp.singleagent.model.statemodel.FullStateModel;

import java.util.List;

/**
 * Created by sparr on 6/13/17.
 */
public class KSModel implements FullStateModel {
    //TODO fully flesh this out
    @Override
    public List<StateTransitionProb> stateTransitions(State state, Action action) {
        //TODO implement
        return null;
    }

    @Override
    public State sample(State state, Action action) {
        //TODO implement
        return null;
    }
}
