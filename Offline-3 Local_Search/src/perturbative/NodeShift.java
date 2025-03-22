package perturbative;

import java.util.ArrayList;
import node.Node;

public class NodeShift implements PerturbativeSearch {
    private ArrayList<Node> path;
    private final String name = "Node Shift";

    public NodeShift() {
        this.path = null;
    }

    @Override
    public double search(ArrayList<Node> path) {
        this.path = path;

        for(int swappingIndex = 0; swappingIndex < path.size(); swappingIndex++) {
            for(int j = 1; j < path.size() - 1; j++) {
                int dstIndex = increaseIndex(swappingIndex, j);

                double gain = gain(swappingIndex, dstIndex);
                if(gain > 1e-5) {
                    shiftNode(swappingIndex, dstIndex);
                    //System.out.println(swappingIndex + " " + dstIndex);
                    return gain;
                }
            }
        }

        return 0;
    }

    @Override
    public ArrayList<Node> getTour() {
        return path;
    }

    @Override
    public String getName() {
        return name;
    }

    private void shiftNode(int swappingIndex, int destIndex) {
        int index = swappingIndex;
        Node swappingNode = path.get(swappingIndex);

        while(index != destIndex) {
            path.set(index, path.get(increaseIndex(index,1)));
            index = increaseIndex(index,1);
        }
        path.set(index, swappingNode);
    }

    private double gain(int swappingIndex, int destIndex) {
        Node prevNode = path.get(prevIndex(swappingIndex));
        Node nextNode = path.get(increaseIndex(swappingIndex,1));
        Node swappingNode = path.get(swappingIndex);
        Node firstDstNode = path.get(destIndex);
        Node secondDstNode = path.get(increaseIndex(destIndex,1));


        double deletedLength = swappingNode.distance(prevNode) +
                swappingNode.distance(nextNode) +
                firstDstNode.distance(secondDstNode);

        double addedLength = nextNode.distance(prevNode) +
                swappingNode.distance(firstDstNode) +
                swappingNode.distance(secondDstNode);

        return deletedLength - addedLength;
    }

    private int increaseIndex(int index, int increment) {
        return (index + increment) % path.size();
    }

    private int prevIndex(int index) {
        if(index == 0) {
            return path.size() - 1;
        }
        return index - 1;
    }
}
