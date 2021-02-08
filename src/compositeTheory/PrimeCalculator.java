package compositeTheory;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;

public class PrimeCalculator {
	
	
	private int Limit = 0; //Default limit	
	private ArrayList<Integer> P_set = null; //Prime set
	private ArrayList<Integer> A_set = null; //Composite set
	private int A_totalIterations = 0;
	private int A_totalMicroSteps = 0;
	private int A_totalMacroSteps = 0;
	private long A_duration = 0;
	private int P_totalSteps = 0;
	private long P_duration = 0;
	private JPanel mainPanel = null;
	private JTextArea COUT = null;
	public MySwingWorker<Void, Integer> LMS = null; // used for publishing the progress
	
	
	
	public PrimeCalculator(JPanel mainPanel, JTextArea console, MySwingWorker<Void, Integer> MS){		
		
		this.LMS = MS; 
		this.mainPanel = mainPanel;	
		this.COUT = console;
		this.Limit = (Integer)((JSpinner)(getComponentByName("spinner1"))).getValue();	
	
	}
	
	
	public void getPrimes() {
		// [P] => (|R)x(|R)
		System.out.println("Getting primes for Limit = " + this.Limit);
		
		COUT.setText("");
		
		this.A_set = getProducts();
		
		Collections.sort(A_set);
		
		((JLabel)(getComponentByName("lbl1"))).setText("|A| = " + (A_set.size() - 2) );
		
		this.P_set = extractPrimes();
		
		((JLabel)(getComponentByName("lbl2"))).setText("|P| = " + P_set.size());
		
		if (this.Limit >= 2) {
			((JLabel)(getComponentByName("lbl3"))).setText("Highest: " + P_set.get(P_set.size() - 1)  );
		}
		else {
			((JLabel)(getComponentByName("lbl3"))).setText("Highest: N/A");
		}
		
		LogToGUI();
				
		
	}
	
	
	private ArrayList<Integer> getProducts() {
		
		
		int lMaxIter = (int)(Math.floor(Math.sqrt(this.Limit)));
		int lCurrentStep = 0;
		int lTotalSteps = getSteps(this.Limit);
		int lRealSteps = 0;
		int lPerformedIterations = 0;
		ArrayList<Integer> lOutputArray = new ArrayList<>();
		lOutputArray.add(2); // the initial set must contain "2" (the first prime number)
		lOutputArray.add(this.Limit + 1); // otherwise L will not be included.
		
		COUT.append("\n===== A SET ======\n");
		
		
		long lStartTime = System.nanoTime();	
		
		for (int i = 2; i <= lMaxIter; i++) {
			
			int j = i;
			
			while((i*j) <= this.Limit) {
				
				//double lPercent = 100*((double)lCurrentStep/(double)lTotalSteps);
				
				this.LMS.MyPublish(lCurrentStep);
				
				
				if (!lOutputArray.contains(i*j)) {
					lOutputArray.add(i*j);
					COUT.append((i*j) + ", ");
					COUT.setCaretPosition(COUT.getDocument().getLength());
					lRealSteps++;
				}
				
	            j++;
	            lCurrentStep++;
	            	
			}
			
			lPerformedIterations = i;
			
		}
		
		long lEndTime = System.nanoTime();
		long lElapsedTime = (lEndTime - lStartTime) / 1000000; //milliseconds
		
		
		this.A_totalIterations = lPerformedIterations;
		this.A_totalMicroSteps = lTotalSteps;
		this.A_totalMacroSteps = lRealSteps;
		this.A_duration = lElapsedTime;
		
		System.out.println("GET A: DONE!");
		System.out.println("GET A: " + lPerformedIterations + " iterations TOTAL");
		System.out.println("GET A: " + lTotalSteps + " micro steps TOTAL");
		System.out.println("GET A: " + lRealSteps + " macro steps TOTAL");
		System.out.println("GET A: " + lElapsedTime + " ms");
			
		return lOutputArray;

	}
	

	private ArrayList<Integer> extractPrimes() {
		
		ArrayList<Integer> lOutputArray = new ArrayList<>();
		int lSteps = 0;
		int lDifference = 0;
		
		COUT.append("\n===== P SET ======\n");
		
		long lStartTime = System.nanoTime();
		
		if (this.Limit >= 2) {
			lOutputArray.add(2); // the initial set must contain "2" (the first prime number)
			COUT.append("2, ");
		}
		
		for (int i = 1; i < this.A_set.size(); i++) {
			
			lSteps++;
		    lDifference = this.A_set.get(i) - this.A_set.get(i-1);

		   
		    
		    switch (lDifference) {
		    
		    case 1:
		    	{
		    		//composite
		    		break;
		    	}
		    case 2:
		    	{
		    		//prime		    		
		    		int temp = this.A_set.get(i) - 1;
		    		lOutputArray.add(temp);
		    		COUT.append(temp + ", ");
					COUT.setCaretPosition(COUT.getDocument().getLength());
		    		break;
		    	}
		    default:
		    	{
		    		//Missing data
		    		break;
		    	}
		    
		    }
		    
		    
			
		}
		
		long lEndTime = System.nanoTime();
		long lElapsedTime = (lEndTime - lStartTime) / 1000000; //milliseconds
		this.P_totalSteps = lSteps;
		this.P_duration = lElapsedTime;
		
		System.out.print("GET P: " + lSteps + " steps");
		System.out.println("GET P: " + lElapsedTime + " ms");
		
		return lOutputArray;
		
	}
	
	
	static int getSteps(int limit) {
		
		int lLimit = limit;
		
		
		int lSteps = 0;
			
		for (int i = 2; i < (int)(Math.sqrt(lLimit)); i++) {
			
			lSteps += (int)(lLimit / i) - (i-2);
			
		}
		
		return lSteps;
		
	}
	
	private Component getComponentByName(String cname) {
		
		Component[] components = this.mainPanel.getComponents();
		
	    Component match = null;
		
		for (int i = 0; i < components.length; i++) {
	    	
	    	if (components[i].getName().contentEquals(cname)) {
	    		match = components[i];
	    	}	
	    }
	    
	    return match;
	    
	}

	private void LogToGUI() {
		
		
		COUT.append("\n\nDONE!\n");
		
		COUT.append("\n===== A SET ======\n");
		COUT.append("Iterations: " + this.A_totalIterations + "\n");
		COUT.append("Micro steps: " + this.A_totalMicroSteps + "\n");
		COUT.append("Macro steps: " + this.A_totalMacroSteps + "\n");
		COUT.append("Duration: " + this.A_duration  + " ms");
		
		COUT.append("\n===== P SET ======\n");
		COUT.append("Steps: " + this.P_totalSteps + "\n");
		COUT.append("Duration: " + this.P_duration + " ms\n");
		
		COUT.setCaretPosition(COUT.getDocument().getLength());
		
	}

}



