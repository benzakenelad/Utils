package List;

public interface Listt<T> {
	void add(T obj);
	void add(T obj, int index);
	T remove(int index);
	int getSize();
	T get(int index);	
	boolean isEmpty();
	void removeAll();
	void forEach(Consumerr<T> consumer);
	
	interface Consumerr<T> {
		void action(T obj);
	}
}
