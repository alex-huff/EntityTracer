package phonis.entitytracer.serializable;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import phonis.entitytracer.EntityTracer;
import phonis.entitytracer.trace.ParticleLocation;
import phonis.entitytracer.trace.ParticleLocationComparator;
import phonis.entitytracer.trace.ParticleType;
import phonis.entitytracer.trace.Trace;

import java.io.Serializable;
import java.util.*;

/**
 * Class for storing data related to a user of the EntityTracer plugin
 */
public class TracerUser implements Serializable {
    public static HashMapData<UUID, TracerUser> hmd = new HashMapData<>(EntityTracer.path + "TracerUser.ser");

    private boolean trace;
    private boolean traceSand;
    private boolean traceTNT;
    private boolean endPosSand;
    private boolean endPosTNT;
    private boolean tickConnect;
    private transient boolean unlimitedRadius = false;
    private double minDistance;
    private double traceRadius;
    private transient double viewRadius;
    private int maxParticles;
    private int traceTime;
    private transient Set<ParticleLocation> pLocs;

    /**
     * Private constructor for TracerUser
     *
     * @param trace        boolean representing tracing
     * @param traceSand    boolean representing tracing of sand
     * @param traceTNT     boolean representing tracing of tnt
     * @param endPosSand   boolean representing end position of sand
     * @param endPosTNT    boolean representing end position of tnt
     * @param tickConnect  boolean representing tick connection
     * @param minDistance  double representing minimum distance
     * @param traceRadius  double representing trace radius
     * @param viewRadius   double representing view radius
     * @param maxParticles int representing max particles
     * @param traceTime    int representing trace time
     */
    private TracerUser(boolean trace, boolean traceSand, boolean traceTNT, boolean endPosSand, boolean endPosTNT, boolean tickConnect, double minDistance, double traceRadius, double viewRadius, int maxParticles, int traceTime) {
        this.trace = trace;
        this.traceSand = traceSand;
        this.traceTNT = traceTNT;
        this.endPosSand = endPosSand;
        this.endPosTNT = endPosTNT;
        this.tickConnect = tickConnect;
        this.minDistance = minDistance;
        this.traceRadius = traceRadius;
        this.viewRadius = viewRadius;
        this.maxParticles = maxParticles;
        this.traceTime = traceTime;
    }

    /**
     * Private default constructor for TracerUser
     */
    private TracerUser() {
        this(false, true, true, false, true, true, 5.0D, 100D, 0D, 1000, 100);
    }

    /**
     * Get existing or new TracerUser from player's UUID
     *
     * @param uuid UUID of player
     * @return TracerUser
     */
    public static TracerUser getUser(UUID uuid) {
        TracerUser ret;

        ret = TracerUser.hmd.data.get(uuid);

        if (ret == null) {
            TracerUser.hmd.data.put(uuid, new TracerUser());

            ret = TracerUser.hmd.data.get(uuid);
        }

        return ret;
    }

    /**
     * Determines whether user has tracing enabled
     *
     * @return boolean
     */
    public boolean isTrace() {
        return trace;
    }

    /**
     * Toggle user tracing
     */
    public void toggleTrace() {
        this.trace = !this.trace;
    }

    /**
     * Determines whether user is tracing sand
     *
     * @return boolean
     */
    public boolean isTraceSand() {
        return traceSand;
    }

    /**
     * Toggle user tracing sand
     */
    public void toggleTraceSand() {
        this.traceSand = !this.traceSand;
    }

    /**
     * Determines whether user is tracing tnt
     *
     * @return boolean
     */
    public boolean isTraceTNT() {
        return traceTNT;
    }

    /**
     * Toggle user tracing tnt
     */
    public void toggleTraceTNT() {
        this.traceTNT = !this.traceTNT;
    }

    /**
     * Determines whether user is tracing sand end positions
     *
     * @return boolean
     */
    public boolean isEndPosSand() {
        return endPosSand;
    }

    /**
     * Toggle user sand end positions tracing
     */
    public void toggleEndPosSand() {
        this.endPosSand = !this.endPosSand;
    }

    /**
     * Determines whether user is tracing tnt end positions
     *
     * @return boolean
     */
    public boolean isEndPosTNT() {
        return endPosTNT;
    }

    /**
     * Toggle user tnt end positions tracing
     */
    public void toggleEndPosTNT() {
        this.endPosTNT = !this.endPosTNT;
    }

    /**
     * Determines whether user has their traces connected
     *
     * @return boolean
     */
    public boolean isTickConnect() {
        return tickConnect;
    }

    /**
     * Toggle user tick connect
     */
    public void toggleTickConnect() {
        this.tickConnect = !this.tickConnect;
    }

    /**
     * Determines whether this user has unlimited radius based on max particles
     *
     * @return boolean
     */
    public boolean isUnlimitedRadius() {
        return this.unlimitedRadius;
    }

    /**
     * Get minimum distance traveled per tick for tracking
     *
     * @return double
     */
    public double getMinDistance() {
        return minDistance;
    }

    /**
     * Set minimum distance from user for tracing
     *
     * @param minDistance new distance
     */
    public void setMinDistance(double minDistance) {
        this.minDistance = minDistance;
    }

    /**
     * Get radius for tracing
     *
     * @return double
     */
    public double getTraceRadius() {
        return traceRadius;
    }

    /**
     * Set tracing radius
     *
     * @param traceRadius new radius
     */
    public void setTraceRadius(double traceRadius) {
        this.traceRadius = traceRadius;
    }

    /**
     * Get view radius for user
     *
     * @return double
     */
    public double getViewRadius() {
        return viewRadius;
    }

