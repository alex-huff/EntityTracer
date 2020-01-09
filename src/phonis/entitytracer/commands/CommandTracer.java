package phonis.entitytracer.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import phonis.entitytracer.EntityTracer;

import java.util.List;

/**
 * Base command for EntityTracer
 */
public class CommandTracer extends EntityTracerCommand {
    /**
     * Default CommandTracer constructor calls EntityTracerCommand super constructor
     */
    public CommandTracer(EntityTracer entityTracer, String name) {
        super(name);
        this.addSubCommand(new CommandToggle());
        this.addSubCommand(new CommandClear());
        this.addSubCommand(new CommandTraceTime());
        this.addSubCommand(new CommandSettings());
        this.addSubCommand(new CommandBounds());
        EntityTracerCommand.registerCommand(entityTracer, this);
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
        if (args.length == 0) {
            player.sendMessage(this.getCommandString(0));
        } else {
            throw new CommandException("Incorrect usage of command " + this.getName());
        }
    }
}
