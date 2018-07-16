package unitn.adk2018.generic.message;

import java.awt.Color;
import java.awt.Label;

import unitn.adk2018.Environment;
import unitn.adk2018.Logger;
import unitn.adk2018.SimulationGui;
import unitn.adk2018.intention.Intention;
import unitn.adk2018.pddl.PddlAction;
import unitn.adk2018.pddl.PddlClause;

public class PddlAction_intention extends Intention<PddlAction_msg> {
	
	@Override
	public Next step0(IntentionInput in) {
//		if (agent.debugOn)
//			System.out.println(agent.getName() + " PddlAction_intention: processing action " + in.event.action);
		PddlAction action = Environment.getPddlDomain().generatePddlAction ( in.event.action );
		if ( action.checkPreconditions(agent.getBeliefs(), in.event.args) )
		{
//			if(in.event.getFrom().equals("c0")) {
//				Environment.GUI.c0Label.setText("C0: "+ in.event.action.toString());
//			}
//			if(in.event.getFrom().equals("c1")) {
//				Environment.GUI.c1Label.setText("C1: "+ in.event.action.toString());
//			}
//			if(in.event.getFrom().equals("c2")) {
//				Environment.GUI.c2Label.setText("C2: "+ in.event.action.toString());
//			}
//			if(in.event.getFrom().equals("c3")) {
//				Environment.GUI.c3Label.setText("C3: "+ in.event.action.toString());
//			}
			if(!in.event.getFrom().equals("env")&&!in.event.getFrom().equals("God")) {
				for(Label label : Environment.GUI.labelsCars) {
					if(label.getText().contains(in.event.getFrom().toUpperCase())) {
						label.setText(in.event.getFrom().toUpperCase()+": "+in.event.action.toString());
					}
				}
			}
			return waitFor(this::stepAfterTimer, 1000); //continue after 3 seconds
		}
		else {
			if(!in.event.getFrom().equals("env")&&!in.event.getFrom().equals("God")) {
				for(Label label : Environment.GUI.labelsCars) {
					if(label.getText().contains(in.event.getFrom().toUpperCase())) {
						label.setText(in.event.getFrom().toUpperCase()+": planning");
					}
				}
			}
			return waitFor(null, 1000); //fail
		}
	}
	
	public Next stepAfterTimer(IntentionInput in) {
		PddlAction action = Environment.getPddlDomain().generatePddlAction ( in.event.action );
		if ( action.checkPreconditionsAndApply ( agent.getBeliefs(), in.event.args ) ) {
			if(!in.event.getFrom().equals("env")&&!in.event.getFrom().equals("God")) {
				for(Label label : Environment.GUI.labelsCars) {
					if(label.getText().contains(in.event.getFrom().toUpperCase())) {
						label.setText(in.event.getFrom().toUpperCase()+": "+in.event.action.toString()+ " DONE");
					}
				}
			}
			
			if (agent.debugOn) {
				Logger.println( this, "Beliefs changed: " + agent.getBeliefs().pddlClauses() );
			}
			
			for(PddlClause c : in.agent.getBeliefs().getACopyOfDeclaredClauses().values()) {
				if(c.getPredicate().equals("on")) {
					int row = 0;
					int col = 0;
					String car = c.getArgs()[0];
					String cell = c.getArgs()[1];
					cell = cell.substring(1, cell.length());
					if(cell.length()==3) {
						row = Integer.parseInt(cell.substring(0,2));
						col = Integer.parseInt(cell.substring(2));
					}
					if(cell.length()==2) {
						row = Integer.parseInt(cell.substring(0,1));
						col = Integer.parseInt(cell.substring(1));
					}
					Color color = Color.white;
					if(car.equals("c0"))
						color = Color.green;
					if(car.equals("c1"))
						color = Color.blue;
					if(car.equals("c2"))
						color = Color.red;
					if(car.equals("c3"))
						color = Color.yellow;
					if(car.equals("c4"))
						color = Color.orange;
					if(car.equals("c5"))
						color = Color.pink;
					if(car.equals("c6"))
						color = Color.gray;
					
					Environment.GUI.fillCell(row, col, color);
				}
			}
				
			return null; //success
		}
		else {
			if(!in.event.getFrom().equals("env")&&!in.event.getFrom().equals("God")) {
				for(Label label : Environment.GUI.labelsCars) {
					if(label.getText().contains(in.event.getFrom().toUpperCase())) {
						label.setText(in.event.getFrom().toUpperCase()+": "+in.event.action.toString()+ " FAILED");
					}
				}
			}
			return waitFor(null, 0); //fail
		}
	}
	
	@Override
	public void pass(Intention<PddlAction_msg>.IntentionInput in) {
	}
	
	@Override
	public void fail(Intention<PddlAction_msg>.IntentionInput in) {
	}

	public String toString() {
		return super.toString() + "(" +  event.action + ")";
	}
}
