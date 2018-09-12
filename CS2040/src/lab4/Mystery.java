package lab4;
/*
 * Name       : 
 * Matric No. :
 * Plab Acct. :
 */
import java.util.*;


public class Mystery {
    private void run() {
    	Scanner input = new Scanner(System.in);
        int numOfOp = input.nextInt();
        AVLTree tree = new AVLTree();
        for(int i = 0; i < numOfOp; i++){
        	String op = input.next();
        	switch (op){
        	    case "PUSH" :
        	    	tree.insert(input.nextInt());
        	    	break;
        	    case "POP":
        	    	tree.pop();
        	    	break;
        	}
        }
        tree.inOrder();
    }
    public static void main(String[] args) {
        Mystery aMystery = new Mystery();
        aMystery.run();
    }
}
class AVLTree {
    private AVLTreeNode mRoot;
    private AVLTreeNode max;
    class AVLTreeNode {
    	int size; 
    	int weight;
        int key;               
        int height;       
        AVLTreeNode left;   
        AVLTreeNode right;   

        public AVLTreeNode(int key, AVLTreeNode left, AVLTreeNode right) {
            this.size = 1;
            this.weight = 1;
        	this.key = key;
            this.left = left;
            this.right = right;
            this.height = 0;
        }
    }

    public AVLTree() {
        mRoot = null;
    }

    private int height(AVLTreeNode tree) {
        if (tree != null)
            return tree.height;
        return 0;
    }

    private int size(AVLTreeNode tree) {
        if (tree != null)
            return tree.size;
        return 0;
    }

    
    private int max(int a, int b) {
        return  a > b ? a : b;
    }

    private void inOrder(AVLTreeNode tree, AVLTreeNode max) {
        if(tree != null)
        {
            inOrder(tree.left, max);
            if(tree != max){
	            for(int i = 0; i < tree.weight; i++){
	            	System.out.print(tree.key + " ");
	            }
            } else {
            	for(int i = 0; i < tree.weight - 1; i++){
	            	System.out.print(tree.key + " ");
	            }
            	System.out.print(tree.key);
            }
            inOrder(tree.right, max);
        }
    }

    public void inOrder() {
        inOrder(mRoot, maximum(mRoot));
        System.out.println("");
    }
    
    
    public void pop(){
    	popKth(mRoot, (size(mRoot) + 1)/2);
    }
    
    private void popKth(AVLTreeNode x, int k) {
    	if(size(x.left) >= k){
    		popKth(x.left, k);
    	} else {
    		if(size(x.left) + x.weight >= k){
    			mRoot = remove(mRoot, x);
    		} else {
    			popKth(x.right, k - size(x.left) - x.weight);	
    		}
    	}
    }
    
    private AVLTreeNode leftLeftRotation(AVLTreeNode k2) {
        AVLTreeNode k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;

        k2.height = max( height(k2.left), height(k2.right)) + 1;
        k1.height = max( height(k1.left), k2.height) + 1;
        
        k2.size = size(k2.left) + size(k2.right) + k2.weight;
        k1.size = size(k1.left) + size(k1.right) + k1.weight;
        return k1;
    }


    private AVLTreeNode rightRightRotation(AVLTreeNode k1) {
        AVLTreeNode k2 = k1.right;
        k1.right = k2.left;
        k2.left = k1;

        k1.height = max( height(k1.left), height(k1.right)) + 1;
        k2.height = max( height(k2.right), k1.height) + 1;
        
        k2.size = size(k2.left) + size(k2.right) + k2.weight;
        k1.size = size(k1.left) + size(k1.right) + k1.weight;
        return k2;
    }

    private AVLTreeNode leftRightRotation(AVLTreeNode k3) {
        k3.left = rightRightRotation(k3.left);
        return leftLeftRotation(k3);
    }

    private AVLTreeNode rightLeftRotation(AVLTreeNode k1) {
        k1.right = leftLeftRotation(k1.right);
        return rightRightRotation(k1);
    }

    private AVLTreeNode insert(AVLTreeNode tree, int key) {
        if (tree == null) {
            tree = new AVLTreeNode(key, null, null);
        } else {
            int cmp = key - tree.key;
               if (cmp < 0) {    
                tree.left = insert(tree.left, key);
                if (height(tree.left) - height(tree.right) == 2) {
                    if (key - tree.left.key < 0)
                        tree = leftLeftRotation(tree);
                    else
                        tree = leftRightRotation(tree);
                }
            } else if (cmp > 0) {   
                tree.right = insert(tree.right, key);
                if (height(tree.right) - height(tree.left) == 2) {
                    if (key - tree.right.key > 0)
                        tree = rightRightRotation(tree);
                    else
                        tree = rightLeftRotation(tree);
                }
            } else {
                tree.weight ++;
            }
        }
        tree.size  = size(tree.left) + size(tree.right) + tree.weight;

        tree.height = max( height(tree.left), height(tree.right)) + 1;

        return tree;
    }

    public void insert(int key) {
        mRoot = insert(mRoot, key);
    }

    private AVLTreeNode remove(AVLTreeNode tree, AVLTreeNode z) {
        if (tree==null || z==null)
            return null;

        int cmp = z.key - tree.key;
        if (cmp < 0) {       
            tree.left = remove(tree.left, z);
            if (height(tree.right) - height(tree.left) == 2) {
                AVLTreeNode r =  tree.right;
                if (height(r.left) > height(r.right))
                    tree = rightLeftRotation(tree);
                else
                    tree = rightRightRotation(tree);
            }
        } else if (cmp > 0) { 
            tree.right = remove(tree.right, z);
            if (height(tree.left) - height(tree.right) == 2) {
                AVLTreeNode l =  tree.left;
                if (height(l.right) > height(l.left))
                    tree = leftRightRotation(tree);
                else
                    tree = leftLeftRotation(tree);
            }
        } else { 
        	if(tree.weight > 1){
        		tree.weight --;
        	} else if ((tree.left!=null) && (tree.right!=null)) {
                if (height(tree.left) > height(tree.right)) {
                    
                    AVLTreeNode max = maximum(tree.left);
                    tree.key = max.key;
                    tree.left = remove(tree.left, max);
                } else {
                    AVLTreeNode min = minimum(tree.right);
                    tree.key = min.key;
                    tree.right = remove(tree.right, min);
                }
            } else {
                tree = (tree.left!=null) ? tree.left : tree.right;
            }
        }
        if(tree == null){
        	return tree;
        }
        tree.size = size(tree.left) + size(tree.right) + tree.weight;
        tree.height = max(height(tree.left), height(tree.right)) + 1;
        return tree;
    }
    
    private AVLTreeNode maximum(AVLTreeNode tree) {
        if (tree == null)
            return null;

        while(tree.right != null)
            tree = tree.right;
        return tree;
    }
    
    private AVLTreeNode minimum(AVLTreeNode tree) {
        if (tree == null)
            return null;

        while(tree.left != null)
            tree = tree.left;
        return tree;
    }

}