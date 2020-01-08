package phonis.entitytracer.trace;

import org.bukkit.Location;

import java.util.List;

/**
 * Class representing tracked TNT and Sand movement
 */
public abstract class Trace {
    private final Location start;
    private final Location finish;
    private int life;

    /**
     * Trace constructor
     *
     * @param start  start location
     * @param finish finish location
     * @param life   life time
     */
    public Trace(Location start, Location finish, int life) {
        this.start = start;
        this.finish = finish;
        this.life = life;
    }

    /**
     * The particles representing the trace
     *
     * @return List<PartlicleLocation>
     */
    public abstract List<ParticleLocation> getParticles();

    /**
     * Get start location
     *
     * @return Location
     */
    public Location getStart() {
        return this.start;
    }

    /**
     * Get finish location
     *
     * @return Location
     */
    public Location getFinish() {
        return this.finish;
    }

    /**
     * Decrement life by one
     *
     * @return int
     */
    public int decLife() {
        return --this.life;
    }
}
