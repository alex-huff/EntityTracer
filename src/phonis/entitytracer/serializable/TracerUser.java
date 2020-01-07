package phonis.entitytracer.serializable;

import java.io.Serializable;
import java.util.UUID;

/**
 * Class for storing data related to a user of the EntityTracer plugin
 */
public class TracerUser implements Serializable {
    public static HashMapData<UUID, TracerUser> hmd = new HashMapData<>("plugins/EntityTracer/TracerUser.ser");

    private boolean trace;
    private boolean traceSand;
    private boolean traceTNT;
    private boolean endPosSand;
    private boolean endPosTNT;
    private boolean tickConnect;
    private double minDistance;
    private double traceRadius;
    private transient double viewRadius;
    private int maxParticles;
    private int traceTime;

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
    private double getViewRadius() {
        return viewRadius;
    }

    /**
     * Set view radius for user
     *
     * @param viewRadius new radius
     */
    private void setViewRadius(double viewRadius) {
        this.viewRadius = viewRadius;
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
}
