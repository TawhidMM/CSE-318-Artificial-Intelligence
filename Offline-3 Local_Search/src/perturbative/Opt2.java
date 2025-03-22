package perturbative;

import node.Node;
import java.util.ArrayList;

public class Opt2 implements PerturbativeSearch {
    private ArrayList<Node> path;
    private final String name = "2-Opt";

    public Opt2() {
        this.path = null;
    }

    @Override
    public double search(ArrayList<Node> path) {
        this.path = path;

        for(int x1 = 0; x1 < path.size(); x1++) {
            for(int j = 2; j < path.size(); j++) {
                int y1 = (x1 + j) % path.size();

                if(y1 - x1 < 2) {
                    continue;
                }

                double gain = gain(x1, y1);
                if(gain > 0) {
                    shiftNode(x1, y1);
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

    private void shiftNode(int x1, int y1) {
        int x2 = nextIndex(x1);

        while(x2 < y1) {
            Node temp = path.get(x2);
            path.set(x2, path.get(y1));
            path.set(y1, temp);
            x2++;
            y1--;
        }
    }

    private double gain(int x1, int y1) {
        double deletedLength = path.get(x1).distance(path.get(nextIndex(x1))) +
                path.get(y1).distance(path.get(nextIndex(y1)));

        double addedLength = path.get(x1).distance(path.get(y1)) +
                path.get(nextIndex(x1)).distance(path.get(nextIndex(y1)));

        return deletedLength - addedLength;
    }

    private int nextIndex(int index) {
        return (index + 1) % path.size();
    }
}
