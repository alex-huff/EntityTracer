package phonis.entitytracer.commands;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import phonis.entitytracer.serializable.TracerUser;
import phonis.entitytracer.trace.GraphTrace;
import phonis.entitytracer.trace.Line;
import phonis.entitytracer.trace.Trace;

import java.util.List;

public class CommandGraph extends EntityTracerCommand {
    public CommandGraph() {
        super("graph");
        this.addAlias("g");
    }

    @Override
    public List<String> topTabComplete(String[] args) {
        return null;
    }

    @Override
    public void execute(CommandSender sender, String[] args) throws CommandException {
        throw new CommandException(CommandException.consoleError);
    }

    @Override
    public void execute(Player player, String[] args) throws CommandException {
        if (args.length > 0) {
            StringBuilder express = new StringBuilder();

            for (String arg : args) {
                express.append(arg);
            }

            String expressString = express.toString();
            Location pLoc = player.getLocation();
            int size = 50;
            int offset = size / 2;
            double[][] array = new double[size + 1][size + 1];
            Expression expression = new ExpressionBuilder(expressString).variables("x", "z").build();
            TracerUser user = TracerUser.getUser(player.getUniqueId());

            for (int i = 0; i < array.length; i++) {
                for (int w = 0; w < array[i].length; w++) {
                    array[i][w] = expression.setVariable("x", i - offset).setVariable("z", w - offset).evaluate();
                }
            }

            Trace trace = new GraphTrace(array, pLoc, offset);

            for (Line line : trace.getLines()) {
                user.addLine(line);
            }
        }
    }
}
