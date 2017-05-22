import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public class SpeedSorterMenuPanel extends JPanel implements MouseListener{
	private SpeedSorter main;
	
	public SpeedSorterMenuPanel(SpeedSorter speedSorter){
		super();
		
		main = speedSorter;
		
		this.addMouseListener(this);
		
		super.setBackground(Color.LIGHT_GRAY);
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		//Local variables
		int panelWidth = this.getWidth();
		int panelHeight = this.getHeight();
		
		g.setColor(Color.RED);
		g.drawString("Click to continue", panelWidth/2, panelHeight/2);
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		main.menuToGame();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
