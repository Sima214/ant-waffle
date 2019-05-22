package sima.antwaffle.types;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class which represents Red ants.
 *
 * @author Anastasios Simeonidis symeonidis@csd.auth 3088
 */
public class RedAnt extends Ant {

    private static final Pattern PATTERN = Pattern
        .compile("^(\\d+)\\s+([+-]?\\d*\\.?\\d*)\\s+([+-]?\\d*\\.?\\d*)\\s+(\\d+)\\s*");

    public final int capacity;

    protected RedAnt(int id, double x, double y, int capacity) {
        super(id, x, y);
        this.capacity = capacity;
    }

    public static RedAnt parse(String s) {
        Matcher m = PATTERN.matcher(s);
        int id = -1;
        double x = Double.NaN;
        double y = Double.NaN;
        int cap = -1;
        if (m.matches()) {
            for (int i = 1; i <= m.groupCount(); i++) {
                String g = m.group(i);
                switch (i) {
                    case 1:
                        id = Integer.parseUnsignedInt(g);
                        break;
                    case 2:
                        x = Double.parseDouble(g);
                        break;
                    case 3:
                        y = Double.parseDouble(g);
                        break;
                    case 4:
                        cap = Integer.parseUnsignedInt(g);
                        break;
                }
            }
        } else {
            throw new RuntimeException("Could not parse string " + s + "as red ant!");
        }
        return new RedAnt(id - 1, x, y, cap);
    }
}
