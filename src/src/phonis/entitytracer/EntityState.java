package src.phonis.entitytracer;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.util.Vector;

public class EntityState {
	private EntityType type;
	private Location location;
	private Vector velocity;
	
	public EntityState(EntityType type, Location location, Vector velocity) {
		this.type = type;
		this.location = location;
		this.velocity = velocity;
	}
	
	public EntityType getType() {
		return this.type;
	}
	
	public Location getLocation() {
		return this.location;
	}
	
	public Vector getVelocity() {
		return this.velocity;
	}
	
	public boolean compareTo(EntityState es) {
		if(this.type == es.getType() && this.location == es.getLocation() && this.velocity == es.getVelocity()) {
			return true;
		}
		
		return false;
	}
}