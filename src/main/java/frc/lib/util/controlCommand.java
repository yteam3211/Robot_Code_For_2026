package frc.lib.util;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import java.util.List;
import java.util.function.BooleanSupplier;

public class controlCommand {
    private List<Command> commands = List.of();

    public Command run(Runnable run, BooleanSupplier until, SubsystemBase... req) {
        Command tempCommand = Commands.run(run, req);
        return addCommandToList(tempCommand).andThen(tempCommand.until(until));
    }

    public Command run(Runnable run, SubsystemBase... req) {
        Command tempCommand = Commands.runOnce(run, req);
        return addCommandToList(tempCommand).andThen(tempCommand);
    }

    public Command addCommandToList(Command new_Command) {
        return Commands.runOnce(() -> commands.add(new_Command));
    }

    public Command removeFromList(Command old_Command) {
        return Commands.runOnce(() -> commands.remove(old_Command));
    }

    public Command cancel(Command old_Command) {
        return Commands.runOnce(() -> old_Command.cancel()).andThen(removeFromList(old_Command));
    }

    public Command cancelhelprer() {
        return commands.get(commands.size());
    }

    public Command cancel() {
        return cancel(cancelhelprer()).until(() -> commands.size() == 0);
    }
}
