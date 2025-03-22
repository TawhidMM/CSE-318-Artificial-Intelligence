import java.io.File;
import java.io.IOException;
import java.util.*;

public class DataSetUtils {
    public static DataSet creatFromCSV(File file, List<String> attributes) throws IOException {
        List<Map<String, Object>> data = new ArrayList<>();
        Scanner scn = new Scanner(file);

        while (scn.hasNextLine()) {
            String line = scn.nextLine();
            String[] values = line.split(",");
            if (values.length != attributes.size()) {
                System.out.println("Invalid data");
                return null;
            }

            Map<String, Object> newRow = new HashMap<>();
            for (int i = 0; i < values.length; i++) {
                newRow.put(attributes.get(i), values[i]);
            }
            data.add(newRow);
        }
        return new DataSet(data, attributes);
    }

    public static List<DataSet> split(DataSet dataset, double trainRatio) {
        int trainSize = (int) (dataset.getSize() * trainRatio);

        Collections.shuffle(dataset.getData());
        DataSet trainSet = dataset.getSubset(0, trainSize);
        DataSet testSet = dataset.getSubset(trainSize, dataset.getSize());
        return List.of(trainSet, testSet);
    }


}
