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

public class TitlePanel extends JPanel{
	private SpeedSorter main;
	private boolean literallyImpossible = false;
	private boolean[] levelsUnlocked = new boolean[]{true, false, false};
	private Title titleImage = new Title();
	private JPanel levelsPanel = new JPanel();
	private Level[] levels = new Level[3];
//	private JPanel possibilityPanel;
	private JPanel footerPanel = new Footer();
	private int[][] levelData = new int[][]{{0,300},{1,300},{2,300}};
	
	public TitlePanel(SpeedSorter speedSorter){
		super();
		
		main = speedSorter;
				
		setBackground(Color.GRAY);
		
		GridLayout levelsLayout = new GridLayout(1,0);
		for(int i = 0; i < 3; i++){
			levels[i] = new Level(i+1, levelData[i][0], levelData[i][1], this);
			levels[i].setBoxSize(new Dimension(getWidth()/4, getHeight()/4));
		}
		levelsPanel.setLayout(levelsLayout);
		levelsPanel.add(levels[0]);
		
		GridLayout layout = new GridLayout(3,1);
		setLayout(layout);
		add(titleImage);
		add(levelsPanel);
		add(footerPanel);
		
		levelsPanel.setBackground(this.getBackground());
		footerPanel.setBackground(this.getBackground());
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		int boxWidth = getWidth()/4,
			boxHeight = getHeight()/4;
		
		for(Level l : levels)
			l.setBoxSize(new Dimension(boxWidth, boxHeight));
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
	
	public void selectLevel(int levelNum){
		main.menuToGame(levelNum, !literallyImpossible);
	}
	
	public void addLevel(int levelNum){
		//if there is a new level to unlock
		if(!levelsUnlocked[levelNum-1]){
			//unlock and add the new level
			levelsUnlocked[levelNum-1] = true;
//			levels[levelNum-1].setLocation(new Point(getWidth()/(levelNum), levels[levelNum-1].getY()));
			levelsPanel.add(levels[levelNum-1]);
			repaint();
		}
	}
	
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
	        int y = (this.getHeight() - (int) r.getHeight()) / 2 + fm.getAscent();
	        g.drawString(title, x, y);
		}
	}
	
	private class Footer extends JPanel implements MouseListener{
		public Footer(){
			addMouseListener(this);
		}
		
		@Override
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			
			int initx = getWidth()/4, inity = 0;
			int width = getWidth()/2, height = getHeight()/3;  
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
	        
			font = new Font("Arial", Font.PLAIN, 11);
			
			g.setFont(font);
			String footer = "Made by Thomas Wang pls give me an A";
			
			//Code modified from Gilbert Le Blanc on https://stackoverflow.com/questions/14284754/
	        fm = g2d.getFontMetrics();
			r = fm.getStringBounds(footer, g2d);
	        x = (this.getWidth() - (int) r.getWidth()) / 2;
	        y = this.getHeight() - (int) r.getHeight() / 2;
	        g.drawString(footer, x, y);
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			int x = e.getX(), y = e.getY();

			//Toggled possibility
			if(Math.abs(y-getHeight()/6)<=getHeight()/6 && Math.abs(x-getWidth()/2)<=getWidth()/4){
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
	}
}
