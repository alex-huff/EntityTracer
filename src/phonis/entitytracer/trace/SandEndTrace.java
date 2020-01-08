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
     */
    public SandEndTrace(Location finish) {
        super(finish);
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
