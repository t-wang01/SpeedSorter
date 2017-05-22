import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class SpeedSorterMenuBar extends JMenuBar implements ActionListener{
	private JMenu file, help;
	private JMenuItem file_reset, file_exit, help_howToPlay, help_about;
	private SpeedSorterControlPanel control;
	
	public SpeedSorterMenuBar(SpeedSorterControlPanel panel){
		super();
		control = panel;
		
		file = new JMenu("File");
		help = new JMenu("Help");
		
		file_reset = new JMenuItem("Reset");
		file_exit = new JMenuItem("Exit");
		help_howToPlay = new JMenuItem("How to play");
		help_about = new JMenuItem("About");
		
		file_reset.addActionListener(this);
		file_exit.addActionListener(this);
		help_howToPlay.addActionListener(this);
		help_about.addActionListener(this);
		
		file.add(file_reset);
		file.add(file_exit);
		help.add(help_howToPlay);
		help.add(help_about);
		
		this.add(file);
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
		} else if (text.equals("How to play")){
			JOptionPane.showMessageDialog(null, 
					"Sort the set by dragging the boxes or clicking two different boxes to swap them.",
					"How to Play", JOptionPane.PLAIN_MESSAGE);
		} else if (text.equals("About")){
			JOptionPane.showMessageDialog(null, 
					"SpeedSorter v1\n"+
					"By Thomas Wang\n"+
					"\n"+
					"Published under MIT Lisense",
					"About", JOptionPane.PLAIN_MESSAGE);
		}
	}
}