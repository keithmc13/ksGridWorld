package ksgridworld.state;

import cleanup.Cleanup;
import utils.MutableObject;
import utils.MutableObjectInstance;
 
public class KSGridWorldBlock extends MutableObject implements MutableObjectInstance {

	private String name;
	
	public KSGridWorldBlock(){
		
		
	}
	
	
	
	
	public KSGridWorldBlock(int x, int y){
		
		this(KSGridWorld.CLASS_BLOCK, (Object)x, (Object)y, (Object)"chair", (Object)"yellow");
	}
	
}
