package phonis.entitytracer.trace;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.bukkit.Location;
import phonis.entitytracer.util.Offset;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing an artifact such as a tnt spawn cube
 */
public class Artifact {
    private Location location;
    private ParticleType type;
    private OffsetType offsetType;

    /**
     * Artifact constructor for type and offset type
     *
     * @param type       ParticleType
     * @param offsetType OffsetType
     */
    public Artifact(Location location, ParticleType type, OffsetType offsetType) {
        this.location = location;
        this.type = type;
        this.offsetType = offsetType;
    }

    /**
     * Gets location of artifact
     *
     * @return Location
     */
    public Location getLocation() {
        return this.location;
    }

    /**
     * Gets type of particle
     *
     * @return ParticleType
     */
    public ParticleType getType() {
        return this.type;
    }

    /**
     * Gets type of offset
     *
     * @return OffsetType
     */
    public OffsetType getOffsetType() {
        return this.offsetType;
    }

    /**
     * Gets a life of particles representing the artifact
     *
     * @param life life in ticks
     * @return List<ParticleLocation>
     */
    public List<ParticleLocation> getParticles(int life) {
        List<ParticleLocation> ret = new ArrayList<>();

        for (Offset offset : this.getOffsetType().getOffset()) {
            ret.add(
                new ParticleLocation(
                    this.location.clone().add(offset.getX(), offset.getY(), offset.getZ()),
                    life,
                    this.type
                )
            );
        }

        return ret;
    }

    /**
     * Method overridden from Object
     *
     * @return int
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31).
                                              append(this.location).
                                                                       append(this.type).
                                                                                            append(this.offsetType).
                                                                                                                       toHashCode();
    }

    /**
     * Method overridden from Object
     *
     * @param obj obj to check
     * @return boolean
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Artifact)) {
            return false;
        }

        if (obj == this) {
            return true;
        }

        final Artifact other = (Artifact) obj;

        return new EqualsBuilder().
                                      append(this.location, other.location).
                                                                               append(this.type, other.type).
                                                                                                                append(this.offsetType, other.offsetType).
                                                                                                                                                             isEquals();
    }
}
