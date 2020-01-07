package phonis.entitytracer;

import org.bukkit.plugin.java.JavaPlugin;
import phonis.entitytracer.commands.CommandTracer;
import phonis.entitytracer.serializable.TracerUser;
import phonis.entitytracer.util.SerializationUtil;

import java.util.logging.Logger;

/**
 * Main class for EntityTracer
 */
public class EntityTracer extends JavaPlugin {
    private final Logger log = getLogger();

    /**
     * Method extended from JavaPlugin
     */
    @Override
    public void onEnable() {
        new CommandTracer(this, "tracer");
        SerializationUtil.deserialize(TracerUser.hmd, this.log);
    }

    /**
     * Method extended from JavaPlugin
     */
    @Override
    public void onDisable() {
        SerializationUtil.serialize(TracerUser.hmd, this.log);
    }
}
