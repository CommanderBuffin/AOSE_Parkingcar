package unitn.adk2018.carparking.goal;

import unitn.adk2018.Agent;
import unitn.adk2018.generic.goal.PddlStep_goal;
import unitn.adk2018.generic.goal.PddlStep_intention;

public class PddlStepDoItByMyself_intention extends PddlStep_intention {
	
	@Override
	public boolean context(Agent a, PddlStep_goal g) {
		return ( g.step.action.equals("move-right") || g.step.action.equals("move-left") || g.step.action.equals("move-down") || g.step.action.equals("move-up") ||
				g.step.action.equals("move-right-wait") || g.step.action.equals("move-left-wait") || g.step.action.equals("move-down-wait") || g.step.action.equals("move-up-wait") 
				|| g.step.action.equals("wait")
				) //supported actions
				&& g.step.args[0].equals( a.getName() ); // I personally have to do the action
	}
	
}
