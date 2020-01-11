package phonis.entitytracer.listeners;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import phonis.entitytracer.EntityTracer;
import phonis.entitytracer.tasks.Tick;
import phonis.entitytracer.trace.ChangeType;
import phonis.entitytracer.trace.EntityLocation;
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
    public void onExplosionEvent(ExplosionPrimeEvent event) {
        Entity entity = event.getEntity();

        if (entity.getType().compareTo(EntityType.PRIMED_TNT) == 0) {
            Location loc = entity.getLocation();
            Location oldLoc = loc;
            Location prevTick = loc.clone().add(entity.getVelocity().multiply(-1.0204081434053665D));
            Location prevTick2 = loc.clone().add(entity.getVelocity().multiply(-1));

            if (this.tick.getLastTicks().contains(new EntityLocation(prevTick, entity.getType()))) {
                oldLoc = prevTick;
            } else if (this.tick.getLastTicks().contains(new EntityLocation(prevTick2, entity.getType()))) {
                oldLoc = prevTick2;
            }

            LocationChange change = new LocationChange(entity.getWorld(), oldLoc, loc, entity.getType(), ChangeType.EXPLOSION, entity.getVelocity().length());

            this.tick.addChange(change);
        }
    }
}
