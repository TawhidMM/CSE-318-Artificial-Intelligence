import java.util.*;

public class DataSet {
    private final List<Map<String, Object>> data;
    private final List<String> featureAttributes;
    private final String targetAttribute;
    private final Map<String, List<Object>> uniqueValues;


    public DataSet(List<Map<String, Object>> data, List<String> attributes) {
        this.data = data;
        this.uniqueValues = new HashMap<>();
        this.targetAttribute = attributes.get(attributes.size() - 1);
        this.featureAttributes = attributes.subList(0, attributes.size() - 1);

        populateUniqueValues();
    }

    private DataSet(
        List<Map<String, Object>> data,
        List<String> featureAttributes,
        String targetAttribute
    ) {
        this.data = data;
        this.featureAttributes = featureAttributes;
        this.targetAttribute = targetAttribute;
        this.uniqueValues = new HashMap<>();

        populateUniqueValues();
    }

    public List<Map<String, Object>> filterOut(String attribute, Object value) {
        List<Map<String, Object>> filteredData = new ArrayList<>();

        for (Map<String, Object> row : data) {
            if (row.get(attribute).equals(value)) {
                filteredData.add(row);
            }
        }
        return filteredData;
    }

    public DataSet subDataSet(String attribute, Object value) {
        List<Map<String, Object>> filteredData = filterOut(attribute, value);
        return new DataSet(filteredData, featureAttributes, targetAttribute);
    }

    public DataSet getSubset(int fromIndex, int toIndex) {
        List<Map<String, Object>> subset = data.subList(fromIndex, toIndex);
        return new DataSet(subset, featureAttributes, targetAttribute);
    }

    private void populateUniqueValues() {
        for (String attribute : featureAttributes) {
            uniqueValues.put(attribute, uniqueValues(attribute));
        }

        uniqueValues.put(targetAttribute, uniqueValues(targetAttribute));
    }

    private List<Object> uniqueValues(String attribute) {
        Set<Object> uniqueValues = new HashSet<>();

        for (Map<String, Object> row : data) {
            uniqueValues.add(row.get(attribute));
        }

        return new ArrayList<>(uniqueValues);
    }

    public Map<Object, Integer> valueCount(String attribute) {
        Map<Object, Integer> valueCounts = new HashMap<>();

        for (Map<String, Object> row : data) {
            Object attributeValue = row.get(attribute);
            valueCounts.put(attributeValue,
                    valueCounts.getOrDefault(attributeValue, 0) + 1);
        }

        return valueCounts;
    }

    public List<Object> getValues(String attribute) {
        List<Object> targetValues = new ArrayList<>();
        for (Map<String, Object> row : data) {
            targetValues.add(row.get(attribute));
        }
        return targetValues;
    }

    public int getSize() {
        return data.size();
    }

    public String getTargetAttribute() {
        return targetAttribute;
    }

    public List<String> getFeatureAttributes() {
        return featureAttributes;
    }

    public List<Object> getUniqueValues(String attribute) {
        return uniqueValues.get(attribute);
    }

    public List<Map<String, Object>> getData() {
        return data;
    }
}
