package phonis.entitytracer.trace;

import org.bukkit.Location;

/**
 * Trace representing tnt movement
 */
public class TNTTrace extends EntityMoveTrace {
    /**
     * TNTTrace constructor that calls the Trace super constructor
     *
     * @param start   start location
     * @param finish  finish location
     * @param isStart start of entity's movement
     */
    public TNTTrace(Location start, Location finish, boolean isStart, boolean isFinish) {
        super(start, finish, isStart, isFinish);
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
     * Gets end type
     *
     * @return ParticleType
     */
    @Override
    protected ParticleType getEndType() {
        return ParticleType.TNTENDPOS;
    }
}
