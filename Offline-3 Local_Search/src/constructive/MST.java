package constructive;

import node.Node;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Random;

public class MST implements ConstructiveHeuristic {
    private Node[] graph;
    private ArrayList<ArrayList<Integer>> mstAdjList;
    private final String name = "MST";

    public MST() {
        this.graph = null;
        this.mstAdjList = null;
    }

    @Override
    public ArrayList<Node> constructivePath(Node[] graph) {
        this.graph = graph;
        ArrayList<Node> path = new ArrayList<>();

        this.mstAdjList = makeMstTree();
        ArrayList<Integer> pathIndices = dfs();

        for(Integer index : pathIndices) {
            path.add(graph[index]);
        }
        return path;
    }

    @Override
    public String getName() {
        return name;
    }

    private ArrayList<ArrayList<Integer>> makeMstTree() {
        ArrayList<ArrayList<Integer>> adjList = new ArrayList<>();

        for(int i = 0; i < graph.length; i++) {
            adjList.add(new ArrayList<>());
        }

        PriorityQueue<MSTNode> queue = new PriorityQueue<>();
        boolean[] addedToMST = new boolean[graph.length];

        int firstIndex = new Random().nextInt(graph.length);
        queue.add(new MSTNode(firstIndex, -1, 0));

        while(!allAdded(addedToMST)) {
            MSTNode node = queue.poll();

            if(addedToMST[node.index]) {
                continue;
            }

            addToAdjList(node, adjList);
            addedToMST[node.index] = true;

            for(int i = 0; i < graph.length; i++) {
                if(addedToMST[i])
                    continue;

                queue.add(
                    new MSTNode(
                        i, node.index,
                        Node.distance(graph[node.index], graph[i])
                    )
                );

            }
        }
        return adjList;
    }

    private boolean allAdded(boolean[] addedToMST) {
        for (boolean b : addedToMST) {
            if (!b) {
                return false;
            }
        }
        return true;
    }

    private void addToAdjList(MSTNode node, ArrayList<ArrayList<Integer>> adjList) {
        if(node.parentIndex != -1) {
            adjList.get(node.parentIndex).add(node.index);
            adjList.get(node.index).add(node.parentIndex);
        }
    }

    private ArrayList<Integer> dfs() {
        ArrayList<Integer> path = new ArrayList<>();
        boolean[] visited = new boolean[graph.length];

        int firstIndex = new Random().nextInt(graph.length);
        dfsUtil(firstIndex, visited, path);

        return path;
    }

    private void dfsUtil(int node, boolean[] visited, ArrayList<Integer> path) {
        path.add(node);
        visited[node] = true;

        for (int adjacent : mstAdjList.get(node)) {
            if (!visited[adjacent]) {
                dfsUtil(adjacent, visited, path);
            }
        }
    }

    private class MSTNode implements Comparable<MSTNode> {
        int index;
        double distance;
        int parentIndex;

        MSTNode(int node, int parent, double distance) {
            this.index = node;
            this.distance = distance;
            this.parentIndex = parent;
        }

        @Override
        public int compareTo(MSTNode o) {
            return Double.compare(this.distance, o.distance);
        }
    }
}
