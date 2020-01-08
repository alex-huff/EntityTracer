package phonis.entitytracer.trace;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.bukkit.Location;

/**
 * ParticleType enum
 */
enum ParticleType {
    SAND, TNT, TNTENDPOS, SANDENDPOS;

    final static Color colorSand = new Color(255, 255, 0);
    final static Color colorTNT = new Color(255, 0, 0);
    final static Color colorExplosion = new Color(0, 255, 255);

    /**
     * Gets color of ParticleType
     *
     * @return Color
     */
    Color getRGB() {
        if (this == SAND) {
            return colorSand;
        } else if (this == TNT) {
            return colorTNT;
        } else {
            return colorExplosion;
        }
    }
}

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
     * Decrements tick life
     *
     * @return int
     */
    public int decLife() {
        return --this.life;
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
                                              append(this.getLocation().getX()).
                                                                                   append(this.getLocation().getY()).
                                                                                                                        append(this.getLocation().getZ()).
                                                                                                                                                             append(this.type).toHashCode();
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
                                      append(this.getLocation().getX(), pl.getLocation().getX()).
                                                                                                    append(this.getLocation().getY(), pl.getLocation().getY()).
                                                                                                                                                                  append(this.getLocation().getZ(), pl.getLocation().getZ()).
                                                                                                                                                                                                                                append(this.getType(), pl.getType()).
                                                                                                                                                                                                                                                                        isEquals();
    }
}