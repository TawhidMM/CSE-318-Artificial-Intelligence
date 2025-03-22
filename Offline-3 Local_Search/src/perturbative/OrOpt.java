package perturbative;

import node.Node;
import java.util.ArrayList;
import java.util.Collections;


public class OrOpt implements PerturbativeSearch {
    private ArrayList<Node> path;
    private final String name = "OrOpt";

    public OrOpt() {
        this.path = null;
    }

    @Override
    public double search(ArrayList<Node> path) {
        this.path = path;

        int[] segmentLengths = {3,2,1};

        for(int segmentLength : segmentLengths) {
            //System.out.println("Segment Length: " + segmentLength);
            for(int x1 = 0; x1 < path.size(); x1++) {
                int y1 = increaseIndex(x1, segmentLength - 1);

                for(int i = segmentLength; i < path.size() - 1; i++) {
                    int z1 = increaseIndex(x1, i);

                    double gain = gain(x1, y1, z1);
                    if(gain > 1e-5) {
                        shiftNode(x1, y1, z1);
                        //System.out.println(x1 + " " + y1 + " " + z1);
                        return gain;
                    }
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

    private void shiftNode(int x1, int y1, int z1) {
        ArrayList<Node> prevTour = new ArrayList<>(path);
        Collections.copy(prevTour, path);

        int index = increaseIndex(y1, 1);
        int count = 0;

        while(count < prevTour.size()) {
            path.set(count, prevTour.get(index));
            count++;

            if(index == z1) {
                index = x1;
            }
            else if (index == y1) {
                index = increaseIndex(z1, 1);
            }
            else {
                index = increaseIndex(index, 1);
            }
        }

    }

    /*private void s2(int x1, int y1, int z1) {
        int segmentSize = Math.abs(x1 - y1) + 1;
        Node[] temp = new Node[segmentSize];
        int copied = 0;
        int index = x1;

        while(copied < segmentSize) {
            temp[copied++] = path.get(index);
            index = increaseIndex(index, 1);
        }

        index = x1;
        int index2 = increaseIndex(y1,1);
        segmentSize = Math.abs(z1 - y1);
        copied = 0;
        while (copied < segmentSize) {
            path.set(index, path.get(index2));
            index = increaseIndex(index,1);
            index2 = increaseIndex(index2,1);
            copied++;
        }
        copied = 0;
        while(copied < temp.length) {
            path.set(index, temp[copied]);
            index = increaseIndex(index, 1);
            copied++;
        }
    }*/

    private double gain(int x1, int y1, int z1) {
        Node prevSegmentNode = path.get(prevIndex(x1));
        Node segmentStartingNode = path.get(x1);
        Node segmentEndingNode = path.get(y1);
        Node postSegmentNode = path.get(increaseIndex(y1,1));
        Node insertingNode = path.get(z1);
        Node nextInsertingNode = path.get(increaseIndex(z1,1));


        double deletedLength = segmentStartingNode.distance(prevSegmentNode) +
                segmentEndingNode.distance(postSegmentNode) +
                nextInsertingNode.distance(insertingNode);

        double addedLength = prevSegmentNode.distance(postSegmentNode) +
                insertingNode.distance(segmentStartingNode) +
                segmentEndingNode.distance(nextInsertingNode);

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

