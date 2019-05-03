package sima.antwaffle.types;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RedAnt extends Ant {
    private static final Pattern PATTERN = Pattern.compile("^(\\d+)\\s+([+-]?\\d*\\.?\\d*)\\s+([+-]?\\d*\\.?\\d*)\\s+(\\d+)\\s*");

    public final int capacity;

    protected RedAnt(long id, double x, double y, int capacity) {
        super(id * 2, x, y);
        this.capacity = capacity;
    }

    public static RedAnt parse(String s) {
        Matcher m = PATTERN.matcher(s);
        if (m.matches()) {
            for (int i = 1; i <= m.groupCount(); i++) {
                System.out.println(m.group(i));
            }
        } else {
            throw new RuntimeException("Could not parse string " + s + "as red ant!");
        }
        return null;
    }
}
