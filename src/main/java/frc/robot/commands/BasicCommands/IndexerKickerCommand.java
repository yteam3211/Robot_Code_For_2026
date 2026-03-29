// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.BasicCommands;

import static edu.wpi.first.units.Units.Minute;
import static edu.wpi.first.units.Units.Rotation;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Indexer.Indexer;
import frc.robot.subsystems.Indexer.IndexerState;
import frc.robot.subsystems.Shooter.Shooter;
import frc.robot.subsystems.kicker.Kicker;
import frc.robot.subsystems.kicker.KickerState;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class IndexerKickerCommand extends Command {
  /** Creates a new IndexerKickerCommand. */
  public IndexerKickerCommand() {
    // Use addRequirements() here to declare subsystem dependencies.
    // addRequirements(Kicker.getInstance(),Indexer.getInstance());
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Kicker.getInstance().setState(KickerState.moveFuelToShooter);
    Indexer.getInstance().setState(IndexerState.Index);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Kicker.getInstance().setState(KickerState.stop);
    Indexer.getInstance().setState(IndexerState.stop);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Shooter.getInstance().getVelocity().in(Rotation.per(Minute)) < 200;
  }
}
