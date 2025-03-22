package constructive;

import node.Node;
import java.util.*;

public class CheapestInsertion implements ConstructiveHeuristic {
    private final int DEFAULT_CANDIDATE = 1;
    private Node[] graph;
    private final int candidate;
    private final String name = "Cheapest Insertion";

    public CheapestInsertion() {
        this.graph = null;
        this.candidate = DEFAULT_CANDIDATE;
    }

    public CheapestInsertion(int candidate) {
        this.graph = null;
        this.candidate = candidate;
    }

    @Override
    public ArrayList<Node> constructivePath(Node[] graph) {
        this.graph = graph;

        ArrayList<Node> path = new ArrayList<>();
        ArrayList<Insertion> candidateNodes;
        Random random = new Random();
        boolean[] added = new boolean[graph.length];
        int firstIndex;
        int secondIndex;

        firstIndex = random.nextInt(graph.length);

        do{
            secondIndex = random.nextInt(graph.length);
        } while (firstIndex == secondIndex);

        path.add(graph[firstIndex]);
        added[firstIndex] = true;

        path.add(graph[secondIndex]);
        added[secondIndex] = true;

        while(path.size() < graph.length) {
            candidateNodes = cheapestNodes(path, added);
            int randomChoice = random.nextInt(candidateNodes.size());

            int newNodeIndex = candidateNodes.get(randomChoice).newNodeIndex;
            int insertIndex = candidateNodes.get(randomChoice).pathInsertIndex;

            path.add(insertIndex, graph[newNodeIndex]);
            added[newNodeIndex] = true;
        }

        return path;
    }

    @Override
    public String getName() {
        return name;
    }


    private ArrayList<Insertion> cheapestNodes(ArrayList<Node> path, boolean[] added) {
        PriorityQueue<Insertion> allInsertion = new PriorityQueue<>(
                new Comparator<Insertion>() {
                    @Override
                    public int compare(Insertion o1, Insertion o2) {
                        return Double.compare(o2.gain, o1.gain);
                    }
                }
        );
        ArrayList<Insertion> candidateInsertions = new ArrayList<>();

        for(int i = 0; i < graph.length; i++){
            if(added[i]){
                continue;
            }

            for(int j = 0; j < path.size(); j++) {
                allInsertion.add(
                    new Insertion(i, j,
                        gain(graph[i], path.get(j),
                            path.get((nextIndex(j, path.size())))
                        )
                    )
                );
            }
        }

        for(int i = 0; i < candidate; i++) {
            if(allInsertion.isEmpty()) {
                break;
            }
            candidateInsertions.add(allInsertion.poll());
        }

        return candidateInsertions;
    }

    private double gain(Node newNode, Node first, Node second) {
        return first.distance(newNode) + newNode.distance(second) -
                first.distance(second);
    }


    private int nextIndex(int index, int length) {
        return (index + 1) % length;
    }

    private static class Insertion {
        int newNodeIndex;
        int pathInsertIndex;
        double gain;

        Insertion(int newNodeIndex, int nodeIndex, double gain) {
            this.newNodeIndex = newNodeIndex;
            this.pathInsertIndex = nodeIndex;
            this.gain = gain;
        }
    }
}
