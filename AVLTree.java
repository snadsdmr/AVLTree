package AVL_Tree;

public class AVLTree {
	// Node class representing a node in the AVL tree
	class Node {
		int key, height;
		Node left, right;

		Node(int d) {
			key = d;
			height = 1;
		}
	}

	Node root;
	// Function to get the height of a node
	int height(Node N) {
		if (N == null)
			return 0;
		return N.height;
	}
	// Function to get the maximum of two integers
	int max(int a, int b) {
		return (a > b) ? a : b;
	}
	// Function to perform a right rotation
	Node rightRotate(Node y) {
		Node x = y.left;
		Node T2 = x.right;
		
		// Perform rotation
		x.right = y;
		y.left = T2;
		
		// Update heights
		y.height = max(height(y.left), height(y.right)) + 1;
		x.height = max(height(x.left), height(x.right)) + 1;
		
		 // Return new root
		return x;
	}
	
	  // Function to perform a left rotation
	Node leftRotate(Node x) {
		Node y = x.right;
		Node T2 = y.left;
         
		 // Perform rotation
		y.left = x;
		x.right = T2;
      
		 // Update heights
		x.height = max(height(x.left), height(x.right)) + 1;
		y.height = max(height(y.left), height(y.right)) + 1;
		
		// Return new root
		return y;
	}
	
	// Function to get the balance factor of a node
	int getBalance(Node N) {
		if (N == null)
			return 0;
		return height(N.left) - height(N.right);
	}
	// Function to insert a key into the AVL tree
	Node insert(Node node, int key) {
		if (node == null)
			return (new Node(key));

		if (key < node.key)
			node.left = insert(node.left, key);
		else if (key > node.key)
			node.right = insert(node.right, key);
		else
			return node;

		node.height = 1 + max(height(node.left), height(node.right));

		int balance = getBalance(node);
		 // Left Left Condition 
		if (balance > 1 && key < node.left.key)
			return rightRotate(node);
		// Right Right Conditon
		if (balance < -1 && key > node.right.key)
			return leftRotate(node);
		 // Left Right Condition
		if (balance > 1 && key > node.left.key) {
			node.left = leftRotate(node.left);
			return rightRotate(node);
		}
		// Right Left Condition
		if (balance < -1 && key < node.right.key) {
			node.right = rightRotate(node.right);
			return leftRotate(node);
		}
		// Return the unchanged node
		return node;
	}
	
	 // Function to find the node with the minimum value in a subtree rooted at node
	Node minValueNode(Node node) {
		Node current = node;
		// Find the leftmost leaf node
		while (current.left != null)
			current = current.left;
		return current;
	}
	// Function to delete a node with the given key from the AVL tree
	Node deleteNode(Node root, int key) {
		 // Perform standard BST delete
		if (root == null)
			return root;
		// If the key is smaller than the root's key, it lies in the left subtree
		if (key < root.key)
			root.left = deleteNode(root.left, key);
		 // If the key is greater than the root's key, it lies in the right subtree
		else if (key > root.key)
			root.right = deleteNode(root.right, key);
		// If the key is the same as the root's key, this is the node to be deleted
		else {
			 // Node with only one child or no child
			if ((root.left == null) || (root.right == null)) {
				Node temp;
				if (root.left != null)
					temp = root.left;
				else
					temp = root.right;
				 // No child case
				if (temp == null) {
					temp = root;
					root = null;
				} else // One child case
					root = temp;
			} else {
				 // Node with two children 
				Node temp = minValueNode(root.right);
				root.key = temp.key;
				root.right = deleteNode(root.right, temp.key);
			}
		}
		 // If the tree had only one node, return
		if (root == null)
			return root;
		 // Update the height of the current node
		root.height = max(height(root.left), height(root.right)) + 1;

		int balance = getBalance(root);
		// If the node becomes unbalanced, perform rotations
		if (balance > 1 && getBalance(root.left) >= 0)
			return rightRotate(root);

		if (balance < -1 && getBalance(root.right) <= 0)
			return leftRotate(root);

		if (balance > 1 && getBalance(root.left) >= 0) {
			root.left = leftRotate(root.left);
			return rightRotate(root);
		}

		if (balance < -1 && getBalance(root.right) > 0) {
			root.right = rightRotate(root.right);
			return leftRotate(root);
		}
		// Return the unchanged node
		return root;
	}
	 // Function to perform the pre-order traversal of the AVL tree
	void preOrder(Node node) {
		if (node != null) {
			System.out.print(node.key + " ");
			preOrder(node.left);
			preOrder(node.right);
		}
	}

	public static void main(String[] args) {
		AVLTree tree = new AVLTree();
		tree.root = tree.insert(tree.root, 10);
		tree.root = tree.insert(tree.root, 20);
		tree.root = tree.insert(tree.root, 30);
		tree.root = tree.insert(tree.root, 40);
		tree.root = tree.insert(tree.root, 50);
		tree.root = tree.insert(tree.root, 25);

		System.out.println("Preorder traversal of constructed AVL tree is:");
		tree.preOrder(tree.root);

		tree.root = tree.deleteNode(tree.root, 30);

		System.out.println("\nPreorder traversal after deletion of 30:");
		tree.preOrder(tree.root);
	}}
