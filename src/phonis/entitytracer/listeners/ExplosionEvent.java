package phonis.entitytracer.listeners;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import phonis.entitytracer.EntityTracer;
import phonis.entitytracer.tasks.Tick;
import phonis.entitytracer.trace.ChangeType;
import phonis.entitytracer.trace.LocationChange;

/**
 * Listener for explosions
 */
public class ExplosionEvent implements Listener {
    private Tick tick;

    public ExplosionEvent(EntityTracer entityTracer, Tick tick) {
        entityTracer.getServer().getPluginManager().registerEvents(this, entityTracer);

        this.tick = tick;
    }

    /**
     * Method inherited from Listener takes in EntityExplodeEvent
     *
     * @param event EntityExplodeEvent
     */
    @EventHandler
    public void onExplosionEvent(EntityExplodeEvent event) {
        Entity entity = event.getEntity();

        if (entity.getType().compareTo(EntityType.PRIMED_TNT) == 0) {
            Location loc = entity.getLocation();
            LocationChange change = new LocationChange(entity.getWorld(), loc, loc, EntityType.PRIMED_TNT, ChangeType.EXPLOSION);

            this.tick.addChange(change);
        }
    }
}
