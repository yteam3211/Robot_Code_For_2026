// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.BasicCommands;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.drive.Drive;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class moveToRotation extends Command {
  /** Creates a new moveToRotation. */
  private PIDController rotController = new PIDController(10, 0, 0.2);
  private Rotation2d RotTarget;
  public moveToRotation() {
    addRequirements(Drive.getInsatnce());
    rotController.enableContinuousInput(-180, 180);
    withName("moveToRot"+ Logger.getTimestamp());
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Drive.getInsatnce().stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Drive.getInsatnce().getRotation().getDegrees() - RotTarget.getDegrees() < 5;
  }
  public static moveToRotation Move(){
    return new moveToRotation();
  } 
}
