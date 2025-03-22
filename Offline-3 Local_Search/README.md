# Local Search(Traveling Salesman Problem)
## Overview
[Assignment Spec](https://github.com/TawhidMM/CSE-318-Artificial-Intelligence/blob/main/Offline-3%20Local_Search/cse_318_TSP_assignment_task.pdf)

This assignment uses local search(hill climbing search) techniques to implement a solution to the Traveling Salesman Problem (TSP). It combines constructive algorithms for initial tour generation and perturbative approaches for refining the solution. The input graphs were built for [.tsp](https://github.com/TawhidMM/CSE-318-Artificial-Intelligence/tree/main/Offline-3%20Local_Search/TSP_benchmark_data) files.

All combination of three  `Constructive Approaches` and `Perturbative Approaches` are used to solve the TSPs.

### Constructive Approaches

1. ***Minimum Spanning Tree (MST)*** 

2. ***Nearest Neighbor (NN):***

3. ***Cheapest Insertion:***

### Perturbative Approaches

1. [***Node Shift:***](https://tsp-basics.blogspot.com/2017/03/node-shift.html) 
2. [***Or-Opt:***](https://tsp-basics.blogspot.com/2017/03/or-opt.html) 
3. [***2-Opt:***](https://tsp-basics.blogspot.com/2017/03/2-opt-move.html) 


## Hill-climbing search

```python
function HILL-CLIMBING(problem) returns a state that is a local maximum
    current = MAKE-NODE(problem.INITIAL-STATE)

    loop do
        neighbor = a highest-valued successor of current
        if neighbor.VALUE ≤ current.VALUE then 
            return current.STATE
        current ←neighbor
```

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
    git sparse-checkout set "Offline-3 Local_Search"
    ```

- Checkout the Files
    ```bash
    git checkout
    ```
- If using ***Intellij***, open the folder as a new project
    ```bash
    file -> new -> Project from Existing Sources -> select "Offline-3 Local_Search"
    ```

## Output
For each [.tsp](https://github.com/TawhidMM/CSE-318-Artificial-Intelligence/tree/main/Offline-3%20Local_Search/TSP_benchmark_data) file, `final cost`, `constructive`, `perturbative` and `total` running time is printed in the console.   
![Example Output](https://github.com/TawhidMM/CSE-318-Artificial-Intelligence/blob/main/Offline-3%20Local_Search/output_image/sample-output.png)


## Resources

- [Animated Algorithms for TSP](https://stemlounge.com/animated-algorithms-for-the-traveling-salesman-problem/)
- [Constructive search methods](https://www2.isye.gatech.edu/~mgoetsch/cali/VEHICLE/TSP/TSP005__.HTM)

### Perturbative search methods
- [TSP 3-OPT move](https://tsp-basics.blogspot.com/2017/03/3-opt-move.html)
- [TSP 2-OPT move](https://tsp-basics.blogspot.com/2017/03/2-opt-move.html)
- [TSP 25-OPT move](https://tsp-basics.blogspot.com/2017/03/25-opt.html)
- [TSP OR-OPT move](https://tsp-basics.blogspot.com/2017/03/or-opt.html)
- [TSP Node-Shift move](https://tsp-basics.blogspot.com/2017/03/node-shift.html)
- [TSP Node-Swap move](https://tsp-basics.blogspot.com/2017/03/node-swap.html)
