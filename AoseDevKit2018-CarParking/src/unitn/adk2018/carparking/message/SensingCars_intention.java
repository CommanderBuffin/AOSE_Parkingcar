package unitn.adk2018.carparking.message;

import java.util.ArrayList;
import java.util.List;

import unitn.adk2018.Environment;
import unitn.adk2018.carparking.Carparking;
import unitn.adk2018.event.Message;
import unitn.adk2018.generic.message.Clause_msg;
import unitn.adk2018.generic.message.Sensing_msg;
import unitn.adk2018.intention.Intention;
import unitn.adk2018.pddl.PddlClause;

public final class SensingCars_intention extends Intention<SensingCars_msg> {
	
	List<Message> messages = new ArrayList<Message>();
	
	@Override
	public Next step0(IntentionInput in) {
		String myPos = "";
		for ( PddlClause c : agent.getBeliefs().getACopyOfDeclaredClauses().values() ) {
			if(c.getPredicate().equals("on")) {
				if(c.getArgs()[0].equals(in.event.getFrom())) {
					myPos = c.getArgs()[1];
				}
			}
		}

		for ( PddlClause c : agent.getBeliefs().getACopyOfDeclaredClauses().values() ) {
			if(c.getPredicate().equals("on") && !c.getArgs()[0].equals(in.event.getFrom())) {
				String cell="";
				String car = c.getArgs()[0];
				cell = c.getArgs()[1];
				String c_rig="";
				String c_col="";
				if(cell.length()==4) {
					c_rig=cell.substring(1, 3);
					c_col=cell.substring(3);
				}
				else
				{
					c_rig=cell.substring(1,2);
					c_col=cell.substring(2);
				}
				String me_rig="";
				String me_col="";
				if(myPos.length()==4) {
					me_rig=myPos.substring(1, 3);
					me_col=myPos.substring(3);
				}
				else
				{
					me_rig=myPos.substring(1,2);
					me_col=myPos.substring(2);
				}
				
				if( Integer.parseInt(c_rig)+1==Integer.parseInt(me_rig) && Integer.parseInt(c_col)==Integer.parseInt(me_col) ||
					Integer.parseInt(c_rig)-1==Integer.parseInt(me_rig) && Integer.parseInt(c_col)==Integer.parseInt(me_col) ||
					Integer.parseInt(c_col)+1==Integer.parseInt(me_col) && Integer.parseInt(c_rig)==Integer.parseInt(me_rig) ||
					Integer.parseInt(c_col)-1==Integer.parseInt(me_col) && Integer.parseInt(c_rig)==Integer.parseInt(me_rig) )
				{
					//vicina a me
					//Do nothing
					System.err.println("NEAR ME "+agent.getName()+" " + myPos + " "+car+" "+cell);

					Message m = new Clause_msg( agent.getName(), in.event.getFrom(), c );
					messages.add( m );
					Environment.sendMessage ( m );
				}
				else {
					//distante
					//c = Carparking.sayFree(cell);
				}	
			}
			
		}
		
		return waitFor(this::step1, 0);
	}
	
	public Next step1(IntentionInput in) {
		for(Message m : messages) {
			if(m.wasNotHandled().isTrue())
				return waitUntil( this::step1, m.wasHandled() );
		}
		return null; //do success!
	}
	
}
