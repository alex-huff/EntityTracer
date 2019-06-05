package src.phonis.entitytracer;

import org.bukkit.Location;

public class CannoningUtil {
	public static boolean in1x1(Location loc) {
		if(Math.abs(loc.getX()) % 1 <= .51 && Math.abs(loc.getX()) % 1 >= .49 && Math.abs(loc.getZ()) % 1 <= .51 && Math.abs(loc.getZ()) % 1 >= .49) {
			return true;
		}
		
		return false;
	}
}
