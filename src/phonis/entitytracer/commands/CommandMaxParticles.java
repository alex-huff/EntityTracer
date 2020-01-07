package phonis.entitytracer.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import phonis.entitytracer.serializable.TracerUser;

import java.util.List;

/**
 * EntityTracerCommand for setting maximum particles
 */
public class CommandMaxParticles extends EntityTracerCommand {
    /**
     * CommandMaxParticles constructor that calls EntityTracerCommand super constructor
     */
    public CommandMaxParticles() {
        super("maxparticles");
        this.addAlias("mp");
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

            tu.setMaxParticles(EntityTracerCommand.parseInt(args[0]));
            player.sendMessage("Max particles is now: " + tu.getMaxParticles());
        } else {
            throw new CommandException("No value entered for particles");
        }
    }
}
