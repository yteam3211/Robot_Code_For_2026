// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Button;

import java.util.function.BooleanSupplier;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.lib.util.FieldConstants;
import frc.robot.Constants;
import frc.robot.Robotstate;
import frc.robot.commands.BasicCommands.IndexerKickerCommand;
import frc.robot.subsystems.Shooter.Shooter;
import frc.robot.subsystems.Shooter.ShooterState;
import frc.robot.subsystems.drive.Drive;

/** Add your docs here. */
public class AutoCommands {
    public static void loadCommands(){
        kickerIndexerAuto();
    }
    private static boolean isAtOriantetion(){
        double x = FieldConstants.Hub.innerCenterPoint.getX() - Drive.getInsatnce().getPose().transformBy(Constants.OFF_SET_SHOOTER).getX();
        double y = FieldConstants.Hub.innerCenterPoint.getY() - Drive.getInsatnce().getPose().transformBy(Constants.OFF_SET_SHOOTER).getY();
        Rotation2d RotTarget = Rotation2d.fromRadians(Math.atan2(y,x)).plus(Rotation2d.k180deg);
        boolean isAtOriantetion = Math.abs(RotTarget.getDegrees() - Drive.getInsatnce().getRotation().getDegrees())<4;
        Logger.recordOutput("is At Oriantetion", isAtOriantetion);
        return isAtOriantetion;
    }
    private static void kickerIndexerAuto() {
        Trigger isAtSpeed = new Trigger(Shooter.getInstance()::isAtVel);
        isAtSpeed.and(new BooleanSupplier() {
            @Override
            public boolean getAsBoolean() {
                return Robotstate.shooterState == ShooterState.shoot && isAtOriantetion() || Robotstate.shooterState == ShooterState.shootAtPlace;
            }
        }).onTrue(new IndexerKickerCommand());
        // isAtSpeed.onTrue(new IndexerKickerCommand());
    }
}
