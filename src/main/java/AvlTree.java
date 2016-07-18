import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
 * Created by sank on 7/14/16.
 */
public class AvlTree {

    /**
     * Structure of the avl node
     */
    class Node {
        private int ID;
        private int count;
        private Node rightChild;
        private Node leftChild;
        private int height;

        /**
         * Constructor
         *
         * @param id1    ID of the AVL node
         * @param count1 count of the AVL node
         */
        public Node(int id1, int count1) {
            ID = id1;
            count = count1;
            height = 0;
            leftChild = null;
            rightChild = null;
        }

        /**
         * @return height of the node
         */
        public int getHeight() {
            return this.height;
        }

        /**
         * @return id of the node
         */
        public int getID() {
            return this.ID;
        }

        /**
         * @return count of the node
         */
        public int getCount() {
            return this.count;
        }

        /**
         * @return right child of the node
         */
        public Node getRightChild() {
            return this.rightChild;
        }

        /**
         * @return left child of the node
         */
        public Node getLeftChild() {
            return this.leftChild;
        }

        /**
         * setter for the right child of the node
         *
         * @param node right child
         */
        public void setRightChild(Node node) {
            this.rightChild = node;
        }

        /**
         * setter for the left child of the node
         *
         * @param node left child
         */
        public void setLeftChild(Node node) {
            this.leftChild = node;
        }

        /**
         * setter for the id of the node
         *
         * @param id ID of the node
         */
        public void setID(int id) {
            this.ID = id;
        }

        /**
         * setter for the count of the node
         *
         * @param count1 count of the node
         */
        public void setCount(int count1) {
            this.count = count1;
        }

        /**
         * setter for the height of the node
         *
         * @param height1 height of the node
         */
        public void setHeight(int height1) {
            this.height = height1;
        }
    }

    /**
     * placeholder for root
     */
    private Node root;

    /**
     * constructor for avl tree
     */
    public AvlTree() {
        root = null;
    }

    /**
     * inserting into the Avl tree
     *
     * @param id    id of the node
     * @param count count of the tree
     */
    private void insert(int id, int count) {
        root = insert(id, count, root);
    }

    /**
     * @param node Avl node
     * @return height of the node in the Avl tree
     */
    private int height(Node node) {
        return node == null ? -1 : node.getHeight();
    }

    /**
     * @return true if the tree is empty or else false
     */
    private boolean isEmpty() {
        return root == null;
    }


    /**
     * @param id id of the avl node
     * @return node with ID as id or null if there is no such node
     */
    private Node search(int id) {
        return searchUtil(root, id);
    }

    /**
     * searchUtil for recursive search
     *
     * @param rootNode node in avl tree
     * @param id       value to be searched for
     * @return node with ID as id or nul if there is no such node
     */
    private Node searchUtil(Node rootNode, int id) {
        if (rootNode == null || id == rootNode.getID())
            return rootNode;
        else if (id > rootNode.getID())
            return searchUtil(rootNode.getRightChild(), id);
        else
            return searchUtil(rootNode.getLeftChild(), id);
    }

    /**
     * insert utility function
     *
     * @param id    ID of the node to be inserted
     * @param count count of the node to be inserted
     * @param node  parent node to which the new node should be inserted as child
     * @return root after inserting and balancing the tree
     */
    private Node insert(int id, int count, Node node) {
        if (node == null)
            node = new Node(id, count);
        else if (id < node.getID()) {
            node.setLeftChild(insert(id, count, node.getLeftChild()));
            if (height(node.getLeftChild()) - height(node.getRightChild()) == 2)
                if (id < node.getLeftChild().getID())
                    node = rotateWithLeftChild(node);
                else
                    node = doubleRotateWithLeftChild(node);
        } else if (id > node.getID()) {
            node.setRightChild(insert(id, count, node.getRightChild()));
            if (height(node.getRightChild()) - height(node.getLeftChild()) == 2)
                if (id > node.getRightChild().getID())
                    node = rotateWithRightChild(node);
                else
                    node = doubleRotateWithRightChild(node);
        } else
            ;
        node.setHeight(Math.max(height(node.getLeftChild()), height(node.getRightChild())) + 1);
        return node;
    }

