package phonis.entitytracer.trace;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

/**
 * Trace representing entity movement
 */
public abstract class EntityMoveTrace extends Trace {
    protected boolean isConnected;
    private boolean isHypotenusal;

    /**
     * EntityMoveTrace constructor that calls the Trace super constructor
     *
     * @param start         start location
     * @param finish        finish location
     * @param isConnected   connected ticks
     * @param isHypotenusal hypotenusal tick connection
     */
    public EntityMoveTrace(Location start, Location finish, boolean isConnected, boolean isHypotenusal) {
        super(start, finish);
        this.isConnected = isConnected;
        this.isHypotenusal = isHypotenusal;
    }

    /**
     * Type of particle
     *
     * @return ParticleType
     */
    protected abstract ParticleType getType();

    /**
     * Method for getting start and end particles
     *
     * @param life life in ticks
     * @return List<ParticleLocation>
     */
    protected List<ParticleLocation> getTickParticles(int life) {
        return this.getEndParticles(life, this.getStart(), this.getFinish());
    }

    /**
     * Method for getting the start and end particles
     *
     * @param life   life in ticks
     * @param start  start location
     * @param finish finish location
     * @return List<ParticleLocation>
     */
    private List<ParticleLocation> getEndParticles(int life, Location start, Location finish) {
        List<ParticleLocation> ret = new ArrayList<>();

        ret.add(new ParticleLocation(start, life, this.getType()));
        ret.add(new ParticleLocation(finish, life, this.getType()));

        return ret;
    }

    /**
     * Get particles in a line
     *
     * @param life   life in ticks
     * @param start  start location
     * @param finish finish location
     * @return List<ParticleLocation>
     */
    private List<ParticleLocation> getLineParticles(int life, Location start, Location finish) {
        double distance = start.distance(finish);
        Vector direction = finish.clone().subtract(start).toVector().normalize().multiply(.25);
        Vector di2 = direction.clone();

        List<ParticleLocation> ret = new ArrayList<>(this.getEndParticles(life, start, finish));

        while (di2.length() < distance) {
            ret.add(new ParticleLocation(start.clone().add(di2.getX(), di2.getY(), di2.getZ()), life, this.getType()));
            di2.add(direction);
        }

        return ret;
    }

    /**
     * Method for getting movement particles
     *
     * @param life life in ticks
     * @return List<ParticleLocation>
     */
    protected List<ParticleLocation> getHypotenuseParticles(int life) {
        return this.getLineParticles(life, this.getStart(), this.getFinish());
    }

    /**
     * Method for getting basic triangle movement particles
     *
     * @param life life in ticks
     * @return List<ParticleLocation>
     */
    protected List<ParticleLocation> getTriangleParticles(int life) {
        List<ParticleLocation> ret = new ArrayList<>();

        Location start = this.getStart();
        Location finish = this.getFinish();

        Location loc1 = start.clone();
        loc1.setY(finish.getY());
        Location loc2 = loc1.clone();
        loc2.setX(finish.getX());

        Location[] locations = new Location[]{
            start, loc1, loc2, finish
        };

        for (int i = 0; i < 3; i++) {
            ret.addAll(this.getLineParticles(life, locations[i], locations[i + 1]));
        }

        return ret;
    }

    /**
     * Method for getting basic connected type particles
     *
     * @param life in ticks
     * @return List<ParticleLocation>
     */
    protected List<ParticleLocation> getConnectedParticles(int life) {
        if (this.isHypotenusal) {
            return this.getHypotenuseParticles(life);
        } else {
            return this.getTriangleParticles(life);
        }
    }
}