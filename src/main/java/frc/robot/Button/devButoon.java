// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Button;


import org.ironmaple.simulation.seasonspecific.rebuilt2026.RebuiltFuelOnField;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.lib.util.DONT_TUCH_THIS.Arena_2026_withBump;
import frc.robot.Controler;
import frc.robot.Robotsubsystems;
import frc.robot.subsystems.IntakePitch.IntakePitchState;

/** Add your docs here. */
public class devButoon {
    public static void loadButton(Robotsubsystems subsystems, Controler controller) {
        IntakePitchButton(subsystems,controller);
        spwanFuel(controller);
    }
                    
    private static void spwanFuel(Controler controller) {
        controller.swerveController.triangle().onTrue(Commands.runOnce(()->Arena_2026_withBump.getInstance().addGamePiece(new RebuiltFuelOnField(new Translation2d(8, 3)))));
    }
        
            private static void IntakePitchButton(Robotsubsystems subsystems, Controler controller) {
        controller.swerveController.L2().whileTrue(subsystems.intakePitch.setStateCommand(IntakePitchState.Open));
        controller.swerveController.L2().whileFalse(subsystems.intakePitch.setStateCommand(IntakePitchState.colse));
    }
}
