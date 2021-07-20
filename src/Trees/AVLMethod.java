package Trees;

import org.w3c.dom.ls.LSOutput;

import java.util.*;


public class AVLMethod {
    static class Node{
        int val;	//Value
        int ht;		//Height
        Node left;	//Left child
        Node right;	//Right child
    }


    public static int iterativeHeight(Node root){
        if(root == null){
            return -1;
        }

        ArrayDeque<Node> queue = new ArrayDeque<>();
        int height = 0;
        queue.offer(root);

        while (queue.size() != 0){
            int nodeCount = queue.size();
            while(nodeCount > 0){
                Node temp = queue.getFirst();
                queue.poll();
                if(temp.left != null){
                    queue.offer(temp.left);
                }
                if(temp.right != null){
                    queue.offer(temp.right);
                }
                nodeCount --;
            }
            if(queue.size() != 0){
                height ++;
            }
        }
        return height;
    }

    public static ArrayList<Node> levelOrderTraversal(Node root)
    {
        Queue<Node> queue = new ArrayDeque<>();
        ArrayList<Node> list = new ArrayList<>();
        queue.add(root);
        Node tempNode;
        while (!queue.isEmpty())
        {
            tempNode = queue.poll();
            tempNode.ht = iterativeHeight(tempNode);
            list.add(tempNode);
            if (tempNode.left != null)
            {
                queue.add(tempNode.left);
            }
            if (tempNode.right != null)
            {
                queue.add(tempNode.right);
            }
        }

        return list;
    }



    static Node insert(Node root,int val)
    {
        // First insert the node in the tree like a standard BSt insert

        Node newNode = new Node();
        newNode.val = val;
        if(root==null){
            root = newNode;
            return root;
        }

        Node current = root;
        Node parent = null;
        boolean found = false;
        while(!found){
            parent = current;
            if(val<current.val){
                current = current.left;
                if(current==null){
                    parent.left = newNode;
                    found = true;
                }
            }else{
                current = current.right;
                if(current==null){
                    parent.right = newNode;
                    found = true;
                }
            }
        }

        // Now check that there's no violation of the AVL property and fix the tree if there's              one
        List<Node> tree = levelOrderTraversal(root);

        for(int i = tree.size() -1; i >= 0; i --){
            Node node = tree.get(i);
            if(node.right != null || node.left != null){

                int rightHeight = node.right == null ? -1 : node.right.ht;
                int leftHeight = node.left == null ? -1 : node.left.ht;

                int balanceFactor = leftHeight - rightHeight;

                while(Math.abs(balanceFactor) > 1){
                    Node leftChild = node.left;
                    Node rightChild = node.right;
                    int leftBalanceFactor = 0;
                    int rightBalanceFactor = 0;
                    if(leftChild != null){
                        int leftRightHeight = leftChild.right == null ? -1 : leftChild.right.ht;
                        int leftLeftHeight = leftChild.left == null ? -1 : leftChild.left.ht;
                        leftBalanceFactor = leftLeftHeight - leftRightHeight;
                    }

                    if(rightChild != null){
                        int rightRightHeight = rightChild.right == null ? -1 : rightChild.right.ht;
                        int rightLeftHeight = rightChild.left == null ? -1 : rightChild.left.ht;
                        rightBalanceFactor = rightLeftHeight - rightRightHeight;
                    }

                    Node padre = searchParent(tree, node.val);

                    if(leftHeight == rightHeight + 2 && leftBalanceFactor == 1){

                        node.left = leftChild.right;
                        leftChild.right = node;
                        if(padre != null){
                            if(padre.right.val == node.val){
                                padre.right = leftChild;
                            }
                            else{
                                padre.left = leftChild;
                            }
                        }
                        else{
                            root = leftChild;
                        }

                    }
                    else if(leftHeight == rightHeight - 2 && rightBalanceFactor == -1){
                        node.right = rightChild.left;
                        rightChild.left = node;
                        if(padre != null){
                            if(padre.right.val == node.val){
                                padre.right = rightChild;
                            }
                            else{
                                padre.left = rightChild;
                            }
                        }
                        else{
                            root = rightChild;
                        }

                    }
                    else if(leftHeight == rightHeight + 2 && leftBalanceFactor == -1){
                        Node leftRightChild = node.left.right;
                        leftChild.right = leftRightChild.left;
                        node.left = leftRightChild;
                        leftRightChild.left = leftChild;
                    }
                    else {
                        Node rightLeftChild = node.right.left;
                        rightChild.left = rightLeftChild.right;
                        node.right = rightLeftChild;
                        rightLeftChild.right = rightChild;
                    }

                    rightHeight = node.right == null ? -1 : iterativeHeight(node.right);
                    leftHeight = node.left == null ? -1 : iterativeHeight(node.left);
                    balanceFactor = leftHeight - rightHeight;

                    for(Node node1 : tree){
                        node1.ht = iterativeHeight(node1);
                    }
                }
            }
        }

        return root;
    }

    public static Node searchParent(List<Node> tree, int val){
        for(Node node : tree){
            if((node.right != null && node.right.val == val) ||(node.left !=null && node.left.val == val)){
                return node;
            }
        }
        return null;

    }

    public static void main(String[] args) {
        Node root = insert(null, 14);
        insert(root, 25);
        root = insert(root, 21);
        root = insert(root, 10);
        root = insert(root, 23);
        root = insert(root, 7);
        root = insert(root, 26);
        root = insert(root, 12);
        root = insert(root, 30);
        root = insert(root, 16);
        root = insert(root, 19);

        List<Node> finalTree = levelOrderTraversal(root);
        for(Node node : finalTree){
            System.out.print(node.val + " ");
        }
    }
}
