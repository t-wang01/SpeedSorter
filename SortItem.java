import java.awt.Color;
import java.awt.Dimension;
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

public class SortItem extends Rectangle implements Comparable<SortItem>{
	private int value;
	private Color[][] colors;
	private SortItemStatus status = SortItemStatus.NORMAL;
	
	public SortItem(int num, Color[][] colorSet){
		colors = colorSet;
		value = num;
		setSize(50,50);
		setLocation(0,0);
	}
	
	public void setStatus (SortItemStatus newStatus){
		status = newStatus;
	}
	
	public void paint(Graphics g){
		Color[] palette;
		switch(status){
			case NORMAL:		palette = colors[0]; break;
			case SELECTED:		palette = colors[1]; break;
			case COMPARED:		palette = colors[2]; break;
			case SEMISORTED:	palette = colors[3]; break;
			case SORTED:		palette = colors[4]; break;
			case PIVOT:			palette = colors[5]; break;
			case PAUSED:		palette = colors[6]; break;
			case FINISHED:		palette = colors[7]; break;
			default: palette = null;
		}
		g.setColor(palette[0]);
		g.fillRect(this.x, this.y, this.width, this.height);
		g.setColor(palette[1]);
		g.drawRect(this.x, this.y, this.width, this.height);
		
//		Code modified from Gilbert Le Blanc on https://stackoverflow.com/questions/14284754/
		Graphics2D g2d = (Graphics2D) g;
		FontMetrics fm = g2d.getFontMetrics();
		Rectangle2D r = fm.getStringBounds(String.valueOf(value), g2d);
		int x = (int) (this.x + this.width/2 - r.getWidth()/2 + 1);
		int y = (int) (this.y + this.height/2 - r.getHeight()/2 + fm.getAscent());
		g.drawString(String.valueOf(value), x, y);
	}

	@Override
	public int compareTo(SortItem other) {
		return value - other.value;
	}
	
	@Override
	public boolean equals(Object other){
		if(other instanceof SortItem)
			return value==((SortItem)other).value;
		else if (other instanceof Integer)
			return value==(Integer)other;
		else
			return false;
	}
}
 