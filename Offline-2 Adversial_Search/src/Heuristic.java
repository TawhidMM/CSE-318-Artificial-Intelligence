public abstract class Heuristic {
    abstract int getCost(Node node);

    int mancalaDifference(Node node) {
        return node.getGem(Mancala.MAX_MANCALA) - node.getGem(Mancala.MIN_MANCALA);
    }

    int gemDifference(Node node) {
        int maxGem = 0;
        int minGem = 0;

        for(int i = 0; i < Mancala.MAX_MANCALA; i++)
            maxGem += node.getGem(i);
        for(int i = Mancala.MAX_MANCALA + 1; i < Mancala.MIN_MANCALA; i++)
            minGem += node.getGem(i);

        return maxGem - minGem;
    }
}

class Heuristic1 extends Heuristic {
    @Override
    public int getCost(Node node) {
        return mancalaDifference(node);
    }
}

class Heuristic2 extends Heuristic {
    private int w1 = 6;
    private int w2 = 1;

    public Heuristic2() {}

    public Heuristic2(int w1, int w2) {
        this.w1 = w1;
        this.w2 = w2;
    }

    @Override
    public int getCost(Node node) {
        return w1 * mancalaDifference(node) + w2 * gemDifference(node);
    }
}

class Heuristic3 extends Heuristic {
    private int w1 = 6;
    private int w2 = 1;
    private int w3 = 3;

    public Heuristic3() {}

    public Heuristic3(int w1, int w2, int w3) {
        this.w1 = w1;
        this.w2 = w2;
        this.w3 = w3;
    }

    @Override
    public int getCost(Node node) {
        return w1 * mancalaDifference(node) + w2 * gemDifference(node) +
                w3 * node.extraMove();

    }
}

class Heuristic4 extends Heuristic {
    private int w1 = 6;
    private int w2 = 1;
    private int w3 = 3;
    private int w4 = 3;

    public Heuristic4() {}

    public Heuristic4(int w1, int w2, int w3, int w4) {
        this.w1 = w1;
        this.w2 = w2;
        this.w3 = w3;
        this.w4 = w4;
    }

    @Override
    public int getCost(Node node) {
        return w1 * mancalaDifference(node) + w2 * gemDifference(node) +
                w3 * node.extraMove() + w4 * node.getCapturedGem();
    }
}