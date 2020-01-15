package phonis.entitytracer.trace;

import org.bukkit.Location;

/**
 * Trace representing sand movement
 */
public class SandTrace extends BlockTrace {

    /**
     * SandTrace constructor that calls the Trace super constructor
     *
     * @param start         start location
     * @param finish        finish location
     * @param isStart       start of entity's movement
     * @param isConnected   connected ticks
     * @param isHypotenusal hypotenusal
     */
    public SandTrace(Location start, Location finish, boolean isStart, boolean isFinish, boolean isConnected, boolean isHypotenusal) {
        super(start, finish, isStart, isFinish, isConnected, isHypotenusal);
    }

    /**
     * Gets type of movement
     *
     * @return ParticleType
     */
    @Override
    protected ParticleType getType() {
        return ParticleType.SAND;
    }

    /**
     * Get particle to represent start
     *
     * @return ParticleType
     */
    @Override
    protected ParticleType getSType() {
        return ParticleType.SAND;
    }

    /**
     * Get particle to represent finish
     *
     * @return ParticleType
     */
    @Override
    protected ParticleType getFType() {
        return ParticleType.SANDENDPOS;
    }
}
