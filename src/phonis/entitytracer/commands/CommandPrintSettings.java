package phonis.entitytracer.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import phonis.entitytracer.serializable.TracerUser;

import java.util.List;

/**
 * EntityTracerCommand for printing a user's settings
 */
public class CommandPrintSettings extends EntityTracerCommand {
    /**
     * CommandPrintSettings constructor that calls the EntityTracerCommand super constructor
     */
    public CommandPrintSettings() {
        super("printsettings");
        this.addAlias("settings");
        this.addAlias("ps");
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
     */
    @Override
    public void execute(Player player, String[] args) {
        TracerUser tu = TracerUser.getUser(player.getUniqueId());

        String message = "" + ChatColor.BOLD + ChatColor.BLUE + "Settings:" + ChatColor.RESET + "\n";

        message +=
            ChatColor.AQUA + "Trace enabled:             " + ChatColor.WHITE + tu.isTrace() + "\n" +
                ChatColor.AQUA + "Sand trace enabled:        " + ChatColor.WHITE + tu.isTraceSand() + "\n" +
                ChatColor.AQUA + "TNT trace enabled:         " + ChatColor.WHITE + tu.isTraceTNT() + "\n" +
                ChatColor.AQUA + "Sand end position enabled: " + ChatColor.WHITE + tu.isEndPosSand() + "\n" +
                ChatColor.AQUA + "TNT end position enabled:  " + ChatColor.WHITE + tu.isEndPosTNT() + "\n" +
                ChatColor.AQUA + "Connect ticks:             " + ChatColor.WHITE + tu.isTickConnect() + "\n" +
                ChatColor.AQUA + "Minimum distance traveled: " + ChatColor.WHITE + tu.getMinDistance() + "\n" +
                ChatColor.AQUA + "Trace radius:              " + ChatColor.WHITE + tu.getTraceRadius() + "\n" +
                ChatColor.AQUA + "Maximum particles:         " + ChatColor.WHITE + tu.getMaxParticles() + "\n" +
                ChatColor.AQUA + "Trace time in ticks:       " + ChatColor.WHITE + tu.getTraceTime() + "\n";

        player.sendMessage(message);
    }
}
