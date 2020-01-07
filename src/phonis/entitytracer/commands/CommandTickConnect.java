package phonis.entitytracer.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import phonis.entitytracer.serializable.TracerUser;

import java.util.List;

/**
 * EntityTracerCommand for toggling the connection of ticks
 */
public class CommandTickConnect extends EntityTracerCommand {
    /**
     * CommandTickConnect constructor that calls the EntityTracerCommand super constructor
     */
    public CommandTickConnect() {
        super("tickconnect");
        this.addAlias("tc");
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

        tu.toggleTickConnect();
        player.sendMessage("Tick connection is now: " + tu.isTickConnect());
    }
}
