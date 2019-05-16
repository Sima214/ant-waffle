package sima.antwaffle.func_c;

import sima.antwaffle.types.BlackAnt;
import sima.antwaffle.types.RedAnt;

public class FillBin {

    /**
     * @return true if a valid selection of seeds was found.
     */
    public static boolean try_fill_bin(RedAnt r, BlackAnt b, int[] seed_count) {
        // Table of 'weight of seeds' -> {'total minimum amount of required seeds', 'amount of a single kind of seed'...}.
        int[][] table = new int[r.capacity + 1][b.types.length + 1];
        // Base case.
        table[0][0] = 0;
        // Initialize the rest of the table to MAX_INT-1 (so it doesn't overflow later).
        for (int i = 1; i <= r.capacity; i++) {
            table[i][0] = Integer.MAX_VALUE - 1;
        }
        // For all possible capacities:
        for (int c = 1; c <= r.capacity; c++) {
            // And all seed kinds:
            for (int k = 0; k < b.types.length; k++) {
                // If we have enough capacity to store this kind of seed...
                if (c >= b.types[k]) {
                    int[] prev = table[c - b.types[k]];
                    int prev_capacity = prev[0];
                    // Did we find a new min set of seeds?
                    if (table[c][0] > (prev_capacity + 1)) {
                        // Copy state.
                        System.arraycopy(prev, 0, table[c], 0, prev.length);
                        // Increase seed count.
                        table[c][0]++;
                        table[c][k + 1]++;
                    }
                }
            }
        }
        // Copy result.
        if (table[r.capacity][0] == Integer.MAX_VALUE - 1) {
            return false;
        } else {
            System.arraycopy(table[r.capacity], 1, seed_count, 0, b.types.length);
            return true;
        }
    }
}
