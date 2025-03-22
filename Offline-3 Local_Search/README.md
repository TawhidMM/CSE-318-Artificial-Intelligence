# Local Search(Traveling Salesman Problem)
## Overview
[Assignment Spec]()

This project uses local search(hill climbing search) techniques to implement a solution to the Traveling Salesman Problem (TSP). It combines constructive algorithms for initial tour generation and perturbative approaches for refining the solution. The input graphs were built for [.tsp]() files.

## Algorithms Implemented

### Constructive Approaches

These algorithms were used to built an initial feasible tour:

1. ***Minimum Spanning Tree (MST):*** Uses Prim's or Kruskal's algorithm to construct a spanning tree and then extracts a tour.

2. ***Nearest Neighbor (NN):*** Starts from a random node and iteratively selects the nearest unvisited node.

3. ***Cheapest Insertion:*** Starts with a partial tour and inserts nodes at the least costly positions.

### Perturbative Approaches

These techniques were used to refine the initial tour by making small modifications:

1. [***Node Shift:***](https://tsp-basics.blogspot.com/2017/03/node-shift.html) Moves a node from one position to another in the tour.

2. [***Or-Opt:***](https://tsp-basics.blogspot.com/2017/03/or-opt.html) Moves a sequence of consecutive nodes to a different position.

3. [***2-Opt:***](https://tsp-basics.blogspot.com/2017/03/2-opt-move.html) Reverses segments of the tour to eliminate crossings and reduce cost.


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
## Output
For each [.tsp]() file, `final cost`, `constructive`, `perturbative` and `total` running time is printed in the console.   
![Example Output]()


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
