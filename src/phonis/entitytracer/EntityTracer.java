package phonis.entitytracer;

import org.bukkit.plugin.java.JavaPlugin;
import phonis.entitytracer.commands.CommandTracer;
import phonis.entitytracer.listeners.ExplosionEvent;
import phonis.entitytracer.listeners.SandBlockEvent;
import phonis.entitytracer.serializable.TracerUser;
import phonis.entitytracer.tasks.Tick;
import phonis.entitytracer.util.SerializationUtil;

import java.io.File;
import java.util.logging.Logger;

/**
 * Main class for EntityTracer
 */
public class EntityTracer extends JavaPlugin {
    public static final String path = "plugins/EntityTracer/";

    private final Logger log = getLogger();

    /**
     * Method extended from JavaPlugin
     */
    @Override
    public void onEnable() {
        File f = new File(EntityTracer.path);

        if (!f.exists()) {
            if (f.mkdirs()) {
                this.log.info("Creating directory: " + path);
            }
        } else {
            SerializationUtil.deserialize(TracerUser.hmd, this.log);
        }

        Tick tick = new Tick(this);

        new CommandTracer(this, "tracer");

        new ExplosionEvent(this, tick);
        new SandBlockEvent(this, tick);

        tick.start();
    }

    /**
     * Method extended from JavaPlugin
     */
    @Override
    public void onDisable() {
        SerializationUtil.serialize(TracerUser.hmd, this.log);
    }
}
