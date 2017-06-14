package ksgridworld.state;


public class KSGridWorldBlock extends KSLocalObject {
	public static final String CLASS_NAME = "KSBlock";
	public KSGridWorldBlock(String name, int x, int y){
		super(name, x, y);
	}
	public static String staticName(){
		return CLASS_NAME;
	}
	@Override
	public String className() {
		return CLASS_NAME;
	}

	@Override
	public KSGridWorldBlock copyWithName(String s) {
		return new KSGridWorldBlock(s, super.getX(), super.getY());
	}

	@Override
	public KSGridWorldBlock copy() {
		return copyWithName(super.name);
	}
}
