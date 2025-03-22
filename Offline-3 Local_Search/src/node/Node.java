package node;

public class Node {
    private final double x;
    private final double y;

    public Node(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double distance(Node n) {
        return Math.sqrt((x - n.x)*(x - n.x) + (y - n.y)*(y - n.y));
    }

    public static double distance(Node n1, Node n2) {
        return Math.sqrt((n1.x - n2.x)*(n1.x - n2.x) +
                (n1.y - n2.y)*(n1.y - n2.y));
    }

    @Override
    public String toString() {
        return "{" +
                 x +
                ", " + y +
                '}';
    }
}
