// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Button;

import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import frc.robot.Controller;
import frc.robot.commands.BasicCommands.DriveCommands;
import frc.robot.commands.BasicCommands.ShootCommands;
import frc.robot.subsystems.Indexer.Indexer;
import frc.robot.subsystems.Indexer.IndexerState;
import frc.robot.subsystems.IntakePitch.IntakePitch;
import frc.robot.subsystems.IntakePitch.IntakePitchState;
import frc.robot.subsystems.IntakeRoller.IntakeRoller;
import frc.robot.subsystems.IntakeRoller.IntakeRollerState;
import frc.robot.subsystems.Shooter.Shooter;
import frc.robot.subsystems.Shooter.ShooterState;
import frc.robot.subsystems.kicker.Kicker;
import frc.robot.subsystems.kicker.KickerState;

/** Add your docs here. */
public class subsystemOp {
    public static void loadButoons(){
        /** מסירה */
        Controller.getSub().L1().whileTrue(DriveCommands.GoToRotationHub().alongWith(Shooter.getInstance().setStateCommand(ShooterState.pass)));
        Controller.getSub().L1().onFalse(Shooter.getInstance().setStateCommand(ShooterState.stop));
        /** איסוף  */
        Controller.getSub().L2().onTrue(
            new ConditionalCommand(IntakePitch.getInstance().setStateCommand(IntakePitchState.Open), 
            IntakePitch.getInstance().setStateCommand(IntakePitchState.colse), 
            ()->isActive()));
        /** ירי עם נסיעה למיקום */
        Controller.getSub().R1().whileTrue(ShootCommands.ShotAndMoveCommand());
        Controller.getSub().R1().whileFalse(ShootCommands.StopShootCommand());
        /** ירי טיפש*/
        Controller.getSub().R2().whileTrue(Shooter.getInstance().setStateCommand(ShooterState.shoot)
            .alongWith(IntakePitch.getInstance().setStateCommand(IntakePitchState.shoot))
                .alongWith(Kicker.getInstance().setStateCommand(KickerState.moevFuelBack)));
        Controller.getSub().R2().onFalse(Shooter.getInstance().setStateCommand(ShooterState.stop));
        /** הוצאת כדורים תקועים*/
        Controller.getSub().circle().whileTrue(Indexer.getInstance().setStateCommand(IndexerState.Back).alongWith(Kicker.getInstance().setStateCommand(KickerState.moevFuelBack)));
        Controller.getSub().circle().onFalse(Indexer.getInstance().setStateCommand(IndexerState.stop).alongWith(Kicker.getInstance().setStateCommand(KickerState.stop)));
        /** */
    }
    private static boolean isActive = true;
    private static boolean isActive(){
        isActive = !isActive;
        return isActive;
    }
}
