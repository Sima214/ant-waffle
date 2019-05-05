package sima.antwaffle.func_a;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import sima.antwaffle.types.Ant;

/**
 * Static method creating a SpanningTree using the Kruskal Union Find algorithm.
 */
public class KruskalUnionFind {

    /**
     * Takes a dataset and returns the minimum spanning tree.
     */
    public static SpanningTree calc(List<Ant> dataset) {
        // Initialize data structures used by the algorithm.
        int n = dataset.size();
        SpanningTree mst = new SpanningTree();
        // Union-Find (optimized for find operations).
        @SuppressWarnings("unchecked")
        LinkedList<Integer>[] unions = (LinkedList<Integer>[]) new LinkedList[n];
        // Generate all edges O(n^2).
        SpanningTree.Edge[] all_edges = new SpanningTree.Edge[n * (n - 1) / 2];
        int all_edges_end = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                all_edges[all_edges_end++] = new SpanningTree.Edge(dataset.get(i), dataset.get(j));
            }
        }
        // Sort edges in ascending order O(eloge).
        Arrays.sort(all_edges, SpanningTree.Edge.getEdgeComparator());
        // Kruskal loop.
        all_edges_end = 0;
        while (!mst.isMinimum(n)) {
            // The edge we are trying to add at the current iteration.
            SpanningTree.Edge current_edge = all_edges[all_edges_end++];
            // Test for cycles - find O(1).
            LinkedList<Integer> u1 = unions[current_edge.v1.id];
            LinkedList<Integer> u2 = unions[current_edge.v2.id];
            if (u1 == null) {
                // make_set O(1)
                u1 = new LinkedList<>();
                u1.add(current_edge.v1.id);
                unions[current_edge.v1.id] = u1;
            }
            if (u2 == null) {
                // make_set O(1)
                u2 = new LinkedList<>();
                u2.add(current_edge.v2.id);
                unions[current_edge.v2.id] = u2;
            }
            if (u1 != u2) {
                // No cycle found!
                // Add edge to tree.
                mst.addEdge(current_edge);
                // Unify u1 with u2 - O(n).
                if (u1.size() <= u2.size()) {
                    // u1 is smaller.
                    u2.addAll(u1);
                    // Migrate u1 to u2.
                    for (int id : u1) {
                        unions[id] = u2;
                    }
                } else {
                    // u2 is smaller.
                    u1.addAll(u2);
                    // Migrate u2 to u1.
                    for (int id : u2) {
                        unions[id] = u1;
                    }
                }
            }
        }
        return mst;
    }

}
