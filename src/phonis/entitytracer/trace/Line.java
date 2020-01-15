package phonis.entitytracer.trace;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Class representing trace line
 */
public class Line {
    private Location start;
    private Location finish;
    private ParticleType type;
    private boolean connected;
    private Vector direction;
    private LineEq lineEq;
    private Set<Artifact> artifacts = new HashSet<>();

    /**
     * Constructor for trace line
     *
     * @param start            start location
     * @param finish           finish location
     * @param type             particle type
     * @param startType        start type
     * @param finishType       finish type
     * @param startOffsetType  start offset type
     * @param finishOffsetType finish offset type
     * @param connected        is line connected
     */
    public Line(Location start, Location finish, ParticleType type, ParticleType startType, ParticleType finishType, OffsetType startOffsetType, OffsetType finishOffsetType, boolean connected) {
        this.start = start;
        this.finish = finish;
        this.type = type;
        this.connected = connected;
        this.direction = this.finish.clone().subtract(this.start).toVector().normalize();
        this.lineEq = new LineEq(direction, this.start);

        if (startType != null && startOffsetType != null) {
            this.artifacts.add(new Artifact(this.start, startType, startOffsetType));
        }

        if (finishType != null && finishOffsetType != null) {
            this.artifacts.add(new Artifact(this.finish, finishType, finishOffsetType));
        }
    }

    /**
     * Constructor for trace line
     *
     * @param start     start location
     * @param finish    finish location
     * @param type      particle type
     * @param connected is line connected
     */
    public Line(Location start, Location finish, ParticleType type, boolean connected) {
        this(start, finish, type, null, null, null, null, connected);
    }

    /**
     * Gets type of particles
     *
     * @return ParticleType
     */
    public ParticleType getType() {
        return this.type;
    }

    /**
     * Gets combining of two lines assuming same particle type and connection
     *
     * @param other other Line
     */
    public Line getCombinedLine(Line other) {
        if (this.start.distance(other.finish) >= other.start.distance(this.finish)) {
            System.out.println(this.start.toString() + " " + this.finish.toString());

            return new Line(this.start, other.finish, this.type, this.connected).addArtifacts(this).addArtifacts(other);
        }

        return new Line(other.start, this.finish, this.type, this.connected).addArtifacts(this).addArtifacts(other);
    }

    /**
     * Adds artifacts of another line
     *
     * @param other Line
     * @return Line
     */
    public Line addArtifacts(Line other) {
        if (!other.artifacts.isEmpty()) this.artifacts.addAll(other.artifacts);

        return this;
    }

    /**
     * Gets line equation
     *
     * @return LineEq
     */
    public LineEq getLineEq() {
        return this.lineEq;
    }

    /**
     * Method for getting the start and end particles
     *
     * @param life life in ticks
     * @return List<ParticleLocation>
     */
    private List<ParticleLocation> getEndParticles(int life) {
        List<ParticleLocation> ret = new ArrayList<>();

        ret.add(new ParticleLocation(this.start, life, this.type));
        ret.add(new ParticleLocation(this.finish, life, this.type));

        return ret;
    }

    /**
     * Get particles in a line
     *
     * @param life life in ticks
     * @return List<ParticleLocation>
     */
    private List<ParticleLocation> getLineParticles(int life) {
        double distance = this.start.distance(this.finish);
        Vector intervalDirection = this.direction.multiply(.25);
        Vector di2 = intervalDirection.clone();

        List<ParticleLocation> ret = new ArrayList<>(this.getEndParticles(life));

        while (di2.length() < distance) {
            ret.add(new ParticleLocation(this.start.clone().add(di2.getX(), di2.getY(), di2.getZ()), life, this.type));
            di2.add(intervalDirection);
        }

        return ret;
    }

    /**
     * Gets particles representing a trace line
     *
     * @param life life in ticks
     * @return List<ParticleLocation>
     */
    public List<ParticleLocation> getParticles(int life) {
        List<ParticleLocation> ret = new ArrayList<>();

        for (Artifact artifact : this.artifacts) {
            ret.addAll(artifact.getParticles(life));
        }

        if (this.connected) ret.addAll(this.getLineParticles(life));
        else ret.addAll(this.getEndParticles(life));

        return ret;
    }

    /**
     * Determines if a line contains another, only applicable if lines face same direction
     *
     * @param other line to check
     * @return boolean
     */
    public boolean contains(Line other) {
        return (
            (
                this.start.getX() <= other.start.getX() &&
                    this.finish.getX() >= other.finish.getX()
            ) || (
                this.start.getX() >= other.start.getX() &&
                    this.finish.getX() <= other.finish.getX()
            )
        ) && (
            (
                this.start.getY() <= other.start.getY() &&
                    this.finish.getY() >= other.finish.getY()
            ) || (
                this.start.getY() >= other.start.getY() &&
                    this.finish.getY() <= other.finish.getY()
            )
        ) && (
            (
                this.start.getZ() <= other.start.getZ() &&
                    this.finish.getZ() >= other.finish.getZ()
            ) || (
                this.start.getZ() >= other.start.getZ() &&
                    this.finish.getZ() <= other.finish.getZ()
            )
        );
    }

    /**
     * Determines of lines overlap, only applicable if lines face same direction
     *
     * @param other other line
     * @return boolean
     */
    public boolean overlaps(Line other) {
        return (
            (
                this.start.getX() <= other.start.getX() &&
                    this.finish.getX() <= other.finish.getX() &&
                    this.finish.getX() >= other.start.getX()
            ) || (
                this.start.getX() >= other.start.getX() &&
                    this.finish.getX() >= other.finish.getX() &&
                    this.finish.getX() <= other.start.getX()
            )
        ) && (
            (
                this.start.getY() <= other.start.getY() &&
                    this.finish.getY() <= other.finish.getY() &&
                    this.finish.getY() >= other.start.getY()
            ) || (
                this.start.getY() >= other.start.getY() &&
                    this.finish.getY() >= other.finish.getY() &&
                    this.finish.getY() <= other.start.getY()
            )
        ) && (
            (
                this.start.getZ() <= other.start.getZ() &&
                    this.finish.getZ() <= other.finish.getZ() &&
                    this.finish.getZ() >= other.start.getZ()
            ) || (
                this.start.getZ() >= other.start.getZ() &&
                    this.finish.getZ() >= other.finish.getZ() &&
                    this.finish.getZ() <= other.start.getZ()
            )
        );
    }
}
