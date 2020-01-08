package phonis.entitytracer.trace;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.bukkit.Location;

/**
 * ParticleType enum
 */
enum ParticleType {
    SAND, TNT, EXPLOSION;

    final static Color colorSand = new Color(255, 255, 0);
    final static Color colorTNT = new Color(255, 0, 0);
    final static Color colorExplosion = new Color(0, 255, 255);

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

public class ParticleLocation {
    private Location location;
    private int life;
    private ParticleType type;

    public ParticleLocation(Location location, int life, ParticleType type) {
        this.location = location;
        this.life = life;
        this.type = type;
    }

    public Location getLocation() {
        return this.location;
    }

    public int decLife() {
        return --this.life;
    }

    public ParticleType getType() {
        return this.type;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31).
                                              append(this.getLocation().getX()).
                                                                                   append(this.getLocation().getY()).
                                                                                                                        append(this.getLocation().getZ()).
                                                                                                                                                             append(this.type).toHashCode();
    }

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