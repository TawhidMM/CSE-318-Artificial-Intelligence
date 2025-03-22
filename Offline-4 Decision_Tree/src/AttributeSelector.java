import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface AttributeSelector {
    String getName();
    double calculateGain(DataSet dataSet, String attribute);
    Comparator<Map.Entry<String, Double>> getComparator();
}

class InformationGain implements AttributeSelector {
    private final String name = "Information Gain";

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double calculateGain(DataSet dataSet, String attribute) {
        double initialEntropy = calculateEntropy(dataSet);

        List<Object> values = dataSet.getUniqueValues(attribute);
        double remainder = 0.0;

        for(Object value : values) {
            DataSet subset = dataSet.subDataSet(attribute, value);

            remainder += ((double) subset.getSize() / dataSet.getSize()) *
                    calculateEntropy(subset);
        }

        return initialEntropy - remainder;
    }

    private double calculateEntropy(DataSet dataset) {
        /*Map<Object, Integer> classCounts = new HashMap<>();

        for (Map<String, Object> row : data) {
            Object classValue = row.get(targetAttribute);
            classCounts.put(classValue,
                    classCounts.getOrDefault(classValue, 0) + 1);
        }*/

        Map<Object, Integer> valueCounts = dataset.valueCount(dataset.getTargetAttribute());
        double entropy = 0.0;

        for (Map.Entry<Object, Integer> entry : valueCounts.entrySet()) {
            double probability = (double) entry.getValue() / dataset.getSize();
            entropy -= probability * log2(probability);
        }

        return entropy;
    }

    @Override
    public Comparator<Map.Entry<String, Double>> getComparator() {
        return new Comparator<Map.Entry<String, Double>>() {
            @Override
            public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
                return Double.compare(o2.getValue(), o1.getValue());
            }
        };
    }

    private double log2(double value) {
        return Math.log(value) / Math.log(2);
    }
}

class GiniImpurity implements AttributeSelector {
    private final String name = "Gini Impurity";

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double calculateGain(DataSet dataSet, String attribute) {
        List<Object> values = dataSet.getUniqueValues(attribute);
        double weightedGini = 0.0;

        for (Object value : values) {
            DataSet subset = dataSet.subDataSet(attribute, value);
            double subsetGini = calculateImpurity(subset);
            weightedGini += ((double) subset.getSize() / dataSet.getSize()) * subsetGini;
        }

        return weightedGini;
    }

    private double calculateImpurity(DataSet dataset) {
        Map<Object, Integer> valueCounts = dataset.valueCount(dataset.getTargetAttribute());
        double sameClassProbability = 0.0;

        for (Map.Entry<Object, Integer> entry : valueCounts.entrySet()) {
            double probability = (double) entry.getValue() / dataset.getSize();
            sameClassProbability += Math.pow(probability, 2);
        }

        return 1.0 - sameClassProbability;
    }

    @Override
    public Comparator<Map.Entry<String, Double>> getComparator() {
        return new Comparator<Map.Entry<String, Double>>() {
            @Override
            public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
                return Double.compare(o1.getValue(), o2.getValue());
            }
        };
    }

}