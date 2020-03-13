package phonis.entitytracer.trace;

import org.bukkit.Location;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GraphTrace extends Trace {
    double[][] positions;
    Location pLoc;
    int offset;

    public GraphTrace(double[][] positions, Location pLoc, int offset) {
        super(null, null);
        this.positions = positions;
        this.pLoc = pLoc;
        this.offset = offset;
    }

    @Override
    public List<Line> getLines() {
        List<Line> lines = new ArrayList<>();

        for (int i = 0; i < this.positions.length; i++) {
            for (int w = 1; w < this.positions[i].length; w++) {
                lines.add(
                    new Line(
                        new Location(
                            this.pLoc.getWorld(),
                            this.pLoc.getX() + i - offset,
                            this.pLoc.getY() + this.positions[i][w - 1],
                            this.pLoc.getZ() + w - offset - 1
                        ),
                        new Location(
                            this.pLoc.getWorld(),
                            this.pLoc.getX() + i - offset,
                            this.pLoc.getY() + this.positions[i][w],
                            this.pLoc.getZ() + w - offset
                        ),
                        ParticleType.GRAPH,
                        true
                    )
                );
            }
        }

        for (int i = 1; i < this.positions.length; i++) {
            for (int w = 0; w < this.positions[i].length; w++) {
                lines.add(
                    new Line(
                        new Location(
                            this.pLoc.getWorld(),
                            this.pLoc.getX() + i - offset - 1,
                            this.pLoc.getY() + this.positions[i - 1][w],
                            this.pLoc.getZ() + w - offset
                        ),
                        new Location(
                            this.pLoc.getWorld(),
                            this.pLoc.getX() + i - offset,
                            this.pLoc.getY() + this.positions[i][w],
                            this.pLoc.getZ() + w - offset
                        ),
                        ParticleType.GRAPH,
                        true
                    )
                );
            }
        }

        double x1, y1, z1, x2, y2, z2;

        x1 = pLoc.getX() - (offset + .001);
        y1 = pLoc.getY() - (offset + .001);
        z1 = pLoc.getZ() - (offset + .001);
        x2 = pLoc.getX() + (offset + .001);
        y2 = pLoc.getY() + (offset + .001);
        z2 = pLoc.getZ() + (offset + .001);
        BoundingBox bb = new BoundingBox(x1, y1, z1, x2, y2, z2);
        Iterator<Line> lineIterator = lines.iterator();

        while (lineIterator.hasNext()) {
            Line line = lineIterator.next();

            if (bb.contains(line.getStart().toVector()) && bb.contains(line.getFinish().toVector())) {
                continue;
            }

            if (!bb.contains(line.getStart().toVector()) && !bb.contains(line.getFinish().toVector())) {
                Vector direction = line.getDirection().clone();
                Vector oppoDirection = line.getDirection().clone().multiply(-1);

                if (oppoDirection.getZ() == -0) {
                    oppoDirection.setZ(0);
                }

                if (oppoDirection.getX() == -0) {
                    oppoDirection.setX(0);
                }

                if (oppoDirection.getY() == -0) {
                    oppoDirection.setY(0);
                }

                RayTraceResult rtr1 = bb.rayTrace(line.getStart().toVector(), direction, Double.MAX_VALUE);
                RayTraceResult rtr2 = bb.rayTrace(line.getFinish().toVector(), oppoDirection, Double.MAX_VALUE);

                if (rtr1 == null || rtr2 == null) {
                    lineIterator.remove();

                    continue;
                }

                Vector hitPos1 = rtr1.getHitPosition();
                Vector hitPos2 = rtr2.getHitPosition();

                if (Double.isNaN(hitPos1.getX()) || Double.isNaN(hitPos2.getX())) {
                    lineIterator.remove();

                    continue;
                }

                line.setStart(
                    new Location(
                        pLoc.getWorld(),
                        hitPos1.getX(),
                        hitPos1.getY(),
                        hitPos1.getZ()
                    )
                );

                line.setFinish(
                    new Location(
                        pLoc.getWorld(),
                        hitPos2.getX(),
                        hitPos2.getY(),
                        hitPos2.getZ()
                    )
                );

                continue;
            }

            if (bb.contains(line.getStart().toVector())) {
                RayTraceResult rtr = bb.rayTrace(line.getStart().toVector(), line.getDirection(), Double.MAX_VALUE);
                Vector hitPos = rtr.getHitPosition();

                line.setFinish(
                    new Location(
                        pLoc.getWorld(),
                        hitPos.getX(),
                        hitPos.getY(),
                        hitPos.getZ()
                    )
                );

                continue;
            }

            RayTraceResult rtr = bb.rayTrace(line.getStart().toVector(), line.getDirection(), Double.MAX_VALUE);
            Vector hitPos = rtr.getHitPosition();

            line.setStart(
                new Location(
                    pLoc.getWorld(),
                    hitPos.getX(),
                    hitPos.getY(),
                    hitPos.getZ()
                )
            );
        }

        lines.add(
            new Line(
                new Location(
                    this.pLoc.getWorld(),
                    this.pLoc.getX(),
                    this.pLoc.getY() - offset,
                    this.pLoc.getZ()
                ),
                new Location(
                    this.pLoc.getWorld(),
                    this.pLoc.getX(),
                    this.pLoc.getY() + offset,
                    this.pLoc.getZ()
                ),
                ParticleType.SAND,
                true
            )
        );

        lines.add(
            new Line(
                new Location(
                    this.pLoc.getWorld(),
                    this.pLoc.getX() - offset,
                    this.pLoc.getY(),
                    this.pLoc.getZ()
                ),
                new Location(
                    this.pLoc.getWorld(),
                    this.pLoc.getX() + offset,
                    this.pLoc.getY(),
                    this.pLoc.getZ()
                ),
                ParticleType.TNT,
                true
            )
        );

        lines.add(
            new Line(
                new Location(
                    this.pLoc.getWorld(),
                    this.pLoc.getX(),
                    this.pLoc.getY(),
                    this.pLoc.getZ() - offset
                ),
                new Location(
                    this.pLoc.getWorld(),
                    this.pLoc.getX(),
                    this.pLoc.getY(),
                    this.pLoc.getZ() + offset
                ),
                ParticleType.PLAYER,
                true
            )
        );

        return lines;
    }
}
