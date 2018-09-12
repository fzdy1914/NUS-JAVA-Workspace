public class BinarySearchTree {  
    public Node root;  
      
    public Node find(int key){  
        if(root == null){  
            System.out.println("The tree is empty!");  
            return null;  
        }  
        Node current = root;  
        while(current.id != key){  
            if(key > current.id)  
                current = current.rightChild;  
            else  
                current = current.leftChild;  
            if(current == null)  
                return null;  
        }  
        return current;  
    }  
      
    public boolean insert(Node node){  
        if(root == null){  
            root = node;  
            return true;  
        }  
        //���в���������ظ���������  
        if(this.find(node.id) != null){  
            System.out.println("Node with id '" +  
                    node.id + "' has already existed!");  
            return false;  
        }  
        Node current = root;  
        while(current != null){  
            if(node.id > current.id){  
                if(current.rightChild == null){  
                    current.rightChild = node;  
                    return true;  
                }  
                current = current.rightChild;  
            }else{  
                if(current.leftChild == null){  
                    current.leftChild = node;  
                    return true;  
                }  
                current = current.leftChild;  
            }  
        }  
        return false;  
    }  
  
    public void preorder_iterator(Node node){  
        System.out.print(node.id + " ");  
        if(node.leftChild != null)  
            this.preorder_iterator(node.leftChild);  
        if(node.rightChild != null)  
            this.preorder_iterator(node.rightChild);  
    }  
      
    public void inorder_iterator(Node node){  
        if(node.leftChild != null)  
            this.inorder_iterator(node.leftChild);  
        System.out.print(node.id + " ");  
        if(node.rightChild != null)  
            this.inorder_iterator(node.rightChild);  
    }  

    public void postorder_iterator(Node node){  
        if(node.leftChild != null)  
            this.postorder_iterator(node.leftChild);  
        if(node.rightChild != null)   
            this.postorder_iterator(node.rightChild);  
        System.out.print(node.id + " ");  
    }  
      
    public Node getMinNode(Node node){  
        if(this.find(node.id) == null){  
            System.out.println("Node dosen't exist!");  
            return null;  
        }  
        if(node.leftChild == null)  
            return node;  
        Node current = node.leftChild;  
        while(current.leftChild != null)  
            current = current.leftChild;  
        return current;  
    }  
      
    public Node getMaxNode(Node node){  
        if(this.find(node.id) == null){  
            System.out.println("Node dosen't exist!");  
            return null;  
        }  
        if(node.rightChild == null)  
            return node;  
        Node current = node.rightChild;  
        while(current.rightChild != null)  
            current = current.rightChild;  
        return current;  
    }  
    
    public boolean delete(int key){  
        if(root == null){  
            System.out.println("The tree is empty!");  
            return false;  
        }  
        Node targetParent = root;  
        Node target = root;  
        boolean isLeftChild = true;  
        while(target.id != key){  
            if(key > target.id){  
                targetParent = target;  
                target = target.rightChild;   
                isLeftChild = false;  
            }else{  
                targetParent = target;  
                target = target.leftChild;  
                isLeftChild = true;  
            }  
            if(target == null)  
                break;  
        }  
        if(target == null){  
            System.out.println("Node dosen't exist!"   
                    + "Can not delete.");  
            return false;  
        }  
        //��ɾ���ڵ�ΪҶ�ӽڵ�  
        if(target.leftChild == null &&  
                target.rightChild == null){  
            if(target.id == root.id){  
                root = null;  
                return true;  
            }  
            if(isLeftChild)  
                targetParent.leftChild = null;  
            else  
                targetParent.rightChild = null;  
        }  
        //��ɾ���ڵ���1���ӽڵ�  
        //��ɾ���ڵ�ֻ�����ӽڵ�  
        else if(target.leftChild == null &&   
                target.rightChild != null){  
            if(target.id == root.id){  
                root = root.rightChild;  
                return true;  
            }  
            if(isLeftChild)  
                targetParent.leftChild = target.rightChild;  
            else  
                targetParent.rightChild = target.rightChild;  
        }  
        //��ɾ���ڵ�ֻ�����ӽڵ�  
        else if(target.leftChild != null &&   
                target.rightChild == null){  
            if(target.id == root.id){  
                root = root.leftChild;  
                return true;  
            }  
            if(isLeftChild)  
                targetParent.leftChild = target.leftChild;  
            else  
                targetParent.rightChild = target.leftChild;  
        }  
        //��ɾ���ڵ��������ӽڵ㣬���ҵ������ڵ㣬����Ȼ�󽫺����ڵ��������ɾ���ڵ��λ��  
        else{  
            Node followingNode = this.getFollowingNode(target);  
            if(target.id == root.id)  
                root = followingNode;  
            else if(isLeftChild)  
                targetParent.leftChild = followingNode;  
            else  
                targetParent.rightChild = followingNode;  
            followingNode.leftChild = target.leftChild;  
            followingNode.rightChild = target.rightChild;  
        }  
        return true;  
    }  
      
    //��ȡ��ɾ���ڵ�ĺ����ڵ�  
    private Node getFollowingNode(Node node2Del){  
        Node nodeParent = node2Del;  
        //ֻ�б�ɾ���ڵ��������ӽڵ�ʱ���Ż���ø÷���  
        //����ֱ�ӵ���rightChild��û�������  
        Node node = node2Del.rightChild;  
        while(node.leftChild != null){  
            nodeParent = node;  
            node = node.leftChild;  
        }  
        if(node.id != node2Del.rightChild.id)  
            nodeParent.leftChild = node.rightChild;  
        else  
            nodeParent.rightChild = node.rightChild;  
        return node;  
    }  
}   

class Node {  
    public int id;  
    public String name;  
    public Node leftChild;  
    public Node rightChild;  
  
    public Node(int id, String name) {  
        this.id = id;  
        this.name = name;  
    }  
} 
