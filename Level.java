import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class Level extends JComponent implements MouseListener{
	private TitlePanel title;
	private int levelNum, sortType, delay;
	private Font optionFont = new Font("Arial", Font.BOLD, 15), 
			     infoFont = new Font("Arial", Font.BOLD, 10);
	
	public Level(int num, int type, int wait, TitlePanel titlePanel){
		levelNum = num;
		sortType = type;
		delay = wait;
		title = titlePanel;
		
		setLocation(new Point(0,0));
		setSize(new Dimension(50,50));
		
		addMouseListener(this);
	}
	
	@Override
	public void paintComponent(Graphics g){
		int x = getX(), y = getY(), width = getWidth(), height = getHeight();
		
		g.setColor(Color.GRAY);
		g.fillRect(x, y, width, height);
		g.setColor(Color.BLACK);
		g.drawRect(x, y, width, height);
		
		g.setFont(optionFont);
		//Code modified from Gilbert Le Blanc on https://stackoverflow.com/questions/14284754/
		Graphics2D g2d = (Graphics2D) g;
        FontMetrics fm = g2d.getFontMetrics();
		Rectangle2D r = fm.getStringBounds("Level "+levelNum, g2d);
        x = width/2 - (int) r.getWidth() / 2;
        y = height/2 - (int) r.getHeight() / 2 + fm.getAscent() - 8;
        g.drawString("Level "+levelNum, x, y);
        
        String info = getSortType()+", "+delay+"ms";
        
        g.setFont(infoFont);
        fm = g2d.getFontMetrics();
		r = fm.getStringBounds(info, g2d);
        x = width/2 - (int) r.getWidth() / 2;
        y = height/2 - (int) r.getHeight() / 2 + fm.getAscent() + 10;
        g.drawString(info, x, y);
	}
	
	public int levelNum(){
		return levelNum;
	}
	
	public String getSortType(){
		String[] out = new String[]{"Selection sort","Insertion sort","Quick sort"};
		return out[sortType];
	}
	
	public int getDelay(){
		return delay;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent arg0) {}

	@Override
	public void mouseReleased(MouseEvent arg0) {}
}
