package List;

public class LinkedListt<T> implements Listt<T> {

	private Node<T> firstNode;
	private Node<T> lastNode;
	private int size = 0;

	@Override
	public void add(T obj) {
		if (obj == null)
			return;
		if (size == 0) {
			firstNode = new Node<T>(obj);
			size++;
			lastNode = firstNode;
			return;
		}
		Node<T> newNode = new Node<T>(obj);
		newNode.setNext(firstNode);
		firstNode.setPrev(newNode);
		firstNode = newNode;
		size++;
	}

	@Override
	public void add(T obj, int index) {
		if(obj == null)
			return;
		if (index > this.size - 1 || index < 0)
			return;
		Node<T> newNode = new Node<T>(obj);
		Node<T> currentNode = firstNode;
		for (int i = 0; i < index; i++)
			currentNode = currentNode.getNext();
		
		Node<T> prev = currentNode.getPrev();
		newNode.setNext(currentNode);
		newNode.setPrev(prev);
		currentNode.setPrev(newNode);
		if(prev != null)
			prev.setNext(newNode);
		else
			firstNode = newNode;
		size++;
	}
	
	@Override
	public T remove(int index) {
		if (index > this.size - 1 || index < 0)
			return null;
		Node<T> currentNode = firstNode;
		for (int i = 0; i < index; i++)
			currentNode = currentNode.getNext();
		Node<T> next = currentNode.getNext();
		Node<T> prev = currentNode.getPrev();
		if (next != null)
			next.setPrev(prev);
		else
			lastNode = prev;
		if (prev != null)
			prev.setNext(next);
		else
			firstNode = next;
		size--;
		
		return currentNode.getValue();
	}

	@Override
	public int getSize() {
		return this.size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public void removeAll() {
		this.firstNode = null;
		this.lastNode = null;
		this.size = 0;
	}

	@Override
	public void forEach(Consumerr<T> consumer) {
		Node<T> currentNode = firstNode;
		for (int i = 0; i < size; i++) {
			consumer.action(currentNode.getValue());
			currentNode = currentNode.getNext();
		}
	}

	public T getLast() {
		return lastNode.getValue();
	}
	
	public T removeLast(){
		if(size == 0)
			return null;
		Node<T> prev = lastNode.getPrev();
		if(prev != null)
			prev.setNext(null);
		else
			firstNode = null;
		T returnedElement = lastNode.getValue();
		lastNode = prev;
		size--;
		return returnedElement;
	}
	
	public T removeFirst(){
		if(size == 0)
			return null;
		Node<T> next = firstNode.getNext();
		if(next != null)
			next.setPrev(null);
		else
			lastNode = null;
		T returnedElement = firstNode.getValue();
		firstNode = next;
		size--;
		return returnedElement;
	}
	
	public T getFirst() {
		return firstNode.getValue();
	}

	@Override
	public T get(int index) {
		if (index > this.size - 1 || index < 0)
			return null;
		Node<T> currentNode = firstNode;
		for (int i = 0; i < index; i++)
			currentNode = currentNode.getNext();

		return currentNode.getValue();
	}



}
