package ThreadPool;

import java.util.ArrayList;

/**
 * 
 * @author Elad Ben Zaken
 *
 */
public class MyThreadPool {
	private int maxThreads;
	private ArrayList<MyActiveObject> pool;

	public MyThreadPool(int maxThreads) {
		this.maxThreads = maxThreads;
		pool = new ArrayList<MyActiveObject>(maxThreads);
	}

	// until pool size is smaller then maxThreads we create new MyActiveObject
	// and add him to the pool
	// otherwise we add the new Task to the least busy MyActiveObject
	public void execute(MyRunnable runnable) {
		if (pool.size() < maxThreads) {
			MyActiveObject activeObject = new MyActiveObject();
			activeObject.run(runnable);
			activeObject.start();
			pool.add(activeObject);
			return;
		}
		int poolSize = pool.size();
		int leastBusyTaskNumber = Integer.MAX_VALUE;
		MyActiveObject leastBusyActiveObject = null;
		for (int i = 0; i < poolSize; i++) {
			MyActiveObject tempActiveObject = pool.get(i);
			int taskNum = tempActiveObject.numOfActiveTasks();
			if (taskNum < leastBusyTaskNumber) {
				leastBusyTaskNumber = taskNum;
				leastBusyActiveObject = tempActiveObject;
			}
		}
		if (leastBusyActiveObject != null)
			leastBusyActiveObject.run(runnable);
	}

	public <V> MyFuture<V> submit(MyCallable<V> callable) {
		MyFuture<V> future = new MyFuture<V>();
		execute(() -> future.set(callable.call()));
		return future;
	}

	public void shutdown(){
		for(MyActiveObject ao: pool)
			ao.stop();	
	}
	
	public int getNumOfActiveThreads() {
		return pool.size();
	}
}
