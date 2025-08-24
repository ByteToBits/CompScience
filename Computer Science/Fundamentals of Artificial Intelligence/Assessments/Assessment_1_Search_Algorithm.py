# Description: Assessment 1 Question 5 - Search Algorithm Implementation
#              Implement a Breath-First, Depth-First, Greed-Best-First and A* Search Algorithm for the given dataset
# Subject: ITO 5047 - Fundamentals of Artificial Intelligence
# Monash University
# Author: Tristan Sim Yook Min
# Date: 18/7/2025

import sys
import csv
import heapq

debug_flag = False # For Debugging and Tracing the Path

def graph_search(graph, heuristic_func, start_state, goal_state, search_strategy):
    """
    Graph Search Function to Find the Path From a Starting Node to a Destination Node for a given Graph.
    Allows the User to Choose between the ideal Search Strategy, X ∈ {B, D, G, A}.
    Where, B = Breadth-First-Search, D = Depth-First Search, G = Greedy-Best-First Search and A = A* Algorithm

    Assignment Search Rules:
    The program should then output the path obtained using the specified algorithm X from start node S and target node T. 
    To order nodes in the frontier, use minimum cost with respect to:  
     •  f(i) = depth(i), for BFS and DFS, with BFS prioritising smallest depth and DFS prioritising greatest depth;  
     •  f(i) = h(i), for Greedy best-first search; and  
     •  f(i) = g(i) + h(i), for A (star). 
    
    Args:
        graph (List): 2-D Adjacency Matrix where graph[i][j] represents the path cost from node i to j
        heuristic_func (callable): Function that takes a Node index and returns a Heuristic Estimate
        start_state (int): The Index of the Starting Node, S ∈ {0, 1, 2, 3, 4, 5} (0-Based Indexing)
        goal_state (int): The Index of the Target Node, T ∈ {0, 1, 2, 3, 4, 5} (0-Based Indexing)
        Search Stragies (Char): The Search Algorithm Selected X ∈ {B, D, G, A}
        
    Returns:
        List: List of Nodes Starting From the Start Node to Goal Node (0-Based Indexing) or None if no path exist

    """
    
    # Declare and Initialize Variables 
    explored_nodes = set()                 # Set - Because Explored Nodes are Unique
    parent_nodes = {}
    path_cost = {} 
    path_cost[start_state] = 0             # Assign a Path Cost of 0 for Start State
    parent_nodes[start_state] = None       # Initialize the Root Node (Has No Parent Nodes)
    expansion_order = []
    step_counter = 0                       # For Debugging Purposes
   
    # Initialize Frontier Data Structure based on Search Strategy, X ∈ {B, D, G, A}
    frontier = initializeDataStructures(search_strategy, start_state, heuristic_func)

    # Execute the Graph Search until all Nodes are Exhausted
    while frontier:
        
        # Reset Values for debugging purposes
        current_depth = 0  
        priority = 0       
        step_counter += 1  # For Debugging Purposes

        # Based on the Search Strategy Update the Current Node, Path Cost and Priortiy (for Greedy/A*)
        # A Heap Data Structure will Automatically Sort Lowest Priority to the Front (Smallest Element on Top)
        # Popping a Heap returns and removes the smallest element and automatically reorganizes the elements to maintain the heap structure
        if search_strategy == 'B':                                  # B) Breadth-First-Search - Select Node with Smallest Depth
            current_depth, current_node = heapq.heappop(frontier)    
            current_path_cost = path_cost[current_node]              
        elif search_strategy == 'D':                                # D) Depth-First-Search - Select Node with Greatest Depth
            negative_depth, current_node = heapq.heappop(frontier)  # Greatest Depth (Negtive Cost; greatest will be closer to Zero)
            current_depth = -negative_depth                         # Invert to convert into positive depth for debugging purposes
            current_path_cost = path_cost[current_node]         
        else:                                                       # Greedy / A* Algorithm - Select Node with Smallest f(n) 
            priority, current_path_cost, current_node = heapq.heappop(frontier)      

        # Ignore Nodes already Explored
        if current_node in explored_nodes: 
           continue
        
        # Add Current Node to the Set of Explored Nodes Set 
        explored_nodes.add(current_node)
        expansion_order.append(current_node + 1) 

        # Check if Current Node is Goal Node; if so, Reconstruct the Path and Terminate the Search
        if current_node == goal_state:
            final_path = reconstructPath(goal_state, parent_nodes)  
            if debug_flag: print(f"Order of Expansion: {expansion_order}")
            return final_path
           
        # Get the List of Neighbours for the Current Node
        current_node_neighbours = getNeighbors(graph, current_node)

        # Explore of Graph by Visitng Current Node Neighbouring Nodes
        for neighbour in current_node_neighbours:

            if neighbour not in explored_nodes: # Ignore Neighbours already Explored
                # Compute the New Path Cost to From Current Node to Neighbour
                edge_cost = graph[current_node][neighbour]       
                new_path_cost = current_path_cost + edge_cost  

                if search_strategy == 'B':             # B) Breadth-First Search
                    if neighbour not in path_cost:     # If Neighbour is Unexplored
                        parent_nodes[neighbour] = current_node
                        path_cost[neighbour] = new_path_cost 
                        heapq.heappush(frontier, (current_depth + 1, neighbour)) # Prioritize Smallest Depth Cost

                elif search_strategy == 'D':           # D) Depth-First Search
                    if neighbour not in path_cost:     # If Neighbour is Unexplored
                        parent_nodes[neighbour] = current_node
                        path_cost[neighbour] = new_path_cost 
                        heapq.heappush(frontier, (-(current_depth + 1), neighbour)) # Prioritize Greatest Depth Cost (Negative depths, most negative = deepest)

                else: # Greedy and A* Algorithm
                    # If Neighbour is Unexplored or a Cheaper Path Exist - Update the Current Node to Parent, Path Cost
                    if (path_cost.get(neighbour) is None) or (path_cost[neighbour] > new_path_cost):
                        parent_nodes[neighbour] = current_node
                        path_cost[neighbour] = new_path_cost                
                        priority = calculatePriority(neighbour, new_path_cost, heuristic_func, search_strategy)
                        heapq.heappush(frontier, (priority, new_path_cost, neighbour))  # Push Neighbour to the Frontier
        
        # For Debugging Purposes: To Observe the Search Algorithm Process
        pathTracing(step_counter, current_node, current_depth, current_path_cost, explored_nodes, frontier, search_strategy, heuristic_func, debug_flag)

    # Once all Nodes have been Visited and the Goal State is not found, return None
    if debug_flag: print(f"Order of Expansion: {expansion_order}")
    return None 


