package phonis.entitytracer.trace;

import org.bukkit.Location;

/**
 * Class representing block traces
 */
public abstract class BlockTrace extends EntityMoveTrace {
    private boolean isStart;
    private boolean isFinish;

    public BlockTrace(Location start, Location finish, boolean isStart, boolean isFinish, boolean isConnected, boolean isHypotenusal) {
        super(start.clone().add(0, .49, 0), finish.clone().add(0, .49, 0), isConnected, isHypotenusal);

        this.isStart = isStart;
        this.isFinish = isFinish;
    }

    /**
     * Gets start particle type regardless of settings
     *
     * @return ParticleType
     */
    protected abstract ParticleType getSType();

    /**
     * Gets finish particle type regardless of settings
     *
     * @return ParticleType
     */
    protected abstract ParticleType getFType();

    /**
     * Get type for start particle
     *
     * @return ParticleType
     */
    @Override
    protected ParticleType getStartType() {
        if (this.isStart) {
            return this.getSType();
        }

        return null;
    }

    /**
     * Get type for finish particle
     *
     * @return ParticleType
     */
    @Override
    protected ParticleType getFinishType() {
        if (this.isFinish) {
            return this.getFType();
        }

        return null;
    }

    /**
     * Gets start offset type
     *
     * @return OffsetType
     */
    @Override
    protected OffsetType getStartOffsetType() {
        return OffsetType.BLOCKBOX;
    }

    /**
     * Gets finish offset type
     *
     * @return OffsetType
     */
    @Override
    protected OffsetType getFinishOffsetType() {
        return OffsetType.BLOCKBOX;
    }
}
