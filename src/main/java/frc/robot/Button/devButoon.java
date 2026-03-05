// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Button;

import static edu.wpi.first.units.Units.Degree;
import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.Millimeter;
import static edu.wpi.first.units.Units.RPM;

import java.lang.invoke.ConstantBootstraps;
import java.util.Set;

import org.ironmaple.simulation.SimulatedArena;
import org.ironmaple.simulation.seasonspecific.rebuilt2026.RebuiltFuelOnFly;
import org.littletonrobotics.junction.Logger;
import org.littletonrobotics.junction.networktables.LoggedNetworkNumber;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.DeferredCommand;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;
import frc.lib.util.AllianceFlipUtil;
import frc.lib.util.FieldConstants;
import frc.robot.Constants;
import frc.robot.Controller;
import frc.robot.commands.BasicCommands.DriveCommands;
import frc.robot.subsystems.IntakePitch.IntakePitch;
import frc.robot.subsystems.IntakePitch.IntakePitchState;
import frc.robot.subsystems.IntakeRoller.IntakeRoller;
import frc.robot.subsystems.IntakeRoller.IntakeRollerState;
import frc.robot.subsystems.Shooter.Shooter;
import frc.robot.subsystems.Shooter.ShooterState;
import frc.robot.subsystems.drive.Drive;

/** Add your docs here. */
public class devButoon {
    public static void loadButton() {
        // IntakePitchButton(controller);
        // shooterButton(controller);
        sysidAll();
    }
    public static Rotation2d findAngle(){
        Pose2d shooterPose = Drive.getInsatnce().getPose().transformBy(Constants.OFF_SET_SHOOTER);
        double x = AllianceFlipUtil.applyX(FieldConstants.Hub.innerCenterPoint.getX()) - shooterPose.getX();
        double y = AllianceFlipUtil.applyY(FieldConstants.Hub.innerCenterPoint.getY()) - shooterPose.getY();
        return Rotation2d.fromRadians(Math.atan2(y,x)).plus(Rotation2d.k180deg);
    }

    private static void sysidAll(  ) {
    }
    static LoggedNetworkNumber AngVel = new LoggedNetworkNumber("/Tuning/RPM",0);
    private static void spwanFuel() {
        RebuiltFuelOnFly rebuiltFuelOnFly = (RebuiltFuelOnFly)new RebuiltFuelOnFly(
                    Drive.getSwerveDriveSim().getSimulatedDriveTrainPose().getTranslation(),
                    new Translation2d(Millimeter.of(-162.22), Millimeter.of(-11.44)), // shooter offet from center
                    Drive.getSwerveDriveSim().getDriveTrainSimulatedChassisSpeedsFieldRelative(),
                    Drive.getSwerveDriveSim().getSimulatedDriveTrainPose().getRotation().plus(Rotation2d.k180deg),
                    Millimeter.of(546.3), // initial height of the ball, in meters
                    Shooter.getInstance().ToLinearVelocity(RPM.of(AngVel.get())), // initial velocity, in m/s
                    Degrees.of(62)) // shooter angle
                    .withProjectileTrajectoryDisplayCallBack(
                        (poses) -> Logger.recordOutput("successfulShotsTrajectory", poses.toArray(Pose3d[]::new)),
                        (poses) -> Logger.recordOutput("missedShotsTrajectory", poses.toArray(Pose3d[]::new))); 
            rebuiltFuelOnFly.setHitTargetCallBack(() -> System.out.println("FUEL hits HUB!"));
            SimulatedArena.getInstance()
                .addGamePieceProjectile(rebuiltFuelOnFly);
    }
}
