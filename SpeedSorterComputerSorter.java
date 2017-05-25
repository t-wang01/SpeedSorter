import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

public class SpeedSorterComputerSorter implements ActionListener{
	private Timer timer;
	private ArrayList<Integer> arr;
	private SpeedSorter main;
	
	public SpeedSorterComputerSorter(int sortType, int delay, SpeedSorter speed, ArrayList<Integer> compArr){
		timer = new Timer(delay, this);
		arr = compArr;
		main = speed;
	}
	
	public void startTimer(){
		timer.start();
	}
	
	public void stopTimer(){
		timer.stop();
	}

	private boolean isInOrder(){
		for(int i = 1; i < arr.size(); i++){
			if(arr.get(i) <= arr.get(i-1))
				return false;
		}
		main.stopStopwatch();
		return true;
	}
	
	private void swap(int ind0, int ind1){
		if(Math.min(ind0, ind1)<0 || Math.max(ind0, ind1)>=arr.size())
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
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		swap(0,1);
	}
}
