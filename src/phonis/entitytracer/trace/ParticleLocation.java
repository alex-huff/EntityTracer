package phonis.entitytracer.trace;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.bukkit.Location;

/**
 * Particle location class
 */
public class ParticleLocation {
    private Location location;
    private int life;
    private ParticleType type;

    /**
     * ParticleLocation constructor
     *
     * @param location location of particle
     * @param life     tick life
     * @param type     particle type
     */
    public ParticleLocation(Location location, int life, ParticleType type) {
        this.location = location;
        this.life = life;
        this.type = type;
    }

    /**
     * Gets location of particle
     *
     * @return Location
     */
    public Location getLocation() {
        return this.location;
    }

    /**
     * Gets life ticks
     *
     * @return int
     */
    public int getLife() {
        return this.life;
    }

    /**
     * Decrements tick life
     */
    public void decLife() {
        this.life--;
    }

    /**
     * Get type
     *
     * @return ParticleType
     */
    public ParticleType getType() {
        return this.type;
    }

    /**
     * Method overridden from Object
     *
     * @return int
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31).
                                              append(this.getLocation()).
                                                                            append(this.getType()).
                                                                                                      toHashCode();
    }

    /**
     * Method overridden from Object
     *
     * @param obj compare obj
     * @return boolean
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ParticleLocation)) {
            return false;
        }

        if (obj == this) {
            return true;
        }

        final ParticleLocation pl = (ParticleLocation) obj;

        return new EqualsBuilder().
                                      append(this.getLocation(), pl.getLocation()).
                                                                                      append(this.getType(), pl.getType()).
                                                                                                                              isEquals();
    }
}