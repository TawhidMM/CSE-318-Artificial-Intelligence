# n-Puzzle
[Assignment Spec](https://github.com/TawhidMM/CSE-318-Artificial-Intelligence/blob/main/Offline-1%20A_Star_Search/Assignment_1_N_Puzzle_description.pdf)

This assignment implements **[n-puzzle problem](https://www.geeksforgeeks.org/8-puzzle-problem-using-branch-and-bound/)** using the **A\* search algorithm**.

The implementation uses two heuristics functions To calculate the estimated cost from node 𝑛 to the goal state.

1. ***Hamming distance:*** The number of blocks in the wrong position (not including the blank)  
2. ***Manhattan distance:*** The sum of the Manhattan distances (sum of the vertical and horizontal 
distance) of the blocks to their goal positions.  

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
    git sparse-checkout set "Offline-1 A_Star_Search"
    ```

- Checkout the Files
    ```bash
    git checkout
    ```
- If using ***Intellij***, open the folder as a new project
    ```bash
    file -> new -> Project from Existing Sources -> select "Offline-1 A_Star_Search"
    ```