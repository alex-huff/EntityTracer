package phonis.entitytracer.trace;

import org.bukkit.Location;
import phonis.entitytracer.util.Offset;

import java.util.ArrayList;
import java.util.List;

/**
 * Trace representing end positions
 */
public abstract class EndPosTrace extends Trace {
    /**
     * EndPosTrace constructor that calls the Trace super constructor
     *
     * @param finish finish location
     * @param life   life ticks
     */
    public EndPosTrace(Location finish, int life) {
        super(null, finish, life);
    }

    /**
     * Type of end position
     *
     * @return ParticleType
     */
    protected abstract ParticleType getType();

    /**
     * Method extended from Trace
     *
     * @return List<ParticleLocation>
     */
    @Override
    public List<ParticleLocation> getParticles() {
        List<ParticleLocation> ret = new ArrayList<>();
        Location finish = this.getFinish();

        for (Offset offset : Trace.vertices) {
            ret.add(
                new ParticleLocation(
                    new Location(
                        finish.getWorld(),
                        finish.getX() + offset.getX(),
                        finish.getY() + offset.getY(),
                        finish.getZ() + offset.getZ()
                    ),
                    this.getLife(),
                    this.getType()
                )
            );
        }

        return ret;
    }
}