def calculatePriority(node, path_cost, heuristic_functions, search_strategy): 
    """
    Calculates the priority for Greedy or A* based on the following parameters.
    
    Args:
        node (int): Current node index in the graph
        path_cost (float): g(n), Path Cost from Start to Current Node
        heuristic_functions (callable): h(n), Heuristic Function
        search_strategy (str): 'G' for Greedy | 'A' for A*
        
    Returns:
        float: Priority Value for the Given Node
    """
    heuristic_cost = heuristic_functions(node)
       
    if search_strategy == 'G':       # G) Greedy-First-Search Heuristics Only
        return heuristic_cost      
    elif search_strategy == 'A':     # A) A* Algorithm: f(n) = g(n) + h(n)
        return path_cost + heuristic_cost  
    else:
        return path_cost             # Fallback for Edge Conditions


def calculatePathCost(path, graph):
    """
    Calculates the Total Cost of a Given Path using information from the Graph Adjacency Matrix
    
    Args:
        path (list): A List of nodes in the Path (One-Based Indexing)
        graph (list): Adjacency matrix
        
    Returns:
        int: Total cost of the path
    """
    if not path or len(path) < 2:           # Check for Empty Paths
        return 0
    total_cost = 0                          # Initialize Total Cost to Zero

    for iter_i in range(len(path) - 1):          # 0-Based Indexing, Iterate through the Nodes in the Given Path
        current_node = path[iter_i] - 1          # Calculate Cost from Node i
        next_hop_node = path[iter_i + 1] - 1     # To Next Hop Node i + 1
        edge_cost = graph[current_node][next_hop_node]
        total_cost += edge_cost
    
    return total_cost


