import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.PriorityQueue;

public class AStarSearch {
    private final Node root;
    private final int[] targetConfig;
    private final CostHeuristics costHeuristics;
    private final HashSet<Node> exploredNodes;
    private int expandedNodes;

    public AStarSearch(int[] initialConfig, int[] targetConfig,
                       CostHeuristics costHeuristics) {

        this.targetConfig = targetConfig;
        this.costHeuristics = costHeuristics;
        this.root = new Node(initialConfig, costHeuristics.cost(initialConfig));
        this.exploredNodes = new HashSet<>();
    }

    public ArrayList<int[]> search() {

        PriorityQueue<Node> queue = new PriorityQueue<>();

        queue.add(root);

        while(!queue.isEmpty()){
            final Node parent = queue.poll();
            exploredNodes.add(parent);

            if(reachedTargetConfig(parent)) {
                return createSolutionPath(parent);
            }

            for(Node child : getChildren(parent)) {
                if(!exploredNodes.contains(child)) {
                    queue.add(child);
                    expandedNodes++;
                }
            }
        }
        return null;
    }
    private ArrayList<int[]> createSolutionPath(Node parent) {
        ArrayList<int[]> solutionConfigs = new ArrayList<>();
        solutionConfigs.add(parent.getConfig());

        while (parent.getParent() != null){
            solutionConfigs.add(parent.getParent().getConfig());
            parent = parent.getParent();
        }

        return solutionConfigs;
    }
    private ArrayList<Node> getChildren(Node parent){
        ArrayList<Node> children = new ArrayList<>();

        if(slideBlankRight(parent) != null){
            children.add(slideBlankRight(parent));
        }
        if(slideBlankLeft(parent) != null){
            children.add(slideBlankLeft(parent));
        }
        if(slideBlankUp(parent) != null){
            children.add(slideBlankUp(parent));
        }
        if(slideBlankDown(parent) != null){
            children.add(slideBlankDown(parent));
        }

        return children;
    }
    private Node slideBlankRight(Node parent){
        int blankIndex = parent.getBlankIndex();
        int gridSize = (int) Math.sqrt(parent.getConfig().length - 1);
        int blankCol = AStarUtil.getCol(blankIndex, gridSize);

        if(blankCol != gridSize){
            return createChild(parent, blankIndex + 1);
        }
        return null;
    }
    private Node slideBlankLeft(Node parent){
        int blankIndex = parent.getBlankIndex();
        int gridSize = (int) Math.sqrt(parent.getConfig().length - 1);
        int blankCol = AStarUtil.getCol(blankIndex, gridSize);

        if(blankCol != 1){
            return createChild(parent, blankIndex - 1);
        }
        return null;
    }
    private Node slideBlankUp(Node parent){
        int blankIndex = parent.getBlankIndex();
        int gridSize = (int) Math.sqrt(parent.getConfig().length - 1);
        int blankRow = AStarUtil.getRow(blankIndex, gridSize);

        if(blankRow != 1){
            return createChild(parent, blankIndex - gridSize);
        }
        return null;
    }
    private Node slideBlankDown(Node parent){
        int blankIndex = parent.getBlankIndex();
        int gridSize = (int) Math.sqrt(parent.getConfig().length - 1);
        int blankRow = AStarUtil.getRow(blankIndex, gridSize);

        if(blankRow != gridSize){
            return createChild(parent, blankIndex + gridSize);
        }
        return null;
    }
    private Node createChild(Node parent, int newBlank) {
        int[] newConfig = Arrays.copyOf(
                parent.getConfig(), parent.getConfig().length
        );
        int prevBlank = parent.getBlankIndex();

        int temp = newConfig[prevBlank];
        newConfig[prevBlank] = newConfig[newBlank];
        newConfig[newBlank] = temp;

        return new Node(newConfig, parent, costHeuristics.cost(newConfig));
        /*return new Node(newConfig, parent, 10);*/
    }
    private boolean reachedTargetConfig(Node parent) {
        int[] currConfig = parent.getConfig();

        for(int i = 0; i < currConfig.length; i++){
            if(currConfig[i] != targetConfig[i]){
                return false;
            }
        }
        return true;
    }
    public int getExpandedNodes() {
        return expandedNodes;
    }
    public int getExploredNodes() {
        return exploredNodes.size();
    }
}

