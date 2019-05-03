package sima.antwaffle.types;

/**
 * Common attributes for ants.
 */
public abstract class Ant {
    final long id;
    final double x;
    final double y;

    protected Ant(long id, double x, double y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }
}
