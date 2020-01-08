package phonis.entitytracer.trace;

import org.bukkit.Location;

/**
 * Trace representing tnt explosions
 */
public class TNTEndTrace extends EndPosTrace {
    /**
     * TNTEndTrace constructor that calls the Trace super constructor
     *
     * @param finish finish location
     * @param life   life ticks
     */
    public TNTEndTrace(Location finish, int life) {
        super(finish, life);
    }

    /**
     * Gets type of end position
     *
     * @return ParticleType
     */
    @Override
    protected ParticleType getType() {
        return ParticleType.TNTENDPOS;
    }
}