def getNeighbors(graph, node):
    """
    Evaluates and Returns list of neighbors for a Given Node
    
    Args:
       graph (list): Adjacency Matrix where graph[i][j] = cost of edge from i to j or None if no edge
       node (int): the current node index

    Returns: 
       list: List of neighboring node indices
    """
    neighbours = []  # Initialize an Empty Neighbour List

    for iter_i in range(len(graph[node])):
        if graph[node][iter_i] is not None:  # Check if Edge Exists
            neighbours.append(iter_i)

    return neighbours


def initializeDataStructures(search_strategy, start_state, heuristic_function): 
    """
    Initialize the Frontier Data Structures based on Search Strategy

    Args:
       search_strategy (char): Search Strategy Selected, X ∈ {B, D, G, A}
       start_state (int): The Index of the Start Node
       heuristic_function (callable): Function that takes a node index and returns heuristic estimate
    Returns:
       list: Priority Queue (using Heap) initialized based on the Search Strategy
    """
    # A Heap Data Structure will Automatically Sort Lowest Priority to the Front (Smallest element on Top)
    # Popping a Heap returns and removes the smallest element and automatically reorganizes the elements to maintain the heap structure

    if search_strategy == 'B':                           # B) Breadth-First-Search - Select Node with Smallest Depth
        if debug_flag: print("\nAlgorithm: Breadth-First-Search")
        frontier = []                                    # Create a Min-Heap Priority Queue for (depth, node)
        heapq.heappush(frontier, (0, start_state))       # Prioritize Smallest Depth First ~ f(i) = depth(i)

    elif search_strategy == 'D':                         # D) Depth-First-Search - Select Node with Greatest Depth
        if debug_flag: print("\nAlgorithm: Depth-First-Search")  
        frontier = []                                    # Create a Min-Heap Priority Queue for (Negative depth, node)  (Negative depths, most negative = deepest)
        heapq.heappush(frontier, (0, start_state))       # Prioritize Greatest Depth First using Most Negative Values ~ f(i) = depth(i)

    elif search_strategy == 'G':                         # G) Greedy-Best-First Search - Select Node with Smallest Heuristics
        if debug_flag: print("\nAlgorithm: Greedy-Best-First Search")   
        frontier = []                                    # Priority Queue (priority, path_cost, node)
        priority = calculatePriority(start_state, 0, heuristic_function, search_strategy) # Calculate Priority of Start Node
        heapq.heappush(frontier, (priority, 0, start_state))   # Initialize, f(n) = h(n) = 0

    else:                                                # A* Algorithm - Select Node with Smallest f(n) = g(n) + h(n)
        if debug_flag: print("\nAlgorithm: A* Search")                  
        frontier = []                                    # Priority Queue (priority, path_cost, node)
        priority = calculatePriority(start_state, 0, heuristic_function, search_strategy)  # Calculate Priority of Start Node
        heapq.heappush(frontier, (priority, 0, start_state))   # Initialize, f(n) = g(n) + h(n) = 0

    return frontier


