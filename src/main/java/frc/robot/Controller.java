// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.button.CommandPS5Controller;

/** Add your docs here. */
public final class Controller {
    public CommandPS5Controller swerveController = new CommandPS5Controller(0);
    public CommandPS5Controller subController = new CommandPS5Controller(1);
    // public CommandXboxController SimController = new CommandXboxController(0);
    private static Controller instance = null;
    public static Controller getInstance(){
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }
    public static CommandPS5Controller getSwerve(){
        return getInstance().swerveController;
    }
    public static CommandPS5Controller getSub(){
        return getInstance().subController;
    }
}
