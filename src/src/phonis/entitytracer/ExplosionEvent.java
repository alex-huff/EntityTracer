package src.phonis.entitytracer;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class ExplosionEvent implements Listener{
	public ExplosionEvent(main plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
    @EventHandler
    public void onExplosionEvent(EntityExplodeEvent event) {
    	Entity entity = event.getEntity();
    	Location loc = entity.getLocation();
    	
        if (entity.getType().compareTo(EntityType.PRIMED_TNT) == 0) {   
    		LocationChange change = new LocationChange(entity.getWorld(), loc, loc, EntityType.PRIMED_TNT, ChangeType.EXPLOTION);
    		
    		main.addChange(change);
        }
    }
}