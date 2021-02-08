package compositeTheory;

import javax.swing.SwingWorker;

public abstract class MySwingWorker<T, V> extends SwingWorker<T, V> {

	public void MyPublish(V args) {
		publish(args);
	}
	
	

}
