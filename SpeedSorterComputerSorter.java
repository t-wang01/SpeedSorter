import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

public class SpeedSorterComputerSorter implements ActionListener{
	private Timer timer;
	private ArrayList<SortItem> arr;
	private SpeedSorter main;
	private int i = 0, j = 0, lowInd = 0, insInd = 0, sortMethod=0;
	private boolean insSwapNext = false;
	
	public SpeedSorterComputerSorter(SpeedSorter game){
		main = game;
		timer = new Timer(500, this);
	}
	
	public SpeedSorterComputerSorter(int sortType, int delay, SpeedSorter speed, ArrayList<SortItem> compArr){
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
	
	public void setArray(ArrayList<SortItem> compArr){
		arr = compArr;
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
	
	private void swap(int ind0, int ind1){
		if(Math.min(ind0, ind1)<0 || Math.max(ind0, ind1)>=arr.size())
			return;
		SortItem temp = arr.get(ind0);
		arr.set(ind0, arr.get(ind1));
		arr.set(ind1, temp);
	}

	private void selectionSort(){
		if(j != arr.size()){	//search for lowest value
			if(j>i && j-1!=lowInd)
				arr.get(j-1).setStatus(SortItemStatus.NORMAL);
			
			//if arr[j]<arr[lowInd], set new lowInd to value of j
			if(arr.get(j).compareTo(arr.get(lowInd))<0){
				arr.get(lowInd).setStatus(SortItemStatus.NORMAL);
				lowInd = j;
				arr.get(lowInd).setStatus(SortItemStatus.COMPARED);
			}
			
			arr.get(j).setStatus(SortItemStatus.COMPARED);
			j++;
		} else {				//finished a loop
			swap(i, lowInd);
			arr.get(i).setStatus(SortItemStatus.SORTED);
			
			j = ++i;
			lowInd = i;
			
			//Check if finished
			if(i == arr.size()){
				main.getControlPanel().stopStopwatch();
				timer.stop();
			} else
				arr.get(arr.size()-1).setStatus(SortItemStatus.NORMAL);
		}
	}
	
	private void insertionSort(){
		if(j > 0 && arr.get(j-1).compareTo(arr.get(j))>0){	//insert
			//Set previous item to sorted
			if(j!=i)
				arr.get(j+1).setStatus(SortItemStatus.SORTED);
			
			//Set new items to be COMPARED
			arr.get(j).setStatus(SortItemStatus.COMPARED);
			arr.get(j-1).setStatus(SortItemStatus.COMPARED);
			swap(j, j-1);
			
			j--;
		} else {								//finished a loop
			//Set statuses back to SORTED
			if(j!=i)
				arr.get(j+1).setStatus(SortItemStatus.SORTED);
			arr.get(j).setStatus(SortItemStatus.SORTED);
			if(j!=0)
				arr.get(j-1).setStatus(SortItemStatus.SORTED);
			
			j = ++i;
			
			if(i == arr.size()){
				//If finished, stop
				main.getControlPanel().stopStopwatch();
				timer.stop();
			} else {
				//If not finished, set new items to be COMPARED
				arr.get(j).setStatus(SortItemStatus.COMPARED);
				arr.get(j-1).setStatus(SortItemStatus.COMPARED);
			}
		}
		
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(main.getControlPanel().stopwatchIsRunning()){
			switch(sortMethod){
				case 0:	selectionSort();	break;		
				case 1: insertionSort();	break;
			}
		}
		main.getGamePanel().repaint();
	}
}
