public class AStarUtil {

    static boolean notSolvable(int[] initialConfig, int blankIndex) {
        int inversion = inversionCount(initialConfig);
        int gridSize = (int) Math.sqrt(initialConfig.length - 1);

        if(gridSize % 2 == 1) {
            return (inversion % 2 == 1);
        }
        else {
            int blankRowFromLast = getRowFromLast(blankIndex, gridSize);

            return ((inversion + blankRowFromLast % 2) == 0);
        }
    }

    private static int inversionCount(int[] config){
        int inversionCount = 0;

        for (int i = 1; i < config.length - 1; i++) {
            if (config[i] == 0) continue;

            for (int j = i + 1; j < config.length; j++) {
                if (config[j] == 0) continue;
                if (config[i] > config[j]) inversionCount++;
            }
        }
        return inversionCount;
    }

    static int getRow(int index, int gridSize) {

        if (index % gridSize == 0) {
            return index / gridSize;
        }
        return (index / gridSize) + 1;
    }

    static int getRowFromLast(int index, int gridSize) {
        return gridSize - getRow(index, gridSize) + 1;
    }

    static int getCol(int index, int gridSize) {

        if(index % gridSize == 0){
            return gridSize;
        }
        else{
            return index % gridSize;
        }
    }

}
