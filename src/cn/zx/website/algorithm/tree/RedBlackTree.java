package cn.zx.website.algorithm.tree;

public class RedBlackTree {
	public class Node {
		public double key;
		public Node left;
		public Node right;
		public Node parent;
		public Color color;

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
			if (color != null) {
				sb.append(", color=" + color.toString());
			}
			sb.append("]");
			return sb.toString();
		}
	}

	public enum Color {
		RED, BLACK;
	}

	protected Node root;

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

	private void leftRotate(double key) {
		Node x = search(key);
		Node y = x.right;
		x.right = y.left;
		if (y.left != null) {
			y.left.parent = x;
		}
		y.parent = x.parent;
		if (x.parent == null) {
			root = y;
		} else if (x == x.parent.left) {
			x.parent.left = y;
		} else {
			x.parent.right = y;
		}
		y.left = x;
		x.parent = y;
	}

	private void rightRotate(double key) {
		Node x = search(key);
		Node y = x.left;
		x.left = y.right;
		if (y.right != null) {
			y.right.parent = x;
		}
		y.parent = x.parent;
		if (x.parent == null) {
			root = y;
		} else if (x == x.parent.right) {
			x.parent.right = y;
		} else {
			x.parent.left = y;
		}
		y.right = x;
		x.parent = y;
	}

	public void insert(double key) {
		// TODO Auto-generated method stub

	}

	public void delete(double key) {
		// TODO Auto-generated method stub

	}
}