import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class SpeedSorter extends JFrame{
	private TitlePanel menu;
	private GamePanel game;
	private ControlPanel control;
	private MenuBar menuBar;
	private ComputerSorter comp;
	private JPanel panel;
	
	public SpeedSorter(){
		super("SpeedSorter v2");
		setSize(500, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		
		menu = new TitlePanel(this);
		comp = new ComputerSorter(this);
		control = new ControlPanel();
		menuBar = new MenuBar(this);
		game = new GamePanel(this);
		
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
	
	public void menuToGame(int level, boolean isPossible){
		game.setLevel(level, isPossible);
		
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
	
	public void returnToTitle(){
		remove(panel);
		
		panel.remove(game);
		panel.remove(control);
		panel.add(menu, BorderLayout.CENTER);
		
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
		return menu;
	}
	
	public ComputerSorter getComputerSorter(){
		return comp;
	}
}