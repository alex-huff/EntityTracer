package src.phonis.entitytracer;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.bukkit.Location;

public class ParticleLocation extends Object{
	Location location;
	int lifespan;
	int r;
	int g;
	int b;
	
	public ParticleLocation(Location location, int lifespan, int r, int g, int b) {
		this.location = location;
		this.lifespan = lifespan;
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	public Location getLocation() {
		return this.location;
	}
	
	public void decLifeSpan() {
		this.lifespan -= 1;
	}
	
	public int getLifeSpan() {
		return this.lifespan;
	}
	
	public int getR() {
		return this.r;
	}
	
	public int getG() {
		return this.g;
	}
	
	public int getB() {
		return this.b;
	}
	
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31).
            append(this.getLocation().getX()).
            append(this.getLocation().getY()).
            append(this.getLocation().getZ()).
            append(this.getR()).
            append(this.getG()).
            append(this.getB()).
            toHashCode();
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
            append(this.getR(), pl.getR()).
            append(this.getG(), pl.getG()).
            append(this.getB(), pl.getB()).
            isEquals();
    }
}