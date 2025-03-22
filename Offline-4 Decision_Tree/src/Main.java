import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main {
    static final int ITERATIONS = 30;

    static final int[] SELECT_ATTRIBUTE_FROM_BEST = {1, 3};
    static final AttributeSelector[] SELECTORS = {
            new InformationGain(),
            new GiniImpurity()
    };

    static Map<String, List<Double>> allAccuracies;
    static Map<String, Double> averageAccuracies;
    static Map<String, Integer> treeDepth;
    static Map<String, Integer> treeNodeCount;
    static Map<String, Integer> treeLeafNodeCount;


    public static void main(String[] args) throws IOException {
        mapInit();
        CSVWriter csvWriter = new CSVWriter();

        String[] array = {"buying", "maint", "doors", "persons", "lug_boot", "safety", "class"};
        List<String> arrayList = new ArrayList<>(Arrays.asList(array));

        final String DATA_FILE = "data/car.data";
        DataSet fullDataset = DataSetUtils.creatFromCSV(new File(DATA_FILE), arrayList);

        for(int i = 0; i < ITERATIONS; i++) {
            for(int selectFromBest : SELECT_ATTRIBUTE_FROM_BEST) {
                for(AttributeSelector selector : SELECTORS) {

                    if(fullDataset == null) {
                        System.out.println("Dataset is null");
                        return;
                    }

                    List<DataSet> splitData = DataSetUtils.split(fullDataset, 0.8);

                    DataSet trainSet = splitData.get(0);
                    DataSet testSet = splitData.get(1);

                    DecisionTree tree = new DecisionTree(selector, selectFromBest);
                    tree.train(trainSet);

                    storeTreeInfo(tree, selector.getName(), selectFromBest);

                    List<Object> predictions = tree.predict(testSet);
                    List<Object> actual = testSet.getValues(testSet.getTargetAttribute());

                    csvWriter.writePredictionsToCsv(actual, predictions,
                            createFileName(selector.getName(), selectFromBest, i));

                    double accuracy = analyzePrediction(predictions, actual);
                    allAccuracies.get(
                            combinationName(selector.getName(), selectFromBest)
                    ).add(accuracy);
                }
            }

            System.out.println("all combination training and prediction done for iteration- " + i);
        }

        calculateAverageAccuracies();
        printTable();

        csvWriter.writeAccuraciesToCsv();
        csvWriter.writeTreeInfoToCsv();
    }


    static void printTable() {
        System.out.println();
        System.out.printf("%-60s%-40s%n", "", "Average accuracy for " + ITERATIONS + " runs");
        System.out.println();
        System.out.printf("%-60s%-20s%-20s%n", "Attribute selection strategy ",
                "Information gain", "Gini impurity");

        System.out.print("------------------------------------------------");
        System.out.println("-----------------------------------------------");

        for(int selectFromBest : SELECT_ATTRIBUTE_FROM_BEST) {
            System.out.printf("%-60s", "Always select the best attribute");

            for(AttributeSelector selector : SELECTORS) {
                String combination = combinationName(selector.getName(), selectFromBest);
                System.out.printf("%-20f", averageAccuracies.get(combination));
            }
            System.out.printf("%n");
        }

    }

    static double analyzePrediction(List<Object> predictions, List<Object> actual) {
        int correct = 0;
        for (int i = 0; i < actual.size(); i++) {
            if (predictions.get(i).equals(actual.get(i))) {
                correct++;
            }
        }
        double accuracy = (double) correct / predictions.size() * 100;
        //System.out.println("Accuracy: " + accuracy * 100 + "%");

        return accuracy;
    }

    static String createFileName(String selectorName, int selectFromBest, int iteration) {
        String directoryName = combinationName(selectorName, selectFromBest);
        return directoryName + "/" +
                selectorName + "_best_" + selectFromBest + "_" + iteration + ".csv";
    }

    static String combinationName(String selectorName, int selectFromBest) {
        return selectorName + " " + selectFromBest;
    }

    static void mapInit() {
        allAccuracies = new HashMap<>();

        for(int selectFromBest : SELECT_ATTRIBUTE_FROM_BEST) {
            for (AttributeSelector selector : SELECTORS) {
                allAccuracies.put(
                        combinationName(selector.getName(), selectFromBest),
                        new ArrayList<>()
                );
            }
        }

        averageAccuracies = new HashMap<>();
        treeDepth = new HashMap<>();
        treeNodeCount = new HashMap<>();
        treeLeafNodeCount = new HashMap<>();
    }

    static void calculateAverageAccuracies() {
        for(int selectFromBest : SELECT_ATTRIBUTE_FROM_BEST) {
            for(AttributeSelector selector : SELECTORS) {
                String combination = combinationName(selector.getName(), selectFromBest);
                List<Double> accuracies = allAccuracies.get(combination);

                double sum = 0.0;
                for(double accuracy : accuracies) {
                    sum += accuracy;
                }
                sum /= ITERATIONS;
                averageAccuracies.put(combination, sum);
            }
        }
    }

    static void storeTreeInfo(DecisionTree tree, String selectorName, int selectFromBest) {
        String combination = combinationName(selectorName, selectFromBest);

        treeDepth.put(combination, tree.treeDepth());
        treeNodeCount.put(combination, tree.countNodes());
        treeLeafNodeCount.put(combination, tree.countLeafNodes());
    }
}
