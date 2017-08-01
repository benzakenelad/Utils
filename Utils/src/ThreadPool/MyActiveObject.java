package ThreadPool;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * 
 * @author Elad Ben Zaken
 *
 */
public class MyActiveObject { 
	private LinkedBlockingQueue<MyRunnable> dispatchQueue;
	private Thread thread;
	private volatile boolean stop = false;

	public MyActiveObject() {
		dispatchQueue = new LinkedBlockingQueue<MyRunnable>();
	}

	public void run(MyRunnable runnable) {
		dispatchQueue.add(runnable);
	}

	public void start() {
		thread = new Thread(new Runnable() {

			@Override
			public void run() {
				while (!stop)
					try {
						dispatchQueue.take().run();
					} catch (Exception e) {
					}
			}
		});
		thread.start();
	}

	public void stop() {
		stop = true;
		if (thread != null)
			thread.interrupt();
		thread = null;
	}

	public int numOfActiveTasks() {
		return dispatchQueue.size();
	}
}
