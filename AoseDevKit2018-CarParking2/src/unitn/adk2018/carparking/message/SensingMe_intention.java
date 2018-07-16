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


//EnvAgent non deve notificare delle altre auto
public final class SensingMe_intention extends Intention<Sensing_msg> {
	
	List<Message> messages = new ArrayList<Message>();
	
	@Override
	public Next step0(IntentionInput in) {
		for ( PddlClause c : agent.getBeliefs().getACopyOfDeclaredClauses().values() ) {
			if(c.getPredicate().equals("on") && !c.getArgs()[0].equals(in.event.getFrom()) ) {
				String cell="";
				cell = c.getArgs()[1];
				c = Carparking.sayFree(cell);
			}

			if(c.getPredicate().equals("me")) {
				c = Carparking.sayMe(in.event.getFrom());
			}
			if(c.getPredicate().equals("slowcar")&& !c.getArgs()[0].equals(in.event.getFrom())) {
				c = Carparking.sayMe(in.event.getFrom());
			}
			if(c.getPredicate().equals("car")&& !c.getArgs()[0].equals(in.event.getFrom())) {
				c = Carparking.sayMe(in.event.getFrom());
			}
			if(c.getPredicate().equals("empty")&& !c.getArgs()[0].equals(in.event.getFrom())) {
				c = Carparking.sayMe(in.event.getFrom());
			}
			
			Message m = new Clause_msg( agent.getName(), in.event.getFrom(), c );
			messages.add( m );
			Environment.sendMessage ( m );
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
