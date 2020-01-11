package phonis.entitytracer.trace;

import org.bukkit.Location;
import phonis.entitytracer.util.Offset;

import java.util.ArrayList;
import java.util.List;

/**
 * Trace representing tnt and sand movement
 */
public abstract class EntityMoveTrace extends Trace {
    private boolean isStart;
    private boolean isFinish;

    /**
     * EntityMoveTrace constructor that calls the Trace super constructor
     *
     * @param start   start location
     * @param finish  finish location
     * @param isStart start of entity's movement
     */
    public EntityMoveTrace(Location start, Location finish, boolean isStart, boolean isFinish) {
        super(start, finish);

        this.isStart = isStart;
        this.isFinish = isFinish;
    }

    /**
     * Type of particle
     *
     * @return ParticleType
     */
    protected abstract ParticleType getType();

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
    public List<ParticleLocation> getParticles(int life, boolean connected) {
        List<ParticleLocation> ret = new ArrayList<>();
        Location start = this.getStart();
        Location finish = this.getFinish();

        if (isStart) {
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

        ret.add(
            new ParticleLocation(
                start.clone(),
                life,
                this.getType()
            )
        );

        if (connected) {
            double offset = .25;

            if (start.getY() < finish.getY()) {
                while (start.getY() + offset < finish.getY()) {
                    ret.add(
                        new ParticleLocation(
                            start.clone().add(0, offset, 0),
                            life,
                            this.getType()
                        )
                    );

                    offset += .25;
                }
            } else {
                while (start.getY() - offset > finish.getY()) {
                    ret.add(
                        new ParticleLocation(
                            start.clone().add(0, -offset, 0),
                            life,
                            this.getType()
                        )
                    );

                    offset += .25;
                }
            }

            ret.add(
                new ParticleLocation(
                    new Location(
                        start.getWorld(),
                        start.getX(),
                        finish.getY(),
                        start.getZ()
                    ),
                    life,
                    this.getType()
                )
            );

            offset = .25;

            if (start.getX() < finish.getX()) {
                while (start.getX() + offset < finish.getX()) {
                    ret.add(
                        new ParticleLocation(
                            new Location(
                                start.getWorld(),
                                start.getX() + offset,
                                finish.getY(),
                                start.getZ()
                            ),
                            life,
                            this.getType()
                        )
                    );

                    offset += .25;
                }
            } else {
                while (start.getX() - offset > finish.getX()) {
                    ret.add(
                        new ParticleLocation(
                            new Location(
                                start.getWorld(),
                                start.getX() - offset,
                                finish.getY(),
                                start.getZ()
                            ),
                            life,
                            this.getType()
                        )
                    );

                    offset += .25;
                }
            }

            ret.add(
                new ParticleLocation(
                    new Location(
                        start.getWorld(),
                        finish.getX(),
                        finish.getY(),
                        start.getZ()
                    ),
                    life,
                    this.getType()
                )
            );

            offset = .25;

            if (start.getZ() < finish.getZ()) {
                while (start.getZ() + offset < finish.getZ()) {
                    ret.add(
                        new ParticleLocation(
                            new Location(
                                start.getWorld(),
                                finish.getX(),
                                finish.getY(),
                                start.getZ() + offset
                            ),
                            life,
                            this.getType()
                        )
                    );

                    offset += .25;
                }
            } else {
                while (start.getZ() - offset > finish.getZ()) {
                    ret.add(
                        new ParticleLocation(
                            new Location(
                                start.getWorld(),
                                finish.getX(),
                                finish.getY(),
                                start.getZ() - offset
                            ),
                            life,
                            this.getType()
                        )
                    );

                    offset += .25;
                }
            }
        }

        ret.add(
            new ParticleLocation(
                finish.clone(),
                life,
                this.getType()
            )
        );

        if (isFinish) {
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
