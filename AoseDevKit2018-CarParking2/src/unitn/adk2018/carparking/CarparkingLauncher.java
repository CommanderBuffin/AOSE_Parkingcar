package unitn.adk2018.carparking;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Collection;
import java.util.Observable;
import java.util.Observer;

import unitn.adk2018.Agent;
import unitn.adk2018.Environment;
import unitn.adk2018.Logger;
import unitn.adk2018.carparking.action.Move_down;
import unitn.adk2018.carparking.action.Move_down_wait;
import unitn.adk2018.carparking.action.Move_left;
import unitn.adk2018.carparking.action.Move_left_wait;
import unitn.adk2018.carparking.action.Move_right;
import unitn.adk2018.carparking.action.Move_right_wait;
import unitn.adk2018.carparking.action.Move_up;
import unitn.adk2018.carparking.action.Move_up_wait;
import unitn.adk2018.carparking.action.Wait;
import unitn.adk2018.carparking.message.ClauseCarOn_intention;
import unitn.adk2018.carparking.message.SensingCars_intention;
import unitn.adk2018.carparking.message.SensingCars_msg;
import unitn.adk2018.carparking.message.SensingMe_intention;
import unitn.adk2018.event.Goal;
import unitn.adk2018.event.Message;
import unitn.adk2018.generic.agent.General_agent;
import unitn.adk2018.generic.goal.ExecutePddlPlan_goal;
import unitn.adk2018.generic.goal.ExecutePddlPlan_intention;
import unitn.adk2018.generic.goal.PddlStep_goal;
import unitn.adk2018.generic.goal.PddlStep_intention;
import unitn.adk2018.generic.goal.PostmanEverythingInParallel_intention;
import unitn.adk2018.generic.goal.PostmanOneRequestAtTime_intention;
import unitn.adk2018.generic.goal.Postman_goal;
import unitn.adk2018.generic.goal.ReachPddlGoal_goal;
import unitn.adk2018.carparking.goal.PddlStep2_intention;
import unitn.adk2018.carparking.goal.PddlStepMove_intention;
import unitn.adk2018.carparking.goal.ReachPddlGoal_intention;
import unitn.adk2018.generic.message.Clause_intention;
import unitn.adk2018.generic.message.Clause_msg;
import unitn.adk2018.generic.message.PddlAction_intention;
import unitn.adk2018.generic.message.PddlAction_msg;
import unitn.adk2018.generic.message.Request_intention;
import unitn.adk2018.generic.message.Request_msg;
import unitn.adk2018.generic.message.Sensing_msg;
import unitn.adk2018.pddl.PddlClause;
import unitn.adk2018.pddl.PddlDomain;
import unitn.adk2018.utils.Sleep;

