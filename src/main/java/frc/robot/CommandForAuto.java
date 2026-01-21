// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.pathplanner.lib.auto.NamedCommands;


/** Add your docs here. */
public class CommandForAuto {
    public static boolean loadCommand(Robotsubsystems subsystems){
        try {
            NamedCommands.registerCommand("shoot full", null);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
