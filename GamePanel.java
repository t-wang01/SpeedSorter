import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JPanel;

public class GamePanel  extends JPanel implements MouseListener, MouseMotionListener{
	private ArrayList<SortItem> humanArr, compArr, last;
	private final int boxHeight = 50, boxWidth = 50;
	private int swap0 = -1, dragging = -1, initial = -1;
	private boolean isDragging = false;
	private SpeedSorter main;
	private ControlPanel control;
	private ComputerSorter comp;
	private int moves = 0;
	private int size = 16;
	private int[][] levelData = {{0, 300},{1, 300},{2,300}};
	private int level;
	private Color[][] colors;
	private boolean humanHasWon = false, customGame = false;
	
	public GamePanel(SpeedSorter speedSorter){
		super();
				
		main = speedSorter;
		control = main.getControlPanel();
		comp = main.getComputerSorter();
		
		//Set up colors //TODO Make this look prettier
				colors = new Color[][]{
					{Color.LIGHT_GRAY, Color.RED},
					{Color.LIGHT_GRAY, Color.BLACK},
					{Color.GRAY, Color.YELLOW},
					{Color.WHITE, Color.BLACK},
					{Color.GRAY, Color.GREEN},
					{Color.WHITE, Color.BLACK},
					{Color.DARK_GRAY, Color.BLACK}
				};
		
		//Set up arrays
		ArrayList<Integer> rand = new ArrayList<Integer>(size);
		
		for(int i = 0; i < size; i++)
			rand.add(i+1);
		Collections.shuffle(rand);
		
		humanArr = new ArrayList<SortItem>(size);
		compArr = new ArrayList<SortItem>(size);
		
		for(int i = 1; i <= size; i++){
			compArr.add(new SortItem(rand.get(i-1), colors));
			humanArr.add(new SortItem(rand.get(i-1), colors));
		}
		last = new ArrayList<SortItem>(humanArr);
		
		//Set up computer
		comp.setTimer(300);	//~45s selection sort
		comp.setMethod(0);
		comp.setArray(compArr);
		comp.startTimer();
		
		setBackground(Color.LIGHT_GRAY);
		
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	public void setLevel(int level, boolean isPossible){
		this.level = level;
		humanHasWon = false;
		customGame = false;
		comp.setMethod(levelData[level-1][0]);
		if(isPossible)
			comp.setTimer(levelData[level-1][1]);
		else
			comp.setTimer(0);
		reset();
	}
	
	@Override
	public void paintComponent(Graphics g){
		int panelWidth = getWidth();
		
		//Set background
		if(isInOrder())
			super.setBackground(Color.WHITE);
		else if(!control.stopwatchIsRunning())
			super.setBackground(Color.GRAY);
		else
			super.setBackground(Color.LIGHT_GRAY);
		super.paintComponent(g);
		
		if(dragging >= size)	//In case user drags off right side
			dragging = size-1;
		
		int incrementY = getHeight()/3;
		
		//Draw boxes
		for(int i = 0; i < size; i++){
			compArr.get(i).setSize(new Dimension(boxWidth, boxHeight));
			compArr.get(i).setLocation(new Point((2*i+1)*panelWidth/(2*size) - boxWidth/2, incrementY - boxHeight/2));
			
			compArr.get(i).paint(g);
			
			humanArr.get(i).setSize(new Dimension(boxWidth, boxHeight));
			humanArr.get(i).setLocation(new Point((2*i+1)*panelWidth/(2*size) - boxWidth/2, 2*incrementY - boxHeight/2));
			
			if(isInOrder())
				humanArr.get(i).setStatus(SortItemStatus.FINISHED);
			else if(humanArr.get(i).equals(i+1))
				humanArr.get(i).setStatus(SortItemStatus.SORTED);
			else if(i == dragging || i == swap0 || i == initial)
				humanArr.get(i).setStatus(SortItemStatus.SELECTED);
			else
				humanArr.get(i).setStatus(SortItemStatus.NORMAL);
			humanArr.get(i).paint(g);
		}
		
		//Draw labels
		g.setColor(Color.BLACK);
		
		//Code modified from Gilbert Le Blanc on https://stackoverflow.com/questions/14284754/
		Graphics2D g2d = (Graphics2D) g;
		FontMetrics fm = g2d.getFontMetrics();
		Rectangle2D r = fm.getStringBounds("COMPUTER ARRAY", g2d);
		int x = (int) (panelWidth/2 - r.getWidth()/2 + 1);
		int y = (int) (r.getHeight());
		g.drawString("COMPUTER ARRAY", x, y);
		
		r = fm.getStringBounds("HUMAN ARRAY", g2d);
		x = (int) (panelWidth/2 - r.getWidth()/2 + 1);
		y = (int) (getHeight() - r.getHeight()/2);
		g.drawString("HUMAN ARRAY", x, y);
		
		//Change moves field
		control.setMoves(moves);
	}
	
	public void pauseArrays(boolean isPaused){
		for(SortItem si : humanArr)
			si.setPause(isPaused);
		for(SortItem si : compArr)
			si.setPause(isPaused);
		repaint();
	}
	
	public void reset(){
		Collections.shuffle(compArr);
//		last = new ArrayList<SortItem>(humanArr);
		for(int i = 0; i < compArr.size(); i++){
			compArr.set(i, new SortItem(compArr.get(i).getValue(), colors));
			humanArr.set(i, new SortItem(compArr.get(i).getValue(), colors));
		}
		comp.setArray(compArr);
		moves = 0;
		swap0 = -1;
		initial = -1;
		isDragging = false;
		dragging = -1;
		repaint();
	}
	
	public void returnToTitle(){
		comp.setArray(compArr); //reset ints
		main.returnToTitle(humanHasWon, level+1);
	}
	
	private boolean isInOrder(){
		for(int i = 1; i < size; i++){
			if(humanArr.get(i).compareTo(humanArr.get(i-1))<0)
				return false;
		}
		control.stopStopwatch();
		humanHasWon = true;
		return true;
	}
	
	public void setCustomGame(){
		customGame = true;
	}
	
	private void swap(int ind0, int ind1){
		if(Math.min(ind0, ind1)<0 || Math.max(ind0, ind1)>=size || ind0 == ind1)
			return;
		SortItem temp = humanArr.get(ind0);
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
