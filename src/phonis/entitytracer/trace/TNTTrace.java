package phonis.entitytracer.trace;

import org.bukkit.Location;

/**
 * Trace representing tnt movement
 */
public class TNTTrace extends BlockTrace {
    /**
     * TNTTrace constructor that calls the Trace super constructor
     *
     * @param start         start location
     * @param finish        finish location
     * @param isStart       start of entity's movement
     * @param isConnected   connected ticks
     * @param isHypotenusal hypotenusal
     */
    public TNTTrace(Location start, Location finish, boolean isStart, boolean isFinish, boolean isConnected, boolean isHypotenusal) {
        super(start, finish, isStart, isFinish, isConnected, isHypotenusal);
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

    /**
     * Get particle to represent start
     *
     * @return ParticleType
     */
    @Override
    protected ParticleType getSType() {
        return ParticleType.TNT;
    }

    /**
     * Get particle to represent finish
     *
     * @return ParticleType
     */
    @Override
    protected ParticleType getFType() {
        return ParticleType.TNTENDPOS;
    }
}
