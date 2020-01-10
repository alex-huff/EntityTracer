package phonis.entitytracer.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import phonis.entitytracer.serializable.TracerUser;

import java.util.List;

/**
 * EntityTracerCommand that sets trace time
 */
public class CommandTraceTime extends EntityTracerCommand {
    /**
     * CommandTraceTime constructor that calls EntityTracerCommand super constructor
     */
    public CommandTraceTime() {
        super("tracetime");
        this.addAlias("tt");
        this.args.add("(Ticks a trace will last)");
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

            tu.setTraceTime(EntityTracerCommand.parseInt(args[0]));
            player.sendMessage("Trace time is now: " + tu.getTraceTime() + " ticks");
        } else {
            throw new CommandException("No value entered for ticks");
        }
    }
}
