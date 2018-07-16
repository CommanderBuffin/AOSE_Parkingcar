package unitn.adk2018.carparking.message;

import unitn.adk2018.Agent;
import unitn.adk2018.carparking.Carparking;
import unitn.adk2018.generic.message.Clause_msg;
import unitn.adk2018.intention.Intention;
import unitn.adk2018.pddl.PddlClause;
import unitn.adk2018.utils.Sleep;

public final class ClauseCarOn_intention extends Intention<Clause_msg> {

	@Override
	public boolean context(Agent a, Clause_msg g) {
		return g.clause.getPredicate().equals("on"); //check whether it is the case that clause predicate is "on"
	}

	@Override
	public Intention<Clause_msg>.Next step0(IntentionInput in) {
		String car = event.clause.getArgs()[0];
		String cell = event.clause.getArgs()[1];

		agent.getBeliefs().declare(event.clause); //dichiaro il nuovo belief
		agent.getBeliefs().undeclare(Carparking.sayFree(cell));
		
		return null;
	}
	
	@Override
	public void pass(IntentionInput in) {
	}
	
	@Override
	public void fail(IntentionInput in) {
	}
	
}
