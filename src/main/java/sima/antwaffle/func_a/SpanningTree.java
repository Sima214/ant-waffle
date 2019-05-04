package sima.antwaffle.func_a;

import sima.antwaffle.types.Ant;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SpanningTree {

    private final List<Edge> edges;

    public SpanningTree() {
        edges = new ArrayList<>();
    }

    public boolean isMinimum(int total_vertex_count) {
        // |E| = |V| - 1
        return edges.size() == total_vertex_count - 1;
    }

    public static class Edge {
        private final Ant v1;
        private final Ant v2;
        private final double d;

        public Edge(Ant v1, Ant v2) {
            this.v1 = v1;
            this.v2 = v2;
            // Precalc to avoid sqrt overhead.
            double x = v1.x - v2.x;
            double y = v1.y - v2.y;
            d = Math.sqrt(x * x + y * y);
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

        public static Comparator<Edge> getEdgeComparator() {
            return (a, b) -> (int) Math.signum(a.d - b.d);
        }
    }
}
