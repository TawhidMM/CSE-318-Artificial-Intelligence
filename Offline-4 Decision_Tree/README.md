# Decision tree
[Assignment Spec]()

This assignment implements a decision-tree algorithm for car evaluation using the [dataset]().

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
The [report]() contains-
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