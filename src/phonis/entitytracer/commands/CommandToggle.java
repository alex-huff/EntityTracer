package phonis.entitytracer.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import phonis.entitytracer.serializable.TracerUser;

import java.util.ArrayList;
import java.util.List;

/**
 * EntityTracerCommand used for toggling values
 */
public class CommandToggle extends EntityTracerCommand {
    private List<String> traceTypes = new ArrayList<>();

    /**
     * CommandToggle constructor that calls the EntityTracerCommand super constructor
     */
    public CommandToggle() {
        super("toggle");
        this.addSubCommand(new CommandEndPos());
        this.addSubCommand(new CommandTickConnect());
        this.addAlias("tog");
        traceTypes.add("tnt");
        traceTypes.add("sand");
        traceTypes.add("t");
        traceTypes.add("s");
    }

    /**
     * Tab complete method extended from EntityTracerCommand
     *
     * @param args Arguments
     * @return List<String>
     */
    @Override
    public List<String> topTabComplete(String[] args) {
        List<String> ret = new ArrayList<>();

        if (args.length > 1) {
            return ret;
        }

        for (String traceType : this.traceTypes) {
            if (traceType.startsWith(args[0])) {
                ret.add(traceType);
            }
        }

        return ret;
    }

    /**
     * Execute method extended from EntityTracerCommand for Non-Player senders
     *
     * @param sender Non-Player sender
     * @param args   Arguments
     * @throws CommandException command exception
     */
    @Override
    public void execute(CommandSender sender, String[] args) throws CommandException {
        throw new CommandException(CommandException.consoleError);
    }

    /**
     * Execute method extended from EntityTracerCommand for Player senders
     *
     * @param player Player
     * @param args   Arguments
     */
    @Override
    public void execute(Player player, String[] args) {
        TracerUser tu;

        if (args.length < 1) {
            tu = TracerUser.getUser(player.getUniqueId());

            tu.toggleTrace();
            player.sendMessage("Tracer is now: " + tu.isTrace());
        } else if (args[0].equals("tnt") || args[0].equals("t")) {
            tu = TracerUser.getUser(player.getUniqueId());

            tu.toggleTraceTNT();
            player.sendMessage("TNT tracing is now: " + tu.isTraceTNT());
        } else if (args[0].equals("sand") || args[0].equals("s")) {
            tu = TracerUser.getUser(player.getUniqueId());

            tu.toggleTraceSand();
            player.sendMessage("Sand tracing is now: " + tu.isTraceSand());
        }
    }
}
