package unitn.adk2018.carparking.goal;

import unitn.adk2018.Environment;
import unitn.adk2018.generic.goal.ExecutePddlPlan_goal;
import unitn.adk2018.generic.goal.ReachPddlGoal_goal;
import unitn.adk2018.generic.message.Sensing_msg;
import unitn.adk2018.intention.Intention;
import unitn.adk2018.pddl.PddlClause;
import unitn.adk2018.pddl.PddlPlan;
import unitn.adk2018.utils.BlackboxUtils;
import unitn.adk2018.carparking.Carparking;
import unitn.adk2018.carparking.message.SensingCars_msg;

public class ReachPddlGoal_intention extends Intention<ReachPddlGoal_goal> {

	int tried = 0;
	int maxTry = 10;
	
	@Override
	public Next step0(IntentionInput in) {
		/*
		 * Do sensing
		 */
		Sensing_msg sensing = new Sensing_msg( agent.getName(), Environment.getEnvironmentAgent().getName() );
		Environment.sendMessage( sensing );

		return waitUntil( this::step01, sensing.wasHandled() );
	}
	
	public Next step01(IntentionInput in) {
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
	
	ExecutePddlPlan_goal g;
	
	public Next step1(Intention<ReachPddlGoal_goal>.IntentionInput in) {
		
		String pddlDomainFile = Environment.getPddlDomain().domainFile;
		PddlClause[] goal = in.event.pddlGoal;

		String pddlGoal = "( and "; 
		for(PddlClause c : goal) {
			pddlGoal += " (" + c + ")";
		}
		pddlGoal += " )";
		
		/*
		 * Generate Pddl plan
		 */
		PddlPlan plan = BlackboxUtils.doPlan(agent, pddlDomainFile, agent.getBeliefs(), pddlGoal);
	
		if (agent.debugOn)
			System.out.println( agent.getName() + " planned? " + (plan!=null) );
		if (plan==null) {
			tried++;
			if(tried<maxTry)
				return waitFor(this::step01,0);
			else
				return waitFor ( null, 0 ); //TODO loop per tipo 10 volte tentativi altrimenti si ferma appena non trova una soluizione aka richiama questo metodo per massimo 10 volte se fallisce il planning
			//System.exit(1);
		}
		tried=0;
		
		/*
		 * Execute generated plan
		 */
		g = new ExecutePddlPlan_goal ( plan );
		agent.pushGoal ( g, in.event.wasNotHandled() );  
		        /// note that the PDDL plan will fail if its goal is retracted (possibly forcely because of a meta-decision)
//		GoalMsg_msg msg = new GoalMsg_msg( agent.getName(), Environment.getEnvironmentAgentName(), g );
//		Environment.getEnvironment().sendMessage( msg );
		return waitUntil ( this::step2, g.wasHandled() );
	}
	
	public Next step2(Intention<ReachPddlGoal_goal>.IntentionInput in) {
		if (g.wasHandledWithSuccess().isTrue()){
			return null; // success
		}
		else
			return waitFor(this::step01, 1000); // fail cosi ricalcola con i nuovi beliefe saltando sensing il secondo parametro è il tempo
	}

	@Override
	public void pass(IntentionInput in) {
	}

	@Override
	public void fail(IntentionInput in) {
	}
}
