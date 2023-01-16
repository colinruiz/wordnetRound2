import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;


public class ShortestCommonAncestor {
    // private Digraph copy;
    private final Digraph G;


    // constructor takes a rooted DAG as argument
    public ShortestCommonAncestor(Digraph G) {
        if (G == null) {
            throw new IllegalArgumentException();
        }

        this.G = new Digraph(G);

        if (!isRootedDAG(this.G)) {
            throw new IllegalArgumentException();
        }

    }

    // returns a true if the graph is a rooted DAG
    private boolean isRootedDAG(Digraph graph) {
        // see read me for explanation
        Queue<Integer> exploreQueue = new LinkedList<>();
        Queue<Integer> orderQueue = new LinkedList<>();
        int rootcount = 0;
        int[] inDegrees = new int[graph.V()];
        for (int i = 0; i < graph.V(); i++) {
            if (graph.outdegree(i) == 0) {
                rootcount++;
            }
            inDegrees[i] = graph.indegree(i);
            if (graph.indegree(i) == 0) {
                exploreQueue.add(i);
            }
            if (rootcount > 1) {
                return false;
            }
        }
        if (rootcount == 0) {
            return false;
        }
        while (!exploreQueue.isEmpty()) {
            int node = exploreQueue.remove();
            orderQueue.add(node);
            for (int neighbors : graph.adj(node)) {
                inDegrees[neighbors] = inDegrees[neighbors] - 1;
                int in = inDegrees[neighbors];
                if (in == 0) {
                    exploreQueue.add(neighbors);
                }
            }
        }
        return orderQueue.size() == graph.V();
    }

    // private helper method to find nodes that can be reached from a source node
    // returns a HashMap
    private HashMap<Integer, Integer> pathFinder(Digraph graph, int source) {

        Queue<Integer> queue = new LinkedList<Integer>();
        queue.add(source);
        HashMap<Integer, Integer> dist = new HashMap<Integer, Integer>();
        dist.put(source, 0);
        while (queue.peek() != null) {
            int v = queue.remove();
            for (int w : graph.adj(v)) {
                if (!dist.containsKey(w)) {
                    queue.add(w);
                    dist.put(w, 1 + dist.get(v));
                }
            }
        }
        return dist;
    }

    // private helper method to find nodes that can be reached from multiple source nodes
    // returns a HashMap
    private HashMap<Integer, Integer> pathFinder(Digraph graph, Iterable<Integer> source) {
        Queue<Integer> queue = new LinkedList<Integer>();
        HashMap<Integer, Integer> dist = new HashMap<Integer, Integer>();
        for (int nodes : source) {
            queue.add(nodes);
            dist.put(nodes, 0);
        }
        while (queue.peek() != null) {
            int v = queue.remove();
            for (int w : graph.adj(v)) {
                if (!dist.containsKey(w)) {
                    queue.add(w);
                    dist.put(w, 1 + dist.get(v));

                }
            }
        }
        return dist;

    }

    // private helper method that returns the length of the shortest common ancestor
    // or the node
    private int sca(HashMap<Integer, Integer> dist, HashMap<Integer, Integer> dist2,
                    boolean length) {
        int distance = Integer.MAX_VALUE;
        int sca = -1;
        for (int node : dist.keySet()) {
            if (dist2.containsKey(node)) {
                int newDistance = dist.get(node) + dist2.get(node);
                if (distance > newDistance) {
                    distance = newDistance;
                    sca = node;
                }
            }
        }
        if (length) {
            if (distance == Integer.MAX_VALUE) {
                return -1;
            }
            else {
                return distance;
            }
        }
        else {
            return sca;
        }
        // some psuedo code

        // look at key sets from both. If there is a match get the value from both
        // and get the running best.
    }


    // length of shortest ancestral path between v and w
    public int length(int v, int w) {
        if (v < 0 || v > this.G.V()) {
            throw new IllegalArgumentException();
        }
        if (w < 0 || w > this.G.V()) {
            throw new IllegalArgumentException();
        }
        // some psuedocode thoughts
        // first use hasPathTo function like the BreadthFirstDirectedPath one.
        // this see's if two the two inputs both have a path to it.
        // then after that put that length in an list with the ancestor. Maybe
        // a hashmap.  See the hashmap or array with lowest length.
        // use previous hash to trace back
        HashMap<Integer, Integer> dist = pathFinder(this.G, v);
        HashMap<Integer, Integer> dist2 = pathFinder(this.G, w);
        return sca(dist, dist2, true);
    }

    // a shortest common ancestor of vertices v and w
    public int ancestor(int v, int w) {
        if (v < 0 || v > this.G.V()) {
            throw new IllegalArgumentException();
        }
        if (w < 0 || w > this.G.V()) {
            throw new IllegalArgumentException();
        }
        // BreadthFirstDirectedPaths first = new BreadthFirstDirectedPaths(this.G, v);
        // BreadthFirstDirectedPaths second = new BreadthFirstDirectedPaths(this.G, w);
        // return shortestcommonanc(first, second);

        HashMap<Integer, Integer> dist = pathFinder(this.G, v);
        HashMap<Integer, Integer> dist2 = pathFinder(this.G, w);

        return sca(dist, dist2, false);

    }

