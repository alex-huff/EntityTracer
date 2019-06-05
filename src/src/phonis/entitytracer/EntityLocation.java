package src.phonis.entitytracer;

import org.bukkit.Location;

public class EntityLocation {
	private Location location;
	private boolean keepAlive;
	private boolean newEntity;
	
	public EntityLocation(Location location, boolean newEntity) {
		this.location = location;
		this.keepAlive = true;
		this.newEntity = newEntity;
	}
	
	public void keepAlive() {
		this.keepAlive = true;
	}
	
	public void kill() {
		this.keepAlive = false;
	}
	
	public boolean getState() {
		return this.keepAlive;
	}
	
	public Location getLocation() {
		return this.location;
	}
	
	public boolean getNew() {
		return this.newEntity;
	}
}