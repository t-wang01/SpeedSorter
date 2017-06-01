import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

public class SpeedSorterComputerSorter implements ActionListener{
	private Timer timer;
	private ArrayList<Integer> arr;
	private SpeedSorter main;
	private int i = 0, j = 0, lowInd = 0, insInd = 0, sortMethod=0;
	private boolean insSwapNext = false;
	
	public SpeedSorterComputerSorter(SpeedSorter game){
		main = game;
		timer = new Timer(500, this);
	}
	
	public SpeedSorterComputerSorter(int sortType, int delay, SpeedSorter speed, ArrayList<Integer> compArr){
		timer = new Timer(delay, this);
		arr = compArr;
		main = speed;
		sortMethod = sortType;
	}
	
	private void resetInts(){
		i = 0;
		j = 0; 
		insInd = 0;
		lowInd = 0; 
	}
	
	public void setMethod(int sortType){
		sortMethod = sortType;
	}
	
	public void setArray(ArrayList<Integer> array){
		arr = array;
		resetInts();
	}
	
	public void setTimer(int delay){
		timer.setDelay(delay);
		timer.setInitialDelay(delay);
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
		main.getControlPanel().stopStopwatch();
		timer.stop();
		return true;
	}
	
	public int[] getHighlights(){
		int[] out = new int[2];
		switch(sortMethod){
			case 0:
				out[0] = j;
				out[1] = lowInd;
				break;
			case 1:
				out[0] = j-1;
				out[1] = j;
		}

		return out;
	}
	
	private void swap(int ind0, int ind1){
		if(Math.min(ind0, ind1)<0 || Math.max(ind0, ind1)>=arr.size())
			return;
		int temp = arr.get(ind0);
		arr.set(ind0, arr.get(ind1));
		arr.set(ind1, temp);
	}

	private void selectionSort(){
		if(j != arr.size()){	//search
			if(arr.get(j) < arr.get(lowInd))
				lowInd = j;
			j++;
		} else {				//finished a loop
			swap(i, lowInd);
			j = ++i;
			lowInd = i;
		}
	}
	
	private void insertionSort(){
		if(j > 0 && arr.get(j-1)>arr.get(j)){	//insert
			swap(j, j-1);
			j--;
		} else {								//finished a loop
			i++;
			j = i;
		}
		
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(main.getControlPanel().stopwatchIsRunning()){
			switch(sortMethod){
				case 0:	selectionSort(); 	break;		
				case 1: insertionSort();	break;
			}
		}
		main.getGamePanel().repaint();
		if(isInOrder())
			main.getControlPanel().stopStopwatch();
	}
}
