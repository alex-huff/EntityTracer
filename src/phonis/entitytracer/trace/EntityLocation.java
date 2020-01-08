package phonis.entitytracer.trace;

import org.bukkit.Location;

/**
 * Class for storing entity location details
 */
public class EntityLocation {
    private Location location;
    private boolean keepAlive;
    private boolean newEntity;

    /**
     * EntityLocation constructor
     *
     * @param location  location of entity
     * @param newEntity is this a new entity
     */
    public EntityLocation(Location location, boolean newEntity) {
        this.location = location;
        this.keepAlive = true;
        this.newEntity = newEntity;
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
}