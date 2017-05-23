import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;

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
		
		String display = "Click anywhere to start...";
		
		//Code modified from Gilbert Le Blanc on https://stackoverflow.com/questions/14284754/
        Graphics2D g2d = (Graphics2D) g;
        FontMetrics fm = g2d.getFontMetrics();
        Rectangle2D r = fm.getStringBounds(display, g2d);
        int x = (this.getWidth() - (int) r.getWidth()) / 2;
        int y = (this.getHeight() - (int) r.getHeight()) / 2 + fm.getAscent();
        g.drawString(display, x, y);
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
