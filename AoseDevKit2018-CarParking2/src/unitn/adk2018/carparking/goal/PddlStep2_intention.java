package unitn.adk2018.carparking.goal;

import unitn.adk2018.Agent;
import unitn.adk2018.Environment;
import unitn.adk2018.event.RequestMessage;
import unitn.adk2018.generic.goal.PddlStep_goal;
import unitn.adk2018.generic.message.PddlAction_msg;
import unitn.adk2018.intention.Intention;
import unitn.adk2018.pddl.PddlAction;

public class PddlStep2_intention extends Intention<PddlStep_goal> {
	
	@Override
	public boolean context(Agent a, PddlStep_goal g) {
		return true;
	}
	
	RequestMessage msg = null;
	
	@Override
	public Next step0(IntentionInput in) {
		/*PddlAction action = Environment.getPddlDomain().generatePddlAction ( in.event.step.action );
		boolean  b = action.checkPreconditions(agent.getBeliefs(), in.event.step.args);*/
	
		msg = new PddlAction_msg ( agent.getName(), Environment.getEnvironmentAgent().getName(),
				event.step.action, event.step.args );
		sendMessage( msg );
		return waitUntil( this::stepEnd, msg.wasHandled() ); //wait until action is done on environment then continue
	}
	
	public Next stepEnd(IntentionInput in) {
		//System.err.println(agent.getName()+" "+event.step.action + " DONE "+  msg.wasHandledWithSuccess().isTrue());
		if ( msg.wasHandledWithSuccess().isTrue() ) {
			PddlAction action = Environment.getPddlDomain().generatePddlAction ( in.event.step.action );
			action.checkPreconditionsAndApply(agent.getBeliefs(), in.event.step.args);
			return null; //success
		}
		else
			return waitFor(null, 0); //fail
	}
	
}
