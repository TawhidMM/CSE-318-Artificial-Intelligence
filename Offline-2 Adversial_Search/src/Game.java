public class Game {
    private final int[] state;
    private final Mancala mancala;
    private Heuristic maxPlayerHeuristic;
    private Heuristic minPlayerHeuristic;
    private int maxPlayerDepth;
    private int minPlayerDepth;
    private boolean currentPlayerMax;

    public Game() {
        this.state = new int[14];
        for (int i = 0; i < state.length; i++) {
            if(i == Mancala.MAX_MANCALA || i == Mancala.MIN_MANCALA)
                continue;
            this.state[i] = 4;
        }

        this.mancala = new Mancala();
        this.maxPlayerHeuristic = null;
        this.minPlayerHeuristic = null;
        this.maxPlayerDepth = 0;
        this.minPlayerDepth = 0;
    }

    public void createMaxPlayer(Heuristic maxPlayerHeuristic, int searchDepth) {
        this.maxPlayerHeuristic = maxPlayerHeuristic;
        this.maxPlayerDepth = searchDepth;
    }
    public void createMinPlayer(Heuristic minPlayerHeuristic, int searchDepth) {
        this.minPlayerHeuristic = minPlayerHeuristic;
        this.minPlayerDepth = searchDepth;
    }

    public boolean simulateGame() {
        if(!bothPlayerCreated())
            return false;

        currentPlayerMax = true;
        Minimax currPlayer = new Minimax(state, maxPlayerHeuristic, true);

        while(!endState()) {

            int move;

            if(currentPlayerMax)
                move = currPlayer.nextBestMove(maxPlayerDepth);
            else
                move = currPlayer.nextBestMove(minPlayerDepth);

            int lastBin = makeMove(move);

            printBoard(move);

            if(gotExtraMove(lastBin))
                currPlayer = samePlayer();
            else
                currPlayer = togglePlayer();
        }

        return printResult();
    }

    private int makeMove(int bin){
        int lastBin = mancala.distributeGems(state, bin, currentPlayerMax);
        mancala.captureFromOpposite(state, lastBin, currentPlayerMax);

        return lastBin;
    }

    private Minimax togglePlayer() {
        boolean current = currentPlayerMax;
        currentPlayerMax = !currentPlayerMax;

        if(current)
            return new Minimax(state, minPlayerHeuristic, false);
        else
            return new Minimax(state, maxPlayerHeuristic, true);
    }

    private Minimax samePlayer() {
        if(currentPlayerMax)
            return new Minimax(state, maxPlayerHeuristic, true);
        else
            return new Minimax(state, minPlayerHeuristic, false);
    }

    private boolean gotExtraMove(int lastBin) {
        return mancala.gotExtraMove(lastBin, currentPlayerMax);
    }

    private boolean bothPlayerCreated(){
        return maxPlayerHeuristic != null && minPlayerHeuristic != null &&
            maxPlayerDepth > 0 && minPlayerDepth > 0;
    }

    private boolean endState() {
        return mancala.endState(state);
    }

    private void printBoard(int move) {
        if(currentPlayerMax)
            System.out.println("Max players move: " + move);
        else
            System.out.println("Min players move: " + move);

        int[] minBins = mancala.getMinBins();

        System.out.println("-------------------------");
        /* print min side(upper) of board */
        System.out.print("\t");
        for(int i = minBins.length-1; i >= 0; i--) {
            System.out.print(state[minBins[i]] + "  ");
        }
        System.out.println();

        System.out.println(state[Mancala.MIN_MANCALA] + "\t\t\t\t\t   " +
                state[Mancala.MAX_MANCALA]);

        /* print max side(lower) of board */
        System.out.print("\t");
        for(int bin : mancala.getMaxBins()) {
            System.out.print(state[bin] + "  ");
        }
        System.out.println();
        System.out.println("-------------------------");

    }

    private boolean printResult() {
        int maxGems = mancala.getMaxSideGems(state) +
                mancala.getMaxMancala(state);

        int minGems = mancala.getMinSideGems(state) +
                mancala.getMinMancala(state);

        System.out.println("MAX - " + maxGems + " : " + minGems + " - MIN");

        if(maxGems > minGems) {
            //System.out.println("MAX player WOOOOONN !!!!");
            return true;
        }
        else {
            //System.out.println("MIN player WOOOOONN !!!!");
            return false;
        }

    }
}
