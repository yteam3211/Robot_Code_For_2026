// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.BasicCommands;

import static edu.wpi.first.units.Units.Meters;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.lib.util.AllianceFlipUtil;
import frc.lib.util.FieldConstants;
import frc.robot.subsystems.drive.Drive;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class moveToRotation extends Command {
  /** Creates a new moveToRotation. */
  private static PIDController rotController = new PIDController(10, 0.01, 0.2);
  private Rotation2d RotTarget;
  private final static Translation2d hubPose = AllianceFlipUtil.apply(new Translation2d(Meters.of(4.59), Meters.of(4.035)));

  public moveToRotation() {
    addRequirements(Drive.getInsatnce());
    rotController.enableContinuousInput(-Math.PI, Math.PI);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    double x = hubPose.getX() - Drive.getInsatnce().getPose().getX();
    double y = hubPose.getY() - Drive.getInsatnce().getPose().getY();
    RotTarget = Rotation2d.fromRadians(Math.atan2(y,x)).plus(Rotation2d.k180deg);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
        double rotOut = rotController.calculate(Drive.getInsatnce().getRotation().getRadians(), RotTarget.getRadians());
              ChassisSpeeds speeds = new ChassisSpeeds(
              0,
              0,
              rotOut);
      boolean isFlipped = DriverStation.getAlliance().isPresent()
              && DriverStation.getAlliance().get() == Alliance.Red;
      Drive.getInsatnce().runVelocity(ChassisSpeeds.fromFieldRelativeSpeeds(
              speeds,
              isFlipped
                      ? Drive.getInsatnce().getRotation().plus(new Rotation2d(Math.PI))
                      : Drive.getInsatnce().getRotation()));
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