    /**
     * Get max particles sent to user per tick
     *
     * @return int
     */
    public int getMaxParticles() {
        return maxParticles;
    }

    /**
     * Set max particles send to user per tick
     *
     * @param maxParticles new max
     */
    public void setMaxParticles(int maxParticles) {
        this.maxParticles = maxParticles;
    }

    /**
     * Get time for trace to last
     *
     * @return int
     */
    public int getTraceTime() {
        return traceTime;
    }

    /**
     * Set time for trace to last
     *
     * @param traceTime new time
     */
    public void setTraceTime(int traceTime) {
        this.traceTime = traceTime;
    }

    /**
     * Get particle location of user
     *
     * @return Set<ParticleLocation>
     */
    public Set<ParticleLocation> getParticleLocations() {
        this.unNull();

        return this.pLocs;
    }

    /**
     * Set set to HashSet if nullified in serialization
     */
    public void unNull() {
        if (this.pLocs == null) {
            this.pLocs = new HashSet<>();
        }
    }

    /**
     * Clears particles from user
     */
    public void clearParticles() {
        this.unNull();

        this.pLocs.clear();
    }

    /**
     * Clear TNT particles
     */
    public void clearTNT() {
        this.unNull();

        this.pLocs.removeIf(pLocation -> pLocation.getType().compareTo(ParticleType.TNT) == 0 || pLocation.getType().compareTo(ParticleType.TNTENDPOS) == 0);
    }

    /**
     * Clear Sand particles
     */
    public void clearSand() {
        this.unNull();

        this.pLocs.removeIf(pLocation -> pLocation.getType().compareTo(ParticleType.SAND) == 0 || pLocation.getType().compareTo(ParticleType.SANDENDPOS) == 0);
    }

    /**
     * Add trace
     *
     * @param trace trace type
     */
    public void addTrace(Trace trace) {
        this.addAllParticles(trace.getParticles(this.getTraceTime(), this.tickConnect));
    }

    /**
     * Add particle locations
     *
     * @param pI particle locations iterable
     */
    private void addAllParticles(Iterable<ParticleLocation> pI) {
        this.unNull();

        if (this.pLocs.size() != 0 && !(this.pLocs.iterator().next().getLocation().getWorld() == pI.iterator().next().getLocation().getWorld())) {
            this.pLocs.clear();
        }

        for (ParticleLocation pl : pI) {
            this.pLocs.remove(pl);
            this.pLocs.add(pl);
        }
    }

    /**
     * Update radius to account for max particles
     *
     * @param loc location of player
     */
    public void updateRadius(Location loc) {
        this.unNull();

        if (this.pLocs.size() != 0 && !(loc.getWorld() == this.pLocs.iterator().next().getLocation().getWorld())) {
            this.pLocs.clear();

            return;
        }

        if (this.maxParticles > this.pLocs.size()) {
            this.unlimitedRadius = true;
        } else {
            this.unlimitedRadius = false;

            List<ParticleLocation> pLAL = new ArrayList<>(this.pLocs);
            PriorityQueue<ParticleLocation> pq = new PriorityQueue<>((new ParticleLocationComparator(loc)).reversed());
            pq.addAll(pLAL.subList(0, this.maxParticles));

            for (int i = this.maxParticles; i < this.pLocs.size(); i++) {
                if (pq.comparator().compare(pLAL.get(i), pq.peek()) > 0) {
                    pq.poll();
                    pq.add(pLAL.get(i));
                }
            }

            if (pq.peek() == null) return;

            this.viewRadius = loc.distance(pq.peek().getLocation());
        }
    }

    /**
     * Get string representing user's settings
     *
     * @return String
     */
    public String printSettings() {
        String message = "" + ChatColor.BOLD + ChatColor.BLUE + "Settings:" + ChatColor.RESET + "\n";

        message +=
            ChatColor.AQUA + "Trace enabled:             " + ChatColor.WHITE + this.isTrace() + "\n" +
                ChatColor.AQUA + "Sand trace enabled:        " + ChatColor.WHITE + this.isTraceSand() + "\n" +
                ChatColor.AQUA + "TNT trace enabled:         " + ChatColor.WHITE + this.isTraceTNT() + "\n" +
                ChatColor.AQUA + "Sand end position enabled: " + ChatColor.WHITE + this.isEndPosSand() + "\n" +
                ChatColor.AQUA + "TNT end position enabled:  " + ChatColor.WHITE + this.isEndPosTNT() + "\n" +
                ChatColor.AQUA + "Connect ticks:             " + ChatColor.WHITE + this.isTickConnect() + "\n" +
                ChatColor.AQUA + "Minimum distance traveled: " + ChatColor.WHITE + this.getMinDistance() + "\n" +
                ChatColor.AQUA + "Trace radius:              " + ChatColor.WHITE + this.getTraceRadius() + "\n" +
                ChatColor.AQUA + "Maximum particles:         " + ChatColor.WHITE + this.getMaxParticles() + "\n" +
                ChatColor.AQUA + "Trace time in ticks:       " + ChatColor.WHITE + this.getTraceTime() + "\n";

        return message;
    }

    /**
     * Copies the settings from another user
     *
     * @param other TracerUser
     */
    public void copySettings(TracerUser other) {
        this.trace = other.trace;
        this.traceSand = other.traceSand;
        this.traceTNT = other.traceTNT;
        this.endPosSand = other.endPosSand;
        this.endPosTNT = other.endPosTNT;
        this.tickConnect = other.tickConnect;
        this.minDistance = other.minDistance;
        this.traceRadius = other.traceRadius;
        this.maxParticles = other.maxParticles;
        this.traceTime = other.traceTime;
    }
}
