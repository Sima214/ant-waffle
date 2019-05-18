package sima.antwaffle.func_b;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import sima.antwaffle.func_a.SpanningTree.Edge;
import sima.antwaffle.types.Ant;
import sima.antwaffle.types.BlackAnt;
import sima.antwaffle.types.RedAnt;

public class GaleShapley {

    private Proposer[] proposers;
    private Receiver[] receivers;

    /**
     * Builds the input required for the Gale-Shapley algorithm by the dataset.
     */
    public void prepare(List<Ant> dataset) {
        int n = dataset.size() / 2;
        // Initialize data structures.
        proposers = new Proposer[n];
        receivers = new Receiver[n];
        RedAnt[] red_dataset = new RedAnt[n];
        BlackAnt[] black_dataset = new BlackAnt[n];
        // Split dataset into Red and Black ants - O(n).
        for (int i = 0; i < n; i++) {
            // Split input.
            RedAnt r = (RedAnt) dataset.get(i * 2);
            BlackAnt b = (BlackAnt) dataset.get(i * 2 + 1);
            // Construct objects used by algorithm.
            Proposer rgs = new Proposer(r.id, new Receiver[n]);
            Receiver bgs = new Receiver(b.id, new Proposer[n]);
            // Add objects to data structures.
            proposers[i] = rgs;
            receivers[i] = bgs;
            red_dataset[i] = r;
            black_dataset[i] = b;
        }
        // Build preference lists for red ants.
        for (int i = 0; i < n; i++) {
            Edge[] pref = new Edge[n];
            RedAnt r = red_dataset[i];
            Proposer proposer = proposers[i];
            // Build 'array of distances'.
            for (int j = 0; j < n; j++) {
                BlackAnt b = black_dataset[j];
                pref[j] = new Edge(r, b);
            }
            // Sort by distance (ascending).
            Arrays.sort(pref, Edge.getEdgeComparator());
            // Finalize preference array.
            for (int k = 0; k < n; k++) {
                int received_index = pref[k].v2.id / 2;
                Receiver receiver = receivers[received_index];
                proposer.preferences[k] = receiver;
            }
        }
        // Build preference lists for black ants (symmetrical with above loop).
        for (int i = 0; i < n; i++) {
            Edge[] pref = new Edge[n];
            BlackAnt b = black_dataset[i];
            Receiver receiver = receivers[i];
            // Build 'array of distances'.
            for (int j = 0; j < n; j++) {
                RedAnt r = red_dataset[j];
                pref[j] = new Edge(b, r);
            }
            // Sort by distance (ascending).
            Arrays.sort(pref, Edge.getEdgeComparator());
            // Finalize preference array.
            for (int k = 0; k < n; k++) {
                int proposed_index = pref[k].v2.id / 2;
                Proposer proposer = proposers[proposed_index];
                receiver.preferences[k] = proposer;
            }
        }
    }

    public void calc() {
        // Queue of free men/proposers.
        Deque<Proposer> free = new LinkedList<>(Arrays.asList(proposers));
        // While there is at least one free man available...
        while (!free.isEmpty()) {
            // Get that man.
            Proposer current = free.pop();
            // Let him propose.
            Proposer new_free = current.propose();
            // The above function returns the man who got dumped.
            if (new_free != null) {
                free.addLast(new_free);
            }
        }
    }

    /**
     * Output results to output stream.
     */
    public void dump(PrintWriter w) {
        for (Proposer p : proposers) {
            p.couple.dump(w);
        }
    }

    private class Proposer {

        final int id;
        final Receiver[] preferences;
        int last_proposal_index;
        Match couple;

        Proposer(int id, Receiver[] preferences) {
            this.id = id;
            this.preferences = preferences;
            last_proposal_index = -1;
        }

        Proposer propose() {
            last_proposal_index++;
            return propose(preferences[last_proposal_index]);
        }

        Proposer propose(Receiver r) {
            if (r.isFree()) {
                // Constructor takes care of updating 'couple' fields.
                new Match(this, r);
                return null;
            } else {
                // Receiver/female is already engaged to 'other' guy.
                if (r.prefers(this)) {
                    // The other guy got rejected.
                    Match old_couple = r.couple;
                    new Match(this, r);
                    return old_couple.breakup();
                } else {
                    // We got rejected.
                    return this;
                }
            }
        }

    }

    private class Receiver {

        final int id;
        final Proposer[] preferences;
        Match couple;

        Receiver(int id, Proposer[] preferences) {
            this.id = id;
            this.preferences = preferences;
        }

        boolean isFree() {
            return couple == null;
        }

        boolean prefers(Proposer p) {
            for (Proposer c : preferences) {
                if (c == p) {
                    // She prefers the new guy.
                    return true;
                }
                if (c == couple.m) {
                    // She is okay with the old guy.
                    return false;
                }
            }
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    private class Match {

        final Proposer m;
        final Receiver f;

        /**
         * Ship m with f.
         */
        Match(Proposer m, Receiver f) {
            this.m = m;
            this.f = f;
            m.couple = this;
            f.couple = this;
        }

        void dump(PrintWriter w) {
            w.printf("%d\t%d\n", m.id + 1, f.id + 1);
        }

        Proposer breakup() {
            if (f.couple == this) {
                f.couple = null;
            }
            if (m.couple == this) {
                m.couple = null;
            }
            return m;
        }
    }
}
