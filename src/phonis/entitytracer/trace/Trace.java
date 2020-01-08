package phonis.entitytracer.trace;

import org.bukkit.Location;
import phonis.entitytracer.util.Offset;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class representing tracked TNT and Sand movement
 */
public abstract class Trace {
    protected static List<Offset> vertices = new ArrayList<>(
        Arrays.asList(
            new Offset(.49F, .49F, .49F),
            new Offset(-.49F, .49F, .49F),
            new Offset(-.49F, -.49F, .49F),
            new Offset(.49F, -.49F, .49F),
            new Offset(.49F, .49F, -.49F),
            new Offset(-.49F, .49F, -.49F),
            new Offset(-.49F, -.49F, -.49F),
            new Offset(.49F, -.49F, -.49F)
        )
    );
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
     * The particles representing the trace
     *
     * @return List<ParticleLocation>
     */
    public abstract List<ParticleLocation> getParticles(int life, boolean connected);

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
