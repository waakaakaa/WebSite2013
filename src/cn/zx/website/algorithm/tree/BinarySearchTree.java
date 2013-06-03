package cn.zx.website.algorithm.tree;

public class BinarySearchTree {
	public class Node {
		public double key;
		public Node left;
		public Node right;
		public Node parent;

		public Node(double key) {
			this.key = key;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("Node [key=" + key);
			if (left != null) {
				sb.append(", left=" + left.key);
			}
			if (right != null) {
				sb.append(", right=" + right.key);
			}
			if (parent != null) {
				sb.append(", parent=" + parent.key);
			}
			sb.append("]");
			return sb.toString();
		}
	}

	private Node root;

	public void insert(double key) {
		Node z = new Node(key);
		Node y = null;
		Node x = root;
		while (x != null) {
			y = x;
			if (z.key < x.key) {
				x = x.left;
			} else {
				x = x.right;
			}
		}
		z.parent = y;
		if (y == null) {
			root = z;
		} else if (z.key < y.key) {
			y.left = z;
		} else {
			y.right = z;
		}
	}

	public void inorderWalk() {
		inorderWalk(root);
	}

	private void inorderWalk(Node x) {
		if (x != null) {
			inorderWalk(x.left);
			// System.out.println(x.key);
			System.out.println(x);
			inorderWalk(x.right);
		}
	}

	public Node search(double k) {
		return search(root, k);
	}

	private Node search(Node x, double k) {
		if (x == null || k == x.key) {
			return x;
		}
		if (k < x.key) {
			return search(x.left, k);
		} else {
			return search(x.right, k);
		}
	}

	public Node iterativeSearch(double k) {
		return iterativeSearch(root, k);
	}

	private Node iterativeSearch(Node x, double k) {
		while (x != null && k != x.key) {
			if (k < x.key) {
				x = x.left;
			} else {
				x = x.right;
			}
		}
		return x;
	}

	public double minimum() {
		return minimum(root).key;
	}

	private Node minimum(Node x) {
		while (x.left != null) {
			x = x.left;
		}
		return x;
	}

	public double maximum() {
		return maximum(root).key;
	}

	private Node maximum(Node x) {
		while (x.right != null) {
			x = x.right;
		}
		return x;
	}

	public void delete(double key) {
		Node z = iterativeSearch(key);
		Node y = null;
		Node x = null;
		if (z.left == null || z.right == null) {
			y = z;
		} else {
			y = successor(z);
		}
		if (y.left != null) {
			x = y.left;
		} else {
			x = y.right;
		}
		if (x != null) {
			x.parent = y.parent;
		}
		if (y.parent == null) {
			root = x;
		} else if (y == y.parent.left) {
			y.parent.left = x;
		} else {
			y.parent.right = x;
		}
		if (y != z) {
			z.key = y.key;

		}
	}

	public Node successor(Node x) {
		if (x.right != null) {
			return minimum(x.right);
		}
		Node y = x.parent;
		while (y != null && x == y.right) {
			x = y;
			y = y.parent;
		}
		return y;
	}

	public static void main(String[] args) {
		BinarySearchTree bst = new BinarySearchTree();
		bst.insert(1);
		bst.insert(10);
		bst.insert(4);
		bst.insert(3);
		bst.insert(8);
		bst.insert(2);
		bst.insert(11);
		bst.insert(100);
		bst.inorderWalk();
		// System.out.println("-----------------");
		// BinarySearchTree.Node node1 = bst.search(100);
		// System.out.println(node1.key);
		// BinarySearchTree.Node node2 = bst.iterativeSearch(100);
		// System.out.println(node2.key);
		// System.out.println("-----------------");
		// System.out.println(bst.minimum());
		// System.out.println(bst.maximum());
		System.out.println("-----------------");
		System.out.println(bst.successor(bst.search(4)).key);
		System.out.println("-----------------");
		bst.delete(4);
		bst.inorderWalk();
	}
}