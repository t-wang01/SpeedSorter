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
	private Dimension boxSize;
	private Font optionFont = new Font("Arial", Font.BOLD, 15), 
			     infoFont = new Font("Arial", Font.BOLD, 10);
	
	public Level(int num, int type, int wait, TitlePanel titlePanel){
		levelNum = num;
		sortType = type;
		delay = wait;
		title = titlePanel;
		
		boxSize = new Dimension(50,50);
		
		setLocation(new Point(0,0));
		setSize(new Dimension(50,50));
		
		addMouseListener(this);
		
		setBackground(Color.GRAY);
	}
	
	@Override
	public void paintComponent(Graphics g){
		int x = getWidth()/2 - boxSize.width/2, 
			y = getHeight()/2 - boxSize.height/2,
			width = getWidth(), height = getHeight();
				
		g.setColor(Color.GRAY);
		g.fillRect(x, y, boxSize.width, boxSize.height);
		g.setColor(Color.BLACK);
		g.drawRect(x, y, boxSize.width, boxSize.height);
		
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
	
	public void setBoxSize(Dimension size){
		boxSize = size;
	}
	
	public int getLevelNum(){
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
	public void mouseClicked(MouseEvent e) {
		int x = e.getX(), y = e.getY();
		
		if(Math.abs(x-getWidth()/2) < boxSize.width/2 &&
			Math.abs(y-getHeight()/2) < boxSize.height/2)
			title.selectLevel(levelNum);
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}
}
