package sima.antwaffle;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import sima.antwaffle.func_a.KruskalUnionFind;
import sima.antwaffle.func_a.SpanningTree;
import sima.antwaffle.func_b.GaleShapley;
import sima.antwaffle.func_c.FillBin;
import sima.antwaffle.types.Ant;
import sima.antwaffle.types.BlackAnt;
import sima.antwaffle.types.RedAnt;

/**
 * This is where the program enters execution.
 */
public class Entry {

    public static void main(String[] args) {
        String input = "input.txt";
        String outa = "min_span.txt";
        String outb = "pairings.txt";
        String outc = "bin_fill.txt";
        switch (args.length) {
            case 4:
                outc = args[3];
            case 3:
                outb = args[2];
            case 2:
                outa = args[1];
            case 1:
                input = args[0];
        }
        // Load data set from input file.
        List<Ant> dataset = new ArrayList<>();
        // Note: odd id(even index) is red.
        int index = 0;
        try (Scanner input_scanner = new Scanner(new File(input))) {
            while (input_scanner.hasNextLine()) {
                String l = input_scanner.nextLine();
                if (index % 2 == 0) {
                    // Red ant.
                    dataset.add(RedAnt.parse(l));
                } else {
                    // Black ant.
                    dataset.add(BlackAnt.parse(l));
                }
                index++;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        // Function A
        SpanningTree tree = KruskalUnionFind.calc(Collections.unmodifiableList(dataset));
        try (PrintWriter w = new PrintWriter(new File(outa))) {
            tree.dump(w);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Function B
        GaleShapley gs = new GaleShapley();
        gs.prepare(Collections.unmodifiableList(dataset));
        gs.calc();
        try (PrintWriter w = new PrintWriter(new File(outb))) {
            gs.dump(w);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Function C
        try (PrintWriter w = new PrintWriter(new File(outc))) {
            for (int i = 0; i < dataset.size() / 2; i++) {
                // Take each set of ants.
                RedAnt r = (RedAnt) dataset.get(i * 2);
                BlackAnt b = (BlackAnt) dataset.get(i * 2 + 1);
                int[] seed_count = new int[b.types.length];
                if (FillBin.try_fill_bin(r, b, seed_count)) {
                    // Set found!
                    w.printf("%d\t%d\t%d\t%d\t%d\t%d\t%d\n", r.id + 1, b.id + 1, seed_count[0],
                        seed_count[1], seed_count[2], seed_count[3], seed_count[4]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
