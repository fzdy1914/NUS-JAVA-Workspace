package ass1.challengeTask;

import java.io.DataInputStream;
import java.io.IOException;

class CountingSwaps {

    // count number of swaps using divide and conquer
    private static long countSwaps(int L, int R, int[] W, int[] id, int[] temp, int D) {
        // if only one element, no swaps needed
        if (L == R) return 0;

        // split array into left and right subarray
        // recurse into each subarray
        int M = (L + R) / 2;
        long swaps = 0;
        int[] tempW = new int[temp.length];
        swaps += countSwaps(L, M, W, id, temp, D);
        swaps += countSwaps(M + 1, R, W, id, temp, D);

        AVLTree avl = new AVLTree();

        for(int i = 0; i < M - L + 1; i++) {
            avl.insert(W[L + i] );
        }

        // sort by choosing the minimum of both subarrays
        int index1 = L, index2 = M + 1, index3 = 0;
        while (index1 <= M && index2 <= R) {
            if (id[index1] <= id[index2]) {
                avl.remove(W[index1]);
                temp[index3] = id[index1];
                tempW[index3++] = W[index1++];
            } else {
                swaps += avl.findBigger(W[index2] + D + 1);
                swaps += avl.findSmaller(W[index2] - D - 1);
                temp[index3] = id[index2];
                tempW[index3++] = W[index2++];
            }
        }

        // add any remaining elements in left subarray
        while (index1 <= M) {
            temp[index3] = id[index1];
            tempW[index3++] = W[index1++];
        }

        // add any remaining elements in right subarray
        while (index2 <= R) {
            temp[index3] = id[index2];
            tempW[index3++] = W[index2++];
        }

        // transfer elements back into original array
        System.arraycopy(temp, 0, id, L, index3);
        System.arraycopy(tempW, 0, W, L, index3);

        return swaps;
    }

    public static void main(String[] args) throws IOException {
        Reader sc = new Reader();
        int N = sc.nextInt();
        int D = sc.nextInt();
        int W[] = new int[N];
        int id[] = new int[N];
        for (int i = 0; i < N; ++i) {
            W[i] = sc.nextInt();
        }
        for (int i = 0; i < N; ++i) {
            id[i] = sc.nextInt();
        }
        System.out.println(countSwaps(0, N - 1, W, id, new int[N], D));
    }

    private static class Reader {
        final private int BUFFER_SIZE = 1 << 16;
        private DataInputStream din;
        private byte[] buffer;
        private int bufferPointer, bytesRead;

        public Reader() {
            din = new DataInputStream(System.in);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public int nextInt() throws IOException {
            byte c;
            while ((c = read()) <= ' ');
            int v = 0;
            do { v = (v << 3) + (v << 1) + (c ^ '0'); }
            while ((c = read()) >= '0' && c <= '9');
            return v;
        }

        private void fillBuffer() throws IOException {
            bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
            if (bytesRead == -1) buffer[0] = -1;
        }

        private byte read() throws IOException {
            if (bufferPointer == bytesRead) fillBuffer();
            return buffer[bufferPointer++];
        }
    }
}

class AVLTree {
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

    private AVLNode insert(int key, AVLNode node) {
        if (node == null) {
            return new AVLNode(key);
        } else if (key == node.key) {
            node.weight++;
            node.size++;
            return node;
        } else if (key > node.key) {
            node.size++;
            node.right = insert(key, node.right);
            node.height = max(height(node.left), height(node.right)) + 1;
            return balance(node);
        } else {
            node.size++;
            node.left = insert(key, node.left);
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

    private int findBigger(int key, AVLNode node) {
        if(node == null) {
            return 0;
        } else if (node.key == key) {
            return node.size - size(node.left);
        } else if (node.key > key) {
            return node.size - size(node.left) + findBigger(key, node.left);
        } else {
            return findBigger(key, node.right);
        }
    }

    public int findSmaller(int key) {
        return findSmaller(key, root);
    }

    private int findSmaller(int key, AVLNode node) {
        if(node == null) {
            return 0;
        } else if (node.key == key) {
            return node.size - size(node.right);
        } else if (node.key < key) {
            return node.size - size(node.right) + findSmaller(key, node.right);
        } else {
            return findSmaller(key, node.left);
        }
    }
}

