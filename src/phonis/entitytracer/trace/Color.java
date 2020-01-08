package phonis.entitytracer.trace;

/**
 * Color class
 */
public class Color {
    private final int r;
    private final int g;
    private final int b;

    /**
     * Color constructor
     *
     * @param r red
     * @param g green
     * @param b blue
     */
    public Color(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    /**
     * Get red
     *
     * @return int
     */
    public int getR() {
        return this.r;
    }

    /**
     * Get green
     *
     * @return int
     */
    public int getG() {
        return this.g;
    }

    /**
     * Get blue
     *
     * @return int
     */
    public int getB() {
        return this.b;
    }
}
