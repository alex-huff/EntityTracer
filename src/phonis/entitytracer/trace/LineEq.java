package phonis.entitytracer.trace;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.bukkit.Location;
import org.bukkit.util.Vector;

/**
 * Class representing a line in space
 */
public class LineEq {
    private Vector direction;
    private double yzInty;
    private double yzIntz;
    private double yxInty;
    private double yxIntx;
    private double zxIntz;
    private double zxIntx;

    /**
     * LineEq constructor generates line properties from a direction and a point
     * @param direction direction of line
     * @param location location of point
     */
    public LineEq(Vector direction, Location location) {
        this.direction = direction;

        if (direction.getX() != 0) {
            double yzInt = (location.getX() * -1) / direction.getX();

            this.yzInty = location.getY() + direction.getY() * yzInt;
            this.yzIntz = location.getZ() + direction.getZ() * yzInt;
        } else {
            this.yzInty = Double.NaN;
            this.yzIntz = Double.NaN;
        }

        if (direction.getY() != 0) {
            double zxInt = (location.getY() * -1) / direction.getY();

            this.zxIntz = location.getZ() + direction.getZ() * zxInt;
            this.zxIntx = location.getX() + direction.getX() + zxInt;
        } else {
            this.zxIntz = Double.NaN;
            this.zxIntx = Double.NaN;
        }

        if (direction.getZ() != 0) {
            double yxInt = (location.getZ() * -1) / direction.getZ();

            this.yxInty = location.getY() + direction.getY() * yxInt;
            this.yxIntx = location.getX() + direction.getX() * yxInt;
        } else {
            this.yxInty = Double.NaN;
            this.yxIntx = Double.NaN;
        }
    }

    /**
     * Method overridden from Object
     *
     * @return int
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31).
                append(this.direction).
                append(this.yzInty).
                append(this.yzIntz).
                append(this.yxInty).
                append(this.yxIntx).
                append(this.zxIntz).
                append(this.zxIntx).
                toHashCode();
    }

    /**
     * Method overridden from Object
     *
     * @param obj obj to check
     * @return boolean
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof LineEq)) {
            return false;
        }

        if (obj == this) {
            return true;
        }

        final LineEq other = (LineEq) obj;

        return new EqualsBuilder().
                append(this.direction, other.direction).
                append(this.yzInty, other.yzInty).
                append(this.yzIntz, other.yzIntz).
                append(this.yxInty, other.yxInty).
                append(this.yxIntx, other.yxIntx).
                append(this.zxIntz, other.zxIntz).
                append(this.zxIntx, other.zxIntx).
                isEquals();
    }
}
