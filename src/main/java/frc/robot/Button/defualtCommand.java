// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Button;

import org.littletonrobotics.junction.networktables.LoggedNetworkNumber;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.lib.util.SetSubsystemTargetCommand;
import frc.robot.Controler;
import frc.robot.SubsystemState;
import frc.robot.commands.BasicCommands.DriveCommands;
import frc.robot.subsystems.Indexer.Indexer;
import frc.robot.subsystems.IntakePitch.IntakePitch;
import frc.robot.subsystems.IntakeRoller.IntakeRoller;
import frc.robot.subsystems.Shooter.Shooter;
import frc.robot.subsystems.drive.Drive;
import frc.robot.subsystems.kicker.Kicker;

/** Add your docs here. */
public class defualtCommand {
    public static void loadButton(Controler controller) {
        swerveDefualt(controller);
        // IntakePitchDefualt(controller);
        ShooterDefualt(controller);
        IndexerDefualt(controller);
        KickerDefualt(controller);
        // IntakeRollerDefualt(controller);
        }
    private static void IntakeRollerDefualt(Controler controller) {
        Runnable RollerRunnable = new Runnable() {
            @Override
            public void run() {
                IntakeRoller.getInstance().SetVoltage(SubsystemState.rollerState.getTarget());
            }
            
        };
        Command RollerCommand = new SetSubsystemTargetCommand(IntakeRoller.getInstance(), RollerRunnable);
        IntakeRoller.getInstance().setDefaultCommand(RollerCommand);
    }
        private static void KickerDefualt(Controler controller) {
            Runnable kickerRunnable = new Runnable() {
            @Override
            public void run() {
                Kicker.getInstance().setVelocity(SubsystemState.kickerState.getTarget());
            }
            
        };
        Command kickerCommand = new SetSubsystemTargetCommand(Kicker.getInstance(), kickerRunnable);
        Kicker.getInstance().setDefaultCommand(kickerCommand);
    }
    private static void IndexerDefualt(Controler controller) {
        Runnable indexerRunnable = new Runnable() {
            @Override
            public void run() {
                Indexer.getInstance().setVelocity(SubsystemState.indexerState.getTarget());
            }
            
        };
        Command indexerCommand = new SetSubsystemTargetCommand(Indexer.getInstance(), indexerRunnable);
        Indexer.getInstance().setDefaultCommand(indexerCommand);
    }
    private static void ShooterDefualt(Controler controller) {
        LoggedNetworkNumber RPM = new LoggedNetworkNumber("/Tuning/RPM",0);
        Runnable shooterRunnable = new Runnable() {
            @Override
            public void run() {
                switch (SubsystemState.shooterState) {
                    case shoot:
                        Shooter.getInstance().setVelocity(Shooter.CalcRPMToShoot());
                    break;
                    case stop:
                        Shooter.getInstance().setVelocity(0);
                    break;
                    case shootAtPlace:
                         Shooter.getInstance().setVelocity(RPM.get());

                    break;
                
                    default:
                         Shooter.getInstance().setVelocity(0);
                    break;
                }
            }
            
        };
        Command shooterComm = new SetSubsystemTargetCommand( Shooter.getInstance(), shooterRunnable);
         Shooter.getInstance().setDefaultCommand(shooterComm);
    }
    private static void swerveDefualt(Controler controller) {
        Drive.getInsatnce().setDefaultCommand(
            DriveCommands.joystickDrive(Drive.getInsatnce(), ()->-controller.swerveController.getLeftY(), 
            ()-> -controller.swerveController.getLeftX(), ()-> -controller.swerveController.getRightX())
        );
        controller.swerveController.touchpad().onTrue(
            Commands.runOnce(
                    () ->
                        Drive.getInsatnce().setPose(
                            new Pose2d(Drive.getInsatnce().getPose().getTranslation(), Rotation2d.kZero)),
                    Drive.getInsatnce()));
    }
    private static void IntakePitchDefualt(Controler controller) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                IntakePitch.getInstance().goToAnlge(SubsystemState.intakePitchState.getTarget());
            }
        };
        Command defuatCommand = new SetSubsystemTargetCommand(IntakePitch.getInstance(), runnable);
        IntakePitch.getInstance().setDefaultCommand(defuatCommand);
    }
}
