import java.util.*;

public class DecisionTree {
    private Node root;
    private final int SELECT_FROM_BEST;
    private final AttributeSelector selector;

    public DecisionTree() {
        this.selector = new InformationGain();
        this.SELECT_FROM_BEST = 1;
    }

    public DecisionTree(AttributeSelector selector, int SELECT_FROM_BEST) {
        this.selector = selector;
        this.SELECT_FROM_BEST = SELECT_FROM_BEST;
    }

    public void train(DataSet dataSet) {
        HashSet<String> remainingAttributes = new HashSet<>(dataSet.getFeatureAttributes());
        Map<String, List<Object>> uniqueValues = new HashMap<>();

        for(String attribute : dataSet.getFeatureAttributes()) {
            uniqueValues.put(attribute, dataSet.getUniqueValues(attribute));
        }

        root = buildTree(dataSet, remainingAttributes, uniqueValues);
    }

    private Node buildTree(
            DataSet dataSet,
            HashSet<String> remainingAttributes,
            Map<String, List<Object>> uniqueValues
    ) {
        if(hasUniqueClassValue(dataSet)) {
            return new Node(
                dataSet.getUniqueValues(
                        dataSet.getTargetAttribute()
                ).get(0)
            );
        }
        if(remainingAttributes.isEmpty() && dataSet.getSize() > 0) {
            return new Node(
                mostFrequentValue(dataSet, dataSet.getTargetAttribute())
            );
        }

        List<String> bestAttributes = nextBestAttributes(dataSet, remainingAttributes);
        int randomIndex = new Random().nextInt(bestAttributes.size());

        Node node = new Node(bestAttributes.get(randomIndex));

        for(Object value : uniqueValues.get(node.attribute)) {
            DataSet subset = dataSet.subDataSet(node.attribute, value);

            if(subset.getSize() == 0) {
                Node child = new Node(
                    mostFrequentValue(dataSet, dataSet.getTargetAttribute())
                );
                node.addChild(value, child);
                continue;
            }
            HashSet<String> remainingAttributesCopy = new HashSet<>(remainingAttributes);
            remainingAttributesCopy.remove(node.attribute);

            node.addChild(value,
                    buildTree(subset, remainingAttributesCopy, uniqueValues));
        }

        return node;
    }

    private List<String> nextBestAttributes(
            DataSet dataSet,
            HashSet<String> remainingAttributes
    ) {
        PriorityQueue<Map.Entry<String, Double>> attributeGains =
                new PriorityQueue<>(selector.getComparator());

        for(String attribute : remainingAttributes) {
            attributeGains.add(Map.entry(attribute,
                    selector.calculateGain(dataSet, attribute)));
        }

        List<String> bestAttributes = new ArrayList<>();

        for(int i = 0; i < SELECT_FROM_BEST; i++) {
            if(attributeGains.isEmpty()) {
                break;
            }
            bestAttributes.add(attributeGains.poll().getKey());
        }

        return bestAttributes;
    }

    private boolean hasUniqueClassValue(DataSet dataset) {
        return dataset.getUniqueValues(
                dataset.getTargetAttribute()
        ).size() == 1;
    }

    private Object mostFrequentValue(DataSet dataSet, String attribute) {
        Map<Object, Integer> valueCounts = dataSet.valueCount(attribute);
        Object mostFrequentValue = null;
        int maxCount = 0;

        for (Map.Entry<Object, Integer> entry : valueCounts.entrySet()) {
            if (entry.getValue() > maxCount) {
                mostFrequentValue = entry.getKey();
                maxCount = entry.getValue();
            }
        }

        return mostFrequentValue;
    }

    public List<Object> predict(DataSet dataset) {
        List<Object> predictions = new ArrayList<>();
        if(root == null) {
            return predictions;
        }

        for (Map<String, Object> instance : dataset.getData()) {
            predictions.add(predict(instance));
        }
        return predictions;
    }

    private Object predict(Map<String, Object> instance) {
        Node current = root;
        while (!current.isLeaf()) {
            Object value = instance.get(current.attribute);
            current = current.children.get(value);
        }
        return current.value;
    }

    public int treeDepth() {
        if(root == null) {
            return 0;
        }
        return root.depth();
    }

    public int countNodes() {
        if(root == null) {
            return 0;
        }
        return root.countNodes();
    }

    public int countLeafNodes() {
        if(root == null) {
            return 0;
        }
        return root.countLeafNodes();
    }

    // Node class
    private static class Node {
        String attribute;
        Object value;
        boolean isLeaf;
        Map<Object, Node> children;

        public Node(String attribute) {
            this.attribute = attribute;
            this.value = null;
            this.children = new HashMap<>();
            this.isLeaf = false;
        }

        public Node(Object leafValue) {
            this.value = leafValue;
            this.isLeaf = true;
        }

        public boolean isLeaf() {
            return isLeaf;
        }

        public void addChild(Object parentValue, Node child) {
            children.put(parentValue, child);
        }


        public int countNodes() {
            if (isLeaf()) return 1;
            int count = 1;
            for (Node child : children.values()) {
                count += child.countNodes();
            }
            return count;
        }

        public int countLeafNodes() {
            if (isLeaf()) return 1;  // This node is a leaf
            int count = 0;
            for (Node child : children.values()) {
                count += child.countLeafNodes();
            }
            return count;
        }

        public int depth() {
            if (isLeaf()) return 0;
            int maxDepth = 0;
            for (Node child : children.values()) {
                maxDepth = Math.max(maxDepth, child.depth());
            }
            return 1 + maxDepth;
        }
    }  // class
}

