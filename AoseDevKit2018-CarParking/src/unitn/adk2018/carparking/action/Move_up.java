package unitn.adk2018.carparking.action;

import unitn.adk2018.carparking.Carparking;
import unitn.adk2018.pddl.PddlAction3Args;
import unitn.adk2018.pddl.PddlWorld;

public class Move_up extends PddlAction3Args{
	
	@Override
	public boolean checkPreconditions(PddlWorld world, String car, String down, String up) {
		return Carparking.sayUp(down, up).isDeclaredIn( world )
				&& Carparking.sayFree(up).isDeclaredIn( world ) //invertito
				&& Carparking.sayCar(car).isDeclaredIn( world )
				&& Carparking.sayOn(car, down).isDeclaredIn( world )
				&& Carparking.sayMe(car).isDeclaredIn(world);
	}
	
	@Override
	public boolean effects(PddlWorld world, String car, String down, String up) {
		world.declare( Carparking.sayFree(down));
		world.undeclare( Carparking.sayFree(up) );
		world.declare( Carparking.sayOn(car, up) );
		world.undeclare( Carparking.sayOn(car, down) );
		return true;
	}
}
