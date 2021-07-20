package Trees;

import java.util.*;

class Result2 {

    public static class Trie
    {
        private boolean isLeaf;
        private Map<Character, Trie> children;
        private int numberOfSubkeys;

        // Constructor
        Trie()
        {
            isLeaf = false;
            children = new HashMap<>();
        }

        // Iterative function to insert a string into a Trie
        public void insert(String key)
        {
            System.out.println("Inserting \"" + key + "\"");

            // start from the root node
            Trie curr = this;

            // do for each character of the key
            for (char c: key.toCharArray())
            {
                // create a new node if the path doesn't exist
                curr.children.putIfAbsent(c, new Trie());
                // go to the next node
                curr = curr.children.get(c);
                curr.numberOfSubkeys ++;
            }

            // mark the current node as a leaf (last character of the key)
            curr.isLeaf = true;
        }

        public int searchAndReturn(String key)
        {

            Trie curr = this;

            int totalNumberOfChildren = 0;

            // do for each character of the key
            for (char c: key.toCharArray())
            {
                // go to the next node
                curr = curr.children.get(c);

                // if the string is invalid (reached end of a path in the Trie)
                if (curr == null) {
                    return 0;
                }
            }

//            ArrayDeque<Trie> queue = new ArrayDeque<>();
//
//            queue.offer(curr);
//
//            while(queue.size() != 0){
//                curr = queue.poll();
//                if(curr.isLeaf){
//                    totalNumberOfChildren ++;
//                }
//                for(Trie child : curr.children.values()){
//                    queue.offer(child);
//                }
//            }

            // return true if the current node is a leaf node and the
            // end of the string is reached
            return curr.numberOfSubkeys;
        }

    }


    /*
     * Complete the 'contacts' function below.
     *
     * The function is expected to return an INTEGER_ARRAY.
     * The function accepts 2D_STRING_ARRAY queries as parameter.
     */

    public static List<Integer> contacts (List<List<String>> queries){
        List<Integer> result = new ArrayList<Integer>();
        Trie root = new Trie();
        for(List<String> query : queries){
            if(query.get(0).equals("add")){
                root.insert(query.get(1));
            }
            else{
                result.add(root.searchAndReturn(query.get(1)));
            }
        }

        return result;
    }
}
