package sima.antwaffle.types;

/**
 * Common attributes for ants.
 *
 * @author Anastasios Simeonidis symeonidis@csd.auth 3088
 */
public abstract class Ant {

    /**
     * Zero indexed.
     */
    public final int id;
    public final double x;
    public final double y;

    protected Ant(int id, double x, double y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }
}
