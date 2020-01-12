package phonis.entitytracer.trace;

import org.bukkit.Location;
import phonis.entitytracer.util.Offset;

import java.util.ArrayList;
import java.util.List;

/**
 * Trace representing tnt and sand movement
 */
public abstract class BlockMoveTrace extends EntityMoveTrace {
    private boolean isStart;
    private boolean isFinish;

    /**
     * BlockMoveTrace constructor that calls the Trace super constructor
     *
     * @param start         start location
     * @param finish        finish location
     * @param isStart       start of entity's movement
     * @param isFinish      end of entity's movement
     * @param isConnected   connected ticks
     * @param isHypotenusal hypotenusal tick connection
     */
    public BlockMoveTrace(Location start, Location finish, boolean isStart, boolean isFinish, boolean isConnected, boolean isHypotenusal) {
        super(start.clone().add(0, .49, 0), finish.clone().add(0, .49, 0), isConnected, isHypotenusal);

        this.isStart = isStart;
        this.isFinish = isFinish;
    }

    /**
     * Type of end position
     *
     * @return ParticleType
     */
    protected abstract ParticleType getEndType();

    /**
     * Method extended from Trace
     *
     * @return List<ParticleLocation>
     */
    @Override
    public List<ParticleLocation> getParticles(int life) {
        List<ParticleLocation> ret = new ArrayList<>();
        Location start = this.getStart();
        Location finish = this.getFinish();

        if (this.isStart) {
            for (Offset offset : Trace.vertices) {
                ret.add(
                    new ParticleLocation(
                        start.clone().add(offset.getX(), offset.getY(), offset.getZ()),
                        life,
                        this.getType()
                    )
                );
            }
        }

        if (this.isConnected) {
            ret.addAll(this.getConnectedParticles(life));
        } else {
            ret.addAll(this.getTickParticles(life));
        }

        if (this.isFinish) {
            for (Offset offset : Trace.vertices) {
                ret.add(
                    new ParticleLocation(
                        finish.clone().add(offset.getX(), offset.getY(), offset.getZ()),
                        life,
                        this.getEndType()
                    )
                );
            }
        }

        return ret;
    }
}
