package phonis.entitytracer.commands;

import org.bukkit.ChatColor;

/**
 * Exception type for EntityTracer commands
 */
public class CommandException extends Exception {
    public static final String consoleError = "Only players can use this command";
    private static final String prefix = ChatColor.RED + "Tracer command usage error: " + ChatColor.WHITE;

    /**
     * CommandException constructor that calls Exception super constructor with the error message
     *
     * @param error String error message
     */
    public CommandException(String error) {
        super(prefix + error);
    }
}
