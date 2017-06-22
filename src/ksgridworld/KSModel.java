package ksgridworld;


import burlap.mdp.core.StateTransitionProb;
import burlap.mdp.core.action.Action;
import burlap.mdp.core.oo.state.ObjectInstance;
import burlap.mdp.core.state.State;
import burlap.mdp.singleagent.model.statemodel.FullStateModel;
import ksgridworld.state.KSGridWorldAgent;
import ksgridworld.state.KSGridWorldBlock;
import ksgridworld.state.KSGridWorldState;
import ksgridworld.state.KSLocalObject;
import static ksgridworld.KSGridWorldDomain.*;

import java.util.Collections;
import java.util.List;


/**
 * Created by sparr on 6/13/17.
 */
public class KSModel implements FullStateModel {
    @Override
    public List<StateTransitionProb> stateTransitions(State state, Action action) {
        KSGridWorldState KSstate = (KSGridWorldState)state;
        KSGStateConditionTest goalTest = new KSGStateConditionTest();
        //TODO write this method more fully for stochastic model
        int dir;
        switch(action.actionName()) {
            case (ACTION_NORTH):dir = 1;break;
            case (ACTION_EAST): dir = 2;break;
            case (ACTION_SOUTH):dir = 3;break;
            case (ACTION_WEST): dir = 4;break;
            default: throw new RuntimeException("Unknown action name");
        }
        vec direction = new vec(dir);
        KSGridWorldAgent agent = (KSGridWorldAgent)KSstate.get(CLASS_AGENT);
        pos agentPosition = new pos(agent.getX(), agent.getY());
        pos potentialPosition = move(agentPosition, direction);
        // pick final position based on whether movement makes sense
        pos finalPosition =
        		goalTest.satisfies(KSstate) || blockAtLocation(KSstate, potentialPosition) ?
                        potentialPosition : agentPosition;

        KSGridWorldState newState = KSstate.copy();
        newState.set(CLASS_AGENT, new KSGridWorldAgent(agent.name(),
                     finalPosition.x, finalPosition.y));
        StateTransitionProb stp = new StateTransitionProb(newState, 1.0);

        return Collections.singletonList(stp);
    }
    public pos move(pos loc, vec dir){
        return loc.transform(dir);
    }

    //TODO write this method more fully for stochasticity
    @Override
    public State sample(State state, Action action) {
        //simply grab the only state in the list
        return stateTransitions(state, action).get(0).s;
    }
    private class pos{
        public final int x;
        public final int y;
        public pos(int x, int y){
            this.x = x;
            this.y = y;
        }
        public pos transform(vec d){
            return new pos(this.x+d.xv, this.y+d.yv);
        }
    }

    private class vec{
        public final int xv;
        public final int yv;
        public vec(int v){
            int tyv, txv;
            //v = 3(south)
            txv = v%2==1 ? 0 : 1;
            tyv = txv==0 ? 1 : 0;
            //txv, tyv = 0, 1
            yv = (v<3) ? tyv : -tyv;
            xv = (v<3) ? txv : -txv;
            //txv, tyv = 0, -1 like it should be
        }
        public vec(int xv, int yv){
            this.xv = xv;
            this.yv = yv;
        }
    }

    public boolean blockAtLocation(KSGridWorldState state, pos position){
        return blockAtLocation(state, position.x, position.y);
    }
    public boolean blockAtLocation(KSGridWorldState state, int x, int y) {
        for (ObjectInstance o : state.objectsOfClass(CLASS_BLOCK))
            if (o instanceof KSLocalObject) {
                KSLocalObject s = (KSLocalObject) o;
                if (s.getX() == x && s.getY() == y)
                    return true;
            }
        return false;
    }
}
