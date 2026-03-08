// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.pathplanner.lib.auto.NamedCommands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.BasicCommands.DriveCommands;
import frc.robot.subsystems.Shooter.Shooter;
import frc.robot.subsystems.Shooter.ShooterState;

/** Add your docs here. */
public class CommandForAuto {
    public static void loadCommand(){
        NamedCommands.registerCommand("shoot full", ShootFull());
    }
    private static Command ShootFull(){
        Command com = Shooter.getInstance().setStateCommand(ShooterState.shoot).alongWith(DriveCommands.GoToRotationHub());
        com = com.until(()->!Shooter.getInstance().haveFuel());
        com = com.andThen(Shooter.getInstance().setStateCommand(ShooterState.stop));
        return com;
    }
}