    /**
     * function which does the rotation with left child
     *
     * @param node node where height difference is 2
     * @return parent of the subtree after balancing
     */
    private Node rotateWithLeftChild(Node node) {
        Node left = node.getLeftChild();
        node.setLeftChild(left.getRightChild());
        left.setRightChild(node);
        node.setHeight(Math.max(height(node.getLeftChild()), height(node.getRightChild())) + 1);
        left.setHeight(Math.max(height(left.getLeftChild()), height(node)) + 1);
        return left;
    }

    /**
     * function which does the rotation with right child
     *
     * @param node node where height difference is 2
     * @return parent of the subtree after balancing
     */
    private Node rotateWithRightChild(Node node) {
        Node right = node.getRightChild();
        node.setRightChild(right.getLeftChild());
        right.setLeftChild(node);
        node.setHeight(Math.max(height(node.getLeftChild()), height(node.getRightChild())) + 1);
        right.setHeight(Math.max(height(right.getRightChild()), node.getHeight()) + 1);
        return right;
    }

    /**
     * function does rotation in case of LR rotation
     *
     * @param node node where the height difference of children is 2
     * @return root of the subtree after balancing
     */
    private Node doubleRotateWithLeftChild(Node node) {
        node.setLeftChild(rotateWithRightChild(node.getLeftChild()));
        return rotateWithLeftChild(node);
    }

    /**
     * function does rotation in case of RL rotation
     *
     * @param node node where the height difference of children is 2
     * @return root of the subtree after balancing
     */
    private Node doubleRotateWithRightChild(Node node) {
        node.setRightChild(rotateWithLeftChild(node.getRightChild()));
        return rotateWithRightChild(node);
    }

    /**
     * returns the number of nodes under the node
     *
     * @param node avlNode
     * @return number of nodes which are children of node
     */
    private int nodeCount(Node node) {
        if (node == null)
            return 0;
        else return nodeCount(node.getLeftChild()) + nodeCount(node.getRightChild()) + 1;
    }

    /**
     * deleting the node with ID as id from the tree
     *
     * @param id ID
     */
    private void remove(int id) {
        root = removeUtil(id, root);
    }

    /**
     * utility to remove the node with ID as id from the avl tree
     *
     * @param id   id of the node to be deleted
     * @param node node to be searched under for node with ID as id
     * @return parent of the subtree after removing the node with ID as id
     */
    private Node removeUtil(int id, Node node) {
        //key doesn't exist.
        if (node == null) return null;
        // go left if it is in left subtree
        if (id < node.getID()) {
            node.setLeftChild(removeUtil(id, node.getLeftChild()));
            node.setHeight(Math.max(height(node.getLeftChild()), height(node.getRightChild())) + 1);
            return node; // Deleting may have unbalanced tree.
        }
        //go right if it is in right subtree
        else if (id > node.getID()) {
            node.setRightChild(removeUtil(id, node.getRightChild()));
            node.setHeight(Math.max(height(node.getLeftChild()), height(node.getRightChild())) + 1);
            return node; // Deleting may have unbalanced tree.
        }
        //Found it
        else {
            // 0 children
            if (node.getLeftChild() == null && node.getRightChild() == null)
                return null;
            // 1 child
            if (node.getLeftChild() == null)
                return node.getRightChild();
            if (node.getRightChild() == null)
                return node.getLeftChild();

            // 2 children
            Node smallInRight = findMin(node.getRightChild());
            node.setID(smallInRight.getID());
            node.setCount(smallInRight.getCount());
            node.setRightChild(removeUtil(smallInRight.getID(), node.getRightChild()));
            node.setHeight(Math.max(height(node.getLeftChild()), height(node.getRightChild())) + 1);
            return node;
        }
    }

