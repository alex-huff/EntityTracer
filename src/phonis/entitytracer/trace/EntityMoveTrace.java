package phonis.entitytracer.trace;

import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * Trace representing entity movement
 */
public abstract class EntityMoveTrace extends Trace {
    protected boolean isConnected;
    private boolean isHypotenusal;

    /**
     * EntityMoveTrace constructor that calls the Trace super constructor
     *
     * @param start         start location
     * @param finish        finish location
     * @param isConnected   connected ticks
     * @param isHypotenusal hypotenusal tick connection
     */
    public EntityMoveTrace(Location start, Location finish, boolean isConnected, boolean isHypotenusal) {
        super(start, finish);
        this.isConnected = isConnected;
        this.isHypotenusal = isHypotenusal;
    }

    /**
     * Type of particle
     *
     * @return ParticleType
     */
    protected abstract ParticleType getType();

    /**
     * Type of particle
     *
     * @return ParticleType
     */
    protected abstract ParticleType getStartType();

    /**
     * Type of particle
     *
     * @return ParticleType
     */
    protected abstract ParticleType getFinishType();

    /**
     * Type of offset
     *
     * @return OffsetType
     */
    protected abstract OffsetType getStartOffsetType();

    /**
     * Type of offset
     *
     * @return OffsetType
     */
    protected abstract OffsetType getFinishOffsetType();

    @Override
    public List<Line> getLines() {
        List<Line> ret = new ArrayList<>();

        if (this.isHypotenusal) {
            ret.add(
                new Line(
                    this.getStart(),
                    this.getFinish(),
                    this.getType(),
                    this.getStartType(),
                    this.getFinishType(),
                    this.getStartOffsetType(),
                    this.getFinishOffsetType(),
                    isConnected
                )
            );

            return ret;
        }

        Location loc1 = this.getStart().clone();
        loc1.setY(this.getFinish().getY());
        Location loc2 = loc1.clone();
        loc2.setX(this.getFinish().getX());

        ret.add(
            new Line(
                this.getStart(),
                loc1,
                this.getType(),
                this.getStartType(),
                null,
                this.getStartOffsetType(),
                null,
                isConnected
            )
        );

        ret.add(
            new Line(
                loc1,
                loc2,
                this.getType(),
                isConnected
            )
        );

        ret.add(
            new Line(
                loc2,
                this.getFinish(),
                this.getType(),
                null,
                this.getFinishType(),
                null,
                this.getFinishOffsetType(),
                isConnected
            )
        );

        return ret;
    }
}