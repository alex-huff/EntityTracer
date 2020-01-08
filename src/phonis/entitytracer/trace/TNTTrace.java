package phonis.entitytracer.trace;

import org.bukkit.Location;

/**
 * Trace representing tnt movement
 */
public class TNTTrace extends EntityMoveTrace {
    /**
     * TNTTrace constructor that calls the Trace super constructor
     *
     * @param start     start location
     * @param finish    finish location
     * @param life      life ticks
     * @param connected is trace connected
     */
    public TNTTrace(Location start, Location finish, int life, boolean connected) {
        super(start, finish, life, connected);
    }

    /**
     * Gets type of movement
     *
     * @return ParticleType
     */
    @Override
    protected ParticleType getType() {
        return ParticleType.TNT;
    }
}
