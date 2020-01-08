package phonis.entitytracer.trace;

import org.bukkit.Location;

import java.util.Comparator;

/**
 * Comparator for particle location
 */
public class ParticleLocationComparator implements Comparator<ParticleLocation> {
    private Location loc;

    /**
     * Constructor for ParticleLocationComparator
     *
     * @param loc location
     */
    public ParticleLocationComparator(Location loc) {
        this.loc = loc;
    }

    /**
     * Method implemented from Comparator
     *
     * @param o1 ParticleLocation 1
     * @param o2 ParticleLocation 2
     * @return int
     */
    @Override
    public int compare(ParticleLocation o1, ParticleLocation o2) {
        return Double.compare(loc.distance(o1.getLocation()), loc.distance(o2.getLocation()));
    }
}
