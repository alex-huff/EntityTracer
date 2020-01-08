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
     */
    public TNTEndTrace(Location finish) {
        super(finish);
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
