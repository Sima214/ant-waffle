package sima.antwaffle.func_a;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import sima.antwaffle.types.Ant;

public class SpanningTree {

    private final List<Edge> edges;
    private double cost;

    SpanningTree() {
        edges = new ArrayList<>();
        cost = 0.0;
    }

    /**
     * Given the amount of vertices, returns if we've reached the point where we have a Minimum
     * Tree. Implies that no edges were added which add cycles.
     */
    boolean isMinimum(int total_vertex_count) {
        // |E| = |V| - 1
        return edges.size() == total_vertex_count - 1;
    }

    /**
     * Add an edge. Does not perform cycle testing.
     */
    void addEdge(Edge e) {
        edges.add(e);
        cost += e.distance();
    }

    /**
     * Dump contents of this Spanning Tree to an output stream.
     */
    public void dump(PrintWriter w) throws IOException {
        // Correct order of edges.
        edges.sort((a, b) -> {
            int c = a.v1.id - b.v1.id;
            if (c != 0) {
                return c;
            } else {
                return a.v2.id - b.v2.id;
            }
        });
        // Dump.
        w.println(cost);
        for (Edge e : edges) {
            boolean v1s = e.v1.id <= e.v2.id;
            int a = (v1s ? e.v1.id : e.v2.id) + 1;
            int b = (v1s ? e.v2.id : e.v1.id) + 1;
            w.printf("%d\t%d\n", a, b);
        }
    }

    public static class Edge {

        public final Ant v1;
        public final Ant v2;
        private final double d;

        public Edge(Ant v1, Ant v2) {
            this.v1 = v1;
            this.v2 = v2;
            // Precalc to avoid sqrt overhead.
            double x = v1.x - v2.x;
            double y = v1.y - v2.y;
            d = Math.sqrt(x * x + y * y);
        }

        public static Comparator<Edge> getEdgeComparator() {
            return (a, b) -> (int) Math.signum(a.d - b.d);
        }

        /**
         * Calculate the euclidean distance of this edge.
         * <p>
         * Constant time complexity.
         *
         * @return euclidean distance
         */
        public double distance() {
            return d;
        }
    }
}
