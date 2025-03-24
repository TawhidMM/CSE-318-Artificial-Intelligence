# Adversarial Search (Mancala) 
[Assignment Spec](https://github.com/TawhidMM/CSE-318-Artificial-Intelligence/blob/main/Offline-2%20Adversial_Search/Assignment-2-Adversarial-Search.pdf)

This assignment implements a game player which uses adversarial search algorithm 
to play a two-player game, [Mancala](https://www.mathplayground.com/mancala.html).  
The implementation includes `minimax algorithm with alpha-beta pruning`.

## Minimax Algorithm with Alpha-Beta Pruning
```python
function alphabeta(node, depth, α, β, maximizingPlayer) is
    if depth == 0 or node is terminal then
        return the heuristic value of node
    if maximizingPlayer then
        value := −∞
        for each child of node do
            value := max(value, alphabeta(child, depth − 1, α, β, FALSE))
            if value > β then
                break (* β cutoff *)
            α := max(α, value)
        return value
    else
        value := +∞
        for each child of node do
            value := min(value, alphabeta(child, depth − 1, α, β, TRUE))
            if value < α then
                break (* α cutoff *)
            β := min(β, value)
        return value
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
    git sparse-checkout set "Offline-2 Adversial_Search"
    ```

- Checkout the Files
    ```bash
    git checkout
    ```
- If using ***Intellij***, open the folder as a new project
    ```bash
    file -> new -> Project from Existing Sources -> select "Offline-2 Adversial_Search"
    ```

## Input-Output  

### Input  
The number of games to be played.  

### Output  
For each game, the program prints:  

- The board state after each move.  
- The final score.  
- The winner.  

At the end, it also prints:  

- The number of games played using each heuristic.  
- The number of games won by each heuristic.  

![Output Example](output_image.png)  
