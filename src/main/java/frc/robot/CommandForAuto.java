// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.MetersPerSecond;
import static edu.wpi.first.units.Units.MetersPerSecondPerSecond;
import static edu.wpi.first.units.Units.Radians;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.RadiansPerSecondPerSecond;
import static edu.wpi.first.units.Units.RotationsPerSecond;
import static edu.wpi.first.units.Units.Second;

import java.util.List;

import com.pathplanner.lib.path.PathConstraints;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.units.measure.LinearVelocity;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.lib.util.AllianceFlipUtil;
import frc.lib.util.DriveToPointFactory;
import frc.robot.commands.BasicCommands.ShootCommands;
import frc.robot.subsystems.IntakePitch.IntakePitch;
import frc.robot.subsystems.IntakePitch.IntakePitchState;
import frc.robot.subsystems.drive.Drive;

/** Add your docs here. */
public class CommandForAuto {
    public static void loadCommand(){
        
    }
    public static Command Auto1(){
        return 
            moveToPointBump(poseListAuto1[0])
                .alongWith(IntakePitch.getInstance().setStateCommand(IntakePitchState.Open))
                    .andThen(moveToPoint(poseListAuto1[1]))
                    .andThen(ShootMove())
                    .andThen(moveToPointBump(poseListAuto1[2]))
                    .andThen(moveToPoint(poseListAuto1[3]));
    }
    private static Pose2d[] poseListAuto1 = new Pose2d[]{
        new Pose2d(7.74,1.13,Rotation2d.fromDegrees(90)),
        new Pose2d(7.74,3.57,Rotation2d.fromDegrees(90)),
        new Pose2d(6.14,2.4,Rotation2d.fromDegrees(90)),
        new Pose2d(6.4,5.81,Rotation2d.fromDegrees(15))};
    private static Command moveToPoint(Pose2d pose){
        return DriveToPointFactory.driveToPose(AllianceFlipUtil.apply(pose),
            new PathConstraints(
                MetersPerSecond.of(3), 
                MetersPerSecondPerSecond.of(4), 
                RadiansPerSecond.of(Drive.getInsatnce().getMaxAngularSpeedRadPerSec()), 
                RadiansPerSecondPerSecond.of(Drive.getInsatnce().getMaxAngularSpeedRadPerSec()* 1.3)),MetersPerSecond.of(1));
    } 
    private static Command moveToPointBump(Pose2d pose){
        return DriveToPointFactory.driveToPose(AllianceFlipUtil.apply(pose),
            new PathConstraints(
                MetersPerSecond.of(2), 
                MetersPerSecondPerSecond.of(2.5), 
                RadiansPerSecond.of(Drive.getInsatnce().getMaxAngularSpeedRadPerSec()), 
                RadiansPerSecondPerSecond.of(Drive.getInsatnce().getMaxAngularSpeedRadPerSec()* 1.3)),MetersPerSecond.of(1));
    }
    private static Command ShootMove(){
        return ShootCommands.ShotAndMoveCommand().andThen(new WaitCommand(7)).withTimeout(Second.of(5));
    }
}
