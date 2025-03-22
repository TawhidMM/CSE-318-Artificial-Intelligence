import constructive.*;
import node.Node;
import perturbative.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class Main {
    static String INPUT_FOLDER = "TSP_benchmark_data";
    static String TSP_EXTENSION = ".tsp";

    static ConstructiveHeuristic[] constructiveHeuristics = {
            new CheapestInsertion(),
            new MST(),
            new NearestNeighbour()
    };

    static PerturbativeSearch[] perturbativeSearches = {
            new NodeShift(),
            new Opt2(),
            new OrOpt()
    };


    public static void main(String[] args) throws FileNotFoundException {
        long startTimer = 0;
        long elapsedTimeCons = 0;
        long elapsedTimePert = 0;

        File[] tspFiles = getFiles(INPUT_FOLDER, TSP_EXTENSION);

        for(File file : tspFiles) {
            //File file = tspFiles[0];
            Node[] graph = createGraph(file);

            System.out.println(file.getName() + " : ");
            System.out.printf("\t%-20s\t\t%-15s\t%-10s\t%-10s\t%-10s\t%-10s%n", "constructive",
                    "perturbative", "cost", "cons time", "pert time", "total time");
            System.out.print("\t----------------------------------------------");
            System.out.println("----------------------------------------------");

            for(ConstructiveHeuristic constructive : constructiveHeuristics) {
                ArrayList<Node> actualInitTour = constructive.constructivePath(graph);

                for(PerturbativeSearch perturbative : perturbativeSearches) {
                    startTimer = System.nanoTime();

                    ArrayList<Node> initTour = (ArrayList<Node>) actualInitTour.clone();

                    elapsedTimeCons = System.nanoTime() - startTimer;
                    startTimer = System.nanoTime();

                    while(true) {
                        double gain = perturbative.search(initTour);

                        if(gain == 0) {
                            break;
                        }
                    }
                    elapsedTimePert = System.nanoTime() - startTimer;

                    double cost =  tourCost(perturbative.getTour());

                    System.out.printf("\t%-20s\t\t%-15s\t%-10.3f\t%-10.3f\t%-10.3f\t%-10.3f%n", constructive.getName(),
                            perturbative.getName(), cost, elapsedTimeCons/1e6, elapsedTimePert/1e6,
                            (elapsedTimeCons + elapsedTimePert)/1e6);
                }
            }
            System.out.println();
        }

    }

    static double tourCost(ArrayList<Node> tour) {
        double cost = 0;

        for(int i = 0; i < tour.size() - 1; i++) {
            cost += Node.distance(tour.get(i), tour.get(i + 1));
        }

        cost += Node.distance(tour.get(tour.size() - 1), tour.get(0));

        return cost;
    }

    static File[] getFiles(String folder, String extension) {
        File[] tspFiles = new File(folder).listFiles(
                new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String name) {
                        return name.endsWith(extension);
                    }
                }
        );
        return tspFiles;
    }

    static Node[] createGraph(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        String line = "";
        int dimension = 0;
        Node[] graph = null;

        while(scanner.hasNextLine()) {
            line = scanner.nextLine();

            if(line.startsWith("DIMENSION")) {
                dimension = Integer.parseInt(line.split(":")[1].trim());
            } else if (line.startsWith("NODE_COORD_SECTION")) {
                graph = new Node[dimension];

                for(int i = 0; i < dimension; i++) {
                    line = scanner.nextLine();
                    String[] node = line.trim().split("\\s+");

                    graph[i] = new Node(Double.parseDouble(node[1]),
                            Double.parseDouble(node[2]));
                }
            }
        }

        return graph;
    }
}
