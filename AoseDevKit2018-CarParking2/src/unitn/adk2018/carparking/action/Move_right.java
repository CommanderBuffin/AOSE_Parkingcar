package unitn.adk2018.carparking.action;

import unitn.adk2018.carparking.Carparking;
import unitn.adk2018.pddl.PddlAction3Args;
import unitn.adk2018.pddl.PddlWorld;

public class Move_right extends PddlAction3Args{
	
	@Override
	public boolean checkPreconditions(PddlWorld world, String car, String left, String right) {
		return Carparking.sayRight(left, right).isDeclaredIn( world )
				&& Carparking.sayFree(right).isDeclaredIn( world )
				&& Carparking.sayCar(car).isDeclaredIn( world )
				&& Carparking.sayOn(car, left).isDeclaredIn( world )
				&& Carparking.sayMe(car).isDeclaredIn(world);
	}
	
	@Override
	public boolean effects(PddlWorld world, String car, String left, String right) {
		world.declare( Carparking.sayFree(left));
		world.undeclare( Carparking.sayFree(right) );
		world.declare( Carparking.sayOn(car, right) );
		world.undeclare( Carparking.sayOn(car, left) );
		return true;
	}
}
