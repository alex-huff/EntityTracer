package phonis.entitytracer.util;

/**
 * Class representing offsets
 */
public class Offset {
    private float xOff;
    private float yOff;
    private float zOff;

    /**
     * Offset constructor
     *
     * @param xOff x offset
     * @param yOff y offset
     * @param zOff z offset
     */
    public Offset(float xOff, float yOff, float zOff) {
        this.xOff = xOff;
        this.yOff = yOff;
        this.zOff = zOff;
    }

    /**
     * Get x offset
     *
     * @return float
     */
    public float getX() {
        return this.xOff;
    }

    /**
     * Get y offset
     *
     * @return float
     */
    public float getY() {
        return this.yOff;
    }

    /**
     * Get z offset
     *
     * @return float
     */
    public float getZ() {
        return this.zOff;
    }
}