    /**
     * prints the tree inorder
     */
    public void printTree() {
        if (isEmpty())
            System.out.println("Empty tree");
        else
            printTreeUtil(root);
    }

    /**
     * inorder print utility to print under node
     *
     * @param node avl node
     */
    private void printTreeUtil(Node node) {
        if (node != null) {
            printTreeUtil(node.getLeftChild());
            System.out.println(node.getID());
            printTreeUtil(node.getRightChild());
        }
    }


    /**
     * increase the count of node with id with count.
     * Inserts into tree if the node is not found
     *
     * @param id    id of the node to be increased
     * @param count node with id should be increased with count
     */
    public void increase(int id, int count) {
        Node node = search(id);
        if (node == null) {
            insert(id, count);
            System.out.println(count);
        } else {
            node.setCount(node.getCount() + count);
            System.out.println(node.getCount());
        }
    }

    /**
     * decreases the count of the node with id id.
     * Deletes the node with id the count < 1
     *
     * @param id    id of the node to be decreased
     * @param count node with id should be decreased with count
     */
    public void reduce(int id, int count) {
        Node node = search(id);
        if (node == null) {
            System.out.println(0);
        } else if (node.getCount() - count < 1) {
            remove(id);
            System.out.println(0);
        } else {
            node.setCount(node.getCount() - count);
            System.out.println(node.getCount());
        }

    }

    /**
     * prints the count of next immediate node with id greater than given id
     *
     * @param id id of the node
     */
    public void next(int id) {
        Node currNode = root;
        Node next = null;
        while (!isNilNode(currNode)) {
            if (currNode.getID() > id) {
                if (next == null) {
                    next = currNode;
                } else {
                    next = (next.getID() < id) ? next : currNode;
                }
                currNode = currNode.getLeftChild();
            } else if (currNode.getID() < id) {
                currNode = currNode.getRightChild();
            } else {
                if (!isNilNode(currNode.getRightChild())) {
                    next = findMin(currNode.getRightChild());
                }
                break;
            }
        }
        if (!isNilNode(next)) {
            System.out.println(next.getID() + " " + next.getCount());
        } else {
            System.out.println("0 0");
        }
    }

    /**
     * prints the id and count of the node which is immediately less than node with ID as id
     *
     * @param id id of the node to be searched for
     */
    public void previous(int id) {
        Node currNode = root;
        Node prev = null;
        while (!isNilNode(currNode)) {
            if (currNode.getID() > id) {

                currNode = currNode.getLeftChild();
            } else if (currNode.getID() < id) {
                if (prev == null) {
                    prev = currNode;
                } else {
                    prev = (prev.getID() > id) ? prev : currNode;
                }
                currNode = currNode.getRightChild();
            } else {
                if (!isNilNode(currNode.getLeftChild())) {
                    prev = findMax(currNode.getLeftChild());
                }
                break;
            }
        }
        if (!isNilNode(prev)) {
            System.out.println(prev.getID() + " " + prev.getCount());
        } else {
            System.out.println("0 0");
        }

    }

    /**
     * prints the count of the node with ID as id
     *
     * @param id id of the node to be searched for in the avl tree
     */
    public void count(int id) {
        Node node = search(id);
        int count = 0;
        if (node != null) {
            count = node.getCount();
        }
        System.out.println(count);

    }

    /**
     * finds the maximum under the node
     *
     * @param node parent node to be looked under
     * @return returns the node with maximum in the tree with root as node
     */
    private Node findMax(Node node) {
        while (!isNilNode(node.getRightChild())) {
            node = node.getRightChild();
        }
        return node;
    }

    /**
     * finds the minimum under the node
     *
     * @param node root node to be looked under
     * @return the node with minimum ID value in the tree with root as node
     */
    private Node findMin(Node node) {
        while (!isNilNode(node.getLeftChild())) {
            node = node.getLeftChild();
        }
        return node;
    }


