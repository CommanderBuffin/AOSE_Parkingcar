package unitn.adk2018.carparking.action;

import unitn.adk2018.carparking.Carparking;
import unitn.adk2018.pddl.PddlAction1Args;
import unitn.adk2018.pddl.PddlWorld;

public class Wait extends PddlAction1Args{
	
	@Override
	public boolean checkPreconditions(PddlWorld world, String slowcar) {
		return Carparking.sayEmpty(slowcar).isDeclaredIn( world )
				&& Carparking.saySlowcar(slowcar).isDeclaredIn( world )
				&& Carparking.sayMe(slowcar).isDeclaredIn(world);
	}
	
	@Override
	public boolean effects(PddlWorld world, String slowcar) {
		world.declare( Carparking.sayWaited(slowcar));
		world.undeclare( Carparking.sayEmpty(slowcar));
		return true;
	}
}
