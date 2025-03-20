public class Mancala {
    private static final int BIN_PER_SIDE = 6;
    static final int MAX_MANCALA = BIN_PER_SIDE;
    static final int MIN_MANCALA = 2 * BIN_PER_SIDE + 1;
    private final int[] maxBins;
    private final int[] minBins;


    public Mancala() {
        maxBins = new int[BIN_PER_SIDE];
        minBins = new int[BIN_PER_SIDE];

        int maxStart = 0;
        int minStart = MAX_MANCALA + 1;

        for (int i = 0; i < BIN_PER_SIDE; i++)
            maxBins[i] = maxStart++;

        for (int i = 0; i < BIN_PER_SIDE; i++)
            minBins[i] = minStart++;
    }


    int distributeGems(int[] config, int bin, boolean maxSide) {
        int nextBin = bin;

        while(config[bin] > 0) {
            nextBin = (nextBin + 1) % config.length;

            if(nextBin == oppositeMancala(maxSide))
                continue;

            config[nextBin] += 1;
            config[bin] -= 1;
        }

        return nextBin;
    }

    public int captureFromOpposite(int[] config, int lastBin, boolean maxSide) {
        if(invalidCaptureBin(lastBin, maxSide))
            return 0;
        if(config[lastBin] > 1 || config[oppositeBin(lastBin)] == 0)
            return 0;

        int capturedGems = config[oppositeBin(lastBin)] + config[lastBin];

        if(maxSide)
            config[MAX_MANCALA] += capturedGems;
        else
            config[MIN_MANCALA] += capturedGems;

        config[lastBin] = 0;
        config[oppositeBin(lastBin)] = 0;

        return capturedGems;
    }

    boolean endState(int[] config) {
        int maxSide = getMaxSideGems(config);
        int minSide = getMinSideGems(config);

        return maxSide == 0 || minSide == 0;
    }

    boolean gotExtraMove(int lastBin, boolean maxSide) {
        if (maxSide && lastBin == MAX_MANCALA)
            return true;
        if (!maxSide && lastBin == MIN_MANCALA)
            return true;

        return false;
    }

    int getMaxMancala(int[] config) {
        return config[MAX_MANCALA];
    }

    int getMinMancala(int[] config) {
        return config[MIN_MANCALA];
    }

    int getMaxSideGems(int[] config) {
        int gemCount = 0;
        for(int bin : maxBins)
            gemCount += config[bin];
        return gemCount;
    }

    int getMinSideGems(int[] config) {
        int gemCount = 0;
        for(int bin : minBins)
            gemCount += config[bin];
        return gemCount;
    }

    private int oppositeMancala(boolean maxPLayer) {
        if(maxPLayer)
            return MIN_MANCALA;
        return MAX_MANCALA;
    }

    private static int oppositeBin(int bin) {
        return bin + 2 * (BIN_PER_SIDE - bin);
    }

    int[] getMaxBins() {
        return maxBins;
    }
    int[] getMinBins() {
        return minBins;
    }

    private boolean invalidCaptureBin(int bin, boolean maxSide) {
        if(bin == MAX_MANCALA || bin == MIN_MANCALA)
            return true;
        if(maxSide)
            return bin > MAX_MANCALA && bin < MIN_MANCALA;
        else
            return bin >= 0 && bin < MAX_MANCALA;
    }

}