    /**
     * function to print the sum of the counts of nodes with id in range of id1 and id2
     *
     * @param id1 ID of avl node
     * @param id2 ID of avl node
     */

//    public void inRange(int id1, int id2) {
//        System.out.println(inRangeRecursive1(root, id1, id2));
//    }

    public void inRange(int id1, int id2) {
        String sum = "";
        List<String> returnNums = inRangeRecursive(root, id1, id2);
        Iterator<String> iter = returnNums.iterator();
        while (iter.hasNext()) {
            String curr = iter.next();
            if (!sum.equals("")) {
                sum = sum + " " + curr;
            } else sum = curr;
        }
        System.out.println(sum);
    }


    /**
     * utility function to find the sum of counts of the nodes with id in range of ID1 and ID2
     *
     * @param root parent node
     * @param ID1  ID of the avl node to be looked for
     * @param ID2  ID of the avl node to be looked for
     * @return the sum of counts of the avl nodes in the range of ID1 and ID2
     */
    private List<String> inRangeRecursive(Node root, int ID1, int ID2) {
        if (isNilNode(root)) {
            return null;
        }
        List<String> returnNums = new ArrayList<String>();
        if (ID1 <= root.getID()) {
            List<String> tmp1 = inRangeRecursive(root.getLeftChild(), ID1, ID2);
            if (tmp1 != null) {
                returnNums.addAll(tmp1);
            }
        }

        if (ID1 <= root.getID() && ID2 >= root.getID()) {
            returnNums.add(String.valueOf(root.getCount()));
        }

        /** If root.ID is smaller than ID2, then only we can get output keys
         in right subtree */
        if (ID2 > root.getID()) {
            List<String> tmp2 = inRangeRecursive(root.getRightChild(), ID1, ID2);
            if (tmp2 != null) {
                returnNums.addAll(tmp2);
            }
        }

        return returnNums;
    }


    /**
     * Utility which does the null checks
     *
     * @param node
     * @return true if node is null
     */
    private boolean isNilNode(Node node) {
        // if the node is null, then set its id as -1
        if (node == null || node.getID() == -1) {
            return true;
        }
        return false;
    }


    /**
     * Driver function which does the file parsing and initializes the tree
     * Takes input for the interactice shell and redirects to the necessary functions
     *
     * @param args takes input as file to initialize
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        String bufferLine = null;
        AvlTree tree = new AvlTree();

        BufferedReader bufferReader1 = new BufferedReader(new FileReader(args[0]));
        int totalEntries = Integer.parseInt(bufferReader1.readLine());

        while ((bufferLine = bufferReader1.readLine()) != null) {
            String[] nodeList = bufferLine.split(" ");
            tree.insert(Integer.parseInt(nodeList[0]), Integer.parseInt(nodeList[1]));
        }

        Scanner scanner = new Scanner(System.in);
        while ((bufferLine = scanner.nextLine()) != null) {
            if (bufferLine.equals("\n") || bufferLine.equals("quit")) {
                break;
            }
            String[] list = bufferLine.split(" ");

            ArrayList<String> arrayList1 = new ArrayList<String>();
            arrayList1.add("count");
            arrayList1.add("next");
            arrayList1.add("previous");

            String methodName = list[0];

            if (arrayList1.contains(methodName)) {
                if (methodName.equals("count")) {
                    tree.count(Integer.parseInt(list[1]));
                } else if (methodName.equals("next")) {
                    tree.next(Integer.parseInt(list[1]));
                } else if (methodName.equals("previous")) {
                    tree.previous(Integer.parseInt(list[1]));
                }
            } else {
                if (methodName.equals("increase")) {
                    tree.increase(Integer.parseInt(list[1]), Integer.parseInt(list[2]));
                } else if (methodName.equals("reduce")) {
                    tree.reduce(Integer.parseInt(list[1]), Integer.parseInt(list[2]));
                } else if (methodName.equals("inrange")) {
                    tree.inRange(Integer.parseInt(list[1]), Integer.parseInt(list[2]));
                }
            }
        }

    }
}
