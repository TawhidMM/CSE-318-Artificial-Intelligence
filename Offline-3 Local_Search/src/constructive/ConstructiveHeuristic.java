package constructive;

import node.Node;
import java.util.ArrayList;

public interface ConstructiveHeuristic {
    ArrayList<Node> constructivePath(Node[] graph);
    String getName();
}
