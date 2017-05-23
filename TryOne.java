import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class TryOne extends JPanel implements MouseListener{
	private ArrayList<Integer> arr;
	
	private int boxHeight = 50, boxWidth = 50;
	
	private int selected = -1;
	
	public TryOne(){
		super();
		
		arr = new ArrayList<Integer>(5);
		for(int i = 1; i <= 5; i++)
			arr.add(i);
		Collections.shuffle(arr);
		
		setBackground(Color.LIGHT_GRAY);
		
		addMouseListener(this);
	}
	
	public static void main(String[] args){
		JFrame frame = new JFrame("Try one");
		frame.setSize(400, 400);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new TryOne());
		frame.setVisible(true);
	}
	
	@Override
	public void paintComponent(Graphics g){
		if(isInOrder())
			super.setBackground(Color.WHITE);
		super.paintComponent(g);
		
		int panelWidth = this.getWidth();
		int panelHeight = this.getHeight();
		
		for(int i = 0; i < 5; i++){
			g.setColor(Color.RED);
			
			if(i == selected){
				g.fillRect((2*i+1)*panelWidth/10 - boxWidth/2, panelHeight/2-boxHeight/2, boxWidth, boxHeight);
				g.setColor(Color.BLACK);
			}
			g.drawRect((2*i+1)*panelWidth/10 - boxWidth/2, panelHeight/2-boxHeight/2, boxWidth, boxHeight);
			
			g.drawString(String.valueOf(arr.get(i)), (2*i+1)*panelWidth/10 - 2, panelHeight/2 + 4);
		}
	}
	
	private boolean isInOrder(){
		for(int i = 1; i < 5; i++){
			if(arr.get(i) <= arr.get(i-1))
				return false;
		}
		return true;
	}
	
	private void swap(int ind0, int ind1){
		int temp = arr.get(ind0);
		arr.set(ind0, arr.get(ind1));
		arr.set(ind1, temp);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		int x = e.getX();
		int y = e.getY();
		
		int yDist = Math.abs(y - getHeight()/2);
		int closest = (int)(x)/(getWidth()/5);
		int xPos = (2*closest+1)*getWidth()/10;
		int xDist = Math.abs(x - xPos);
		
		if(yDist<boxHeight/2 && xDist<boxWidth/2){ //Selected a box
			if(selected != -1){
				swap(selected, closest);
				selected = -1;
			} else
				selected = closest;
		} else {
			selected = -1;
		}
		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
		
}
