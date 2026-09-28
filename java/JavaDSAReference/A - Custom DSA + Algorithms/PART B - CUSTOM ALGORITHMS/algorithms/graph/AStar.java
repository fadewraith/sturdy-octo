package algorithms.graph;

/**
 * A* (A-Star) SEARCH
 * 
 * WHAT IT IS:
 * An incredibly famous pathfinding algorithm used heavily in games and AI. 
 * It finds the shortest path from a start node to a target node faster than Dijkstra's.
 * 
 * WHEN TO USE THIS:
 * - When you want the shortest path, but you have a way to "guess" (Heuristic) 
 *   how close you are to the target.
 * 
 * COMBINATION USAGE:
 * - A* = Dijkstra's Algorithm + Greedy Best-First Search (Heuristic) + Min-Heap.
 * 
 * STRATEGY:
 * Dijkstra always explores equally in all directions (like a circle expanding). 
 * A* uses a Heuristic function to prioritize exploring nodes that seem to lead 
 * closer to the target (stretching the circle into an ellipse).
 * 
 * Equation: F = G + H
 * G: Exact cost from start to current node (This is what Dijkstra uses)
 * H: Heuristic estimated cost from current node to end (This is what Greedy uses)
 * F: Total estimated cost (This is what A* uses to pick the next node in the Min-Heap)
 * 
 * COMPLEXITY:
 * Time: Depends highly on the Heuristic. Worst case O((V+E) log V).
 * Space: O(V)
 */
public class AStar {

    // A simple node representation for a 2D grid graph
    static class Node {
        int x, y;
        int g, h, f;
        Node parent;

        Node(int x, int y) {
            this.x = x;
            this.y = y;
            this.g = 0;
            this.h = 0;
            this.f = 0;
            this.parent = null;
        }
    }

    /**
     * Calculates the Manhattan Distance heuristic.
     * Suitable when movement is restricted to 4 directions (up, down, left, right).
     */
    private static int heuristic(Node a, Node target) {
        return Math.abs(a.x - target.x) + Math.abs(a.y - target.y);
    }

    /**
     * A simplified A* implementation over a 2D grid where 1 is a wall and 0 is path.
     * To avoid external dependencies, we use a basic array search for the minimum F value 
     * instead of a true Priority Queue, but the mathematical logic is exactly A*.
     */
    public static void findPath(int[][] grid, Node start, Node target) {
        int rows = grid.length;
        int cols = grid[0].length;

        boolean[][] closedSet = new boolean[rows][cols];
        Node[][] openSet = new Node[rows][cols];
        
        openSet[start.x][start.y] = start;

        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};

        while (true) {
            // 1. Find the node in openSet with the lowest F score (A* magic happens here)
            Node current = getLowestFNode(openSet, rows, cols);
            
            if (current == null) {
                System.out.println("No path found!");
                return;
            }

            // 2. If we reached the target, reconstruct and print the path
            if (current.x == target.x && current.y == target.y) {
                System.out.println("Path Found! Total Cost (G): " + current.g);
                printPath(current);
                return;
            }

            // 3. Move current from openSet to closedSet
            openSet[current.x][current.y] = null;
            closedSet[current.x][current.y] = true;

            // 4. Check neighbors
            for (int i = 0; i < 4; i++) {
                int nx = current.x + dx[i];
                int ny = current.y + dy[i];

                // Bounds and obstacle check
                if (nx < 0 || nx >= rows || ny < 0 || ny >= cols) continue;
                if (grid[nx][ny] == 1 || closedSet[nx][ny]) continue; // Wall or already fully evaluated

                int tentativeG = current.g + 1; // Assuming cost to move is 1

                Node neighbor = openSet[nx][ny];
                if (neighbor == null) {
                    neighbor = new Node(nx, ny);
                    openSet[nx][ny] = neighbor;
                } else if (tentativeG >= neighbor.g) {
                    continue; // This is not a better path
                }

                // This path is the best so far. Record it!
                neighbor.parent = current;
                neighbor.g = tentativeG;
                neighbor.h = heuristic(neighbor, target);
                neighbor.f = neighbor.g + neighbor.h; // F = G + H
            }
        }
    }

    private static Node getLowestFNode(Node[][] openSet, int rows, int cols) {
        Node minNode = null;
        int minF = Integer.MAX_VALUE;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Node n = openSet[i][j];
                if (n != null && n.f < minF) {
                    minF = n.f;
                    minNode = n;
                }
            }
        }
        return minNode;
    }

    private static void printPath(Node target) {
        if (target.parent != null) {
            printPath(target.parent);
        }
        System.out.print("(" + target.x + "," + target.y + ") ");
    }

    public static void main(String[] args) {
        System.out.println("--- A* SEARCH DEMO ---");
        
        // 0 = Path, 1 = Wall
        int[][] grid = {
            {0, 0, 0, 0, 0},
            {0, 1, 1, 1, 0},
            {0, 0, 0, 1, 0},
            {0, 1, 0, 0, 0},
            {0, 0, 0, 0, 0}
        };

        Node start = new Node(0, 0);
        Node target = new Node(4, 4);
        
        findPath(grid, start, target);
        System.out.println();
    }
}
