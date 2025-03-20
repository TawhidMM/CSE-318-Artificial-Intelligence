import java.util.Arrays;
import java.util.Objects;

public class Node implements Comparable<Node> {

    private final Node parent;
    private final int[] config;
    private final int blankIndex;
    private final int costFromStart;
    private final int hCostToGoal;


    public Node(int[] config, int hCostToGoal) {
        this.config = config;
        this.blankIndex = calculateBlankIndex();
        this.costFromStart = 0;
        this.hCostToGoal = hCostToGoal;
        this.parent = null;
    }

    public Node(int[] config, Node parent, int hCostToGoal) {
        this.config = config;
        this.blankIndex = calculateBlankIndex();
        this.costFromStart = parent.costFromStart + 1;
        this.hCostToGoal = hCostToGoal;
        this.parent = parent;
    }

    public int[] getConfig() {
        return Arrays.copyOf(config, config.length);
    }
    public Node getParent() {
        return parent;
    }
    public int getBlankIndex() {
        return blankIndex;
    }

    private int calculateBlankIndex(){
        int BLANK_ELEMENT = 0;

        for(int i = 1; i < config.length; i++){
            if(config[i] == BLANK_ELEMENT){
                return i;
            }
        }
        return -1;
    }

    @Override
    public int compareTo(Node node) {
        return (costFromStart + hCostToGoal) -
                (node.costFromStart + node.hCostToGoal);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node node)) return false;
        return Objects.deepEquals(config, node.config);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(config);
    }
}
