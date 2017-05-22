import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class SpeedSorter{
	private SpeedSorterMenuPanel menu;
	private SpeedSorterGamePanel game;
	private SpeedSorterControlPanel control;
	private SpeedSorterMenuBar menuBar;
	private JFrame frame;
	private JPanel panel;
	
	public SpeedSorter(){
		menu = new SpeedSorterMenuPanel(this);
		game = new SpeedSorterGamePanel(this);
		control = new SpeedSorterControlPanel(game);
		menuBar = new SpeedSorterMenuBar(control);
	}
	
	public static void main(String[] args){
		// Set system look and feel:
	    String plafName = UIManager.getSystemLookAndFeelClassName();
	    try{UIManager.setLookAndFeel(plafName);}
	    catch(Exception ex){};
	    
		SpeedSorter game = new SpeedSorter();
	    
		game.frame = new JFrame("SpeedSorter v1");
		game.frame.setSize(500, 400);
		game.frame.setLocationRelativeTo(null);
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		
		game.panel = new JPanel(new BorderLayout());
		
		game.panel.add(game.menu, BorderLayout.CENTER);
		
		game.frame.setJMenuBar(game.menuBar);
		game.frame.add(game.panel);
		game.frame.setVisible(true);
	}
	
	public void menuToGame(){
		frame.remove(panel);
		
		panel.remove(menu);
		panel.add(game, BorderLayout.CENTER);
		panel.add(control, BorderLayout.LINE_END);
		
		frame.add(panel);
		
		frame.revalidate();
		frame.repaint();
	}
	
	public boolean stopwatchIsRunning(){
		return control.stopwatchIsRunning();
	}
	
	public void stopStopwatch(){
		control.stopStopwatch();
	}
	
	public void setMoves(int moves){
		control.setMoves(moves);
	}
}