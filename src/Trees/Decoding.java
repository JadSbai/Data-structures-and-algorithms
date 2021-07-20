package Trees;

class Decoding {

/*
	class Node
		public  int frequency; // the frequency of this tree
    	public  char data;
    	public  Node left, right;

*/

    public static void decode(String s, Node root){
        int currIndex = 0;
        Character currChar = s.charAt(currIndex);
        Node currNode = root;
        Character currData = root.data;
        StringBuilder sb = new StringBuilder();

        while(currIndex <= s.length() - 1){

            while((currData == '\0')){
                currChar = s.charAt(currIndex);
                if(currChar.equals('1')){
                    currNode = currNode.right;
                }
                else{
                    currNode = currNode.left;
                }
                currData = currNode.data;
                currIndex ++;

            }
            sb.append(currData);
            currNode = root;
            currData = currNode.data;
        }

        System.out.print(sb.toString());
    }




}
