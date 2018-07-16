package unitn.adk2018.carparking.action;

import unitn.adk2018.carparking.Carparking;
import unitn.adk2018.pddl.PddlAction3Args;
import unitn.adk2018.pddl.PddlWorld;

public class Move_right_wait extends PddlAction3Args{
	
	@Override
	public boolean checkPreconditions(PddlWorld world, String slowcar, String left, String right) {
		return Carparking.sayRight(left, right).isDeclaredIn( world )
				&& Carparking.sayFree(right).isDeclaredIn( world )
				&& Carparking.saySlowcar(slowcar).isDeclaredIn( world )
				&& Carparking.sayOn(slowcar, left).isDeclaredIn( world )
				&& Carparking.sayWaited(slowcar).isDeclaredIn( world )
				&& Carparking.sayMe(slowcar).isDeclaredIn(world);
	}
	
	@Override
	public boolean effects(PddlWorld world, String slowcar, String left, String right) {
		world.declare( Carparking.sayFree(left));
		world.undeclare( Carparking.sayFree(right) );
		world.declare( Carparking.sayOn(slowcar, right) );
		world.undeclare( Carparking.sayWaited(slowcar) );
		world.declare( Carparking.sayEmpty(slowcar) );
		world.undeclare( Carparking.sayOn(slowcar, left) );
		return true;
	}
}
