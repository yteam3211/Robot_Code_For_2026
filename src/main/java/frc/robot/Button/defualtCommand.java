// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Button;

import static edu.wpi.first.units.Units.Degree;
import static edu.wpi.first.units.Units.RPM;
import static edu.wpi.first.units.Units.RotationsPerSecond;
import static edu.wpi.first.units.Units.Volts;

import org.littletonrobotics.junction.networktables.LoggedNetworkNumber;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.lib.util.SetSubsystemTargetCommand;
import frc.robot.Controller;
import frc.robot.Robotstate;
import frc.robot.commands.BasicCommands.DriveCommands;
import frc.robot.subsystems.Indexer.Indexer;
import frc.robot.subsystems.IntakePitch.IntakePitch;
import frc.robot.subsystems.IntakePitch.IntakePitchConstants;
import frc.robot.subsystems.IntakeRoller.IntakeRoller;
import frc.robot.subsystems.Shooter.Shooter;
import frc.robot.subsystems.drive.Drive;
import frc.robot.subsystems.kicker.Kicker;

/** Add your docs here. */
public class defualtCommand {
    public static void loadButton( ) {
        swerveDefualt();
        IntakePitchDefualt();
        IntakeRollerDefualt();
        ShooterDefualt();
        KickerDefualt();
        IndexerDefualt();
        }
        private static void IndexerDefualt() {
            Runnable indexerRunnable = new Runnable() {
                @Override
                public void run() {
                    Indexer.getInstance().setVelocity(Robotstate.indexerState.getTarget());
                }
            };
            Command indexerCommand = new SetSubsystemTargetCommand(Indexer.getInstance(), indexerRunnable);
            Indexer.getInstance().setDefaultCommand(indexerCommand);
        }
        private static void IntakeRollerDefualt( ) {
            Runnable RollerRunnable = new Runnable() {
                @Override
                public void run() {
                    IntakeRoller.getInstance().SetVoltage(Robotstate.rollerState.getTarget());//.times(Math.hypot(Drive.getInsatnce().getChassisSpeeds().vxMetersPerSecond, Drive.getInsatnce().getChassisSpeeds().vyMetersPerSecond)/Drive.getInsatnce().getMaxLinearSpeedMetersPerSec())
                }
            };
            Command RollerCommand = new SetSubsystemTargetCommand(IntakeRoller.getInstance(), RollerRunnable);
            IntakeRoller.getInstance().setDefaultCommand(RollerCommand);
        }
        private static void KickerDefualt( ) {
            Runnable kickerRunnable = new Runnable() {
            @Override
            public void run() {
                Kicker.getInstance().setVelocity(Robotstate.kickerState.getTarget());
            }
            
        };
        Command kickerCommand = new SetSubsystemTargetCommand(Kicker.getInstance(), kickerRunnable);
        Kicker.getInstance().setDefaultCommand(kickerCommand);
    }
    private static void ShooterDefualt( ) {
        LoggedNetworkNumber RPm = new LoggedNetworkNumber("/Tuning/RPM", 0);
        Runnable shooterRunnable = new Runnable() {
            @Override
            public void run() {
                switch (Robotstate.shooterState) {
                    case shoot:
                        Shooter.getInstance().setVelocity(RPM.of(RPm.get()));
                    break;
                    case stop:
                        if (Shooter.getInstance().getVelocity().in(RPM)< 500) {
                            Shooter.getInstance().setVoltage(Volts.of(0));                            
                        }else{
                            Shooter.getInstance().setVoltage(Volts.of(0.4));
                        }
                    break;
                    case shootAtPlace:  
                         Shooter.getInstance().setVelocity(RPM.of(2450));
                    break;
                    case pass:
                        Shooter.getInstance().setVelocity(Shooter.getInstance().CalcRPMToShootAtHub());
                    break;
                
                    default:
                         Shooter.getInstance().setVelocity(RotationsPerSecond.of(0));
                    break;
                }
            }
            
        };
        Command shooterComm = new SetSubsystemTargetCommand( Shooter.getInstance(), shooterRunnable);
         Shooter.getInstance().setDefaultCommand(shooterComm);
    }
    private static void swerveDefualt( ) {
        Drive.getInsatnce().setDefaultCommand(
            DriveCommands.joystickDrive(Drive.getInsatnce(), ()->-Controller.getSwerve().getLeftY(), 
            ()-> -Controller.getSwerve().getLeftX(), ()-> -Controller.getSwerve().getRightX())
        );
        Controller.getSwerve().touchpad().onTrue(
            Commands.runOnce(
                    () ->
                        Drive.getInsatnce().setPose(
                            new Pose2d(Drive.getInsatnce().getPose().getTranslation(), Rotation2d.kZero)),
                    Drive.getInsatnce()));
    }
    private static void IntakePitchDefualt( ) {
        Runnable runnable = new Runnable() {
            int degree = 140;
            @Override
            public void run() {
                switch (Robotstate.intakePitchState) {
                    case Open:
                        IntakePitch.getInstance().goToAnlge(IntakePitchConstants.maxAngleDegree);
                        break;
                    case shoot:
                        IntakePitch.getInstance().goToAnlge(Degree.of(degree));
                        if (degree -5< IntakePitch.getInstance().getAngle().in(Degree)) {
                            degree = degree - 10;
                        }
                        if (degree < 85) {
                            degree = 140;
                        }
                        break;
                    case colse:
                        IntakePitch.getInstance().goToAnlge(IntakePitchConstants.minAngleDegree);
                    default:
                        break;
                }
            }
        };
        Command defuatCommand = new SetSubsystemTargetCommand(IntakePitch.getInstance(), runnable);
        IntakePitch.getInstance().setDefaultCommand(defuatCommand);
    }
}
