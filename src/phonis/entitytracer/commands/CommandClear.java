package phonis.entitytracer.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import phonis.entitytracer.serializable.TracerUser;

import java.util.List;

/**
 * EntityTracerCommand for clearing traces
 */
public class CommandClear extends EntityTracerCommand {
    /**
     * CommandClear constructor that calls EntityTracerCommand super constructor
     */
    public CommandClear() {
        super("clear");
        this.addAlias("c");
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

            tu.clearParticles();
            player.sendMessage("Cleared particles");
        } else if (args[0].equals("tnt") || args[0].equals("t")) {
            tu = TracerUser.getUser(player.getUniqueId());

            tu.clearTNT();
            player.sendMessage("Cleared TNT particles");
        } else if (args[0].equals("sand") || args[0].equals("s")) {
            tu = TracerUser.getUser(player.getUniqueId());

            tu.clearSand();
            player.sendMessage("Cleared sand particles");
        } else {
            throw new CommandException("Invalid toggle command");
        }
    }
}
