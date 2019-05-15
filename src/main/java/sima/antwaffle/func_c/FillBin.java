package sima.antwaffle.func_c;

import sima.antwaffle.types.BlackAnt;
import sima.antwaffle.types.RedAnt;

public class FillBin {

    /**
     * @return true if a valid selection of seeds was found.
     */
    public static boolean try_fill_bin(RedAnt r, BlackAnt b, int[] seed_count) {
        // Table of 'weight of seeds' -> 'total minimum amount of required coins'.
        int[] table = new int[r.capacity + 1];
        // Base case.
        table[0] = 0;
        // Initialize the rest of the table to MAX_INT(so it doesn't affect min calls).
        for (int i = 1; i <= r.capacity; i++) {
            table[i] = Integer.MAX_VALUE - 1;
        }
        // TODO: iterate over all possible capacities.
        for (int c = 1; c <= r.capacity; c++) {
            for (int k = 0; k < b.types.length; k++) {
                // If we have enough capacity to store this *k*ind...
                if (c >= b.types[k]) {
                    int prev = table[c - b.types[k]];
                    // TODO: Did we find a new min value?
                    if (table[c] > (prev + 1)) {
                        table[c] = prev + 1;
                    }
                }
            }
        }
        return false;
    }
}
