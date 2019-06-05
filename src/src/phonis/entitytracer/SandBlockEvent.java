package src.phonis.entitytracer;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;

public class SandBlockEvent implements Listener{
	public SandBlockEvent(main plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onEntityBlockFormEvent(EntityChangeBlockEvent event) {
		if(!event.getTo().equals(Material.AIR)) {
	    	Entity entity = event.getEntity();
	    	Location loc = entity.getLocation();
	    	
	        if (entity.getType().compareTo(EntityType.FALLING_BLOCK) == 0) {   
	    		LocationChange change = new LocationChange(entity.getWorld(), loc, loc, EntityType.FALLING_BLOCK, ChangeType.EXPLOTION);
	    		
				main.addChange(change);
	        }
		}
	}
}