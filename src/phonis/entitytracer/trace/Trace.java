package phonis.entitytracer.trace;

import org.bukkit.Location;

import java.util.List;

/**
 * Class representing tracked TNT and Sand movement
 */
public abstract class Trace {
    private final Location start;
    private final Location finish;

    /**
     * Trace constructor
     *
     * @param start  start location
     * @param finish finish location
     */
    public Trace(Location start, Location finish) {
        this.start = start;
        this.finish = finish;
    }

    /**
     * The lines representing the trace
     *
     * @return List<Line>
     */
    public abstract List<Line> getLines();

    /**
     * Get start location
     *
     * @return Location
     */
    protected Location getStart() {
        return this.start;
    }

    /**
     * Get finish location
     *
     * @return Location
     */
    protected Location getFinish() {
        return this.finish;
    }
}
