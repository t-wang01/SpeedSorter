import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

import javax.swing.Timer;

public class ComputerSorter implements ActionListener{
	private Timer timer;
	private ArrayList<SortItem> arr;
	private SpeedSorter main;
	private int i = 0, j = 0, lowInd = 0, min = 0, max = 0, pivot = 0, sortMethod=0;
	private Deque<int[]> stack = new ArrayDeque<int[]>();
	private boolean insSwapNext = false;
	
	public ComputerSorter(SpeedSorter game){
		main = game;
		timer = new Timer(500, this);
	}
	
	public ComputerSorter(int sortType, int delay, SpeedSorter speed, ArrayList<SortItem> compArr){
		timer = new Timer(delay, this);
		arr = compArr;
		main = speed;
		sortMethod = sortType;
	}
	
	private void resetInts(){
		i = 0; j = 0; 
		lowInd = 0; 
		min = 0; max = arr.size()-1;
		pivot = (min + max)/2;
		
		stack = new ArrayDeque<int[]>();
		stack.push(new int[]{min, max});
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
				setStatus(j-1, SortItemStatus.NORMAL);
			
			//if arr[j]<arr[lowInd], set new lowInd to value of j
			if(arr.get(j).compareTo(arr.get(lowInd))<0){
				setStatus(lowInd,SortItemStatus.NORMAL);
				lowInd = j;
				setStatus(lowInd,SortItemStatus.COMPARED);
			}
			
			setStatus(j,SortItemStatus.COMPARED);
			j++;
		} else {				//finished a loop
			swap(i, lowInd);
			setStatus(i,SortItemStatus.SORTED);
			
			j = ++i;
			lowInd = i;
			
			//Check if finished
			if(i == arr.size()){
				main.getControlPanel().stopStopwatch();
				timer.stop();
			} else
				setStatus(arr.size()-1,SortItemStatus.NORMAL);
		}
	}
	
	private void insertionSort(){
		if(j > 0 && arr.get(j-1).compareTo(arr.get(j))>0){	//insert
			//Set previous item to sorted
			if(j!=i)
				setStatus(j+1,SortItemStatus.SORTED);
			
			//Set new items to be COMPARED
			setStatus(new int[]{j,j-1},SortItemStatus.COMPARED);
			swap(j, j-1);
			
			j--;
		} else {								//finished a loop
			//Set statuses back to SORTED
			if(j!=i)
				setStatus(j+1,SortItemStatus.SORTED);
			setStatus(j,SortItemStatus.SORTED);
			if(j!=0)
				setStatus(j-1,SortItemStatus.SORTED);
			
			j = ++i;
			
			if(i == arr.size()){
				//If finished, stop
				main.getControlPanel().stopStopwatch();
				timer.stop();
			} else {
				//If not finished, set new items to be COMPARED
				setStatus(new int[]{j,j-1},SortItemStatus.COMPARED);
			}
		}
		
	}
	
	private void quickSort(){
		//Modified code from Yaroslav S. on https://stackoverflow.com/questions/19124752/
		if(j == 0 && i == 0){ //initial
			popStack();
		} else if(i >= j){	//Completed a partition
			setStatus(pivot, SortItemStatus.SORTED);
			
		    if(min < (pivot - 1))
		        stack.add(new int[] {min, pivot - 1});
		    else
		    	setStatus(min, SortItemStatus.SORTED);
		    
		    if(max > (pivot + 1)) 
		        stack.add(new int[] {pivot + 1, max});
		    else
		    	setStatus(max, SortItemStatus.SORTED);
		    popStack();
		} else {			//Sort partition
			if(arr.get(i).compareTo(arr.get(pivot))<0){
				setStatus(i, SortItemStatus.NORMAL);
				i++;
				setStatus(i, SortItemStatus.COMPARED);
			} else if (arr.get(j).compareTo(arr.get(pivot))>0){
				setStatus(j, SortItemStatus.NORMAL);
				j--;
				setStatus(j, SortItemStatus.COMPARED);
			} else {
				swap(i,j);
				if(pivot == i)
					pivot = j;
				else if (pivot == j)
					pivot = i;
			}
		}
	}
	
	private void popStack(){
		try{
			int[] temp = stack.pop();
			min = temp[0]; i = min;
			max = temp[1]; j = max;
			pivot = (min+max)/2;
			
			setStatus(new int[]{i,j}, SortItemStatus.COMPARED);
			setStatus(pivot, SortItemStatus.PIVOT);
		} catch (Exception e){
			timer.stop();
			main.getControlPanel().stopStopwatch();
		}
	}
	
	private void setStatus(int index, SortItemStatus status){
		arr.get(index).setStatus(status);
	}
	
	private void setStatus(int[] indexes, SortItemStatus status){
		for(int index : indexes)
			setStatus(index, status);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(main.getControlPanel().stopwatchIsRunning()){
			switch(sortMethod){
				case 0:	selectionSort();	break;		
				case 1: insertionSort();	break;
				case 2: quickSort();		break;
			}
		}
		main.getGamePanel().repaint();
	}
}
