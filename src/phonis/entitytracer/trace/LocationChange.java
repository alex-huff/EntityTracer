package phonis.entitytracer.trace;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;

/**
 * Class representing an entity location change
 */
public class LocationChange {
    private World world;
    private Location start;
    private Location finish;
    private EntityType type;
    private ChangeType changeType;
    private double velocity;

    /**
     * LocationChange constructor
     *
     * @param world      World
     * @param start      start location
     * @param finish     finish location
     * @param type       entity type
     * @param changeType change type
     */
    public LocationChange(World world, Location start, Location finish, EntityType type, ChangeType changeType) {
        this.world = world;
        this.start = start;
        this.finish = finish;
        this.type = type;
        this.changeType = changeType;
        this.velocity = this.start.distance(this.finish);
    }

    /**
     * LocationChange constructor
     *
     * @param world      World
     * @param start      start location
     * @param finish     finish location
     * @param type       entity type
     * @param changeType change type
     * @param velocity   speed of the entity
     */
    public LocationChange(World world, Location start, Location finish, EntityType type, ChangeType changeType, double velocity) {
        this.world = world;
        this.start = start;
        this.finish = finish;
        this.type = type;
        this.changeType = changeType;
        this.velocity = velocity;
    }

    /**
     * Method overridden from Object
     *
     * @return int
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31).
                                              append(this.getWorld().getUID()).
                                                                                  append(this.getStart()).
                                                                                                             append(this.getFinish()).
                                                                                                                                         append(this.getType()).
                                                                                                                                                                   append(this.getChangeType()).
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
        if (!(obj instanceof LocationChange)) {
            return false;
        }

        if (obj == this) {
            return true;
        }

        final LocationChange other = (LocationChange) obj;

        return new EqualsBuilder().
                                      append(this.getWorld().getUID(), other.getWorld().getUID()).
                                                                                                     append(this.getStart(), other.getStart()).
                                                                                                                                                  append(this.getFinish(), other.getFinish()).
                                                                                                                                                                                                 append(this.getType(), other.getType()).
                                                                                                                                                                                                                                            append(this.getChangeType(), other.getChangeType()).
                                                                                                                                                                                                                                                                                                   isEquals();
    }

    /**
     * Get world
     *
     * @return World
     */
    public World getWorld() {
        return this.world;
    }

    /**
     * Get start location
     *
     * @return Location
     */
    public Location getStart() {
        return this.start;
    }

    /**
     * Get finish location
     *
     * @return Location
     */
    public Location getFinish() {
        return this.finish;
    }

    /**
     * Gets change velocity
     *
     * @return double
     */
    public double getVelocity() {
        return this.velocity;
    }

    /**
     * Get entity type
     *
     * @return EntityType
     */
    public EntityType getType() {
        return this.type;
    }

    /**
     * Get change type
     *
     * @return ChangeType
     */
    public ChangeType getChangeType() {
        return this.changeType;
    }
}