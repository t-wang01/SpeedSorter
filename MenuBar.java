import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class MenuBar extends JMenuBar implements ActionListener{
	private SpeedSorter main;
	private JMenu file, options, help;
	private JMenuItem file_reset, file_exit,
					  options_delay,
					  help_howToPlay, help_about;
	private ControlPanel control;
	
	public MenuBar(SpeedSorter main){
		super();
		this.main = main;
		control = main.getControlPanel();
		
		file = new JMenu("File");
		options = new JMenu("Options");
		help = new JMenu("Help");
		
		file_reset = new JMenuItem("Reset");
		file_exit = new JMenuItem("Exit");
		options_delay = new JMenuItem("Change delay");
		help_howToPlay = new JMenuItem("How to play");
		help_about = new JMenuItem("About");
		
		file_reset.addActionListener(this);
		file_exit.addActionListener(this);
		options_delay.addActionListener(this);
		help_howToPlay.addActionListener(this);
		help_about.addActionListener(this);
		
//		file.add(file_reset);
		file.add(file_exit);
		options.add(options_delay);
		help.add(help_howToPlay);
		help.add(help_about);
		
		this.add(file);
//		this.add(options);
		this.add(help);
	}
	
	public void exitGame(){
		file.remove(file_reset);
		this.remove(options);
	}
	
	public void enterGame(){
		file.remove(file_exit);
		file.add(file_reset);
		file.add(file_exit);
		
		this.remove(help);
		this.add(options);
		this.add(help);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JMenuItem src = (JMenuItem)e.getSource();
		String text = src.getText();
		
		if(text.equals("Reset")){
			control.stopStopwatch();
			control.reset();
		} else if(text.equals("Exit")){
			System.exit(1);
		} else if (text.equals("Change delay")){
			//Get raw input
			String rawin = JOptionPane.showInputDialog(null, "Input new computer sorter delay (ms)...", "Change computer delay", JOptionPane.PLAIN_MESSAGE);
			
			//Exit if blank or pressed "cancel"
			try{
				if(rawin.equals(""))
					return;
			} catch (Exception ex){
				return;
			}
			
			//Check if the input is a number
			String numin = "";
			for(int i = 0; i < rawin.length(); i++){
				char charin = rawin.charAt(i);
				
				//Digits and '.' are ok, ' ' and ',' are ignored
				if (Character.isDigit(charin) || charin=='.')
					numin += rawin.charAt(i);
				else if (charin!=' ' || charin!=','){
					JOptionPane.showMessageDialog(null, "Invalid input. Please use . as decimal seperator.", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
			
			//Try to set new delay
			int newDelay;
			try{
				newDelay = Integer.parseInt(numin);
				main.getComputerSorter().setTimer(newDelay);
				main.getGamePanel().setCustomGame();
			} catch(Exception ex) {
				JOptionPane.showMessageDialog(null, "Invalid input.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} else if (text.equals("How to play")){
			JOptionPane.showMessageDialog(null, 
					"Sort the set by dragging the boxes or clicking two different boxes to swap them.",
					"How to Play", JOptionPane.PLAIN_MESSAGE);
		} else if (text.equals("About")){
			JOptionPane.showMessageDialog(null, 
					"SpeedSorter v3\n"+
					"By Thomas Wang\n"+
					"\n"+
					"Source code available on https://github.com/t-wang01/SpeedSorter",
					"About", JOptionPane.PLAIN_MESSAGE);
		}
	}
}