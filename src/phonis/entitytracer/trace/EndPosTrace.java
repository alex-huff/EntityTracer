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
     */
    public EndPosTrace(Location finish) {
        super(null, finish);
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
    public List<ParticleLocation> getParticles(int life, boolean connected) {
        List<ParticleLocation> ret = new ArrayList<>();
        Location finish = this.getFinish();

        for (Offset offset : Trace.vertices) {
            ret.add(
                new ParticleLocation(
                    finish.clone().add(offset.getX(), offset.getY(), offset.getZ()),
                    life,
                    this.getType()
                )
            );
        }

        return ret;
    }
}
