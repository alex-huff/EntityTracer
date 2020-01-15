package phonis.entitytracer.trace;

import org.bukkit.Location;

/**
 * Trace representing player movement
 */
public class PlayerTrace extends EntityMoveTrace {
    /**
     * PlayerTrace constructor that calls the Trace super constructor
     *
     * @param start         start location
     * @param finish        finish location
     * @param isConnected   connected ticks
     * @param isHypotenusal hypotenusal tick connection
     */
    public PlayerTrace(Location start, Location finish, boolean isConnected, boolean isHypotenusal) {
        super(start.clone().add(0, .25, 0), finish.clone().add(0, .25, 0), isConnected, isHypotenusal);
    }

    /**
     * Gets type of movement
     *
     * @return ParticleType
     */
    @Override
    protected ParticleType getType() {
        return ParticleType.PLAYER;
    }

    /**
     * Gets start particle type
     *
     * @return ParticleType
     */
    @Override
    protected ParticleType getStartType() {
        return null;
    }

    /**
     * Gets finish particle type
     *
     * @return ParticleType
     */
    @Override
    protected ParticleType getFinishType() {
        return null;
    }

    /**
     * Gets start offset type
     *
     * @return OffsetType
     */
    @Override
    protected OffsetType getStartOffsetType() {
        return null;
    }

    /**
     * Gets finish offset type
     *
     * @return OffsetType
     */
    @Override
    protected OffsetType getFinishOffsetType() {
        return null;
    }
}
