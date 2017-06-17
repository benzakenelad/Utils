package Trees;

import java.util.Comparator;

public class BST<T> implements Tree<T> {

	private Node<T> root;
	private Comparator<T> comp;

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
			recursivelyPrint(root);
	}

	private void recursivelyPrint(Node<T> node) {
		if (node == null)
			return;

		recursivelyPrint(node.getLeftNode());
		System.out.println(node.getValue());
		recursivelyPrint(node.getRightNode());
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
