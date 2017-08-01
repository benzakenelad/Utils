package ThreadPool;

public class MyFuture<V> {
	V value;

	public synchronized void set(V value) {
		this.value = value;
		notifyAll();
	}
	
	// Guarded Suspension + Double Check Lock
	public V get() {
		if (value == null) {
			synchronized (this) {
				try {
					if (value == null)
						wait();
				} catch (Exception e) {
				}
			}
		}
		return value;
	}
}
