package unitn.adk2018.carparking.action;

import unitn.adk2018.carparking.Carparking;
import unitn.adk2018.pddl.PddlAction3Args;
import unitn.adk2018.pddl.PddlWorld;

public class Move_down extends PddlAction3Args{
	
	@Override
	public boolean checkPreconditions(PddlWorld world, String car, String down, String up) {
		return Carparking.sayUp(down, up).isDeclaredIn( world )
				&& Carparking.sayFree(down).isDeclaredIn( world )
				&& Carparking.sayCar(car).isDeclaredIn( world )
				&& Carparking.sayOn(car, up).isDeclaredIn( world )
				&& Carparking.sayMe(car).isDeclaredIn(world);
	}
	
	@Override
	public boolean effects(PddlWorld world, String car, String down, String up) {
		world.declare( Carparking.sayFree(up));
		world.undeclare( Carparking.sayFree(down) );
		world.declare( Carparking.sayOn(car, down) );
		world.undeclare( Carparking.sayOn(car, up) );
		return true;
	}
}
