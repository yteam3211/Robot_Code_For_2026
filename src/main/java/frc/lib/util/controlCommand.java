// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.lib.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.BooleanSupplier;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.util.function.BooleanConsumer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class ControlCommand extends Command {
  /** Creates a new ControlCommand. */
  private static List<ControlCommand> commandList = new ArrayList<>();
  private Runnable initialize;
  private Runnable execute;
  private BooleanConsumer end;
  private BooleanSupplier Finish;
  private Set<Subsystem> req;
  public ControlCommand(Runnable initilaize, Runnable execute, BooleanConsumer end , BooleanSupplier finish, Set<Subsystem> req, String name) {
    this.initialize = initilaize;
    this.execute = execute;
    this.end = end;
    this.Finish = finish;
    this.req = req;
    super.setName(name);
    commandList.add(this);
  }

  public ControlCommand(Command command){
    this.initialize = command::initialize;
    this.execute = command::execute;
    this.end = command::end;
    this.Finish = command::isFinished;
    this.req = command.getRequirements();
    super.setName(command.getName());
    commandList.add(this);
  }
  public static ControlCommand run(Runnable run, String name, Subsystem... req){
    return new ControlCommand(()->{}, run, (bool)->{}, ()-> false, Set.of(req), name);
  }
  public static ControlCommand runOnce(Runnable run, String name, Subsystem... req){
    return new ControlCommand(()->{}, run, (bool)->{}, ()-> true, Set.of(req), name);
  }
  // public static ControlCommand startEnd(Runnable Start, Runnable end ,String name, Subsystem... req){
  //   return new ControlCommand(Start, ()->{}, , null, null, name)
  //   Commands.startEnd(Start, end, req)
  // }
  public static void logCommands(){
    for(ControlCommand command : commandList){
      if (command.isFinished()) {
        commandList.remove(command);
      }
    }
    Logger.recordOutput("Commands", 
    commandList.stream().map(Command::toString).toArray(String[]::new));
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    initialize.run();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    execute.run();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    end.accept(interrupted);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Finish.getAsBoolean();
  }
  @Override
  public String toString() {
    String commandName = getName();
    commandName = commandName + "\n" + "req:";
    for(var subsystem : req){
      commandName = commandName + "\n";
      commandName = commandName + subsystem.toString();
    }
    return commandName;
  }
}
