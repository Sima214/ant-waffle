package sima.antwaffle.func_a;

import sima.antwaffle.types.Ant;

import java.util.Arrays;
import java.util.List;

/**
 * Static method creating a SpanningTree using the Kruskal Union Find algorithm.
 */
public class KruskalUnionFind {

    /**
     * Takes a dataset and returns the minimum spanning tree.
     */
    public static SpanningTree calc(List<Ant> dataset) {
        int n = dataset.size();
        SpanningTree mst = new SpanningTree();
        // TODO: Generate all edges.
        SpanningTree.Edge[] all_edges = new SpanningTree.Edge[n * (n - 1) / 2];
        int all_edges_end = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                // O(n^2)
                all_edges[all_edges_end++] = new SpanningTree.Edge(dataset.get(i), dataset.get(j));
            }
        }
        // Sort edges in ascending order.
        // O(nlogn)
        Arrays.sort(all_edges, SpanningTree.Edge.getEdgeComparator());
        // Kruskal loop.
        all_edges_end = 0;
        while (!mst.isMinimum(n)) {

        }
        return mst;
    }

}
