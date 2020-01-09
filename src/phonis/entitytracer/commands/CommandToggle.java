package phonis.entitytracer.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import phonis.entitytracer.serializable.TracerUser;

import java.util.List;

/**
 * EntityTracerCommand used for toggling values
 */
public class CommandToggle extends EntityTracerCommand {
    /**
     * CommandToggle constructor that calls the EntityTracerCommand super constructor
     */
    public CommandToggle() {
        super("toggle");
        this.addSubCommand(new CommandEndPos());
        this.addSubCommand(new CommandTickConnect());
        this.addAlias("tog");
        this.args.add("tnt");
        this.args.add("sand");
    }

    /**
     * Tab complete method extended from EntityTracerCommand
     *
     * @param args Arguments
     * @return List<String>
     */
    @Override
    public List<String> topTabComplete(String[] args) {
        return this.argsAutocomplete(args, 1);
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
     * @throws CommandException command exception
     */
    @Override
    public void execute(Player player, String[] args) throws CommandException {
        TracerUser tu;

        if (args.length < 1) {
            tu = TracerUser.getUser(player.getUniqueId());

            tu.toggleTrace();
            player.sendMessage("Tracer is now: " + tu.isTrace());
        } else if (args[0].equals("tnt")) {
            tu = TracerUser.getUser(player.getUniqueId());

            tu.toggleTraceTNT();
            player.sendMessage("TNT tracing is now: " + tu.isTraceTNT());
        } else if (args[0].equals("sand")) {
            tu = TracerUser.getUser(player.getUniqueId());

            tu.toggleTraceSand();
            player.sendMessage("Sand tracing is now: " + tu.isTraceSand());
        } else {
            throw new CommandException("Invalid toggle command");
        }
    }
}
