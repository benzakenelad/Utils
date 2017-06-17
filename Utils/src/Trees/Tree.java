package Trees;

public interface Tree <T>{
	void insert(T obj); 
	boolean remove(T obj);
	boolean search(T key);
	void sortedPrint();
	T findMax();
	T findMin();
	T findSuccesor(T obj);
}
