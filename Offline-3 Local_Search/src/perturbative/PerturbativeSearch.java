package perturbative;

import node.Node;
import java.util.ArrayList;

public interface PerturbativeSearch {
    double search(ArrayList<Node> path);
    ArrayList<Node> getTour();
    String getName();
}
