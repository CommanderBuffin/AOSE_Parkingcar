package unitn.adk2018.carparking.action;

import unitn.adk2018.carparking.Carparking;
import unitn.adk2018.pddl.PddlAction3Args;
import unitn.adk2018.pddl.PddlWorld;

public class Move_up_wait extends PddlAction3Args{
	
	@Override
	public boolean checkPreconditions(PddlWorld world, String slowcar, String down, String up) {
		return Carparking.sayUp(down, up).isDeclaredIn( world )
				&& Carparking.sayFree(up).isDeclaredIn( world )
				&& Carparking.saySlowcar(slowcar).isDeclaredIn( world )
				&& Carparking.sayOn(slowcar, down).isDeclaredIn( world )
				&& Carparking.sayWaited(slowcar).isDeclaredIn( world )
				&& Carparking.sayMe(slowcar).isDeclaredIn(world);
	}
	
	@Override
	public boolean effects(PddlWorld world, String slowcar, String down, String up) {
		world.declare( Carparking.sayFree(down));
		world.undeclare( Carparking.sayFree(up) );
		world.declare( Carparking.sayOn(slowcar, up) );
		world.undeclare( Carparking.sayWaited(slowcar) );
		world.declare( Carparking.sayEmpty(slowcar) );
		world.undeclare( Carparking.sayOn(slowcar, down) );
		return true;
	}
}
