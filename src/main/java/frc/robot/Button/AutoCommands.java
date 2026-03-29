// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Button;

import static edu.wpi.first.units.Units.Degree;

import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.BasicCommands.IndexerKickerCommand;
import frc.robot.subsystems.IntakePitch.IntakePitch;
import frc.robot.subsystems.IntakePitch.IntakePitchConstants;
import frc.robot.subsystems.IntakeRoller.IntakeRoller;
import frc.robot.subsystems.IntakeRoller.IntakeRollerState;
import frc.robot.subsystems.Shooter.Shooter;

/** Add your docs here. */
public class AutoCommands {
    public static void loadCommands(){
        kickerIndexerAuto();
        IntakePitchAuto();
            }
    private static void IntakePitchAuto() {
        Trigger isAtClosed = new Trigger(()->IntakePitch.getInstance().getAngle().isNear(IntakePitchConstants.minAngleDegree, Degree.of(10)));
        isAtClosed.onTrue(IntakeRoller.getInstance().setStateCommand(IntakeRollerState.stop));
        isAtClosed.whileFalse(IntakeRoller.getInstance().setStateCommand(IntakeRollerState.move));
    }
    private static void kickerIndexerAuto() {
        Trigger isAtSpeed = new Trigger(Shooter.getInstance()::isAtVel);
        isAtSpeed.onTrue(new IndexerKickerCommand());
    }
}
