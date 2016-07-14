import lombok.Data;

/**
 * Created by sank on 7/14/16.
 */
@Data
public class Node {
    private int ID;
    private int count;
    private Node rightChild;
    private Node leftChild;
    private int height;
}
