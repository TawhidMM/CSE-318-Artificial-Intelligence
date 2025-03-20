public interface CostHeuristics {
    int cost(int[] config);

}

class ManhattanHeuristics implements CostHeuristics {
    private final int[] targetConfig;

    public ManhattanHeuristics(int[] targetConfig) {
        this.targetConfig = targetConfig;
    }

    @Override
    public int cost(int[] config) {
        int cost = 0;

        for (int i = 1; i < config.length; i++) {
            for (int j = 1; j < config.length; j++) {
                if (config[i] == targetConfig[j] && config[i] != 0) {
                    cost += manhattanDistance(i, j);
                    break;
                }
            }
        }
        return cost;
    }

    private int manhattanDistance(int index1, int index2) {
        int gridSize = (int) Math.sqrt(targetConfig.length - 1);

        return Math.abs(
                AStarUtil.getRow(index1, gridSize) -
                AStarUtil.getRow(index2, gridSize)
        ) + Math.abs(
                        AStarUtil.getCol(index1, gridSize) -
                        AStarUtil.getCol(index2, gridSize)
        );
    }
}

class HammingHeuristics implements CostHeuristics {
    private final int[] targetConfig;

    public HammingHeuristics(int[] targetConfig) {
        this.targetConfig = targetConfig;
    }

    @Override
    public int cost(int[] config) {
        int cost = 0;

        for (int i = 1; i < config.length; i++) {
            if(config[i] != targetConfig[i] && config[i] != 0) {
                cost++;
            }
        }
        return cost;
    }
}
