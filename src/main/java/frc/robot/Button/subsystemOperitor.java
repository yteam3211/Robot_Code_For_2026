// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Button;

import java.util.Set;

import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.DeferredCommand;
import frc.robot.Controller;
import frc.robot.commands.BasicCommands.DriveCommands;
import frc.robot.subsystems.IntakePitch.IntakePitch;
import frc.robot.subsystems.IntakePitch.IntakePitchState;
import frc.robot.subsystems.IntakeRoller.IntakeRoller;
import frc.robot.subsystems.IntakeRoller.IntakeRollerState;
import frc.robot.subsystems.Shooter.Shooter;
import frc.robot.subsystems.Shooter.ShooterState;
import frc.robot.subsystems.drive.Drive;

/** Add your docs here. */
public class subsystemOperitor {
    public static void loadButoons(){
        /** מסירה */
        Controller.getSub().L1().whileTrue(DriveCommands.GoToRotationHub().alongWith(Shooter.getInstance().setStateCommand(ShooterState.pass)));
        Controller.getSub().L1().onFalse(Shooter.getInstance().setStateCommand(ShooterState.stop));
        /** איסוף  */
        Controller.getSub().L2().onTrue(
            new ConditionalCommand(IntakePitch.getInstance().setStateCommand(IntakePitchState.Open).alongWith(IntakeRoller.getInstance().setStateCommand(IntakeRollerState.move)), 
            IntakePitch.getInstance().setStateCommand(IntakePitchState.colse)
            .alongWith(IntakeRoller.getInstance().setStateCommand(IntakeRollerState.stop)), 
            ()->isActive()));
        /** ירי עם סיבוב ומרחק*/
        Controller.getSub().R1().whileTrue(DriveCommands.GoToRotationHub().alongWith(Shooter.getInstance().setStateCommand(ShooterState.shoot)));
        Controller.getSub().R1().onFalse(Shooter.getInstance().setStateCommand(ShooterState.stop));
        /** ירי מימקום ספציפי*/

        Controller.getSub().R2().whileTrue(Shooter.getInstance().setStateCommand(ShooterState.shootAtPlace));
        Controller.getSub().L1().onFalse(Shooter.getInstance().setStateCommand(ShooterState.stop));
        /** ירי עם מרחק בלי סיבוב*/
        Controller.getSub().circle().whileTrue(Shooter.getInstance().setStateCommand(ShooterState.shoot));
        Controller.getSub().circle().onFalse(Shooter.getInstance().setStateCommand(ShooterState.stop));
        /** */
    }
    private static boolean isActive = false;
    private static boolean isActive(){
        isActive = !isActive;
        return isActive;
    }
}
