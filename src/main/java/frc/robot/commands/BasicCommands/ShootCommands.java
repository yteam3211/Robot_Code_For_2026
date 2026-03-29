// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.BasicCommands;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.DeferredCommand;
import edu.wpi.first.wpilibj2.command.SelectCommand;
import frc.lib.util.DriveToPointFactory;
import frc.robot.Constants;
import frc.robot.subsystems.IntakePitch.IntakePitch;
import frc.robot.subsystems.IntakePitch.IntakePitchState;
import frc.robot.subsystems.Shooter.Shooter;
import frc.robot.subsystems.Shooter.ShooterState;
import frc.robot.subsystems.drive.Drive;
import frc.robot.subsystems.kicker.Kicker;
import frc.robot.subsystems.kicker.KickerState;

/** Add your docs here. */
public class ShootCommands {
    private static Map<Pose2d,Command> CommandMap = 
    Map.of(
        Constants.LeftShot, DriveToPointFactory.driveToPose(Constants.LeftShot),
        Constants.rightShot, DriveToPointFactory.driveToPose(Constants.rightShot));
    public static Command ShotAndMoveCommand(){
        return moveCommand().andThen(shotCommand());
    }
    public static Command shotCommand(){
        return DriveCommands.GoToRotationHub()
            .alongWith(Shooter.getInstance().setStateCommand(ShooterState.shoot)
                .alongWith(IntakePitch.getInstance().setStateCommand(IntakePitchState.shoot))
                    .alongWith(Kicker.getInstance().setStateCommand(KickerState.moevFuelBack)));
    }
    public static Command StopShootCommand(){
        return Shooter.getInstance().setStateCommand(ShooterState.stop)
            .alongWith(IntakePitch.getInstance().setStateCommand(IntakePitchState.Open));
    }
    public static Command moveCommand(){
        return new DeferredCommand(()-> DriveToPointFactory.driveToPose(Drive.getInsatnce().getPose().nearest(List.of(Constants.LeftShot,Constants.rightShot))), Set.of());
    }
}
