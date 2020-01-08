package phonis.entitytracer.trace;

import org.bukkit.Location;

/**
 * Trace representing sand movement
 */
public class SandTrace extends EntityMoveTrace {
    /**
     * SandTrace constructor that calls the Trace super constructor
     *
     * @param start   start location
     * @param finish  finish location
     * @param isStart start of entity's movement
     */
    public SandTrace(Location start, Location finish, boolean isStart) {
        super(start, finish, isStart);
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
}
