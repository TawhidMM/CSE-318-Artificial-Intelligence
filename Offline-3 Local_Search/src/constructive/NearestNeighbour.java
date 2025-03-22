package constructive;

import node.Node;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;

public class NearestNeighbour implements ConstructiveHeuristic {
    private final int DEFAULT_CANDIDATE = 1;
    private Node[] graph;
    private final int candidate;
    private final String name = "Nearest Neighbour";


    public NearestNeighbour() {
        this.graph = null;
        this.candidate = DEFAULT_CANDIDATE;
    }

    public NearestNeighbour(int candidate) {
        this.graph = null;
        this.candidate = candidate;
    }

    @Override
    public ArrayList<Node> constructivePath(Node[] graph) {
        this.graph = graph;

        ArrayList<Node> path = new ArrayList<>();
        ArrayList<NearestNode> candidateNodes;
        Random random = new Random();
        boolean[] added = new boolean[graph.length];

        int startIndex = new Random().nextInt(graph.length);

        path.add(graph[startIndex]);
        added[startIndex] = true;

        while(path.size() < graph.length) {
            candidateNodes = nearestNeighbour(path.get(path.size()-1), added);
            int randomChoice = random.nextInt(candidateNodes.size());
            int selectedIndex = candidateNodes.get(randomChoice).nodeIndex;

            path.add(graph[selectedIndex]);
            added[selectedIndex] = true;
        }

        return path;
    }

    @Override
    public String getName() {
        return name;
    }

    private ArrayList<NearestNode> nearestNeighbour(Node node, boolean[] added) {
        PriorityQueue<NearestNode> allNodes = new PriorityQueue<>(
                new Comparator<NearestNode>() {
                    @Override
                    public int compare(NearestNode o1, NearestNode o2) {
                        return Double.compare(o1.distance, o2.distance);
                    }
                }
        );
        ArrayList<NearestNode> candidateNodes = new ArrayList<>();

        for (int i = 0; i < graph.length; i++) {
            if(added[i]) {
                continue;
            }

            allNodes.add(
                new NearestNode(i, node.distance(graph[i]))
            );
        }

        for (int i = 0; i < candidate; i++) {
            if(allNodes.isEmpty()) {
                break;
            }
            candidateNodes.add(allNodes.poll());
        }
        return candidateNodes;
    }

    private static class NearestNode {
        int nodeIndex;
        double distance;

        public NearestNode(int nodeIndex, double distance) {
            this.nodeIndex = nodeIndex;
            this.distance = distance;
        }
    }
}
