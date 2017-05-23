import java.awt.Color;
import java.awt.Cursor;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JPanel;

public class SpeedSorterGamePanel  extends JPanel implements MouseListener, MouseMotionListener{
	private ArrayList<Integer> arr, last;
	private int boxHeight = 50, boxWidth = 50;
	private int selected = -1, dragging = -1, initial = -1;
	private boolean moveDrag = false;
	private SpeedSorter main;
	private int moves = 0;
	private int size = 16;
	
	public SpeedSorterGamePanel(SpeedSorter speedSorter){
		super();
		
		main = speedSorter;
		
		arr = new ArrayList<Integer>(size);
		for(int i = 1; i <= size; i++)
			arr.add(i);
		Collections.shuffle(arr);
		
		last = new ArrayList<Integer>(arr);
		
		setBackground(Color.LIGHT_GRAY);
		
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	@Override
	public void paintComponent(Graphics g){
		//Local variables
		int panelWidth = this.getWidth();
		int panelHeight = this.getHeight();
		
		//Set background
		if(isInOrder())
			super.setBackground(Color.WHITE);
		else if(!main.stopwatchIsRunning())
			super.setBackground(Color.GRAY);
		else
			super.setBackground(Color.LIGHT_GRAY);
		super.paintComponent(g);
		
		//Draw boxes
		for(int i = 0; i < size; i++){
			g.setColor(Color.RED);
			
			if(dragging >= size)	//In case user drags off right side
				dragging = size-1;
			
			//Special colors to boxes
			if(i == dragging || i == selected){
				g.fillRect((2*i+1)*panelWidth/(2*size) - boxWidth/2, panelHeight/2-boxHeight/2, boxWidth, boxHeight);
				g.setColor(Color.BLACK);
			} else if(i+1 == arr.get(i) && main.stopwatchIsRunning()){
				g.setColor(Color.WHITE);
				g.fillRect((2*i+1)*panelWidth/(2*size) - boxWidth/2, panelHeight/2-boxHeight/2, boxWidth, boxHeight);
				g.setColor(Color.RED);
			}
			
			//Draw box outline
			g.drawRect((2*i+1)*panelWidth/(2*size) - boxWidth/2, panelHeight/2-boxHeight/2, boxWidth, boxHeight);

			String number = String.valueOf(arr.get(i));
			
			//Code modified from Gilbert Le Blanc on https://stackoverflow.com/questions/14284754/
	        Graphics2D g2d = (Graphics2D) g;
	        FontMetrics fm = g2d.getFontMetrics();
	        Rectangle2D r = fm.getStringBounds(number, g2d);
	        int x = (2*i+1)*panelWidth/(2*size) - (int)r.getWidth()/2 + 1;
	        int y = panelHeight/2 - (int)r.getHeight()/2 + fm.getAscent();
	        g.drawString(number, x, y);	        
		}
		
		//Change moves field
		main.setMoves(moves);
	}
	
	public void reset(){
		Collections.shuffle(arr);
		last = new ArrayList<Integer>(arr);
		moves = 0;
		repaint();
	}
	
	private boolean isInOrder(){
		for(int i = 1; i < size; i++){
			if(arr.get(i) <= arr.get(i-1))
				return false;
		}
		main.stopStopwatch();
		return true;
	}
	
	private void swap(int ind0, int ind1){
		if(Math.min(ind0, ind1)<0 || Math.max(ind0, ind1)>=size)
			return;
		int temp = arr.get(ind0);
		arr.set(ind0, arr.get(ind1));
		arr.set(ind1, temp);
	}
	
	private void shift(int ind0, int ind1){
		if(ind0==ind1)
			return;
		if(ind0 > ind1){
			for(; ind0 > ind1; ind0--)
				swap(ind0, ind0-1);
		} else if (ind0 < ind1){
			for(; ind0 < ind1; ind0++)
				swap(ind0, ind0+1);
		}
	}
	
	private int clickedBox(int x, int y){
		int yDist = Math.abs(y - getHeight()/2);
		int closest = (int)(x)/(getWidth()/size);
		int xPos = (2*closest+1)*getWidth()/(2*size);
		int xDist = Math.abs(x - xPos);
		
		if(yDist<boxHeight/2 && xDist<boxWidth/2) //in a box
			return closest;
		return -1;
	}
	
	private int draggedPos(int x){
		int closest = (int)(x)/(getWidth()/size);
		int xPos = (2*closest+1)*getWidth()/(size*2);
		int xDist = Math.abs(x - xPos);
		
		if(xDist<boxWidth/2) //correct column
			return closest;
		return -1;
	}
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(!main.stopwatchIsRunning())
			return;
		
		int x = e.getX(); int y = e.getY();
		
		int closest = clickedBox(x, y);
		
		if(closest != -1){ //Selected a box
			if(selected != -1){
				swap(selected, closest);
				if(selected != closest){
					moves++;
					last = new ArrayList<Integer>(arr);
				}
				selected = -1;
			} else
				selected = closest;
		} else {
			selected = -1;
		}
		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {
		dragging = -1;
		initial = -1;
		moveDrag = false;
		repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if(!main.stopwatchIsRunning())
			return;
		
		int x = e.getX(); int y = e.getY();
		
		int closest = clickedBox(x, y);
		int column = draggedPos(x);
		
		this.setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
		
		if(closest != -1 && dragging == -1){//clicked in a box and not currently dragging
			dragging = closest;				//begin dragging
			initial = dragging;
		}else if(column != -1 && dragging != -1){//currently dragging
			shift(dragging, column);
			dragging = column;
			if(dragging != initial && !moveDrag){
				moves++;
				last = new ArrayList<Integer>(arr);
				moveDrag = true;
			}
		}
		
		selected = -1;
		
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		repaint();
	}
}
