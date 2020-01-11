package phonis.entitytracer.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import phonis.entitytracer.EntityTracer;
import phonis.entitytracer.tasks.Tick;
import phonis.entitytracer.trace.ChangeType;
import phonis.entitytracer.trace.LocationChange;

/**
 * Listener for sand solidifying
 */
public class SandBlockEvent implements Listener {
    private Tick tick;

    public SandBlockEvent(EntityTracer entityTracer, Tick tick) {
        entityTracer.getServer().getPluginManager().registerEvents(this, entityTracer);

        this.tick = tick;
    }

    /**
     * Method inherited from Listener takes in EntityChangeBlockEvent
     *
     * @param event EntityChangeBlockEvent
     */
    @EventHandler
    public void onEntityBlockFormEvent(EntityChangeBlockEvent event) {
        if (!event.getTo().equals(Material.AIR)) {
            Entity entity = event.getEntity();

            if (entity.getType().compareTo(EntityType.FALLING_BLOCK) == 0) {
                Location loc = entity.getLocation();
                LocationChange change = new LocationChange(entity.getWorld(), loc, loc, EntityType.FALLING_BLOCK, ChangeType.EXPLOSION, entity.getVelocity().length());

                this.tick.addChange(change);
            }
        }
    }
}
