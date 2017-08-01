package Trees;

import java.util.Comparator;
import java.util.Stack;

public class BST<T> implements Tree<T> {

	private Node<T> root;
	private Comparator<T> comp;

	public Node<T> getRoot(){
		return root;
	}
	
	public BST(Comparator<T> comp) {
		this.comp = comp;
	}

	@Override
	public void insert(T obj) {

		if (obj == null)
			return;

		if (root == null) {
			root = new Node<T>(obj);
			return;
		}

		Node<T> newNode = new Node<T>(obj);
		Node<T> currentNode = root;

		while (true) {
			if (comp.compare(obj, currentNode.getValue()) > 0) {
				if (currentNode.getRightNode() == null) {
					currentNode.setRightNode(newNode);
					newNode.setParent(currentNode);
					break;
				} else
					currentNode = currentNode.getRightNode();
			} else if (comp.compare(obj, currentNode.getValue()) < 0) {
				if (currentNode.getLeftNode() == null) {
					currentNode.setLeftNode(newNode);
					newNode.setParent(currentNode);
					break;
				} else
					currentNode = currentNode.getLeftNode();
			}
			if (comp.compare(obj, currentNode.getValue()) == 0)
				return;
		}

	}

	@Override
	public boolean search(T obj) {
		if (obj == null)
			return false;

		if (searchNode(obj) != null)
			return true;
		else
			return false;
	}

	private Node<T> searchNode(T obj) {
		Node<T> currentNode = root;

		while (currentNode != null) {
			if (comp.compare(obj, currentNode.getValue()) == 0) {
				return currentNode;
			} else if (comp.compare(obj, currentNode.getValue()) > 0) {
				currentNode = currentNode.getRightNode();
			} else
				currentNode = currentNode.getLeftNode();
		}
		return null;
	}

	@Override
	public void sortedPrint() {
		if (root != null)
			iterativePrint(root);
	}
	
	private void iterativePrint(Node<T> node){
		if(node == null)
			return;
		Stack<Node<T>> stack = new Stack<Node<T>>();
		stack.push(node);
		while(!stack.isEmpty()){
			if(node != null){
				node = node.getLeftNode();
				if(node != null)
					stack.push(node);
				continue;
			}
			node = stack.pop();
			System.out.println(node.getValue());
			node = node.getRightNode();
			if(node != null)
				stack.push(node);	
		}
	}

	@SuppressWarnings("unused")
	private void recursivelyPrint(Node<T> node) {
		if (node == null)
			return;

		recursivelyPrint(node.getLeftNode());
		System.out.println(node.getValue());
		recursivelyPrint(node.getRightNode());
	}
	
	public void recursivePostOrder(Node<T> node){
		if (node == null)
			return;

		recursivePostOrder(node.getLeftNode());
		recursivePostOrder(node.getRightNode());
		System.out.println(node.getValue());
	}
	
	public void iterativePostOrder(Node<T> node){
		Stack<Node<T>> s1, s2;
		s1 = new Stack<Node<T>>();
		s2 = new Stack<Node<T>>();
		s1.push(node);
		Node<T> current = null;
		while(!s1.isEmpty()){
			current = s1.pop();
			if(current.getLeftNode() != null)
				s1.push(current.getLeftNode());
			if(current.getRightNode() != null)
				s1.push(current.getRightNode());
			s2.push(current);
		}
		while(!s2.isEmpty()){
			System.out.println(s2.pop());
		}
		
	}

	@Override
	public T findMax() {
		if (root == null)
			return null;

		return findMaximumByNode(root);
	}

	@Override
	public T findMin() {
		if (root == null)
			return null;

		return findMinimumByNode(root);
	}

	private T findMinimumByNode(Node<T> node) {
		Node<T> currentNode = node;
		while (true) {
			if (currentNode.getLeftNode() == null)
				return currentNode.getValue();
			else
				currentNode = currentNode.getLeftNode();
		}
	}

	private T findMaximumByNode(Node<T> node) {
		Node<T> currentNode = node;
		while (true) {
			if (currentNode.getRightNode() == null)
				return currentNode.getValue();
			else
				currentNode = currentNode.getRightNode();
		}
	}
	
	public void iterativePreOrder(Node<T> node){
		Stack<Node<T>> stack = new Stack<Node<T>>();
		stack.push(node);
		Node<T> current = null;
		while(!stack.isEmpty()){
			current = stack.pop();
			System.out.println(current);
			if(current.getRightNode() != null)
				stack.push(current.getRightNode());
			if(current.getLeftNode() != null)
				stack.push(current.getLeftNode());
		}			
	}

	@Override
	public T findSuccesor(T obj) {
		Node<T> objNode = searchNode(obj);
		if (objNode == null)
			return null;

		if (objNode.getRightNode() != null)
			return findMinimumByNode(objNode.getRightNode());
		else {
			Node<T> currentNode = objNode;
			Node<T> temp = objNode;
			while (true) {
				currentNode = currentNode.getParent();
				if (currentNode == null)
					return null;
				if (currentNode.getLeftNode() != null)
					if (comp.compare(temp.getValue(), currentNode.getLeftNode().getValue()) == 0) {
						return currentNode.getValue();
					}
				temp = currentNode;
			}

		}

	}

	@Override
	public boolean remove(T obj) {
		Node<T> objNode = searchNode(obj);
		if (obj == null)
			return false;

		if (objNode.getLeftNode() == null && objNode.getRightNode() == null) {
			Node<T> parent = objNode.getParent();
			if (parent != null)
				if (parent.getLeftNode()!= null && comp.compare(obj, parent.getLeftNode().getValue()) == 0)
					parent.setLeftNode(null);
				else
					parent.setRightNode(null);
			else
				root = null;
		} else if (objNode.getLeftNode() != null && objNode.getRightNode() != null) {
			T succesor = findSuccesor(obj);
			remove(succesor);
			objNode.setValue(succesor);
			if (comp.compare(succesor, objNode.getRightNode().getValue()) == 0) {
				objNode.setRightNode(null);
			} else if (comp.compare(succesor, objNode.getLeftNode().getValue()) == 0) {
				objNode.setLeftNode(null);
			}
		} else if (objNode.getLeftNode() != null) {
			Node<T> parent = objNode.getParent();
			Node<T> child = objNode.getLeftNode();
			child.setParent(parent);
			if (parent != null) {
				if (comp.compare(obj, parent.getLeftNode().getValue()) == 0)
					parent.setLeftNode(child);
				else
					parent.setRightNode(child);
			} else {
				this.root = child;
			}

		} else if (objNode.getRightNode() != null) {
			Node<T> parent = objNode.getParent();
			Node<T> child = objNode.getRightNode();
			child.setParent(parent);
			if (parent != null)
				if (comp.compare(obj, parent.getLeftNode().getValue()) == 0)
					parent.setLeftNode(child);
				else
					parent.setRightNode(child);
			else {
				this.root = child;
			}
		} else
			return false;

		return true;
	}

}
