package phonis.entitytracer.trace;

import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * Trace representing tnt and sand movement
 */
public abstract class EntityMoveTrace extends Trace {
    private boolean connected;

    /**
     * EntityMoveTrace constructor that calls the Trace super constructor
     *
     * @param start     start location
     * @param finish    finish location
     * @param life      life ticks
     * @param connected is trace connected
     */
    public EntityMoveTrace(Location start, Location finish, int life, boolean connected) {
        super(start, finish, life);

        this.connected = connected;
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
        Location start = this.getStart();
        Location finish = this.getFinish();

        ret.add(
            new ParticleLocation(
                start.clone(),
                this.getLife(),
                this.getType()
            )
        );

        if (this.connected) {
            double offset = .25;

            if (start.getY() < finish.getY()) {
                while (start.getY() + offset < finish.getY()) {
                    ret.add(
                        new ParticleLocation(
                            start.clone().add(0, offset, 0),
                            this.getLife(),
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
                            this.getLife(),
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
                    this.getLife(),
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
                            this.getLife(),
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
                            this.getLife(),
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
                    this.getLife(),
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
                            this.getLife(),
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
                            this.getLife(),
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
                this.getLife(),
                this.getType()
            )
        );

        return ret;
    }
}
