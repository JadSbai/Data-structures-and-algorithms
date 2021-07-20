package Trees;

import java.util.ArrayDeque;
import java.util.*;
import java.util.Stack;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Scanner;

public class TreeMethods {

    // Standard Node class for a binary tree
    static class Node
    {
        int data;
        Node left, right;
        Node(int item)
        {
            data = item;
            left = right = null;
        }
    }

    static class TreeNode
    {
        int val;
        TreeNode left, right;
        TreeNode(int item)
        {
            val = item;
            left = right = null;
        }
    }

    static class Pair<U, V>
    {
        public final U first;       // first field of a pair
        public final V second;      // second field of a pair

        // Constructs a new Pair with specified values
        private Pair(U first, V second)
        {
            this.first = first;
            this.second = second;
        }

        // Factory method for creating a Typed Pair immutable instance
        public static <U, V> Pair <U, V> of(U a, V b)
        {
            // calls private constructor
            return new Pair<>(a, b);
        }
    }

    // Level order traversal for a binary tree
    public static ArrayList<Node> levelOrderTraversal(Node root)
    {
        Queue<Node> queue = new ArrayDeque<>();
        ArrayList<Node> list = new ArrayList<>();
        queue.add(root);
        Node tempNode;
        while (!queue.isEmpty())
        {
            tempNode = queue.poll();
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

    public static void levelOrder(Node root){
        if(root == null){
            return;
        }
        ArrayDeque<Node> queue = new ArrayDeque<>();
        queue.offer(root);
        Node temp;
        while(queue.size() != 0){
            temp = queue.getFirst();
            System.out.print(queue.poll().data);
            if(temp.left != null){
                queue.offer(temp.left);
            }
            if(temp.right != null){
                queue.offer(temp.right);
            }
        }

    }


    // Postorder traversal for a binary tree
    public static void postOrder(Node root) {
        ArrayDeque<Node> stack = new ArrayDeque<Node>();
        Node temp = root;
        stack.push(temp);
        while(!stack.isEmpty()){
            temp = stack.peek();
            if(temp.left == null && temp.right == null){
                System.out.print(stack.pop().data + " ");
            }
            if (temp.right != null){
                stack.push(temp.right);
                temp.right = null;
            }
            if (temp.left != null){
                stack.push(temp.left);
                temp.left = null;
            }

        }
    }


    //Function to convert a binary tree into its mirror tree.
    void mirror(Node node)
    {
        ArrayList<Node> list = levelOrderTraversal(node);

        for(Node node1 : list){
            Node temp = node1.left;
            node1.left = node1.right;
            node1.right = temp;

        }
    }

    public static void inOrder(Node root)
    {
    // create an empty stack
    ArrayDeque<Node> stack = new ArrayDeque<>();

    // start from the root node (set current node to the root node)
    Node curr = root;

    // if the current node is null and the stack is also empty, we are done
        while (!stack.isEmpty() || curr != null)
    {
        // if the current node exists, push it into the stack (defer it)
        // and move to its left child
        if (curr != null)
        {
            stack.push(curr);
            curr = curr.left;
        }
        else {
            // otherwise, if the current node is null, pop an element from
            // the stack, print it, and finally set the current node to its
            // right child
            curr = stack.pop();
            System.out.print(curr.data + " ");

            curr = curr.right;
        }
    }
}



//    public static Node insert(Node root, int data) {
//        if(root == null) {
//            return new Node(data);
//        } else {
//            Node cur;
//            if(data <= root.data) {
//                cur = insert(root.left, data);
//                root.left = cur;
//            } else {
//                cur = insert(root.right, data);
//                root.right = cur;
//            }
//            return root;
//        }
//    }

    // Preorder traversal for a binary tree
    public static void preOrder(Node root){
        ArrayDeque<Node> stack = new ArrayDeque<>();
        Node temp = root;
        stack.push(temp);
        while(!stack.isEmpty() || temp != null){
            System.out.print(stack.pop().data);
            if (temp.right != null) {
                stack.push(temp.right);
            }
            if(temp.left != null){
                stack.push(temp.left);
            }
            temp = stack.peek();
        }
    }

    // Compute height of a binary tree recursively
    public static int recursiveHeight(Node root){
        int rightHeight = 0;
        int leftHeight = 0;

        if(root.left != null){
            leftHeight = recursiveHeight(root.left) + 1;
        }
        if(root.right != null){
            rightHeight = recursiveHeight(root.right) + 1;
        }

        return Math.max(leftHeight, rightHeight);
    }

    // Computes iteratively the height for a binary tree
    public static int iterativeHeight(Node root){
        if(root == null){
            return 0;
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

    // print top view for a binary tree
    public static void topView(Node root){
        if(root == null){
            return;
        }

        int min = 0;
        int max = 0;

        // Map from horizontal level to associated Node
        HashMap<Integer, Node> map = new HashMap<>();

        map.put(0, root);

        // Queue of pairs (Node, horizontal level)
        ArrayDeque<Pair<Node, Integer>> queue = new ArrayDeque<>();


        queue.offer(new Pair<Node, Integer>(root, 0));

        while(queue.size() != 0){
            Pair<Node, Integer> temp = queue.getFirst();
            queue.poll();
            if(temp.first.left != null){
                queue.offer(new Pair<Node, Integer>(temp.first.left, temp.second - 1));
                if(!map.containsKey(temp.second - 1)){
                    map.put(temp.second -1, temp.first.left);
                    min = temp.second - 1;
                }
            }
            if(temp.first.right != null){
                queue.offer(new Pair<Node, Integer>(temp.first.right, temp.second + 1));
                if(!map.containsKey(temp.second + 1)){
                    map.put(temp.second +1, temp.first.right);
                    max = temp.second + 1;
                }
            }
        }
        for(int i = min; i <= max; i++){
            System.out.print(map.get(i).data);
        }

    }




    public static Node insertInBST(Node root, int data){

        if(root == null){
            return new Node(data);
        }
        Node previous = root;
        boolean isLeft = false;
        Node current = root;
        while(current != null){
            if( data < current.data){
                previous = current;
                current = current.left;
                isLeft = true;
            }
            else if (data > current.data){
                previous = current;
                current = current.right;
                isLeft = false;
            }
            else {
                return root;
            }
        }

        Node newNode = new Node(data);
        if(isLeft){
            previous.left = newNode;
        }
        else{
            previous.right = newNode;
        }

        return root;

    }

    public boolean isValidBST(TreeNode root) {
        if(root == null || (root.right == null && root.left == null)){
            return true;
        }

        ArrayDeque<TreeNode> queue = new ArrayDeque<>();
        ArrayList<TreeNode> list = new ArrayList<>();
        ArrayList<Integer> list2 = new ArrayList<>();
        queue.offer(root);
        TreeNode temp;
        while(queue.size() != 0){
            temp = queue.poll();
            if(list2.contains(temp.val)){
                return false;
            }
            list.add(temp);
            list2.add(temp.val);
            if (temp.left != null){
                queue.offer(temp.left);
            }
            if (temp.right != null){
                queue.offer(temp.right);
            }
        }

        TreeNode current;

        for(TreeNode node : list){
            current = root;
            while(current != null){
                if(node.val == current.val){
                    break;
                }
                else if(node.val < current.val){
                    current = current.left;
                }
                else if (node.val > current.val){
                    current = current.right;
                }
                else{
                    return false;
                }
            }
            if(current == null){
                return false;
            }
        }
        return true;

    }

    public Node insertLevelOrder(int[] arr, Node root,
                                 int i)
    {
        // Base case for recursion
        if (i < arr.length) {
            Node temp;
            if(arr[i] == -1) {
                temp = null;
            }
            else{
                temp = new Node(arr[i]);
            }
            root = temp;

            // insert left child
            root.left = insertLevelOrder(arr, root.left,
                    2 * i + 1);

            // insert right child
            root.right = insertLevelOrder(arr, root.right,
                    2 * i + 2);
        }
        return root;
    }




    public static void main(String[] args) {
//        Scanner scan = new Scanner(System.in);
//        int t = scan.nextInt();
//        Node root = null;
//        while(t-- > 0) {
//            int data = scan.nextInt();
//            root = insertInBST(root, data);
//        }
//        scan.close();
//        preOrder(root);
    }

}

