package sima.antwaffle.types;

/**
 * Common attributes for ants.
 */
public abstract class Ant {
    public final long id;
    public final double x;
    public final double y;

    protected Ant(long id, double x, double y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }
}
