package compositeTheory;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;


public class MainForm  {
	
	private JFrame mainWindow;
	private JPanel panel1;
	private JButton btn1;
	private SpinnerNumberModel model;
	private JSpinner spinner1;
	private JLabel lbl1; // |A|
	private JLabel lbl2; // |P|
	private JLabel lbl3; // P max
	private JLabel lbl4; // status
	private JLabel lbl5; // signature
	private JTextArea myConsole;
	private JScrollPane scrollpane1;
	private JProgressBar progress1;
	
	
    public MainForm() {
    	
    	//dimensions
    	Dimension window_size = new Dimension(440, 460);
    	Dimension panel1_size = new Dimension(440, 460);
    	Dimension spinner1_size = new Dimension(100, 35);    	
    	Dimension btn1_size = new Dimension(100, 35);    	
    	Dimension lbl1_size = new Dimension(200, 20);
    	Dimension lbl2_size = new Dimension(200, 20);
    	Dimension lbl3_size = new Dimension(200, 20);
    	Dimension lbl4_size = new Dimension(200, 20);
    	Dimension lbl5_size = new Dimension(200, 20);
    	Dimension console_size = new Dimension(400,300);
    	Dimension scrollpane_size = new Dimension(400, 300);
    	Dimension progress1_size = new Dimension(230, 10);
    	//coordinates
    	Point wind_poz = new Point(500, 500);
    	Point spinner1_poz = new Point(20, 50);
    	Point btn1_poz = new Point(150, 50);
    	Point lbl1_poz = new Point(270, 20);
    	Point lbl2_poz = new Point(270, 50);
    	Point lbl3_poz = new Point(270, 80);
    	Point lbl4_poz = new Point(20, 20);
    	Point lbl5_poz = new Point(310, 406);
    	Point scrollpane_poz = new Point(20, 110);
    	Point progress1_poz = new Point(20, 90);
    	       
        
        //spinner 1
		model = new SpinnerNumberModel(100, 0, 100000, 1);
        spinner1 = new JSpinner(model);
        spinner1.setName("spinner1");
        spinner1.setLocation(spinner1_poz);
        spinner1.setSize(spinner1_size);        
        spinner1.setAlignmentX(JTextField.CENTER_ALIGNMENT);
        spinner1.setAlignmentY(JTextField.CENTER_ALIGNMENT);
        
        //label 1
        lbl1 = new JLabel();
        lbl1.setName("lbl1");
        lbl1.setText("|A| = ?");
        lbl1.setLocation(lbl1_poz);
        lbl1.setSize(lbl1_size);
        
        //label 2
        lbl2 = new JLabel();
        lbl2.setName("lbl2");
        lbl2.setText("|P| = ?");
        lbl2.setLocation(lbl2_poz);
        lbl2.setSize(lbl2_size);
        
        //label 3
        lbl3 = new JLabel();
        lbl3.setName("lbl3");
        lbl3.setText("max(P) = ?");
        lbl3.setLocation(lbl3_poz);
        lbl3.setSize(lbl3_size);
        lbl3.setVisible(true);
        
        //label 4
        lbl4 = new JLabel();
        lbl4.setName("lbl4");
        lbl4.setText("Ready");
        lbl4.setLocation(lbl4_poz);
        lbl4.setSize(lbl4_size);
        lbl4.setVisible(true);
        
      //label 5
        lbl5 = new JLabel();
        lbl5.setName("lbl5");
        lbl5.setText("Viktor Torma, 2018");
        lbl5.setLocation(lbl5_poz);
        lbl5.setSize(lbl5_size);
        lbl5.setVisible(true);
        
        //progress bar 1
        progress1 = new JProgressBar();
        progress1.setName("progress1");
        progress1.setLocation(progress1_poz);
        progress1.setSize(progress1_size);
        progress1.setIndeterminate(false);
        
        
        //console
        myConsole = new JTextArea();
        myConsole.setText("Console output\n");
        myConsole.setName("myConsole");
        myConsole.setSize(console_size);
        myConsole.setVisible(true);
        myConsole.setForeground(Color.GREEN);
        myConsole.setBackground(Color.BLACK);
        myConsole.setAutoscrolls(true);
        myConsole.setLineWrap(true);
	        //wrap the console into a scroll pane
	        scrollpane1 = new JScrollPane(myConsole);
	        scrollpane1.setName("scrollpane1");
	        scrollpane1.setLocation(scrollpane_poz);
	        scrollpane1.setSize(scrollpane_size);
	        
        
      //button 1
    	btn1 = new JButton();
    	btn1.setName("btn1");
        btn1.setText("Get primes");
        btn1.setLocation(btn1_poz);
        btn1.setSize(btn1_size);  	
		btn1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {	
				onClick();		
			}
		});
        
        
        //main panel
        panel1 = new JPanel();
        panel1.setName("panel1");
        panel1.setSize(panel1_size);
        panel1.add(lbl1);
        panel1.add(lbl2);
        panel1.add(lbl3);
        panel1.add(lbl4);
        panel1.add(lbl5);
        panel1.add(btn1);
        panel1.add(spinner1);
        panel1.add(scrollpane1);
        panel1.add(progress1);
        panel1.setVisible(true);
        panel1.setLayout(null);
        
        //main window
        mainWindow = new JFrame();
        mainWindow.setTitle("Composite Theory V1.0");
        mainWindow.setMinimumSize(window_size);
        mainWindow.setSize(window_size);  
        mainWindow.setLocation(wind_poz);
        mainWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainWindow.setLayout(null);
        mainWindow.setVisible(true);
        mainWindow.setResizable(false);
        mainWindow.add(panel1);
        mainWindow.setContentPane(panel1);
        
            
    }
    
    
    //SWING WORKER
    private void onClick() {
    	
    	
    	MySwingWorker<Void, Integer> worker = new MySwingWorker<Void, Integer>(){
    		
    		
    		PrimeCalculator P = new PrimeCalculator(panel1, myConsole, this);
    		
    		
    		int MyLimit = (int) spinner1.getValue();
    		
			int MaxSteps = PrimeCalculator.getSteps(MyLimit);
    		
    		@Override
    		protected Void doInBackground() throws Exception {
    			
    			lbl1.setText("|A| = calculating...");
    			lbl2.setText("|P| = calculating...");
    			lbl3.setText("max(P) = calculating...");
    			
    			progress1.setIndeterminate(true);
    			P.getPrimes();
    			return null;
    			
    		}

			
    		
    		@Override
			protected void process(List<Integer> chunks) {
				// TODO Auto-generated method stub
				Integer CurrentStep = chunks.get(chunks.size() - 1);
				lbl4.setText("Steps: " + CurrentStep + " / " + MaxSteps );
    		}



			@Override
			protected void done() {
				// TODO Auto-generated method stub
				System.out.println("\nDONE!");
				lbl4.setText("DONE");
				progress1.setIndeterminate(false);
				
			}
    		
    		
    		
    	};
    	
    	worker.execute();
    	
    }
    
    
    
}