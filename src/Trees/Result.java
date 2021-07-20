package Trees;

import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

class Result {

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

    /*
     * Complete the 'swapNodes' function below.
     *
     * The function is expected to return a 2D_INTEGER_ARRAY.
     * The function accepts following parameters:
     *  1. 2D_INTEGER_ARRAY indexes
     *  2. INTEGER_ARRAY queries
     */

    public static Node insertLevelOrder(List<List<Integer>> arr, Node root)
    {
        ArrayDeque<Node> queue = new ArrayDeque<>();
        queue.offer(root);
        for(int i = 0; i < arr.size(); i++){
            List<Integer> list = arr.get(i);
            Node curr = queue.poll();
            if(list.get(0) == -1){
                curr.left = null;
            }
            else{
                curr.left = new Node(list.get(0));
                queue.offer(curr.left);
            }
            if(list.get(1)== -1){
                curr.right = null;
            }
            else{
                curr.right = new Node(list.get(1));
                queue.offer(curr.right);
            }
        }
        return root;
    }

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

    public static List<Integer> inOrder(Node root)
    {
        // create an empty stack
        ArrayDeque<Node> stack = new ArrayDeque<>();
        List<Integer> result = new ArrayList<>();

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
                result.add(curr.data);

                curr = curr.right;
            }
        }
        return result;
    }


    public static ArrayList<Node> levelOrderTraversal(Node root, int level, int totalHeight)
    {
        Queue<Node> queue = new ArrayDeque<>();
        ArrayList<Node> list = new ArrayList<>();
        queue.add(root);
        Node tempNode;
        while (!queue.isEmpty())
        {
            tempNode = queue.poll();
            if(totalHeight - iterativeHeight(tempNode) + 1 == level){
                list.add(tempNode);
            }

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

    public static List<List<Integer>> swapNodes(List<List<Integer>> indexes, List<Integer> queries){
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        Node root = new Node(1);

        insertLevelOrder(indexes, root);

        int h = iterativeHeight(root);

        for(int query : queries){
            int current = query;
            while(current <= h +1){
                for(Node node : levelOrderTraversal(root, current, h)){
                    if(node.right != null || node.left != null) {
                        Node temporary = node.right;
                        node.right = node.left;
                        node.left = temporary;
                    }
                }
                current += query;
            }
            result.add(inOrder(root));
        }
        return result;
    }


}




