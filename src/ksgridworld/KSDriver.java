package ksgridworld;

import burlap.mdp.singleagent.oo.OOSADomain;

/**
 * Created by sparr on 6/13/17.
 */
public class KSDriver {


    public static void main(String [] args){

        KSGridWorldDomain dgen = new KSGridWorldDomain();
        OOSADomain domain = dgen.generateDomain();

    }
    
}
