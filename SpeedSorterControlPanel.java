import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import org.apache.commons.lang3.time.StopWatch;

public class SpeedSorterControlPanel extends JPanel{
	
	private SpeedSorter game;
	private JLabel timeLabel, moveLabel;
	private JTextField timeField, moveField;
	private JButton actionButton;
	private StopWatch stopwatch;
	private Timer timer;
	
	public SpeedSorterControlPanel(SpeedSorter gaem){
		super(new GridBagLayout());
		game = gaem;
		
		setUpGUIFields();
		
		setUpStopwatch();
		
		setUpGUI();
	}
	
	private void setUpGUIFields(){
		timeLabel = new JLabel("Time: ");
		timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		timeField = new JTextField("00:00:00");
		timeField.setHorizontalAlignment(SwingConstants.CENTER);
		timeField.setEditable(false);
		
		moveLabel = new JLabel("Moves: ");
		moveLabel.setHorizontalAlignment(SwingConstants.CENTER);
		moveField = new JTextField("0   ");
		moveField.setHorizontalAlignment(SwingConstants.RIGHT);
		moveField.setEditable(false);
		
		actionButton = new JButton("Go!");
		actionButton.addActionListener(new buttonListener());
		actionButton.setPreferredSize(new Dimension(90, 30));
	}
	
	private void setUpGUI(){
		GridBagConstraints gbc = new GridBagConstraints();

		Box box = Box.createVerticalBox();
		box.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		JPanel display = new JPanel(new GridLayout(4, 1));
		
		display.add(timeLabel); display.add(timeField);
		display.add(moveLabel); display.add(moveField);
		
		gbc.weighty = 2;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		
		box.add(display);
		box.setPreferredSize(new Dimension(75, 150));
		
		this.add(box, gbc);
		
		gbc.weighty = 1.5;
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		
		this.add(actionButton, gbc);
		
		this.setPreferredSize(new Dimension(100, 400));
	}
	
	private void setUpStopwatch(){
		stopwatch = new StopWatch();
		timer = new Timer(1000, new timeListener());
	}
	
	public void stopStopwatch(){
		if(!stopwatch.isStopped()){
			timer.stop();
			stopwatch.stop();
			
			actionButton.setText("Restart");
		}
	}

	public void pauseStopwatch(){
		stopwatch.suspend();
		actionButton.setText("Resume");
	}
	
	public void setMoves(int moves){
		moveField.setText(moves+"   ");
	}
	
	private void reset(){
		game.reset();
		timeField.setText("00:00:00");
	}
	
	public boolean stopwatchIsRunning(){
		return stopwatch.isStarted() && !stopwatch.isSuspended();
	}
	
	private class buttonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton src = (JButton)e.getSource();
			String text = src.getText();
			if(text.equals("Go!")){
				timer.start();
				stopwatch.start();
				src.setText("Pause");
			} else if(text.equals("Pause")){
				stopwatch.suspend();
				src.setText("Resume");
			} else if (text.equals("Resume")){
				stopwatch.resume();
				src.setText("Pause");
			} else if (text.equals("Restart")){
				reset();
				stopwatch.reset();
				src.setText("Go!");
			}
			
			game.repaint();
		}
		
	}

	private class timeListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			int secs = (int) stopwatch.getTime()/1000;
			int mins = secs/60, hrs = mins/60;
			mins %= 60;	secs %= 60;
			
			if(hrs >= 24){
				secs = 59; mins = 59; hrs = 23;
			}
		    timeField.setText(String.format("%02d:%02d:%02d",hrs, mins, secs));
		}
		
	}
}
