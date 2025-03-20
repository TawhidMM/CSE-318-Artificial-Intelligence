import java.util.Random;
import java.util.Scanner;

public class Main {
    static final int MIN_DEPTH = 3;
    static final int MAX_DEPTH = 8;
    static int TOTAL_MATCH = 100;

    static final Heuristic[] heuristics = {
            new Heuristic1(), new Heuristic2(),
            new Heuristic3(), new Heuristic4()
    };
    static final String[] heuristicName = {
            "Heuristic1", "Heuristic2",
            "Heuristic3", "Heuristic4"
    };

    static final int[] heuristicResults = new int[heuristics.length];
    static final int[] heuristicPlayed = new int[heuristics.length];


    public static void main(String[] args) {
        Random rand = new Random();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Total number of matches: ");
        TOTAL_MATCH = scanner.nextInt();

        for(int i = 1; i <= TOTAL_MATCH; i++) {
            System.out.print("Match- " + i + ": ");

            int maxHeuristic = rand.nextInt(heuristics.length);
            int minHeuristic = rand.nextInt(heuristics.length);
            int maxDepth = rand.nextInt(MAX_DEPTH-MIN_DEPTH) + MIN_DEPTH;
            int minDepth = rand.nextInt(MAX_DEPTH-MIN_DEPTH) + MIN_DEPTH;

            System.out.println("MAX PLAYER- " + heuristicName[maxHeuristic] +
                    ", MIN PLAYER- " + heuristicName[minHeuristic]);

            Game game = new Game();
            game.createMaxPlayer(heuristics[maxHeuristic], maxDepth);
            game.createMinPlayer(heuristics[minHeuristic], minDepth);

            boolean maxWon = game.simulateGame();

            if(maxWon) {
                heuristicResults[maxHeuristic]++;
                System.out.println("MAX winnerrrrr !!!!!");
            }
            else {
                heuristicResults[minHeuristic]++;
                System.out.println("MIN winnerrrrr !!!!!");
            }
            System.out.println("_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _  ");

            heuristicPlayed[maxHeuristic]++;
            heuristicPlayed[minHeuristic]++;
            System.out.println();
        }

        printReport();

    }

    static void printReport() {
        System.out.println("   name " + "\t   won" + "\t   played");
        System.out.println("-----------------------------");

        for(int i = 0; i < heuristicName.length; i++) {
            System.out.println(heuristicName[i] + "\t\t" +
                    heuristicResults[i] + "\t\t" +
                    heuristicPlayed[i]);
        }
    }

}
