package phonis.entitytracer.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import phonis.entitytracer.serializable.TracerUser;

import java.util.List;

/**
 * EntityTracerCommand for setting minimum distance
 */
public class CommandMinDistance extends EntityTracerCommand {
    /**
     * CommandMinDistance constructor that calls EntityTracerCommand super constructor
     */
    public CommandMinDistance() {
        super("mindistance");
        this.addAlias("md");
        this.args.add("(Minimum distance entity must travel for tracking)");
    }

    /**
     * Tab complete method extended from EntityTracerCommand
     *
     * @param args Arguments
     * @return List<String>
     */
    @Override
    public List<String> topTabComplete(String[] args) {
        return null;
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
        if (args.length > 0) {
            TracerUser tu = TracerUser.getUser(player.getUniqueId());

            tu.setMinDistance(EntityTracerCommand.parseDouble(args[0]));
            player.sendMessage("Min distance is now: " + tu.getMinDistance());
        } else {
            throw new CommandException("No value entered for distance");
        }
    }
}
