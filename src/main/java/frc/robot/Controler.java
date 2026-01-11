// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.button.CommandPS5Controller;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

/** Add your docs here. */
public final class Controler {
    public CommandPS5Controller swerveController = new CommandPS5Controller(0);
    public CommandPS5Controller subController = new CommandPS5Controller(1);
    public CommandXboxController testController = new CommandXboxController(0);
}
