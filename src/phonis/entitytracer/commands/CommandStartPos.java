package phonis.entitytracer.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import phonis.entitytracer.serializable.TracerUser;

import java.util.List;

/**
 * EntityTracerCommand that toggles start position tracking
 */
public class CommandStartPos extends EntityTracerCommand {
    /**
     * CommandStartPos constructor that calls the EntityTracerCommand super constructor
     */
    public CommandStartPos() {
        super("startpos");
        this.addAlias("s");
        this.addAlias("sp");
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
     */
    @Override
    public void execute(Player player, String[] args) {
        TracerUser tu;

        if (args.length < 1) {
            player.sendMessage(this.getCommandString(0));
        } else if (args[0].equals("tnt") || args[0].equals("t")) {
            tu = TracerUser.getUser(player.getUniqueId());

            tu.toggleStartPosTNT();
            player.sendMessage("TNT start positions are now: " + tu.isStartPosTNT());
        } else if (args[0].equals("sand") || args[0].equals("s")) {
            tu = TracerUser.getUser(player.getUniqueId());

            tu.toggleStartPosSand();
            player.sendMessage("Sand start positions are now: " + tu.isStartPosSand());
        }
    }
}