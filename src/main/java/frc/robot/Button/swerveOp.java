// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Button;

import java.util.Set;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.wpilibj2.command.DeferredCommand;
import frc.lib.util.AllianceFlipUtil;
import frc.lib.util.DriveToPointFactory;
import frc.robot.Controller;
import frc.robot.commands.BasicCommands.DriveCommands;
import frc.robot.subsystems.drive.Drive;

/** Add your docs here. */
public class swerveOp {
    public static void loadButton(){
        Controller.getSwerve().R2().whileTrue(new DeferredCommand(()->DriveToPointFactory.driveToPosesimple(Drive.getInsatnce().getPose().transformBy(new Transform2d(1 ,1,Rotation2d.fromDegrees(90)))),Set.of(Drive.getInsatnce())));
        Controller.getSwerve().L2().whileTrue(new DeferredCommand(()->DriveToPointFactory.driveToPosesimple(Drive.getInsatnce().getPose().transformBy(new Transform2d(-1,1,Rotation2d.fromDegrees(-90)))),Set.of(Drive.getInsatnce())));
        Controller.getSwerve().R1().whileTrue(DriveCommands.joystickDriveAtAngle(Drive.getInsatnce(), ()-> -Controller.getSwerve().getLeftY(), 
            ()-> -Controller.getSwerve().getLeftX(),()-> Rotation2d.fromDegrees(45)));
        Controller.getSwerve().circle().whileTrue(
            DriveToPointFactory.driveToPosesimple(AllianceFlipUtil.apply(new Pose2d(10.38,0.422,Rotation2d.fromDegrees(180))))
                .andThen(DriveToPointFactory.driveToPose( AllianceFlipUtil.apply(new Pose2d(6.2,0.422,Rotation2d.fromDegrees(180))))));
        Controller.getSwerve().square().whileTrue(DriveToPointFactory.driveToPosesimple( AllianceFlipUtil.apply(new Pose2d())));
    }
}
