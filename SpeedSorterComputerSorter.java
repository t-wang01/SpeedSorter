import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

public class SpeedSorterComputerSorter implements ActionListener{
	private Timer timer;
	private ArrayList<Integer> arr;
	private SpeedSorter main;
	private int i = 0, j = 0, comp0 = 0, comp1 = 1, lowest = 0;;
	
	public SpeedSorterComputerSorter(SpeedSorter game){
		main = game;
		timer = new Timer(1000, this);
	}
	
	public SpeedSorterComputerSorter(int sortType, int delay, SpeedSorter speed, ArrayList<Integer> compArr){
		timer = new Timer(delay, this);
		arr = compArr;
		main = speed;
	}
	
	public void setArray(ArrayList<Integer> array){
		arr = array;
		i = 0;
	}
	
	public void setTimer(int delay){
		timer = new Timer(delay, this);
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
	
	private void swap(int ind0, int ind1){
		if(Math.min(ind0, ind1)<0 || Math.max(ind0, ind1)>=arr.size())
			return;
		int temp = arr.get(ind0);
		arr.set(ind0, arr.get(ind1));
		arr.set(ind1, temp);
	}
	
/**
 * public static void selectionSort(int[] arr){  
        for (int i = 0; i < arr.length - 1; i++)  
        {  
            int index = i;  
            for (int j = i + 1; j < arr.length; j++){  
                if (arr[j] < arr[index]){  
                    index = j;//searching for lowest index  
                }  
            }  
            int smallerNumber = arr[index];   
            arr[index] = arr[i];  
            arr[i] = smallerNumber;  
        }  
 */
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(main.getControlPanel().stopwatchIsRunning()){
			int swap0, swap1;
			int index = i;
            if (arr.get(j) < arr.get(index)){  
                index = j;//searching for lowest index
            }  
			if(j==arr.size()){
	            swap0 = index; swap1 = i;
				swap(swap0, swap1);
				if(i<arr.size())
					i++;
			}
		}
		if(isInOrder())
			main.getControlPanel().stopStopwatch();
		main.getGamePanel().repaint();
	}
}
