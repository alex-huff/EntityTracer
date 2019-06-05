package src.phonis.entitytracer;

import java.util.Comparator;

import org.bukkit.Location;

public class ParticleLocationComparator implements Comparator<ParticleLocation>{
	private Location loc;
	
	public ParticleLocationComparator(Location loc) {
		this.loc = loc;
	}
	
	@Override
	public int compare(ParticleLocation o1, ParticleLocation o2) {
		if (loc.distance(o1.getLocation()) == loc.distance(o2.getLocation())) {
			return 0;
		}else if (loc.distance(o1.getLocation()) > loc.distance(o2.getLocation())) {
			return 1;
		}else {
			return -1;
		}
	}
}
