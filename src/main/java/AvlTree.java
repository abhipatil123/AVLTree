import lombok.Data;

/**
 * Created by sank on 7/14/16.
 */
@Data
public class AvlTree {
    private Node root;

    public AvlTree() {
        root = null;
    }

    public void insert(int id, int count) {

    }

    public int height(Node node) {
        return node == null ? -1 : node.getHeight();
    }

    public boolean isEmpty() {
        return root == null;
    }

    //returns null if there is no such value
    public Node search(int id) {
        return searchUtil(root, id);
    }

    //returns null if there is no such value
    private Node searchUtil(Node rootNode, int id) {
        if (rootNode == null || id == rootNode.getID())
            return rootNode;
        else if (id > rootNode.getID())
            return searchUtil(rootNode.getRightChild(), id);
        else
            return searchUtil(rootNode.getLeftChild(), id);
    }

    private Node rotateWithLeftChild(Node node) {
        return node;
    }

    private Node rotateWithRightChild(Node node) {
        return node;
    }

    private Node doubleRotateWithLeftChild(Node node) {
        return node;
    }

    private Node doubleRotateWithRightChild(Node node) {
        return node;
    }

    public int nodeCount(Node node) {
        if (node == null)
            return 0;
        else return nodeCount(node.getLeftChild()) + nodeCount(node.getRightChild()) + 1;
    }
}
