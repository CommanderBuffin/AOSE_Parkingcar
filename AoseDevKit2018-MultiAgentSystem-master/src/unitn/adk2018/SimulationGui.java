package unitn.adk2018;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class SimulationGui {

   private Frame mainFrame;
   
   public JPanel[][] cells;
   public int rows= 12;
   public int cols = 7;
   
   public ArrayList<Label> labelsCars = new ArrayList<Label>();
   public Panel infoPanel;

//   public Label c0Label;
//   public Label c1Label;
//   public Label c2Label;
//   public Label c3Label;
   
   
   public SimulationGui(){
	   mainFrame = new Frame("Simulation GUI");
	   mainFrame.setSize(500,1000);
	   //mainFrame.setLayout(new BoxLayout(mainFrame, BoxLayout.Y_AXIS));
	   mainFrame.setLayout(new GridLayout(2,1));
	   mainFrame.addWindowListener(new WindowAdapter() {
		   public void windowClosing(WindowEvent windowEvent){
//			   Environment.getEnvironment().pauseSimulationTime();
			   System.exit(0);
		   }
	   });
	   
	   Panel panelButtons = new Panel();
	   panelButtons.setSize(500,200);
	   panelButtons.setLayout(new BoxLayout(panelButtons, BoxLayout.Y_AXIS));
	   
	   infoPanel = new Panel();
	   
	   
	   Panel p = createTimerPanel();
	   panelButtons.add(p);
	   panelButtons.add(infoPanel);
	   
	   JPanel grid = createGridPanel(rows,cols);

	   mainFrame.add(panelButtons,0,0);
	   //mainFrame.add(infoPanel);
	   mainFrame.add(grid,1,0);
	   
	   mainFrame.setVisible(true);
   }
   
   public Panel InitPanelInfo(ArrayList<Agent> cars) {
	   //infoPanel = new Panel();
	   infoPanel.setSize(500,200);
	   infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
	   
	   for(Agent car : cars) {
		   Label carLabel = new Label();
		   carLabel.setText(car.getName().toUpperCase()+": ");
		   infoPanel.add(carLabel);
		   labelsCars.add(carLabel); 
	   }
	   
	   return infoPanel;
   }
   
   public static String GetFormattedInterval(final long ms) {
	    long millis = ms % 1000;
	    long x = ms / 1000;
	    long seconds = x % 60;
	    x /= 60;
	    long minutes = x % 60;
	    x /= 60;
	    long hours = x % 24;
	
	    return String.format("%02d:%02d:%02d.%03d", hours, minutes, seconds, millis);
	}
   
   public void fillObstacleCell(int row, int col, Color color) {
	   cells[row][col].setBackground(color);
   }
   
   public void fillCell(int row, int col, Color color) {
	   for(int r = 0; r < rows; r++) {
		   for(int c = 0; c < cols; c++) {
			   if(cells[r][c].getBackground().equals(color)) {
				   cells[r][c].setBackground(Color.white);
			   }
		   }
	   }
	   
	   cells[row][col].setBackground(color);
   }
   
   public void borderGoalCell(int row, int col, Color color) {
	   Border borderline = BorderFactory.createStrokeBorder(new BasicStroke(5.0f), color);
	   cells[row][col].setBorder(borderline);
   }
   
   public JPanel createGridPanel(int rows, int cols) {
	   
	   Border blackline = BorderFactory.createLineBorder(Color.black);
	   
	   JPanel panel = new JPanel();
	   
	   cells = new JPanel[rows][cols];
	   
	   GridLayout gridLayout = new GridLayout(rows,cols);
	   panel.setLayout(gridLayout);
	   
	   for(int r=0; r < rows; r++) {
		   for(int c=0; c < cols; c++) {
			   cells[r][c] = new JPanel();
			   cells[r][c].setSize(200, 200);
			   cells[r][c].setBackground(Color.black);
			   cells[r][c].setBorder(blackline);
			   panel.add(cells[r][c],r,c);
		   }
	   }
	   
	   return panel;
   }
   
    public Panel createTimerPanel() {
	   Panel controlPanel = new Panel();
	   controlPanel.setLayout(new FlowLayout());
	   
	   //DurationFormatUtils.formatDuration(value, "HH:mm:ss.S")
	   
//	   Label timer = new Label();
//	   timer.setText("Timer: "+GetFormattedInterval(warehouse.getSimulationTime().get()));
//	   Environment.getEnvironment().getSimulationTime().getSimulationTime().registerListener(new IListener<Long>() {
//    	  public void notifyChanged(Long value) {
//    		  
//    		  timer.setText("Timer: "+GetFormattedInterval(value));
//    	  }
//	   });
	   
//	   Label state = new Label();
//	   state.setText(warehouse.getSimulationState().get().toString());
//	   warehouse.getSimulationState().registerListener(new IListener<SimulationState>() {
//		   public void notifyChanged(SimulationState value) {
//			   state.setText(value.toString());
//		   }
//	   });

	   Button play = new Button("play");
	   play.addActionListener(new ActionListener() {
		   public void actionPerformed(ActionEvent e) {
			   Environment.resumeSimulationTime();
		   }
	   });
	   
	   Button pause = new Button("pause");
	   pause.addActionListener(new ActionListener() {
		   public void actionPerformed(ActionEvent e) {
			   Environment.pauseSimulationTime();
		   }
	   });
	   
	   Button printFullState = new Button("printFullState");
	   printFullState.addActionListener(new ActionListener() {
		   public void actionPerformed(ActionEvent e) {
			   System.err.println("########## FULL DUMP ##########");
			   for (Agent a : Environment.getAgents().values()) {
				   a.printFullState();
			   }
		   }
	   });
	   
	   
	   
//	   controlPanel.add(timer);
//	   controlPanel.add(state);
	   controlPanel.add(play);
	   controlPanel.add(pause);
	   controlPanel.add(printFullState);
	   
	   mainFrame.setVisible(true);
      
	   return controlPanel;
	}
}