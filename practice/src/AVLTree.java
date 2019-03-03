public class AVLTree {
    public AVLNode root;

    public AVLTree() {
        root = null;
    }

    private class AVLNode {
        int key;
        int weight;
        int height;
        int size;
        AVLNode left;
        AVLNode right;
        AVLNode(int key) {
            this.key = key;
            this.weight = 1;
            this.height = 1;
            this.size = 1;
            this.left = null;
            this.right = null;
        }
    }

    private int max(int a, int b) {
        return a > b ? a : b;
    }

    private int size(AVLNode tree) {
        if (tree == null) {
            return 0;
        }
        return tree.size;
    }

    private int height(AVLNode tree) {
        if (tree == null) {
            return 0;
        }
        return tree.height;
    }

    private AVLNode rotateRight(AVLNode node) {
        AVLNode left = node.left;
        node.left = left.right;
        left.right = node;

        node.size = size(node.left) + size(node.right) + node.weight;
        node.height = max(height(node.left), height(node.right)) + 1;

        left.size = size(left.left) + size(node) + left.weight;
        left.height = max(height(left.left), height(node)) + 1;
        return left;
    }

    private AVLNode rotateLeft(AVLNode node) {
        AVLNode right = node.right;
        node.right = right.left;
        right.left = node;

        node.size = size(node.left) + size(node.right) + node.weight;
        node.height = max(height(node.left), height(node.right)) + 1;

        right.size = size(node) + size(right.right) + right.weight;
        right.height = max(height(node), height(right.right)) + 1;
        return right;
    }

    private AVLNode balance(AVLNode node) {
        if (height(node.right) - height(node.left) == 2) {
            AVLNode right = node.right;
            if (height(right.right) > height(right.left)) {
                return rotateLeft(node);
            } else {
                node.right = rotateRight(right);
                return rotateLeft(node);
            }
        } else if (height(node.left) - height(node.right) == 2) {
            AVLNode left = node.left;
            if (height(left.left) > height(left.right)) {
                return rotateRight(node);
            } else {
                node.left = rotateLeft(left);
                return rotateRight(node);
            }
        } else {
            return node;
        }
    }

    public void insert(int key) {
        root = insert(key, root);
    }

    private AVLNode insert(int weight, AVLNode node) {
        if (node == null) {
            return new AVLNode(weight);
        } else if (weight == node.key) {
            node.weight++;
            node.size++;
            return node;
        } else if (weight > node.key) {
            node.size++;
            node.right = insert(weight, node.right);
            node.height = max(height(node.left), height(node.right)) + 1;
            return balance(node);
        } else {
            node.size++;
            node.left = insert(weight, node.left);
            node.height = max(height(node.left), height(node.right)) + 1;
            return balance(node);
        }
    }

    private int findMin(AVLNode node) {
        if (node.left == null) {
            return node.key;
        }
        return findMin(node.left);
    }

    public void remove(int key) {
        root = remove(key, root);
    }

    private AVLNode remove(int key, AVLNode node) {
        if (node == null) {
            return null;
        }
        if (key == node.key) {
            if (node.weight > 1) {
                node.weight--;
                node.size--;
                return node;
            } else if (node.left == null || node.right == null) {
                return node.left == null ? node.right : node.left;
            } else {
                int min = findMin(node.right);
                node.key = min;
                node.right = remove(min, node.right);
                node.size--;
                node.height = max(height(node.left), height(node.right)) + 1;
                return balance(node);
            }
        } else if (key < node.key) {
            node.size--;
            node.left = remove(key, node.left);
            node.height = max(height(node.left), height(node.right)) + 1;
            return balance(node);
        } else {
            node.size--;
            node.right = remove(key, node.right);
            node.height = max(height(node.left), height(node.right)) + 1;
            return balance(node);
        }
    }

    public int findBigger(int key) {
        return findBigger(key, root);
    }

    private int findBigger(int weight, AVLNode node) {
        if(node == null) {
            return 0;
        } else if (node.key == weight) {
            return node.size - size(node.left);
        } else if (node.key > weight) {
            return node.size - size(node.left) + findBigger(weight, node.left);
        } else {
            return findBigger(weight, node.right);
        }
    }

    public int findSmaller(int key) {
        return findSmaller(key, root);
    }

    private int findSmaller(int weight, AVLNode node) {
        if(node == null) {
            return 0;
        } else if (node.key == weight) {
            return node.size - size(node.right);
        } else if (node.key < weight) {
            return node.size - size(node.right) + findSmaller(weight, node.right);
        } else {
            return findSmaller(weight, node.left);
        }
    }
}