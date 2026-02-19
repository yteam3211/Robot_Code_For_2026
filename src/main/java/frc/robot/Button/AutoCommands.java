// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Button;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.lib.util.FieldConstants;
import frc.robot.commands.BasicCommands.IndexerKickerCommand;
import frc.robot.subsystems.Shooter.Shooter;
import frc.robot.subsystems.drive.Drive;

/** Add your docs here. */
public class AutoCommands {
    public static void loadCommands(){
        kickerIndexerAuto();
    }
    private static boolean isAtOriantetion(){
        // double x = FieldConstants.Hub.innerCenterPoint.getX() - Drive.getInsatnce().getPose().getX();
        // double y = FieldConstants.Hub.innerCenterPoint.getY() - Drive.getInsatnce().getPose().getY();
        // return Math.max(Drive.getInsatnce().getRotation().getDegrees(), 
        // Rotation2d.fromRadians(Math.atan2(y,x)).plus(Rotation2d.k180deg).getDegrees()) - 
        // Math.min(Drive.getInsatnce().getRotation().getDegrees(), 
        // Rotation2d.fromRadians(Math.atan2(y, x)).plus(Rotation2d.k180deg).getDegrees()) < 5;
        return true;
    }
    private static void kickerIndexerAuto() {
        Trigger isAtOriantetion = new Trigger(AutoCommands::isAtOriantetion);
        Trigger isAtSpeed = new Trigger(Shooter.getInstance()::isAtVel);
        isAtOriantetion.and(isAtSpeed).onTrue(new IndexerKickerCommand());
    }
}
