// Copyright 2021-2024 FRC 6328
// http://github.com/Mechanical-Advantage
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// version 3 as published by the Free Software Foundation or
// available in the root directory of this project.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.

package frc.robot;

import org.littletonrobotics.junction.networktables.LoggedDashboardChooser;

import com.pathplanner.lib.auto.AutoBuilder;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;
import frc.robot.Button.AutoCommands;
import frc.robot.Button.defualtCommand;
import frc.robot.Button.devButoon;
import frc.robot.Button.subsystemOp;
import frc.robot.Button.swerveOp;
import frc.robot.commands.BasicCommands.DriveCommands;
import frc.robot.subsystems.LEDSubsystem;
import frc.robot.subsystems.Indexer.Indexer;
import frc.robot.subsystems.IntakePitch.IntakePitch;
import frc.robot.subsystems.IntakeRoller.IntakeRoller;
import frc.robot.subsystems.Shooter.Shooter;
import frc.robot.subsystems.drive.Drive;
import frc.robot.subsystems.limeLight.limeLightTemp;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a "declarative" paradigm, very
 * little robot logic should actually be handled in the {@link Robot} periodic methods (other than the scheduler calls).
 * Instead, the structure of the robot (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
    // Dashboard inputs
    private final LoggedDashboardChooser<Command> autoChooser;

    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer() {
        getInstnce();
        new LEDSubsystem();
        // Set up auto routines
        CommandForAuto.loadCommand();
        autoChooser = new LoggedDashboardChooser<>("Auto Choices", AutoBuilder.buildAutoChooser());

        // Set up SysId routines
        autoChooser.addOption(
                "Drive Wheel Radius Characterization", DriveCommands.wheelRadiusCharacterization(Drive.getInsatnce()));
        autoChooser.addOption(
                "Drive Simple FF Characterization", DriveCommands.feedforwardCharacterization(Drive.getInsatnce()));
        autoChooser.addOption(
                "Drive SysId (Quasistatic Forward)",
                Drive.getInsatnce().sysIdQuasistatic(SysIdRoutine.Direction.kForward));
        autoChooser.addOption(
                "Drive SysId (Quasistatic Reverse)",
                Drive.getInsatnce().sysIdQuasistatic(SysIdRoutine.Direction.kReverse));
        autoChooser.addOption(
                "Drive SysId (Dynamic Forward)", Drive.getInsatnce().sysIdDynamic(SysIdRoutine.Direction.kForward));
        autoChooser.addOption(
                "Drive SysId (Dynamic Reverse)", Drive.getInsatnce().sysIdDynamic(SysIdRoutine.Direction.kReverse));
        autoChooser.addDefaultOption("Auto1", CommandForAuto.Auto1());

        configureButtonBindings();
    }
    private void configureButtonBindings() {
        // devButoon.loadButton();
        AutoCommands.loadCommands();
        defualtCommand.loadButton();
        subsystemOp.loadButoons();
        swerveOp.loadButton();
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        return autoChooser.get();
    }

    private static void getInstnce(){
        Drive.getInsatnce();
        Shooter.getInstance();
        IntakePitch.getInstance();
        IntakeRoller.getInstance();
        Indexer.getInstance();
        new limeLightTemp("limelight-gg");
    }
    private static double FuelAmount = 0;
    public static boolean haveFuel(){
        return FuelAmount != 0;
    }
    public static boolean getFuel(){
        if (haveFuel()) {
            FuelAmount--;
            return true;
        }
        return false;
        
    }
    public static void addFuelToSim(){
        FuelAmount++;
    }
}
