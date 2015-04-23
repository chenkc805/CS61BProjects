import java.util.Observable;
import java.util.ArrayDeque;
/** 
 *  @author Josh Hug
 */

public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields: 
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */

    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze; 
    private ArrayDeque<Integer> q;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);    
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
        q = new ArrayDeque<Integer>();
        q.add(s);
    }

    /** Conducts a breadth first search of the maze starting at vertex v. */
    private void bfs() {
        while (!q.isEmpty()) {
            int v = q.pop();
            announce();
            if (v == t) {
                targetFound = true;
            }

            if (targetFound) {
                return;
            }
            for (int w : maze.adj(v)) {
                if (!marked[w]) {
                    edgeTo[w] = v;
                    distTo[w] = distTo[v] + 1;
                    marked[w] = true;
                    q.add(w);
                }
            }
        }
    }


    @Override
    public void solve() {
        bfs();
    }
} 

