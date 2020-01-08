package phonis.entitytracer.trace;

import org.bukkit.Location;

/**
 * Trace representing sand end positions
 */
public class SandEndTrace extends EndPosTrace {
    /**
     * SandEndTrace constructor that calls the Trace super constructor
     *
     * @param finish finish location
     * @param life   life ticks
     */
    public SandEndTrace(Location finish, int life) {
        super(finish, life);
    }

    /**
     * Gets type of end position
     *
     * @return ParticleType
     */
    @Override
    protected ParticleType getType() {
        return ParticleType.SANDENDPOS;
    }
}
