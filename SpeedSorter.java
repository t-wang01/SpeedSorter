import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class SpeedSorter extends JFrame{
	private SpeedSorterMenuPanel menu;
	private SpeedSorterGamePanel game;
	private SpeedSorterControlPanel control;
	private SpeedSorterMenuBar menuBar;
	private SpeedSorterComputerSorter comp;
	private JPanel panel;
	
	public SpeedSorter(){
		super("SpeedSorter v2");
		setSize(500, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		
		menu = new SpeedSorterMenuPanel(this);
		comp = new SpeedSorterComputerSorter(this);
		control = new SpeedSorterControlPanel();
		menuBar = new SpeedSorterMenuBar(this);
		game = new SpeedSorterGamePanel(this);
		
		control.addGamePanel(this);

		
		panel = new JPanel(new BorderLayout());
		panel.add(menu, BorderLayout.CENTER);
		
		setJMenuBar(menuBar);
		add(panel);
	}
	
	public static void main(String[] args){
		// Set system look and feel:
	    String plafName = UIManager.getSystemLookAndFeelClassName();
	    try{UIManager.setLookAndFeel(plafName);}
	    catch(Exception ex){};
	    
		SpeedSorter game = new SpeedSorter();
		game.setVisible(true);
	}
	
	public void menuToGame(){
		remove(panel);
		
		panel.remove(menu);
		panel.add(game, BorderLayout.CENTER);
		panel.add(control, BorderLayout.LINE_END);
		
		add(panel);
		setSize(1000,400);
		setLocationRelativeTo(null);

		revalidate();
		repaint();
	}
	
	public SpeedSorterControlPanel getControlPanel(){
		return control;
	}
	
	public SpeedSorterGamePanel getGamePanel(){
		return game;
	}
	
	public SpeedSorterMenuPanel getMenuPanel(){
		return menu;
	}
	
	public SpeedSorterComputerSorter getComputerSorter(){
		return comp;
	}
}