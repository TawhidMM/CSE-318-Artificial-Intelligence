import java.util.*;

public class Main {
    final static String BLANK_CHAR = "*";


    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("grid size :");
        int gridSize = sc.nextInt();

        System.out.println("enter configuration(use * as blank space) :");

        int blankIndex = -1;
        String[] inputConfig = new String[gridSize*gridSize];

        for (int i = 0; i < inputConfig.length; i++) {
            inputConfig[i] = sc.next();

            if (inputConfig[i].equals(BLANK_CHAR)) blankIndex = i+1;
        }
        int[] parsedInput = parseInput(inputConfig);


        if(AStarUtil.notSolvable(parsedInput, blankIndex)) {
            System.out.println("Not Solvable");

            return;
        }

        /* using  Hamming Heuristics */
        AStarSearch aStar = new AStarSearch(
                parsedInput, targetState(gridSize),
                new HammingHeuristics(targetState(gridSize))
        );

        System.out.println("Hamming Heuristics :");
        ArrayList<int[]> solutionPath = aStar.search();

        System.out.println("\texpanded nodes - " + aStar.getExpandedNodes());
        System.out.println("\texplored nodes - " + aStar.getExploredNodes());

        printSolutionPath(solutionPath);

        /* using  Manhattan Heuristics */
        aStar = new AStarSearch(
                parsedInput, targetState(gridSize),
                new ManhattanHeuristics(targetState(gridSize))
        );

        System.out.println("Manhattan Heuristics :");
        solutionPath = aStar.search();

        System.out.println("\texpanded nodes - " + aStar.getExpandedNodes());
        System.out.println("\texplored nodes - " + aStar.getExploredNodes());

        printSolutionPath(solutionPath);
    }

    private static void printSolutionPath(ArrayList<int[]> solutionPath) {
        ListIterator<int[]> iterator =
                solutionPath.listIterator(solutionPath.size());

        System.out.println("\tnumber of steps - " + (solutionPath.size()-1));
        System.out.println();

        while(iterator.hasPrevious()){
            printState(iterator.previous());
            System.out.println();
        }
    }
    private static void printState(int[] config){
        int gridSize = (int)Math.sqrt(config.length - 1);

        for (int i = 1; i < config.length; i++) {
            if(config[i] == 0){
                System.out.print("* ");
            }
            else{
                System.out.print(config[i] + " ");
            }

            if(i % gridSize == 0){
                System.out.println();
            }
        }

        Objects.hash();
    }
    private static int[] targetState(int gridSize) {
        int[] targetState = new int[gridSize*gridSize + 1];

        for (int i = 1; i < targetState.length - 1; i++) {
            targetState[i] = i;
        }
        targetState[targetState.length - 1] = 0;

        return targetState;
    }
    private static int[] parseInput(String[] config) {
        int[] parsedConfig = new int[config.length + 1];

        for (int i = 0; i < config.length; i++) {
            if (config[i].equals(BLANK_CHAR)) {
                parsedConfig[i + 1] = 0;
            }
            else {
                parsedConfig[i + 1] = Integer.parseInt(config[i]);
            }
        }
        return parsedConfig;
    }
}
