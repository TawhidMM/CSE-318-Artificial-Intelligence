public class Minimax {
    private final Node initialNode;
    private final Mancala mancala;
    private final Heuristic heuristic;
    private int nextMove = -1;
    private int searchDepth;


    public Minimax(int[] initialState, Heuristic heuristic, boolean maxPlayer) {
        if (maxPlayer)
            this.initialNode = new Node(initialState, Side.MAX, 0, 0);
        else
            this.initialNode = new Node(initialState, Side.MIN, 0, 0);

        this.heuristic = heuristic;
        this.mancala = new Mancala();
    }

    public int nextBestMove(int searchDepth) {
        this.searchDepth = searchDepth;
        minimax(initialNode, Integer.MIN_VALUE,
                Integer.MAX_VALUE, searchDepth);

        return nextMove;
    }

    private int minimax(Node node, int alpha, int beta, int depth) {
        if (depth <= 0 || endNode(node))
            return getCost(node);

        int value;

        if(node.maxPlayer()) {
            value = Integer.MIN_VALUE;

            for (int move : mancala.getMaxBins()) {
                if(node.binEmpty(move)){
                    continue;
                }

                Node child = makeMove(node, move);
                int nextValue = minimax(child, alpha, beta, depth-1);

                if(value < nextValue) {
                    value = nextValue;

                    if(depth == searchDepth)
                        nextMove = move;
                }
                if(value > beta)
                    break;
                alpha = Math.max(alpha, value);
            }
        }
        else {
            value = Integer.MAX_VALUE;

            for (int move : mancala.getMinBins()) {
                if(node.binEmpty(move))
                    continue;

                Node child = makeMove(node, move);
                int nextValue = minimax(child, alpha, beta, depth-1);

                if(value > nextValue) {
                    value = nextValue;

                    if(depth == searchDepth)
                        nextMove = move;
                }
                if(value < alpha)
                    break;
                beta = Math.min(beta, value);
            }
        }

        return value;
    }

    private Node makeMove(Node parent, int bin) {
        int[] childConfig = parent.getConfigCopy();

        int lastBin = mancala.
                distributeGems(childConfig, bin, parent.maxPlayer());

        int extraMove = 0;
        Side nextSide = parent.getToggledSide();

        if(mancala.gotExtraMove(lastBin, parent.maxPlayer())) {
            extraMove = 1;
            nextSide = parent.getSide();
        }

        int capturedGem = mancala.
                captureFromOpposite(childConfig, lastBin, parent.maxPlayer());

        return new Node(childConfig, nextSide, extraMove, capturedGem);
    }

    private boolean endNode(Node node) {
       return mancala.endState(node.getConfigCopy());
    }

    private int getCost(Node node){
        if(endNode(node)) {
            int[] config = node.getConfigCopy();

            int maxGems = mancala.getMaxSideGems(config) +
                    mancala.getMaxMancala(config);
            int minGems = mancala.getMinSideGems(config) +
                    mancala.getMinMancala(config);

            if(maxGems > minGems) {
                return 1;
            }
            else if(maxGems < minGems) {
                return -1;
            }
            else
                return 0;
        }
        return heuristic.getCost(node);
    }

}
