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
	private ArrayList<Integer> humanArr, compArr, last;
	private final int boxHeight = 50, boxWidth = 50;
	private int swap0 = -1, dragging = -1, initial = -1;
	private boolean isDragging = false;
	private SpeedSorter main;
	private SpeedSorterControlPanel control;
	private SpeedSorterComputerSorter comp;
	private int moves = 0;
	private int size = 16;
	
	public SpeedSorterGamePanel(SpeedSorter speedSorter){
		super();
		
		main = speedSorter;
		control = main.getControlPanel();
		comp = main.getComputerSorter();
		
		humanArr = new ArrayList<Integer>(size);
		for(int i = 1; i <= size; i++)
			humanArr.add(i);
		Collections.shuffle(humanArr);
		
		compArr = new ArrayList<Integer>(humanArr);
		last = new ArrayList<Integer>(humanArr);
		
		comp.setTimer(300);	//~45s selection sort
		comp.setArray(compArr);
		comp.setMethod(1);
		comp.startTimer();
		
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
		else if(!control.stopwatchIsRunning())
			super.setBackground(Color.GRAY);
		else
			super.setBackground(Color.LIGHT_GRAY);
		super.paintComponent(g);
		
		//Draw boxes
		for(int i = 0; i < size; i++){
			g.setColor(Color.RED);
			
			if(dragging >= size)	//In case user drags off right side
				dragging = size-1;
			
			int incrX = panelWidth/(2*size);
			int adjY = panelHeight/3;
			int[] compare = comp.getHighlights();
			
			//Computer array box
			if(i+1 == compArr.get(i) && control.stopwatchIsRunning()){
				g.setColor(Color.LIGHT_GRAY);
				g.fillRect((2*i+1)*incrX - boxWidth/2, adjY*2 - boxHeight/2, boxWidth, boxHeight);
			} else {
				g.setColor(Color.GRAY);
				g.fillRect((2*i+1)*incrX - boxWidth/2, adjY - boxHeight/2, boxWidth, boxHeight);
			}
			g.setColor(Color.RED);
			if(i+1 == compArr.get(i))
				g.setColor(Color.BLACK);
			if(i == compare[0] || i == compare[1])
				g.setColor(Color.YELLOW);
			g.drawRect((2*i+1)*incrX - boxWidth/2, adjY - boxHeight/2, boxWidth, boxHeight);
	        String number = String.valueOf(compArr.get(i));
	        
	        
	        
			//Code modified from Gilbert Le Blanc on https://stackoverflow.com/questions/14284754/
	        Graphics2D g2d = (Graphics2D) g;
	        FontMetrics fm = g2d.getFontMetrics();
			Rectangle2D r = fm.getStringBounds(number, g2d);
			int x = (2*i+1)*incrX - (int)r.getWidth()/2 + 1;
			int y = panelHeight/3 - (int)r.getHeight()/2 + fm.getAscent();
			g.drawString(number, x, y);
			
			g.setColor(Color.RED);
			
			//Special colors to boxes
			if(i == dragging || i == initial || i == swap0){
				g.fillRect((2*i+1)*incrX - boxWidth/2, adjY*2 - boxHeight/2, boxWidth, boxHeight);
				g.setColor(Color.BLACK);
			} else if(i+1 == humanArr.get(i) && control.stopwatchIsRunning()){
				g.setColor(Color.WHITE);
				g.fillRect((2*i+1)*incrX - boxWidth/2, adjY*2 - boxHeight/2, boxWidth, boxHeight);
				g.setColor(Color.RED);
			}
			
			//Draw box outline
			g.drawRect((2*i+1)*incrX - boxWidth/2, adjY*2 - boxHeight/2, boxWidth, boxHeight);

			//Display numbers
			number = String.valueOf(humanArr.get(i));
			
			//Code modified from Gilbert Le Blanc on https://stackoverflow.com/questions/14284754/
	        r = fm.getStringBounds(number, g2d);
	        x = (2*i+1)*incrX - (int)r.getWidth()/2 + 1;
	        y = panelHeight*2/3 - (int)r.getHeight()/2 + fm.getAscent();
	        g.drawString(number, x, y);
		}
		
		//Change moves field
		control.setMoves(moves);
	}
	
	public void reset(){
		Collections.shuffle(humanArr);
		last = new ArrayList<Integer>(humanArr);
		compArr = new ArrayList<Integer>(humanArr);
		comp.setArray(compArr);
		moves = 0;
		swap0 = -1;
		initial = -1;
		isDragging = false;
		dragging = -1;
		repaint();
	}
	
	private boolean isInOrder(){
		for(int i = 1; i < size; i++){
			if(humanArr.get(i) <= humanArr.get(i-1))
				return false;
		}
		control.stopStopwatch();
		return true;
	}
	
	private void swap(int ind0, int ind1){
		if(Math.min(ind0, ind1)<0 || Math.max(ind0, ind1)>=size || ind0 == ind1)
			return;
		int temp = humanArr.get(ind0);
		humanArr.set(ind0, humanArr.get(ind1));
		humanArr.set(ind1, temp);
		moves++;
	}
	
	private void shift(int ind0, int ind1){
		if(Math.min(ind0, ind1)<0 || Math.max(ind0, ind1)>=size || ind0 == ind1)
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
		int yDist = Math.abs(y - getHeight()*2/3);
		int closest = (int)(x)/(getWidth()/size);
		int xPos = (2*closest+1)*getWidth()/(2*size);
		int xDist = Math.abs(x - xPos);
		
		if(yDist<boxHeight/2 && xDist<boxWidth/2) //in a box
			return closest;
		return -1;
	}
	
	private int draggedPos(int x){
		int closest = (int)(x/(getWidth()/size));
		int xPos = (2*closest+1)*getWidth()/(size*2);
		int xDist = Math.abs(x - xPos);

		if(xDist<boxWidth/2) //correct column
			return closest;
		return -1;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(!control.stopwatchIsRunning())
			return;
		
		int x = e.getX(), y = e.getY();
		
		initial = clickedBox(x, y);
		
		repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {		
		if(!control.stopwatchIsRunning())
			return;
		
		int x = e.getX();
		
		int column = draggedPos(x);
		
		if(!isDragging && column!=-1){
			isDragging = column!=initial;
			dragging = initial;
		}
		if(isDragging && column!=-1){
			shift(dragging, column);
			dragging = column;
			swap0 = -1;
			initial = -1;
		}
		
		//Change cursor
		this.setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
			
		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {		
		if(!control.stopwatchIsRunning())
			return;
		
		int x = e.getX(), y = e.getY();
		
		int closest = clickedBox(x,y);
		
		if(initial == closest){
			if(swap0 == -1)
				swap0 = initial;
			else{
				swap(swap0, closest);
				swap0 = -1;
			}
		}
		initial = -1;
		isDragging = false;
		dragging = -1;
		repaint();
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseMoved(MouseEvent e) {
		this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

}
