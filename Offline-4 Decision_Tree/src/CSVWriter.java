import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVWriter {
    private final String CSV_FOLDER = "train-test results";

    CSVWriter() {
        new File(CSV_FOLDER).mkdir();
    }

    void writePredictionsToCsv(List<Object> yTrue, List<Object> yPred, String fileName) {
        // adding the base directory name
        fileName = CSV_FOLDER + "/" + fileName;

        // creating subdirectory
        new File(CSV_FOLDER + "/" + fileName).getParentFile().mkdir();

        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("actual,prediction\n");

            for (int i = 0; i < yTrue.size(); i++) {
                writer.write(yTrue.get(i) + "," + yPred.get(i) + "\n");
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    void writeAccuraciesToCsv() {
        try (FileWriter writer = new FileWriter(CSV_FOLDER + "/" + "accuracies.csv")) {
            writer.write("Iteration,Information Gain 1, Gini Impurity 1, Information Gain 3,Gini Impurity 3\n");

            int iterations = Main.allAccuracies.get(
                    Main.combinationName(Main.SELECTORS[0].getName(), Main.SELECT_ATTRIBUTE_FROM_BEST[0])
            ).size();

            for(int i = 0; i < iterations; i++) {
                writer.write(i + "");
                for(int selectFromBest : Main.SELECT_ATTRIBUTE_FROM_BEST) {
                    for (AttributeSelector selector : Main.SELECTORS) {
                        double accuracy = Main.allAccuracies.get(
                                Main.combinationName(selector.getName(), selectFromBest)
                        ).get(i);
                        writer.write("," + accuracy);
                    }
                }
                writer.write("\n");
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    void writeTreeInfoToCsv() {
        try (FileWriter writer = new FileWriter(CSV_FOLDER + "/" + "treeInfo.csv")) {
            writer.write("Combination,Tree depth,Node count,Leaf node count\n");

            for(int selectFromBest : Main.SELECT_ATTRIBUTE_FROM_BEST) {
                for (AttributeSelector selector : Main.SELECTORS) {
                    String combination = Main.combinationName(selector.getName(), selectFromBest);

                    writer.write(combination + "," +
                            Main.treeDepth.get(combination) + "," +
                            Main.treeNodeCount.get(combination) + "," +
                            Main.treeLeafNodeCount.get(combination) + "\n");
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

}