    // private helper method for throwing exceptions
    private void throwexceptions(Iterable<Integer> subsetA, Iterable<Integer> subsetB) {
        if (subsetA == null || subsetB == null) {
            throw new IllegalArgumentException();
        }
        int nodecountA = 0;
        int nodecountB = 0;
        for (Integer node : subsetA) {
            nodecountA++;
            if (node == null) {
                throw new IllegalArgumentException();
            }
        }
        if (nodecountA == 0) {
            throw new IllegalArgumentException();
        }
        for (Integer node : subsetB) {
            nodecountB++;
            if (node == null) {
                throw new IllegalArgumentException();
            }
        }
        if (nodecountB == 0) {
            throw new IllegalArgumentException();
        }
    }

    // length of shortest ancestral path of vertex subsets A and B
    public int lengthSubset(Iterable<Integer> subsetA, Iterable<Integer> subsetB) {
        throwexceptions(subsetA, subsetB);
        // BreadthFirstDirectedPaths first = new BreadthFirstDirectedPaths(this.G, subsetA);
        // BreadthFirstDirectedPaths second = new BreadthFirstDirectedPaths(this.G, subsetB);
        //
        // return lengths(first, second);

        HashMap<Integer, Integer> dist = pathFinder(this.G, subsetA);
        HashMap<Integer, Integer> dist2 = pathFinder(this.G, subsetB);

        return sca(dist, dist2, true);
    }

    // a shortest common ancestor of vertex subsets A and B
    public int ancestorSubset(Iterable<Integer> subsetA, Iterable<Integer> subsetB) {
        // if (subsetA == null || subsetB == null) {
        //     throw new IllegalArgumentException();
        // }
        throwexceptions(subsetA, subsetB);

        HashMap<Integer, Integer> dist = pathFinder(this.G, subsetA);
        HashMap<Integer, Integer> dist2 = pathFinder(this.G, subsetB);

        return sca(dist, dist2, false);
    }

    // unit testing (required)
    public static void main(String[] args) {
        In digraph = new In(args[0]);
        Digraph G = new Digraph(digraph);
        ShortestCommonAncestor sca = new ShortestCommonAncestor(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sca.length(v, w);
            int ancestor = sca.ancestor(v, w);
            System.out.println("length: " + length);
            System.out.println("ancestor: " + ancestor);
        }


    }

}

// failed code

// helper method to see if the Digraph is rooted
// private boolean isRootedDAG(Digraph graph) {
// DirectedCycle directedCycle = new DirectedCycle(graph);

// if (directedCycle.hasCycle()) {
//     return false;
// }
// int rootcount = 0;
//
// for (int i = 0; i < graph.V(); i++) {
//
//     if (G.outdegree(i) == 0) {
//         rootcount++;
//         this.root = i;
//         if (rootcount > 1) {
//             return false;
//         }
//     }
// if (G.outdegree(i) > 0) {
//     if (!first.hasPathTo(root)) {
//         return false;
//     }
// }

// }
// Digraph reverse = graph.reverse();
// if (!hasCycle(reverse)) {
//     return false;
// }

// if (rootcount == 0) {
//     return false;
// }

// BreadthFirstDirectedPaths first = new BreadthFirstDirectedPaths(reverse, this.root);
// HashMap<Integer, Integer> dist = pathFinder(reverse, root);
//     for(
// int i = 0; i<graph.V();i++)
//
// {
//
//     if (graph.outdegree(i) > 0) {
//         if (!hasPathTo(i, dist)) {
//             return false;
//         }
//         if (!first.hasPathTo(i)) {
//             return false;
//         }
// }
// }
//
//     return true;
//
// }

// private methods for the lengths
// private int lengths(BreadthFirstDirectedPaths first, BreadthFirstDirectedPaths second) {
//     int minimumLength = Integer.MAX_VALUE;
//     int l;
// for (int i = 0; i < this.G.V(); i++) {
//     if (first.hasPathTo(i) && second.hasPathTo(i)) {
//         int length = first.distTo(i) + second.distTo(i);
//         if (length < minimumLength) {
//             minimumLength = length;
//         }
//     }
// }
//
// if (minimumLength == Integer.MAX_VALUE) {
//     return -1;
// }
// else {
//     return minimumLength;
// }
// }

// helper method for the shoretest common ancestor
// private int shortestcommonanc(BreadthFirstDirectedPaths first,
//                               BreadthFirstDirectedPaths second) {
//     int minimumLength = Integer.MAX_VALUE;
//     int sca = -1;
//     int length;
//     for (int i = 0; i < this.G.V(); i++) {
//         if (first.hasPathTo(i) && second.hasPathTo(i)) {
//             length = first.distTo(i) + second.distTo(i);
//             if (length < minimumLength) {
//                 minimumLength = length;
//                 sca = i;
//             }
//         }
//     }
//     if (minimumLength == Integer.MAX_VALUE) {
//         return -1;
//     }
//     else {
//         return sca;
//     }
// }
