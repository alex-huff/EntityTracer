package src.phonis.entitytracer;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;

enum ChangeType{
	EXPLOTION, START, NORMAL;
}

public class LocationChange {
	private World world;
	private Location start;
	private Location finish;
	private EntityType type;
	private ChangeType changeType;
	
	public LocationChange(World world, Location start, Location finish, EntityType type, ChangeType changeType) {
		this.world = world;
		this.start = start;
		this.finish = finish;
		this.type = type;
		this.changeType = changeType;
	}
	
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31).
            append(this.getWorld()).
            append(this.getStart()).
            append(this.getFinish()).
            append(this.getType()).
            append(this.getChangeType()).
            toHashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof LocationChange)) {
        	return false;
        }
        
        if (obj == this) {
        	return true;
        }

        final LocationChange other = (LocationChange) obj;
        
//        if (!this.world.equals(other.getWorld()) || !this.start.equals(other.getStart()) || !this.finish.equals(other.getFinish()) || !(this.type.compareTo(other.getType()) == 0) || !(this.changeType.compareTo(other.getChangeType()) == 0)) {
//        	return false;
//        }

        return new EqualsBuilder().
            append(this.getWorld(), other.getWorld()).
            append(this.getStart(), other.getStart()).
            append(this.getFinish(), other.getFinish()).
            append(this.getType(), other.getType()).
            append(this.getChangeType(), other.getChangeType()).
            isEquals();
    }
    	
	public World getWorld() {
		return this.world;
	}
	
	public Location getStart() {
		return this.start;
	}
	
	public Location getFinish() {
		return this.finish;
	}
	
	public EntityType getType() {
		return this.type;
	}
	
	public ChangeType getChangeType() {
		return this.changeType;
	}
}