def pathTracing(step_counter, current_node, current_depth, current_path_cost, explored_nodes, frontier, search_strategy, heuristic_func, enable):
    """
    For Debugging Purposes Path Tracing for Debugging Purposes to Observe the Search Algorithm Process
    """
    if enable: 
        print(f"Step {step_counter}: Expanding Node {current_node + 1}")
            
        if search_strategy == 'B':    # B) Breadth-First Search
            print(f"Depth: {current_depth}  |   Path Cost: {current_path_cost}  |  Priority Queue size: {len(frontier)}")
            frontier_display = [(node+1, depth) for depth, node in frontier]
            print(f"Open Nodes (Node, Depth): {list(frontier_display)}")

        elif search_strategy == 'D':   # D) Depth-First Search
            print(f"Depth: {current_depth}  |   Path Cost: {current_path_cost}  |  Priority Queue size: {len(frontier)}")
            frontier_display = [(node+1, -neg_depth) for neg_depth, node in frontier]
            print(f"Open Nodes (Node, Depth): {list(frontier_display)}")

        elif search_strategy == 'G':   # G) Greedy-Best-First Search (Priority = h(n))
            heuristic_value = heuristic_func(current_node)
            print(f"h(n): {heuristic_value:.1f}  |   Path Cost: {current_path_cost}  |  Priority Queue size: {len(frontier)}")
            frontier_display = [(priority, cost, node+1) for priority, cost, node in frontier]
            print(f"Open Nodes (Priority, Cost, Node): {list(frontier_display)}")

        else:                          # A* Algorithm 
            heuristic_value = heuristic_func(current_node)
            current_priority = current_path_cost + heuristic_value  # Priority = f(n) = g(n) + h(n)
            print(f"f(n) = g(n) + h(n) = {current_path_cost} + {heuristic_value:.0f} = {current_priority:.0f}  |  Priority Queue size: {len(frontier)}")
            frontier_display = [(priority, cost, node+1) for priority, cost, node in frontier]
            print(f"Open Nodes (Priority, Cost, Node): {list(frontier_display)}")
        
        print(f"Closed Nodes: {sorted([node+1 for node in explored_nodes])}\n")

def reconstructPath(goal_state, parent_nodes):
    """
    Reconstructs the path from start to goal by backtracking through parent nodes

    Args:
       goal_state (int): the Goal Node Index
       parent_nodes (dict): dictionary mapping each node to its parent node

    Returns: 
       list: A List of Nodes starting from the Start Node to Goal Node 
    """  
    reconstructed_path = []     # Initialize an Empty Path List
    current = goal_state        # Start with the Goal Node and Backtrack the Route 
    
    # Reconstruct Path starting from the Goal State to the Root Node
    # Once it Reaches the Root Node, The Current Node will equal None (No Parent Nodes)
    while current is not None:  
        reconstructed_path.append(current)
        current = parent_nodes[current]   

    reconstructed_path.reverse()    # Reverse the Path Order because Goal Node was Appended First
    return reconstructed_path


if __name__ == "__main__":

    # get parameters from command line. We need -1 for our vertex numbers since our indexing starts at 0 in python
    start_state = int(sys.argv[1]) - 1
    goal_state = int(sys.argv[2]) - 1
    search_strategy = sys.argv[3]
    graph_csv = sys.argv[4]
    heuristic_csv = sys.argv[5]

    # get the graph
    graph = []
    with open(graph_csv, "r", encoding='utf-8-sig') as csvfile:
        reader_variable = csv.reader(csvfile, delimiter=",")
        for row in reader_variable:
            row_as_ints = [int(val) if val != '' else None for val in row]
            graph.append(row_as_ints)

    # get the heuristic matrix
    heuristic = []
    with open(heuristic_csv, "r", encoding='utf-8-sig') as csvfile:
        reader_variable = csv.reader(csvfile, delimiter=",")
        for row in reader_variable:
            heuristic_row = [float(val) if val != '' else None for val in row]
            heuristic.append(heuristic_row)

    # once we have the goal we can create the heuristic function using the matrix
    heuristic_func = lambda n: heuristic[n][goal_state]       
            
    # find and print the path. The vertices are numbered as they appear in the original graph
    path = graph_search(graph, heuristic_func, start_state, goal_state, search_strategy)
    if path is not None:
        path = [state + 1 for state in path]
        path_cost = calculatePathCost(path, graph)
        if debug_flag: print(f"Path Cost: {path_cost}")
    print(path)

# To Run the Script: python a1_q5.py 1 5 A graph.csv h_sld.csv
