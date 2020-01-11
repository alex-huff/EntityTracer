package phonis.entitytracer.trace;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

/**
 * Class for storing entity location details
 */
public class EntityLocation {
    private Location location;
    private boolean keepAlive;
    private boolean newEntity;
    private EntityType type;

    /**
     * EntityLocation constructor
     *
     * @param location  location of entity
     * @param newEntity is this a new entity
     * @param type      entity type
     */
    public EntityLocation(Location location, boolean newEntity, EntityType type) {
        this.location = location;
        this.keepAlive = true;
        this.newEntity = newEntity;
        this.type = type;
        //this.round(this.location);
    }

    /**
     * EntityLocation constructor
     *
     * @param location location of entity
     * @param type     entity type
     */
    public EntityLocation(Location location, EntityType type) {
        this.location = location;
        this.keepAlive = true;
        this.newEntity = false;
        this.type = type;
        //this.round(this.location);
    }

    /**
     * Truncate to n decimal places
     *
     * @param a       original
     * @param decimal places
     * @return double
     */
    private static double roundToDecimal(double a, int decimal) {
        double shiftLeft = a * Math.pow(10, decimal);
        long rounded = (long) shiftLeft;
        return (rounded / Math.pow(10, decimal));
    }

    /**
     * Round the locations so reverse engineered locations can be determined even with floating point error
     */
    private void round(Location loc) {
        loc.setX(EntityLocation.roundToDecimal(loc.getX(), 3));
        loc.setY(EntityLocation.roundToDecimal(loc.getY(), 3));
        loc.setZ(EntityLocation.roundToDecimal(loc.getZ(), 3));
    }

    /**
     * Gets type of entity
     *
     * @return EntityType
     */
    public EntityType getType() {
        return this.type;
    }

    /**
     * Marks entity for execution
     */
    public void kill() {
        this.keepAlive = false;
    }

    /**
     * Gets whether the entity is alive
     *
     * @return boolean
     */
    public boolean getState() {
        return this.keepAlive;
    }

    /**
     * Get location of entity
     *
     * @return Location
     */
    public Location getLocation() {
        return this.location;
    }

    /**
     * Gets whether this entity is new
     *
     * @return boolean
     */
    public boolean getNew() {
        return this.newEntity;
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
        if (!(obj instanceof EntityLocation)) {
            return false;
        }

        if (obj == this) {
            return true;
        }

        final EntityLocation other = (EntityLocation) obj;

        return new EqualsBuilder().
                                      append(this.getLocation(), other.getLocation()).
                                                                                         append(this.getType(), other.getType()).
                                                                                                                                    isEquals();
    }
}