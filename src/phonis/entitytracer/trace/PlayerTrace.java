package phonis.entitytracer.trace;

import org.bukkit.Location;

import java.util.List;

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
     * Method extended from Trace
     *
     * @return List<ParticleLocation>
     */
    @Override
    public List<ParticleLocation> getParticles(int life) {
        if (this.isConnected) {
            return this.getConnectedParticles(life);
        }

        return this.getTickParticles(life);
    }
}
