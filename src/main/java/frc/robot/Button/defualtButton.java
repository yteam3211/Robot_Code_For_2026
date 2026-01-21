// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Button;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandPS5Controller;
import frc.lib.util.SetSubsystemTargetCommand;
import frc.robot.Controler;
import frc.robot.Robotsubsystems;
import frc.robot.SubsystemState;
import frc.robot.commands.DriveCommands;
import frc.robot.subsystems.drive.Drive;

/** Add your docs here. */
public class defualtButton {
    public static void loadButton(Robotsubsystems subsystems, Controler controller) {
        swerveDefualt(subsystems.drive, controller.swerveController);
        IntakePitchDefualt(subsystems, controller);
    }

    private static void swerveDefualt(Drive drive, CommandPS5Controller controller) {
        drive.setDefaultCommand(DriveCommands.joystickDrive(
                drive, () -> -controller.getLeftY(), () -> -controller.getLeftX(), () -> -controller.getRightX()));
        controller.touchpad().onTrue(
            Commands.runOnce(
                    () ->
                        drive.setPose(
                            new Pose2d(drive.getPose().getTranslation(), Rotation2d.kZero)),
                    drive)
                .ignoringDisable(true));
    }
    private static void IntakePitchDefualt(Robotsubsystems subsystems, Controler controller) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                subsystems.intakePitch.goToPos(SubsystemState.intakePitchState.getTarget());

            }
        };
        Command defuatCommand = new SetSubsystemTargetCommand(subsystems.intakePitch, runnable);
        subsystems.intakePitch.setDefaultCommand(defuatCommand);
    }
}
