package unitn.adk2018.carparking.goal;

import unitn.adk2018.Agent;
import unitn.adk2018.Environment;
import unitn.adk2018.carparking.Carparking;
import unitn.adk2018.carparking.message.SensingCars_msg;
import unitn.adk2018.event.RequestMessage;
import unitn.adk2018.generic.goal.PddlStep_goal;
import unitn.adk2018.generic.goal.ReachPddlGoal_goal;
import unitn.adk2018.generic.message.PddlAction_msg;
import unitn.adk2018.generic.message.Sensing_msg;
import unitn.adk2018.intention.Intention;
import unitn.adk2018.intention.Intention.Next;
import unitn.adk2018.pddl.PddlAction;
import unitn.adk2018.pddl.PddlClause;

public class PddlStepMove_intention extends Intention<PddlStep_goal> {
	
	@Override
	public boolean context(Agent a, PddlStep_goal g) {
		//controllo su move
		if(event.step.action.toLowerCase().contains("move")) {
			return true;
		}
		else
			return false;
	}
	
	RequestMessage msg = null;
	//reachpddlgoal step0
	
	@Override
	public Next step0(IntentionInput in) {
		/*
		 * Do sensing
		 * eliminare tutti i belief delle auto
		 */	
		for(PddlClause c : agent.getBeliefs().getACopyOfDeclaredClauses().values() ) {
			if(c.getPredicate().equals("on") && !c.getArgs()[0].equals(agent.getName())) {
				agent.getBeliefs().declare(Carparking.sayFree(c.getArgs()[1]));
				agent.getBeliefs().undeclare(c);
			}
		}
		
		SensingCars_msg sensing = new SensingCars_msg( agent.getName(), Environment.getEnvironmentAgent().getName() );
		Environment.sendMessage( sensing );
		return waitUntil( this::step1, sensing.wasHandled() );
	}
	//step controlla se sul percorso
	//return waitFor ( null, 0 ); per far fallire
	//step checkprecondition
	/*
	 * PddlAction action = Environment.getPddlDomain().generatePddlAction ( in.event.step.action );
		if ( action.checkPreconditions(agent.getBeliefs(), in.event.step.args) )
	 * */
	
	public Next step1(IntentionInput in) {
		
		PddlAction action = Environment.getPddlDomain().generatePddlAction ( in.event.step.action );
		
		if ( action.checkPreconditions(agent.getBeliefs(), in.event.step.args) )
		{	
			msg = new PddlAction_msg ( agent.getName(), Environment.getEnvironmentAgent().getName(),
					event.step.action, event.step.args );
			sendMessage( msg );
			return waitUntil( this::stepEnd, msg.wasHandled() );
		}
		else
			//return waitFor ( null, 0 );
			return waitFor ( null, 0 );//ripianifico
	}
	
	public Next stepEnd(IntentionInput in) {
		if ( msg.wasHandledWithSuccess().isTrue() ) {
			PddlAction action = Environment.getPddlDomain().generatePddlAction ( in.event.step.action );
			action.checkPreconditionsAndApply(agent.getBeliefs(), in.event.step.args);
			
			return null; //success
		}
		else {
			//System.err.println("STEP "+agent.getName()+" ACTION " + in.event.step.action.toString()+ " ARGS"+str+" FAILED");
			return waitFor(null, 0); //fail
		}
	}
	
}