public abstract class CarparkingLauncher {
	public static void main(String[] args) throws InterruptedException {
		
		PrintStream err;
		try {
			err = new PrintStream(new FileOutputStream("output.txt"));
			System.setErr(err);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Logger.A_MAX = 2;
		Logger.GANTT = true;
		
		/*
		 * Setup domain
		 */
		PddlDomain pddlDomain = new PddlDomain("park-domain");
		Environment.setPddlDomain(pddlDomain);
		// Actions of the PDDL domain
		pddlDomain.addSupportedAction ("move-left", Move_left.class);
		pddlDomain.addSupportedAction ("move-right", Move_right.class);
		pddlDomain.addSupportedAction ("move-down", Move_down.class);
		pddlDomain.addSupportedAction ("move-up", Move_up.class);
		pddlDomain.addSupportedAction ("move-left-wait", Move_left_wait.class);
		pddlDomain.addSupportedAction ("move-right-wait", Move_right_wait.class);
		pddlDomain.addSupportedAction ("move-down-wait", Move_down_wait.class);
		pddlDomain.addSupportedAction ("move-up-wait", Move_up_wait.class);	
		pddlDomain.addSupportedAction ("wait", Wait.class);	
		
		/*
		 * Setup problem
		 */
		String env = "env";
		String c0 = "c0";
		String c1 = "c1";
		String c2 = "c2";
		String c3 = "c3";
		/*String s0 = "s0";
		String s1 = "s1";
		String s2 = "s2";
		String s3 = "s3";*/
		String[][] grid = new String[12][7];
		int r = 12;
		int c = 7;
		
		for(int i = r-1; i >= 0; i--){
			String riga = "";
			for(int j = 0; j < c; j++) {
				grid[i][j] = "g"+i+""+j;
				riga+=grid[i][j];
			}
			System.out.println(riga);
		}
		
		//Sleep.sleep(5000);
		
		/*
		 * Setup environmentAgent
		 */
		Agent envAgent = new General_agent(env, true);
		// Goals
		envAgent.addSupportedEvent(Postman_goal.class, PostmanEverythingInParallel_intention.class);
		// Messages
		envAgent.addSupportedEvent(PddlAction_msg.class, PddlAction_intention.class);
		envAgent.addSupportedEvent(Sensing_msg.class, SensingMe_intention.class); //filtrare eliminando auto
		envAgent.addSupportedEvent(SensingCars_msg.class, SensingCars_intention.class); //filtra solo auto vicine
		// Beliefs
		envAgent.getBeliefs().declareObject( c0 );
		envAgent.getBeliefs().declareObject( c1 );
		envAgent.getBeliefs().declareObject( c2 );
		envAgent.getBeliefs().declareObject( c3 );
		for(int i = r-1; i >= 0; i--){
			for(int j = 0; j < c; j++) {
				envAgent.getBeliefs().declareObject( grid[i][j] );
			}
		}
		// Setup 1
		/*envAgent.getBeliefs().declare( Carparking.sayMe(c0) );
		envAgent.getBeliefs().declare( Carparking.sayMe(c1) );
		envAgent.getBeliefs().declare( Carparking.sayMe(c2) );
		envAgent.getBeliefs().declare( Carparking.sayMe(c3) );*/
		
		
//		envAgent.getBeliefs().declare( Carparking.sayCar(c0) );
//		envAgent.getBeliefs().declare( Carparking.sayOn(c0, grid[0][0]) );
//
//		envAgent.getBeliefs().declare( Carparking.saySlowcar(c1) );
//		envAgent.getBeliefs().declare( Carparking.sayOn(c1, grid[3][2]) );
//		envAgent.getBeliefs().declare( Carparking.sayEmpty(c1));
//		
//		envAgent.getBeliefs().declare( Carparking.saySlowcar(c2) );
//		envAgent.getBeliefs().declare( Carparking.sayOn(c2, grid[6][0]) );
//		envAgent.getBeliefs().declare( Carparking.sayEmpty(c2));
//
//		envAgent.getBeliefs().declare( Carparking.saySlowcar(c3) );
//		envAgent.getBeliefs().declare( Carparking.sayOn(c3, grid[2][4]) );
//		envAgent.getBeliefs().declare( Carparking.sayEmpty(c3));
		
		for(int i = 0; i < r; i++) {
			for(int j = 0; j < c-1; j++) {
				envAgent.getBeliefs().declare( Carparking.sayRight(grid[i][j], grid[i][j+1]) );
			}
		}
		
		for(int i = 0; i < r-1; i++) {
			for(int j = 0; j < c; j++) {
				envAgent.getBeliefs().declare( Carparking.sayUp(grid[i][j], grid[i+1][j]) );
			}
		}
		
		envAgent.getBeliefs().declare( Carparking.sayFree(grid[0][1] ));
		envAgent.getBeliefs().declare( Carparking.sayFree(grid[0][2] ));
		envAgent.getBeliefs().declare( Carparking.sayFree(grid[0][3] ));
		envAgent.getBeliefs().declare( Carparking.sayFree(grid[0][4] ));
		envAgent.getBeliefs().declare( Carparking.sayFree(grid[0][5] ));
		envAgent.getBeliefs().declare( Carparking.sayFree(grid[0][6] ));
		envAgent.getBeliefs().declare( Carparking.sayFree(grid[1][2] ));
		envAgent.getBeliefs().declare( Carparking.sayFree(grid[1][4] ));
		envAgent.getBeliefs().declare( Carparking.sayFree(grid[1][5] ));
		envAgent.getBeliefs().declare( Carparking.sayFree(grid[1][6] ));
		envAgent.getBeliefs().declare( Carparking.sayFree(grid[2][2] ));
		envAgent.getBeliefs().declare( Carparking.sayFree(grid[3][0] ));
		envAgent.getBeliefs().declare( Carparking.sayFree(grid[3][1] ));
		envAgent.getBeliefs().declare( Carparking.sayFree(grid[3][4] ));
		envAgent.getBeliefs().declare( Carparking.sayFree(grid[3][5] ));
		envAgent.getBeliefs().declare( Carparking.sayFree(grid[3][6] ));
		envAgent.getBeliefs().declare( Carparking.sayFree(grid[4][0] ));
		envAgent.getBeliefs().declare( Carparking.sayFree(grid[4][1] ));
		envAgent.getBeliefs().declare( Carparking.sayFree(grid[4][2] ));
		envAgent.getBeliefs().declare( Carparking.sayFree(grid[4][3] ));
		envAgent.getBeliefs().declare( Carparking.sayFree(grid[4][4] ));
		envAgent.getBeliefs().declare( Carparking.sayFree(grid[4][5] ));
		envAgent.getBeliefs().declare( Carparking.sayFree(grid[4][6] ));
		envAgent.getBeliefs().declare( Carparking.sayFree(grid[5][0] ));
		envAgent.getBeliefs().declare( Carparking.sayFree(grid[5][2] ));
		envAgent.getBeliefs().declare( Carparking.sayFree(grid[5][3] ));
		envAgent.getBeliefs().declare( Carparking.sayFree(grid[5][4] ));
		envAgent.getBeliefs().declare( Carparking.sayFree(grid[5][5] ));
		envAgent.getBeliefs().declare( Carparking.sayFree(grid[6][1] ));
		envAgent.getBeliefs().declare( Carparking.sayFree(grid[6][2] ));
		envAgent.getBeliefs().declare( Carparking.sayFree(grid[6][3] ));
		envAgent.getBeliefs().declare( Carparking.sayFree(grid[6][5] ));
		envAgent.getBeliefs().declare( Carparking.sayFree(grid[7][0] ));
		envAgent.getBeliefs().declare( Carparking.sayFree(grid[7][1] ));
		envAgent.getBeliefs().declare( Carparking.sayFree(grid[7][2] ));
		envAgent.getBeliefs().declare( Carparking.sayFree(grid[7][3] ));
		envAgent.getBeliefs().declare( Carparking.sayFree(grid[7][4] ));
		envAgent.getBeliefs().declare( Carparking.sayFree(grid[7][5] ));
		envAgent.getBeliefs().declare( Carparking.sayFree(grid[7][6] ));
		envAgent.getBeliefs().declare( Carparking.sayFree(grid[8][0] ));
		envAgent.getBeliefs().declare( Carparking.sayFree(grid[8][1] ));
		envAgent.getBeliefs().declare( Carparking.sayFree(grid[8][2] ));
		envAgent.getBeliefs().declare( Carparking.sayFree(grid[8][4] ));
		envAgent.getBeliefs().declare( Carparking.sayFree(grid[8][5] ));
		envAgent.getBeliefs().declare( Carparking.sayFree(grid[9][0] ));
		envAgent.getBeliefs().declare( Carparking.sayFree(grid[9][2] ));
		envAgent.getBeliefs().declare( Carparking.sayFree(grid[9][4] ));
		envAgent.getBeliefs().declare( Carparking.sayFree(grid[9][5] ));
		envAgent.getBeliefs().declare( Carparking.sayFree(grid[10][0] ));
		envAgent.getBeliefs().declare( Carparking.sayFree(grid[10][1] ));
		envAgent.getBeliefs().declare( Carparking.sayFree(grid[10][2] ));
		envAgent.getBeliefs().declare( Carparking.sayFree(grid[10][4] ));
		envAgent.getBeliefs().declare( Carparking.sayFree(grid[10][5] ));
		envAgent.getBeliefs().declare( Carparking.sayFree(grid[10][6] ));
		envAgent.getBeliefs().declare( Carparking.sayFree(grid[11][0] ));
		envAgent.getBeliefs().declare( Carparking.sayFree(grid[11][1] ));
		envAgent.getBeliefs().declare( Carparking.sayFree(grid[11][2] ));
		envAgent.getBeliefs().declare( Carparking.sayFree(grid[11][3] ));
		envAgent.getBeliefs().declare( Carparking.sayFree(grid[11][4] ));
		envAgent.getBeliefs().declare( Carparking.sayFree(grid[11][5] ));
		envAgent.getBeliefs().declare( Carparking.sayFree(grid[11][6] ));
		
		InitGUIObstacles(envAgent.getBeliefs().getACopyOfDeclaredClauses().values());
		
		// Env
		Environment.addAgent (envAgent);
		Environment.setEnvironmentAgent (envAgent);
//		envAgent.startInSeparateThread();
		
		//TODO
		/*
		 * Coordinator asktodo job see exercise3coordinator e gli altri doitbymyself senza reachpddl o executepddl
		 */
		
		/*
		 * Setup car 0 agent
		 */

		Agent c0Agent = createAgentCar(c0,false,grid[0][0],envAgent);
		Agent c1Agent = createAgentCar(c1,true,grid[3][2],envAgent);
		Agent c2Agent = createAgentCar(c2,true,grid[6][0],envAgent);
		Agent c3Agent = createAgentCar(c3,true,grid[2][4],envAgent);

		Environment.addAgent (c0Agent);
//		c0Agent.startInSeparateThread();
		Environment.addAgent (c1Agent);
//		c1Agent.startInSeparateThread();
		Environment.addAgent (c2Agent);
//		c2Agent.startInSeparateThread();
		Environment.addAgent (c3Agent);
//		c3Agent.startInSeparateThread();

		PddlClause[] c0goals = {Carparking.sayOn(c0, grid[10][6])};
		
		Message msg0 = addGoalToAgent(c0, c0goals);
		
		PddlClause[] c1goals = {Carparking.sayOn(c1, grid[11][0])};
		Message msg1 = addGoalToAgent(c1, c1goals);
		
		PddlClause[] c2goals = {Carparking.sayOn(c2, grid[11][2])};
		Message msg2 = addGoalToAgent(c2, c2goals);
		
		PddlClause[] c3goals = {Carparking.sayOn(c3, grid[11][5])};
		Message msg3 = addGoalToAgent(c3, c3goals);
		
//		Agent c0Agent = new General_agent(c0, false);
//		c0Agent.addSupportedEvent(Postman_goal.class, PostmanOneRequestAtTime_intention.class);
//		// Messages
//		c0Agent.addSupportedEvent(Clause_msg.class, ClauseCarOn_intention.class);
//		c0Agent.addSupportedEvent(Clause_msg.class, Clause_intention.class);
//		c0Agent.addSupportedEvent(Request_msg.class, Request_intention.class);
//		// Goals
//		c0Agent.addSupportedEvent(ReachPddlGoal_goal.class, ReachPddlGoal_intention.class);
//		c0Agent.addSupportedEvent(ExecutePddlPlan_goal.class, ExecutePddlPlan_intention.class);
//		c0Agent.addSupportedEvent(PddlStep_goal.class, PddlStepMove_intention.class);
//		c0Agent.addSupportedEvent(PddlStep_goal.class, PddlStep2_intention.class);
//		// Beliefs
//		c0Agent.getBeliefs().declareObject( c0 );
//		c0Agent.getBeliefs().declare( Carparking.sayCar(c0));
//		c0Agent.getBeliefs().declare( Carparking.sayOn(c0, grid[0][0]));
//		c0Agent.getBeliefs().declare( Carparking.sayMe(c0));
//		// Env
//		Environment.addAgent (c0Agent);
//		c0Agent.startInSeparateThread();
//		
//		/*
//		 * Setup car 1 agent
//		 */
//		Agent c1Agent = new General_agent(c1, false);
//		c1Agent.addSupportedEvent(Postman_goal.class, PostmanOneRequestAtTime_intention.class);
//		// Messages
//		c1Agent.addSupportedEvent(Clause_msg.class, ClauseCarOn_intention.class);
//		c1Agent.addSupportedEvent(Clause_msg.class, Clause_intention.class);
//		c1Agent.addSupportedEvent(Request_msg.class, Request_intention.class);
//		// Goals
//		c1Agent.addSupportedEvent(ReachPddlGoal_goal.class, ReachPddlGoal_intention.class);
//		c1Agent.addSupportedEvent(ExecutePddlPlan_goal.class, ExecutePddlPlan_intention.class);
//		c1Agent.addSupportedEvent(PddlStep_goal.class, PddlStepMove_intention.class);
//		c1Agent.addSupportedEvent(PddlStep_goal.class, PddlStep2_intention.class);
//		// Beliefs
//		c1Agent.getBeliefs().declareObject( c1 );
//		c1Agent.getBeliefs().declare( Carparking.saySlowcar(c1));
//		c1Agent.getBeliefs().declare( Carparking.sayEmpty(c1));
//		c1Agent.getBeliefs().declare( Carparking.sayOn(c1, grid[3][2]));
//		c1Agent.getBeliefs().declare( Carparking.sayMe(c1));
//		// Env
//		Environment.addAgent (c1Agent);
//		c1Agent.startInSeparateThread();
//		
//		/*
//		 * Setup car 2 agent
//		 */
//		Agent c2Agent = new General_agent(c2, false);
//		c2Agent.addSupportedEvent(Postman_goal.class, PostmanOneRequestAtTime_intention.class);
//		// Messages
//		c2Agent.addSupportedEvent(Clause_msg.class, ClauseCarOn_intention.class);
//		c2Agent.addSupportedEvent(Clause_msg.class, Clause_intention.class);
//		c2Agent.addSupportedEvent(Request_msg.class, Request_intention.class);
//		// Goals
//		c2Agent.addSupportedEvent(ReachPddlGoal_goal.class, ReachPddlGoal_intention.class);
//		c2Agent.addSupportedEvent(ExecutePddlPlan_goal.class, ExecutePddlPlan_intention.class);
//		c2Agent.addSupportedEvent(PddlStep_goal.class, PddlStepMove_intention.class);
//
//		c2Agent.addSupportedEvent(PddlStep_goal.class, PddlStep2_intention.class);
//		// Beliefs
//
//		c2Agent.getBeliefs().declareObject( c2 );
//		c2Agent.getBeliefs().declare( Carparking.saySlowcar(c2));
//		c2Agent.getBeliefs().declare( Carparking.sayEmpty(c2));
//		c2Agent.getBeliefs().declare( Carparking.sayOn(c2, grid[6][0]));
//		c2Agent.getBeliefs().declare( Carparking.sayMe(c2));
//		// Env
//		Environment.addAgent (c2Agent);
//		c2Agent.startInSeparateThread();
//		
//		/*
//		 * Setup car 3 agent
//		 */
//		Agent c3Agent = new General_agent(c3, false);
//		c3Agent.addSupportedEvent(Postman_goal.class, PostmanOneRequestAtTime_intention.class);
//		// Messages
//		c3Agent.addSupportedEvent(Clause_msg.class, ClauseCarOn_intention.class);
//		c3Agent.addSupportedEvent(Clause_msg.class, Clause_intention.class);
//		c3Agent.addSupportedEvent(Request_msg.class, Request_intention.class);
//		// Goals
//		c3Agent.addSupportedEvent(ReachPddlGoal_goal.class, ReachPddlGoal_intention.class);
//		c3Agent.addSupportedEvent(ExecutePddlPlan_goal.class, ExecutePddlPlan_intention.class);
//		c3Agent.addSupportedEvent(PddlStep_goal.class, PddlStepMove_intention.class);
//
//		c3Agent.addSupportedEvent(PddlStep_goal.class, PddlStep2_intention.class);
//		// Beliefs
//		c3Agent.getBeliefs().declareObject(c3);
//		c3Agent.getBeliefs().declare( Carparking.saySlowcar(c3));
//		c3Agent.getBeliefs().declare( Carparking.sayEmpty(c3));
//		c3Agent.getBeliefs().declare( Carparking.sayOn(c3, grid[2][4]));
//		c3Agent.getBeliefs().declare( Carparking.sayMe(c3));
//		// Env
//		Environment.addAgent (c3Agent);
//		c3Agent.startInSeparateThread();
		
		

		/*
		 * Wait 1000ms on simulation timer
		 */
//		System.err.println("First test wait, 1000 msecs, at " + envAgent.getAgentTime());
		Sleep.sleep(1000);
//		System.err.println("End of first test wait at " + envAgent.getAgentTime());
		
		
		
		/*
		 * Notify car0 of everything in the world with Clause INFORM Messages
		 */
//		for(PddlClause clause : envAgent.getBeliefs().getACopyOfDeclaredClauses().values()) {
//			Environment.sendMessage ( new Clause_msg( "God", c0, clause ) );
//			Environment.sendMessage ( new Clause_msg( "God", c1, clause ) );
//			Environment.sendMessage ( new Clause_msg( "God", c2, clause ) );
//			Environment.sendMessage ( new Clause_msg( "God", c3, clause ) );
//		}	

		envAgent.getBeliefs().declare(Carparking.sayMe(c0));
		envAgent.getBeliefs().declare(Carparking.sayMe(c1));
		envAgent.getBeliefs().declare(Carparking.sayMe(c2));
		envAgent.getBeliefs().declare(Carparking.sayMe(c3));
		
		envAgent.getBeliefs().declare( Carparking.sayCar(c0) );
		envAgent.getBeliefs().declare( Carparking.sayOn(c0, grid[0][0]) );

		envAgent.getBeliefs().declare( Carparking.saySlowcar(c1) );
		envAgent.getBeliefs().declare( Carparking.sayOn(c1, grid[3][2]) );
		envAgent.getBeliefs().declare( Carparking.sayEmpty(c1));
		
		envAgent.getBeliefs().declare( Carparking.saySlowcar(c2) );
		envAgent.getBeliefs().declare( Carparking.sayOn(c2, grid[6][0]) );
		envAgent.getBeliefs().declare( Carparking.sayEmpty(c2));

		envAgent.getBeliefs().declare( Carparking.saySlowcar(c3) );
		envAgent.getBeliefs().declare( Carparking.sayOn(c3, grid[2][4]) );
		envAgent.getBeliefs().declare( Carparking.sayEmpty(c3));
		
		/*
		 * Wait 2000ms on simulation timer
		 */
		Sleep.sleep(2000);
		
		//Environment.addAgent (c0Agent);
		c0Agent.startInSeparateThread();
		//Environment.addAgent (c1Agent);
		c1Agent.startInSeparateThread();
		//Environment.addAgent (c2Agent);
		c2Agent.startInSeparateThread();
		//Environment.addAgent (c3Agent);
		c3Agent.startInSeparateThread();

		envAgent.startInSeparateThread();
		
		/*
		 * Send message Goal_msg to car0 with goal ReachPddlGoal_goal with pddlGoal (on c0, slot)
		 */
//		
//		PddlClause[] pddlGoal0 = { Carparking.sayOn(c0, grid[10][6]) };
//		Goal g0 = new ReachPddlGoal_goal( pddlGoal0 );
//		Message msg0 = new Request_msg( "God", c0, g0 );
//		Environment.sendMessage ( msg0 );
//
//		PddlClause[] pddlGoal1 = { Carparking.sayOn(c1, grid[11][0]) };
//		Goal g1 = new ReachPddlGoal_goal( pddlGoal1 );
//		Message msg1 = new Request_msg( "God", c1, g1 );
//		Environment.sendMessage ( msg1 );
//
//		PddlClause[] pddlGoal2 = { Carparking.sayOn(c2, grid[11][2]) };
//		Goal g2 = new ReachPddlGoal_goal( pddlGoal2 );
//		Message msg2 = new Request_msg( "God", c2, g2 );
//		Environment.sendMessage ( msg2 );
//
//		PddlClause[] pddlGoal3 = { Carparking.sayOn(c3, grid[11][5]) };
//		Goal g3 = new ReachPddlGoal_goal( pddlGoal3 );
//		Message msg3 = new Request_msg( "God", c3, g3 );
//		Environment.sendMessage ( msg3 );
		
		
		
		/*
		 * When msgs are handled print full dump
		 */
		

		InitGUIGoals(10,6,Color.green);
		InitGUIGoals(11,0,Color.blue);
		InitGUIGoals(11,2,Color.red);
		InitGUIGoals(11,5,Color.yellow);

		msg0.wasHandled().addObserver( new Observer() {
			public void update(Observable o, Object arg) {
				System.err.println("########## FULL DUMP C0 ########");
				c0Agent.printFullState();
				envAgent.printFullState();
			}
		});
		
		msg1.wasHandled().addObserver( new Observer() {
			public void update(Observable o, Object arg) {
				System.err.println("########## FULL DUMP C1 ########");
				c1Agent.printFullState();
				envAgent.printFullState();
			}
		});

		msg2.wasHandled().addObserver( new Observer() {
			public void update(Observable o, Object arg) {
				System.err.println("########## FULL DUMP C2 ########");
				c2Agent.printFullState();
				envAgent.printFullState();
			}
		});
		
		msg3.wasHandled().addObserver( new Observer() {
			public void update(Observable o, Object arg) {
				System.err.println("########## FULL DUMP C3 ########");
				c3Agent.printFullState();
				envAgent.printFullState();
			}
		});
	}
	
	private static Agent createAgentCar(String name, boolean slowcar, String initPosition, Agent envAgent) {
		Agent cAgent = new General_agent(name, true);
		cAgent.addSupportedEvent(Postman_goal.class, PostmanOneRequestAtTime_intention.class);
		// Messages
		cAgent.addSupportedEvent(Clause_msg.class, ClauseCarOn_intention.class);
		cAgent.addSupportedEvent(Clause_msg.class, Clause_intention.class);
		cAgent.addSupportedEvent(Request_msg.class, Request_intention.class);
		// Goals
		cAgent.addSupportedEvent(ReachPddlGoal_goal.class, ReachPddlGoal_intention.class);
		cAgent.addSupportedEvent(ExecutePddlPlan_goal.class, ExecutePddlPlan_intention.class);
		cAgent.addSupportedEvent(PddlStep_goal.class, PddlStepMove_intention.class);
		cAgent.addSupportedEvent(PddlStep_goal.class, PddlStep2_intention.class);
		// Beliefs
		cAgent.getBeliefs().declareObject( name );
		if(slowcar) {
			cAgent.getBeliefs().declare( Carparking.saySlowcar(name));
			cAgent.getBeliefs().declare( Carparking.sayEmpty(name));
		}
		else {
			cAgent.getBeliefs().declare( Carparking.sayCar(name));
		}
		cAgent.getBeliefs().declare( Carparking.sayOn(name, initPosition));
		cAgent.getBeliefs().declare( Carparking.sayMe(name));
		// Env
//		Environment.addAgent (cAgent);
//		cAgent.startInSeparateThread();
		
		for(PddlClause clause : envAgent.getBeliefs().getACopyOfDeclaredClauses().values()) {
			Environment.sendMessage ( new Clause_msg( "God", name, clause ) );
			}
		
		return cAgent;
	}
	
	private static Message addGoalToAgent(String name, PddlClause[] goals) {
		PddlClause[] pddlGoal0 = goals;
		Goal g0 = new ReachPddlGoal_goal( pddlGoal0 );
		Message msg0 = new Request_msg( "God", name, g0 );
		Environment.sendMessage ( msg0 );
		return msg0;
	}
	
	private static void InitGUIGoals(int row, int col, Color color) {
		Environment.GUI.borderGoalCell(row, col, color);
		//Environment.GUI.fillObstacleCell(row, col, color);
	}
	
	private static void InitGUIObstacles(Collection<PddlClause> collection) {
		for(PddlClause c : collection) {
			if(c.getPredicate().equals("free")) {
				int row = 0;
				int col = 0;
				String cell = c.getArgs()[0];
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
				
				Environment.GUI.fillCell(row, col, color);
			}
			if(c.getPredicate().equals("on")) {
				int row = 0;
				int col = 0;
				String cell = c.getArgs()[0];
				String car = c.getArgs()[1];
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
				
				Environment.GUI.fillCell(row, col, color);
			}
		}
	}
}
