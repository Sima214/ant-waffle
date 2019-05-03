package sima.antwaffle;

import sima.antwaffle.types.Ant;
import sima.antwaffle.types.BlackAnt;
import sima.antwaffle.types.RedAnt;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
        // Function B
        // Function C
    }
}
