package sima.antwaffle.types;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BlackAnt extends Ant {
    private static final Pattern PATTERN = Pattern.compile("^(\\d+)\\s+([+-]?\\d*\\.?\\d*)\\s+([+-]?\\d*\\.?\\d*)\\s+(\\d+)\\s+(\\d+)\\s+(\\d+)\\s+(\\d+)\\s+(\\d+)\\s*");

    public final int[] types;

    protected BlackAnt(long id, double x, double y, int[] types) {
        super(id * 2 + 1, x, y);
        this.types = types;
    }

    public static BlackAnt parse(String s) {
        Matcher m = PATTERN.matcher(s);
        if (m.matches()) {
            for (int i = 1; i <= m.groupCount(); i++) {
                System.out.println(m.group(i));
            }
        } else {
            throw new RuntimeException("Could not parse string " + s + "as black ant!");
        }
        return null;
    }
}
