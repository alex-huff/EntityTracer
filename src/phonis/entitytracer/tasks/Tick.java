package phonis.entitytracer.tasks;

import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import phonis.entitytracer.EntityTracer;
import phonis.entitytracer.serializable.TracerUser;
import phonis.entitytracer.trace.*;

import java.util.*;

/**
 * Bukkit runnable that handles the per-tick calculations
 */
public class Tick implements Runnable {
    private Set<LocationChange> changes = new HashSet<>();
    private Map<Integer, EntityLocation> locations = new HashMap<>();
    private EntityTracer entityTracer;
    private int tickCount = 0;

    /**
     * Tick constructor
     */
    public Tick(EntityTracer entityTracer) {
        this.entityTracer = entityTracer;
    }

    /**
     * Starts the ticking
     */
    public void start() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(entityTracer, this, 0L, 1L);
    }

    /**
     * Add a location change to be processed
     *
     * @param lc location change
     */
    public void addChange(LocationChange lc) {
        this.changes.add(lc);
    }

    /**
     * Process an entity and get the change
     *
     * @param world  World
     * @param entity Entity
     */
    private void processEntity(World world, Entity entity) {
        Location loc = entity.getLocation();
        int id = entity.getEntityId();

        if (this.locations.containsKey(id)) {
            Location old = this.locations.get(id).getLocation();
            LocationChange change;

            if (this.locations.get(id).getNew()) {
                change = new LocationChange(world, old, loc, entity.getType(), ChangeType.START);
            } else {
                change = new LocationChange(world, old, loc, entity.getType(), ChangeType.NORMAL);
            }

            if (change.getChangeType().compareTo(ChangeType.NORMAL) != 0 || old.distance(loc) != 0) {
                this.changes.add(change);
            }

            this.locations.put(entity.getEntityId(), new EntityLocation(loc, false));
        } else {
            this.locations.put(entity.getEntityId(), new EntityLocation(loc, true));
        }
    }

    /**
     * Goes through server entities and processes them with processEntity
     */
    private void processEntities() {
        for (World world : Bukkit.getServer().getWorlds()) {
            for (Entity entity : world.getEntities()) {
                if (entity.getType().equals(EntityType.FALLING_BLOCK) || entity.getType().equals(EntityType.PRIMED_TNT)) {
                    processEntity(world, entity);
                }
            }
        }

        Set<Integer> keys = this.locations.keySet();
        List<Integer> removeList = new ArrayList<>();

        for (int key : keys) {
            if (this.locations.get(key).getState()) {
                this.locations.get(key).kill();
            } else {
                removeList.add(key);
            }
        }

        for (int key : removeList) {
            this.locations.remove(key);
        }
    }

    /**
     * Increments tick and resets of necessary
     */
    private void updateTick() {
        if (this.tickCount == 5) {
            this.tickCount = 0;
        } else {
            this.tickCount += 1;
        }
    }

    /**
     * Sends packets to player
     *
     * @param tu     TracerUser
     * @param player Player
     */
    private void sendPackets(TracerUser tu, Player player) {
        tu.updateRadius(player.getLocation());
        PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
        Iterator<ParticleLocation> it = tu.getParticleLocations().iterator();

        while (it.hasNext()) {
            ParticleLocation pLocation = it.next();

            if (pLocation.getLocation().getWorld().equals(player.getWorld())) {
                if (tickCount == 5 && (pLocation.getLocation().distance(player.getLocation()) < tu.getViewRadius() || tu.isUnlimitedRadius())) {
                    PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(
                        EnumParticle.REDSTONE,
                        true,
                        (float) pLocation.getLocation().getX(),
                        (float) pLocation.getLocation().getY() + 0.49F,
                        (float) pLocation.getLocation().getZ(),
                        pLocation.getType().getRGB().getR() / 255f,
                        pLocation.getType().getRGB().getG() / 255f,
                        pLocation.getType().getRGB().getB() / 255f,
                        1,
                        0
                    );

                    connection.sendPacket(packet);
                }
            }

            pLocation.decLife();

            if (pLocation.getLife() == 0) {
                it.remove();
            }
        }
    }

    /**
     * Method inherited from Runnable
     */
    @Override
    public void run() {
        this.processEntities();

        Set<UUID> keySet = TracerUser.hmd.data.keySet();

        for (UUID uuid : keySet) {
            TracerUser tu = TracerUser.getUser(uuid);

            if (tu.isTrace()) {
                Player player = Bukkit.getPlayer(uuid);

                if (player != null) {
                    World world = player.getWorld();

                    for (LocationChange change : this.changes) {
                        if (change.getWorld() == world && (change.getStart().distance(change.getFinish()) >= tu.getMinDistance() || change.getChangeType().compareTo(ChangeType.EXPLOSION) == 0)) {
                            if (player.getLocation().distance(change.getStart()) <= tu.getTraceRadius()) {
                                if (change.getType().compareTo(EntityType.PRIMED_TNT) == 0 && tu.isTraceTNT()) {
                                    if (change.getChangeType().compareTo(ChangeType.EXPLOSION) == 0 && tu.isEndPosTNT()) {
                                        tu.addTrace(
                                            new TNTEndTrace(change.getFinish())
                                        );
                                    } else if (change.getChangeType().compareTo(ChangeType.START) == 0) {
                                        tu.addTrace(
                                            new TNTTrace(
                                                change.getStart(),
                                                change.getFinish(),
                                                true
                                            )
                                        );
                                    } else {
                                        tu.addTrace(
                                            new TNTTrace(
                                                change.getStart(),
                                                change.getFinish(),
                                                false
                                            )
                                        );
                                    }
                                } else if (change.getType().compareTo(EntityType.FALLING_BLOCK) == 0 && tu.isTraceSand()) {
                                    if (change.getChangeType().compareTo(ChangeType.EXPLOSION) == 0 && tu.isEndPosSand()) {
                                        tu.addTrace(
                                            new SandEndTrace(change.getFinish())
                                        );
                                    } else if (change.getChangeType().compareTo(ChangeType.START) == 0) {
                                        tu.addTrace(
                                            new SandTrace(
                                                change.getStart(),
                                                change.getFinish(),
                                                true
                                            )
                                        );
                                    } else {
                                        tu.addTrace(
                                            new SandTrace(
                                                change.getStart(),
                                                change.getFinish(),
                                                false
                                            )
                                        );
                                    }
                                }
                            }
                        }
                    }

                    this.sendPackets(tu, player);
                }
            }
        }

        this.changes.clear();
        this.updateTick();
    }
}
