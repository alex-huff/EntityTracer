package phonis.entitytracer.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import phonis.entitytracer.EntityTracer;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class EntityTracerCommand implements CommandExecutor, TabCompleter {
    private final String name;
    private Set<EntityTracerCommand> subCommands = new HashSet<>();
    private Set<String> aliases = new HashSet<>();
    protected Set<String> args = new HashSet<>();

    /**
     * EntityTracerCommand constructor takes in name of command
     *
     * @param name Name
     */
    public EntityTracerCommand(String name) {
        this.name = name;
    }

    /**
     * Removes first element of String[]
     *
     * @param strings String[] array to truncate
     * @return String[]
     */
    private static String[] truncate(String[] strings) {
        String[] ret = new String[strings.length - 1];

        System.arraycopy(strings, 1, ret, 0, strings.length - 1);

        return ret;
    }

    /**
     * Goes through array and converts all Strings to lowercase
     *
     * @param strings String[] to lower
     * @return String[]
     */
    private static String[] arrayToLower(String[] strings) {
        for (int i = 0; i < strings.length; i++) {
            strings[i] = strings[i].toLowerCase();
        }

        return strings;
    }

    /**
     * Registers PluginCommand for the EntityTracerCommand
     *
     * @param entityTracer EntityTracer main class
     */
    public static void registerCommand(EntityTracer entityTracer, EntityTracerCommand etc) {
        PluginCommand pc = entityTracer.getCommand(etc.getName());

        if (pc == null) return;

        pc.setExecutor(etc);
        pc.setTabCompleter(etc);
    }

    /**
     * Parse a double and throw CommandException if failed
     *
     * @param arg Argument
     * @return double
     * @throws CommandException command exceptions
     */
    public static double parseDouble(String arg) throws CommandException {
        try {
            return Double.parseDouble(arg);
        } catch (NumberFormatException e) {
            throw new CommandException("Invalid double: " + arg);
        }
    }

    /**
     * Parse an int and throw CommandException if failed
     *
     * @param arg Argument
     * @return int
     * @throws CommandException command exceptions
     */
    public static int parseInt(String arg) throws CommandException {
        try {
            return Integer.parseInt(arg);
        } catch (NumberFormatException e) {
            throw new CommandException("Invalid int: " + arg);
        }
    }

    /**
     * Tab complete for set args
     *
     * @param args Arguments given
     * @return List<String>
     */
    public List<String> argsAutocomplete(String[] args, int size) {
        List<String> ret = new ArrayList<>();

        if (args.length > size) {
            return ret;
        }

        for (String traceType : this.args) {
            if (traceType.startsWith(args[args.length - 1])) {
                ret.add(traceType);
            }
        }

        return ret;
    }

    /**
     * Tab complete to be overridden by top-level commands
     *
     * @param args Arguments
     * @return List<String>
     */
    public abstract List<String> topTabComplete(String[] args);

    /**
     * Tab complete for sub-command system
     *
     * @param label Last command
     * @param args  Arguments
     * @return List<String>
     */
    public List<String> tabComplete(String label, String[] args) {
        List<String> ret = new ArrayList<>();

        if (args.length > 0) {
            for (EntityTracerCommand subETC : this.subCommands) {
                if (args[0].equalsIgnoreCase(subETC.getName()) || subETC.getAliases().contains(args[0])) {
                    return subETC.tabComplete(args[0], EntityTracerCommand.truncate(args));
                }
            }

            List<String> top = this.topTabComplete(args);

            if (top != null) ret = top;

            if (args.length > 1) return ret;

            for (EntityTracerCommand subETC : this.subCommands) {
                String subName = subETC.getName();

                if (subName.startsWith(args[0])) ret.add(subName);

//                for (String alias : subETC.aliases) {
//                    if (alias.startsWith(args[0])) ret.add(alias);
//                }
            }

            return ret;
        }

        if (this.getName().startsWith(label)) ret.add(this.getName());

//        for (String alias : this.aliases) {
//            if (alias.startsWith(label)) ret.add(alias);
//        }

        return ret;
    }

    /**
     * Abstract execute method for Non-Player senders
     *
     * @param sender Non-Player sender
     * @param args   Arguments
     */
    public abstract void execute(CommandSender sender, String[] args) throws CommandException;

    /**
     * Abstract execute method for Player senders
     *
     * @param player Player
     * @param args   Arguments
     */
    public abstract void execute(Player player, String[] args) throws CommandException;

    /**
     * Execute method that abstracts down to sub-command
     *
     * @param sender CommandSender
     * @param args   Arguments
     * @throws CommandException Command error
     */
    public void executeTree(CommandSender sender, String[] args) throws CommandException {
        if (args.length > 0) {
            for (EntityTracerCommand subETC : this.subCommands) {
                if (args[0].equalsIgnoreCase(subETC.getName()) || subETC.getAliases().contains(args[0])) {
                    subETC.executeTree(sender, truncate(args));

                    return;
                }
            }
        }

        if (sender instanceof Player) {
            execute((Player) sender, args);
        } else {
            execute(sender, args);
        }
    }

    /**
     * Method implemented from the TabCompleter interface
     *
     * @param sender CommandSender object
     * @param cmd    Command object
     * @param alias  String representing alias
     * @param args   String[] containing command arguments
     * @return boolean
     */
    @Override
    public List<String> onTabComplete(@Nonnull CommandSender sender, @Nonnull Command cmd, @Nonnull String alias, @Nonnull String[] args) {
        return this.tabComplete(alias, EntityTracerCommand.arrayToLower(args));
    }

    /**
     * Method implemented from CommandExecutor interface
     *
     * @param sender CommandSender object
     * @param cmd    Command object
     * @param label  String representing label
     * @param args   String[] containing command arguments
     * @return boolean
     */
    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command cmd, @Nonnull String label, @Nonnull String[] args) {
        try {
            this.executeTree(sender, EntityTracerCommand.arrayToLower(args));
        } catch (CommandException e) {
            sender.sendMessage(e.getMessage());
        }

        return true;
    }

    /**
     * Adds a sub-command
     *
     * @param subETC EntityTracerCommand sub-command to add
     */
    public void addSubCommand(EntityTracerCommand subETC) {
        this.subCommands.add(subETC);
    }

    /**
     * Adds and alias
     *
     * @param alias String alias to add
     */
    public void addAlias(String alias) {
        this.aliases.add(alias.toLowerCase());
    }

    /**
     * Gets aliases
     *
     * @return Set<String>
     */
    private Set<String> getAliases() {
        return this.aliases;
    }

    /**
     * Gets name of command
     *
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * Gets string representing commands
     *
     * @param depth recursion depth
     * @return String
     */
    public String getCommandString(int depth) {
        StringBuilder message = new StringBuilder();

        for (int i = 0; i < depth; i++) message.append("   ");

        String name = "" + ChatColor.RESET + ChatColor.AQUA + this.getName() + "\n" + ChatColor.GRAY;
        message.append(name);

        depth += 1;

        for (String arg : this.args) {
            for (int i = 0; i < depth; i++) message.append("   ");
            message.append(arg).append("\n");
        }

        if (!this.subCommands.isEmpty()) {
            for (EntityTracerCommand etc : this.subCommands) {
                message.append(etc.getCommandString(depth));
            }
        }

        return message.toString();
    }
}
