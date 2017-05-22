import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class SpeedSorter{
//	private ArrayList<Integer> arr;
//	private int boxHeight = 50, boxWidth = 50;
//	private int selected = -1, dragging = -1, initial = -1;
//	private boolean moveDrag = false;
	private SpeedSorterControlPanel control;
	private SpeedSorterMenuBar menuBar;
	private SpeedSorterGamePanel game;
//	private int moves = 0;
//	private int size = 16;
	
	public SpeedSorter(){
//		super();
		
//		arr = new ArrayList<Integer>(size);
//		for(int i = 1; i <= size; i++)
//			arr.add(i);
//		Collections.shuffle(arr);
		
//		setBackground(Color.LIGHT_GRAY);
	
		game = new SpeedSorterGamePanel(this);
		
		control = new SpeedSorterControlPanel(game);
		
		menuBar = new SpeedSorterMenuBar(control);
//		
//		addMouseListener(this);
//		addMouseMotionListener(this);
	}
	
	public static void main(String[] args){
		// Set system look and feel:
	    String plafName = UIManager.getSystemLookAndFeelClassName();
	    try{UIManager.setLookAndFeel(plafName);}
	    catch(Exception ex){};
	    
		JFrame frame = new JFrame("SpeedSorter v1");
		frame.setSize(500, 400);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		
		JPanel panel = new JPanel(new BorderLayout());
		
		SpeedSorter game = new SpeedSorter();
		panel.add(game.game, BorderLayout.CENTER);
		panel.add(game.control, BorderLayout.LINE_END);
		
		
		frame.setJMenuBar(game.menuBar);
		frame.add(panel);
		frame.setVisible(true);
	}
	
	public boolean stopwatchIsRunning(){
		return control.stopwatchIsRunning();
	}
	
	public void stopStopwatch(){
		control.stopStopwatch();
	}
	
	public void setMoves(int moves){
		control.setMoves(moves);
	}
	
//	@Override
//	public void paintComponent(Graphics g){
//		//Local variables
//		int panelWidth = this.getWidth();
//		int panelHeight = this.getHeight();
//		
//		//Set background
//		if(isInOrder())
//			super.setBackground(Color.WHITE);
//		else if(!control.stopwatchIsRunning())
//			super.setBackground(Color.GRAY);
//		else
//			super.setBackground(Color.LIGHT_GRAY);
//		super.paintComponent(g);
//		
//		//Draw boxes
//		for(int i = 0; i < size; i++){
//			g.setColor(Color.RED);
//			
//			if(dragging >= size)
//				dragging = size-1;
//			
//			if(i == dragging || i == selected){
//				g.fillRect((2*i+1)*panelWidth/(2*size) - boxWidth/2, panelHeight/2-boxHeight/2, boxWidth, boxHeight);
//				g.setColor(Color.BLACK);
//			}
//			g.drawRect((2*i+1)*panelWidth/(2*size) - boxWidth/2, panelHeight/2-boxHeight/2, boxWidth, boxHeight);
//			
//			g.drawString(String.valueOf(arr.get(i)), (2*i+1)*panelWidth/(2*size) - 2, panelHeight/2 + 4);
//		}
//		
//		//Change moves field
//		control.setMoves(moves);
//	}
//	
//	public void reset(){
//		Collections.shuffle(arr);
//		moves = 0;
//		repaint();
//	}
//	
//	private boolean isInOrder(){
//		for(int i = 1; i < size; i++){
//			if(arr.get(i) <= arr.get(i-1))
//				return false;
//		}
//		control.stopStopwatch();
//		return true;
//	}
//	
//	private void swap(int ind0, int ind1){
//		if(Math.min(ind0, ind1)<0 || Math.max(ind0, ind1)>=size)
//			return;
//		int temp = arr.get(ind0);
//		arr.set(ind0, arr.get(ind1));
//		arr.set(ind1, temp);
//	}
//	
//	private void shift(int ind0, int ind1){
//		if(ind0==ind1)
//			return;
//		if(ind0 > ind1){
//			for(; ind0 > ind1; ind0--)
//				swap(ind0, ind0-1);
//		} else if (ind0 < ind1){
//			for(; ind0 < ind1; ind0++)
//				swap(ind0, ind0+1);
//		}
//	}
//	
//	private int clickedBox(int x, int y){
//		int yDist = Math.abs(y - getHeight()/2);
//		int closest = (int)(x)/(getWidth()/size);
//		int xPos = (2*closest+1)*getWidth()/(2*size);
//		int xDist = Math.abs(x - xPos);
//		
//		if(yDist<boxHeight/2 && xDist<boxWidth/2) //in a box
//			return closest;
//		return -1;
//	}
//	
//	private int draggedPos(int x){
//		int closest = (int)(x)/(getWidth()/size);
//		int xPos = (2*closest+1)*getWidth()/(size*2);
//		int xDist = Math.abs(x - xPos);
//		
//		if(xDist<boxWidth/2) //correct column
//			return closest;
//		return -1;
//	}
//	
//	
//	@Override
//	public void mouseClicked(MouseEvent e) {
//		// TODO Auto-generated method stub
//		if(!control.stopwatchIsRunning())
//			return;
//		
//		int x = e.getX(); int y = e.getY();
//		
//		int closest = clickedBox(x, y);
//		
//		if(closest != -1){ //Selected a box
//			if(selected != -1){
//				swap(selected, closest);
//				if(selected != closest)
//					moves++;
//				selected = -1;
//			} else
//				selected = closest;
//		} else {
//			selected = -1;
//		}
//		repaint();
//	}
//
//	@Override
//	public void mouseEntered(MouseEvent e) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void mouseExited(MouseEvent e) {
//		// TODO Auto-generated method stub
////		control.pauseStopwatch();
////		repaint();
//	}
//
//	@Override
//	public void mousePressed(MouseEvent e) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void mouseReleased(MouseEvent e) {
//		// TODO Auto-generated method stub
//		dragging = -1;
//		initial = -1;
//		moveDrag = false;
//		repaint();
//	}
//
//	@Override
//	public void mouseDragged(MouseEvent e) {
//		// TODO Auto-generated method stub
//		if(!control.stopwatchIsRunning())
//			return;
//		
//		int x = e.getX(); int y = e.getY();
//		
//		int closest = clickedBox(x, y);
//		int column = draggedPos(x);
//		
//		this.setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
//		
//		if(closest != -1 && dragging == -1){//clicked in a box and not currently dragging
//			dragging = closest;				//begin dragging
//			initial = dragging;
//		}else if(column != -1 && dragging != -1){//currently dragging
//			shift(dragging, column);
//			dragging = column;
//			if(dragging != initial && !moveDrag){
//				moves++;
//				moveDrag = true;
//			}
//		}
//		
//		selected = -1;
//		
//		repaint();
//	}
//
//	@Override
//	public void mouseMoved(MouseEvent e) {
//		// TODO Auto-generated method stub
//		this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
//		repaint();
//	}
//		
}
