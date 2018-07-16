package unitn.adk2018.pddl;

public abstract class PddlAction5Args extends PddlAction {
	
	abstract public boolean checkPreconditions (PddlWorld world, String a1, String a2, String a3, String a4, String a5);
	abstract public boolean effects (PddlWorld world, String a1, String a2, String a3, String a4, String a5);
	
	public boolean checkPreconditions (PddlWorld world, String[] args) {
		if (args.length!=5)
			return false;
		return checkPreconditions(world, args[0], args[1], args[2], args[3], args[4]);
	}
	
	public boolean effects (PddlWorld world, String[] args) {
		if (args.length!=5)
			return false;
		return effects(world, args[0], args[1], args[2], args[3], args[4]);
	}
	
}
