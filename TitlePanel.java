import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;
import javax.swing.UIManager;

public class TitlePanel extends JPanel implements MouseListener{
	private SpeedSorter main;
	private boolean literallyImpossible = false;
	private boolean[] levelsUnlocked = new boolean[]{true, true, false};
	private Title titleImage = new Title();
	private JPanel levelsPanel = new JPanel();
	private Level[] levels = new Level[3];
//	private JPanel possibilityPanel;
	private JPanel footerPanel = new Footer();
	private int[][] levelData = new int[][]{{0,300},{1,300},{2,300}};
	
	public TitlePanel(SpeedSorter speedSorter){
		super();
		
		main = speedSorter;
		
		addMouseListener(this);
		
		setBackground(Color.GRAY);
		
		GridLayout levelsLayout = new GridLayout(1,0);
		for(int i = 0; i < 3; i++)
			levels[i] = new Level(i+1, levelData[i][0], levelData[i][1], this);
		levelsPanel.setLayout(levelsLayout);
		levelsPanel.add(levels[0]);
		
		GridLayout layout = new GridLayout(3,1);
		setLayout(layout);
		add(titleImage);
		add(levelsPanel);
		add(footerPanel);
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
//		
//		paintTitle(g);
//		
//		paintOptions(g);
//		
//		paintImpossibleBox(g);
//		
//		paintFooter(g);
	}
	
	private void paintOptions(Graphics g) {
        Font optionFont = new Font("Arial", Font.BOLD, 15);
        Font infoFont = new Font("Arial", Font.BOLD, 10);
        
		int initx = getWidth()/6;
		int inity = getHeight()*2/5;
		int gap = getWidth()/24;
		int width = getWidth()/3;
		int height = getHeight()/4;
		String[] options = {"Level 1","Level 2"};
		String[] infos = {"Selection sort 300ms","Insertion sort 300ms"};
		
		for(int i = 0; i < 2; i++){
			if(!levelsUnlocked[i])
				break;
			
			g.setColor(Color.BLACK);
			g.drawRect(initx+gap, inity, width-(2*gap), height);
			
			g.setFont(optionFont);
			//Code modified from Gilbert Le Blanc on https://stackoverflow.com/questions/14284754/
			Graphics2D g2d = (Graphics2D) g;
	        FontMetrics fm = g2d.getFontMetrics();
			Rectangle2D r = fm.getStringBounds(options[i], g2d);
	        int x = initx + width/2 - (int) r.getWidth() / 2;
	        int y = inity + height/2 - (int) r.getHeight() / 2 + fm.getAscent() - 8;
	        g.drawString(options[i], x, y);
	        
	        g.setFont(infoFont);
	        fm = g2d.getFontMetrics();
			r = fm.getStringBounds(infos[i], g2d);
	        x = initx + width/2 - (int) r.getWidth() / 2;
	        y = inity + height/2 - (int) r.getHeight() / 2 + fm.getAscent() + 10;
	        g.drawString(infos[i], x, y);
	        
	        initx += width;
		}
	}
	
	private void paintImpossibleBox(Graphics g) {
		int initx = getWidth()/4, inity = getHeight()*7/10;
		int width = getWidth()/2, height = getHeight()/10;  
		Font font = new Font("Arial", Font.PLAIN, 14);
		g.setFont(font);
		
		g.drawRect(initx, inity, width, height);

		String text = "Literally Impossible mode: ";
		if(literallyImpossible){
			text += "ON";
		} else {
			text += "OFF";
		}
		
		//Code modified from Gilbert Le Blanc on https://stackoverflow.com/questions/14284754/
		Graphics2D g2d = (Graphics2D) g;
        FontMetrics fm = g2d.getFontMetrics();
		Rectangle2D r = fm.getStringBounds(text, g2d);
        int x = initx + width/2 - (int) r.getWidth() / 2;
        int y = inity + height/2 - (int) r.getHeight() / 2 + fm.getAscent();
        g.drawString(text, x, y);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX(), y = e.getY();
		
//		//Selected a level
//		if(Math.abs(y-getHeight()*21/40)<=getHeight()/8){
//			if(Math.abs(x-getWidth()/3)<=getWidth()*7/24){
//				main.menuToGame(1, !literallyImpossible);
//			} else if (Math.abs(x-getWidth()*2/3)<=getWidth()*7/24){
//				main.menuToGame(2, !literallyImpossible);
//			}
//		}
		//Toggled possibility
		if(Math.abs(y-getHeight()*7.5/10)<=getHeight()/10 && Math.abs(x-getWidth()/2)<=getWidth()/4){
			literallyImpossible = !literallyImpossible;
			repaint();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}
	
	private class Title extends JPanel{
		public Title(){
//			setLocation(new Point(0,0));
//			setSize(new Dimension(50,50));
		}
		
		@Override
		public void paintComponent(Graphics g){
			Font font = new Font("Arial", Font.BOLD, 30);
			
			g.setFont(font);
			String title = "SpeedSorter";
			
			//Code modified from Gilbert Le Blanc on https://stackoverflow.com/questions/14284754/
			Graphics2D g2d = (Graphics2D) g;
	        FontMetrics fm = g2d.getFontMetrics();
			Rectangle2D r = fm.getStringBounds(title, g2d);
	        int x = (this.getWidth() - (int) r.getWidth()) / 2;
	        int y = this.getHeight() / 4 - (int) r.getHeight() / 2 + fm.getAscent();
	        g.drawString(title, x, y);
		}
	}
	
	private class Footer extends JPanel{
		public Footer(){
			
		}
		
		@Override
		public void paintComponent(Graphics g){
			Font font = new Font("Arial", Font.PLAIN, 11);
			
			g.setFont(font);
			String title = "Made by Thomas Wang pls give me an A";
			
			//Code modified from Gilbert Le Blanc on https://stackoverflow.com/questions/14284754/
			Graphics2D g2d = (Graphics2D) g;
	        FontMetrics fm = g2d.getFontMetrics();
			Rectangle2D r = fm.getStringBounds(title, g2d);
	        int x = (this.getWidth() - (int) r.getWidth()) / 2;
	        int y = this.getHeight() - (int) r.getHeight() / 2;
	        g.drawString(title, x, y);
		}
	}
}
