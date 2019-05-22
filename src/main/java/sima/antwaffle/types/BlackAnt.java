package sima.antwaffle.types;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class which represents Black ants.
 *
 * @author Anastasios Simeonidis symeonidis@csd.auth 3088
 */
public class BlackAnt extends Ant {

    private static final Pattern PATTERN = Pattern.compile(
        "^(\\d+)\\s+([+-]?\\d*\\.?\\d*)\\s+([+-]?\\d*\\.?\\d*)\\s+(\\d+)\\s+(\\d+)\\s+(\\d+)\\s+(\\d+)\\s+(\\d+)\\s*");

    public final int[] types;

    protected BlackAnt(int id, double x, double y, int[] types) {
        super(id, x, y);
        this.types = types;
    }

    public static BlackAnt parse(String s) {
        Matcher m = PATTERN.matcher(s);
        int id = -1;
        double x = Double.NaN;
        double y = Double.NaN;
        int[] types = new int[]{-1, -1, -1, -1, -1};
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
                    // Case statement with range values.
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                        int type_index = i - 4;
                        types[type_index] = Integer.parseUnsignedInt(g);
                }
            }
        } else {
            throw new RuntimeException("Could not parse string " + s + "as black ant!");
        }
        return new BlackAnt(id - 1, x, y, types);
    }
}
