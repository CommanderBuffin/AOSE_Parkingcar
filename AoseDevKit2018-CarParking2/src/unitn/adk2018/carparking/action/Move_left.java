package unitn.adk2018.carparking.action;

import unitn.adk2018.carparking.Carparking;
import unitn.adk2018.pddl.PddlAction3Args;
import unitn.adk2018.pddl.PddlWorld;

public class Move_left extends PddlAction3Args{
	
	@Override
	public boolean checkPreconditions(PddlWorld world, String car, String left, String right) {
		return Carparking.sayRight(left, right).isDeclaredIn( world )
				&& Carparking.sayFree(left).isDeclaredIn( world )
				&& Carparking.sayCar(car).isDeclaredIn( world )
				&& Carparking.sayOn(car, right).isDeclaredIn( world )
				&& Carparking.sayMe(car).isDeclaredIn(world);
	}
	
	@Override
	public boolean effects(PddlWorld world, String car, String left, String right) {
		world.declare( Carparking.sayFree(right));
		world.undeclare( Carparking.sayFree(left) );
		world.declare( Carparking.sayOn(car, left) );
		world.undeclare( Carparking.sayOn(car, right) );
		return true;
	}
}
