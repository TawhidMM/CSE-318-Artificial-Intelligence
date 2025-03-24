# Decision tree
[Assignment Spec](https://github.com/TawhidMM/CSE-318-Artificial-Intelligence/blob/main/Offline-4%20Decision_Tree/CSE318_%20Assignment%204.pdf)

This assignment implements a decision-tree algorithm for car evaluation using the [dataset](https://github.com/TawhidMM/CSE-318-Artificial-Intelligence/tree/main/Offline-4%20Decision_Tree/data).

### Attribute Selection Methods

The decision tree selects attributes based on:
- Information Gain
- Gini Impurity

### Dataset Splitting

- The dataset is randomly split into 80% training and 20% testing sets.
- The program runs 30 times, each time randomly selecting training and testing sets.

### Selection Strategy

For each dataset, the algorithm selects either the best attribute or a random attribute from the top three.

## Train-Test Report
The [report](https://github.com/TawhidMM/CSE-318-Artificial-Intelligence/blob/main/Offline-4%20Decision_Tree/2005036_decision_tree_report.pdf) contains-
- Accuracy for each iteration
- Average accuracy
- Standard deviation of accuracies
- Tree depth
- Number of nodes in tree
- Number of *leaf* nodes in tree
- Confusion matrix
- Precision and Recall for confusion matrix

## How to Clone ??

To clone only this assignment-

- Clone the whole repo without check-out
    ```bash
    git clone --no-checkout --filter=blob:none git@github.com:TawhidMM/CSE-318-Artificial-Intelligence.git
    ```
- Move Into the Repository
    ```bash
    cd CSE-318-Artificial-Intelligence
    ```
- Enable Sparse Checkout
    ```bash
    git sparse-checkout init --cone
    ```


- Specify the Folder
    ```bash
    git sparse-checkout set "Offline-4 Decision_Tree"
    ```

- Checkout the Files
    ```bash
    git checkout
    ```
- If using ***Intellij***, open the folder as a new project
    ```bash
    file -> new -> Project from Existing Sources -> select "Offline-4 Decision_Tree"
    ```

## Input-Output

### Input
The `car.data` file containing attribute-class values.

### Output  
The program prints the average accuracy for both ***Gini Impurity*** and ***Information Gain***, considering two selection strategies:  

- Choosing the best attribute.  
- Selecting a random attribute from the top three best options.  

![sample output]()

Additionally, it generates the following files and sub-folders inside the `train-test results` folder:  

- **accuracies.csv**: Contains accuracy for each iteration across all combinations of `attribute selection methods` and `selection strategies`.  
- **treeInfo.csv**: Records tree depth, number of leaf nodes, and total number of nodes for each combination.  
- **Gini Impurity 1**: A folder containing a CSV file for each iteration (random train-test split) when selecting the best attribute using Gini Impurity. Each CSV includes actual class values and predicted class values for every row in the test dataset.  
