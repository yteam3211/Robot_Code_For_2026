// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Button;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.button.CommandPS5Controller;
import frc.robot.Controler;
import frc.robot.Robotsubsystems;
import frc.robot.commands.DriveCommands;
import frc.robot.subsystems.drive.Drive;

/** Add your docs here. */
public class defualtButton {
    public static void loadButton(Robotsubsystems subsystems, Controler controller) {
        swerveDefualt(subsystems.drive, controller.swerveController);
        controller
                .swerveController
                .triangle()
                .whileTrue(subsystems.driveToPointFactory.driveToPose(new Pose2d(2, 2, new Rotation2d(Math.PI))));
    }

    private static void swerveDefualt(Drive drive, CommandPS5Controller controller) {
        drive.setDefaultCommand(DriveCommands.joystickDrive(
                drive, () -> -controller.getLeftY(), () -> -controller.getLeftX(), () -> -controller.getRightX()));
    }
}
