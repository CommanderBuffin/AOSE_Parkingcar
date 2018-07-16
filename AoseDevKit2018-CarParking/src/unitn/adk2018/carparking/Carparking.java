package unitn.adk2018.carparking;

import unitn.adk2018.pddl.PddlClause;

public class Carparking {
	
	public static PddlClause sayOn(String element, String cell) {
		return PddlClause.say("on", element, cell);
	}

	public static PddlClause sayRight(String left, String right) {
		return PddlClause.say("right", left, right);
	}

	public static PddlClause sayUp(String down, String up) {
		return PddlClause.say("up", down, up);
	}

	public static PddlClause sayFree(String cell) {
		return PddlClause.say("free", cell);
	}

	public static PddlClause sayWaited(String slowcar) {
		return PddlClause.say("waited", slowcar);
	}

	public static PddlClause sayCar(String car) {
		return PddlClause.say("car", car);
	}

	public static PddlClause saySlowcar(String slowcar) {
		return PddlClause.say("slowcar", slowcar);
	}
	
	public static PddlClause sayEmpty(String slowcar) {
		return PddlClause.say("empty", slowcar);
	}
	
	public static PddlClause sayMe(String slowcar) {
		return PddlClause.say("me", slowcar);
	}
	
	public static PddlClause sayNotMe(String slowcar) {
		return PddlClause.say("not-me", slowcar);
	}
}
