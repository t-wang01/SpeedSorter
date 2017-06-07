import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class SpeedSorter extends JFrame{
	private TitlePanel title;
	private GamePanel game;
	private ControlPanel control;
	private MenuBar menuBar;
	private ComputerSorter comp;
	private JPanel panel;
	
	public SpeedSorter(){
		super("SpeedSorter v3");
		setSize(500, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		
		title = new TitlePanel(this);
		comp = new ComputerSorter(this);
		control = new ControlPanel();
		menuBar = new MenuBar(this);
		game = new GamePanel(this);
		
		control.addGamePanel(this);

		
		panel = new JPanel(new BorderLayout());
		panel.add(title, BorderLayout.CENTER);
		
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
	
	public void menuToGame(int level, boolean isPossible){
		game.setLevel(level, isPossible);
		
		remove(panel);
		
		panel.remove(title);
		panel.add(game, BorderLayout.CENTER);
		panel.add(control, BorderLayout.LINE_END);
		
		add(panel);
		setSize(1000,400);
		setLocationRelativeTo(null);

		revalidate();
		repaint();
	}
	
	public void returnToTitle(boolean hasWon, int levelNum){
		if(hasWon)
			title.addLevel(levelNum);
		remove(panel);
		
		panel.remove(game);
		panel.remove(control);
		panel.add(title, BorderLayout.CENTER);
		
		add(panel);
		setSize(500,400);
		setLocationRelativeTo(null);
		
		revalidate();
		repaint();
	}
	
	public ControlPanel getControlPanel(){
		return control;
	}
	
	public GamePanel getGamePanel(){
		return game;
	}
	
	public TitlePanel getMenuPanel(){
		return title;
	}
	
	public ComputerSorter getComputerSorter(){
		return comp;
	}
}