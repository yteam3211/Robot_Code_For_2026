// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Button;

import org.littletonrobotics.junction.networktables.LoggedNetworkNumber;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.DeferredCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;
import frc.lib.util.DriveToPointFactory;
import frc.lib.util.FieldConstants;
import frc.lib.util.FuelSim;
import frc.robot.Controler;
import frc.robot.Robot;
import frc.robot.commands.BasicCommands.DriveCommands;
import frc.robot.commands.BasicCommands.moveToRotation;
import frc.robot.subsystems.Indexer.Indexer;
import frc.robot.subsystems.Indexer.IndexerState;
import frc.robot.subsystems.IntakePitch.IntakePitch;
import frc.robot.subsystems.IntakePitch.IntakePitchState;
import frc.robot.subsystems.IntakeRoller.IntakeRoller;
import frc.robot.subsystems.IntakeRoller.IntakeRollerState;
import frc.robot.subsystems.Shooter.Shooter;
import frc.robot.subsystems.Shooter.ShooterState;
import frc.robot.subsystems.drive.Drive;
import frc.robot.subsystems.kicker.Kicker;
import frc.robot.subsystems.kicker.KickerState;

/** Add your docs here. */
public class devButoon {
    public static void loadButton(Controler controller) {
        // IntakePitchButton(controller);
        // shooterButton(controller);
        sysidAll(controller);

        if (Robot.isSimulation()) {
            spwanFuel(controller);
        }
    }
    private static Rotation2d findAngle(){
        double x = Shooter.hubPose.getX() - Drive.getInsatnce().getPose().getX();
        double y = Shooter.hubPose.getY() - Drive.getInsatnce().getPose().getY();
        return Rotation2d.fromRadians(Math.atan2(y,x)).plus(Rotation2d.k180deg);
    }

    private static void sysidAll(Controler controller) {
        controller.swerveController.triangle().whileTrue(Shooter.getInstance().setStateCommand(ShooterState.shoot));
        controller.swerveController.triangle().whileTrue(Shooter.getInstance().setStateCommand(ShooterState.shoot));
        controller.swerveController.triangle().whileFalse(Shooter.getInstance().setStateCommand(ShooterState.stop));
        controller.swerveController.square().whileTrue(Kicker.getInstance().setStateCommand(KickerState.moveFuelToShooter).alongWith(Indexer.getInstance().setStaetCommand(IndexerState.Index)));
        controller.swerveController.square().whileFalse(Kicker.getInstance().setStateCommand(KickerState.stop).alongWith(Indexer.getInstance().setStaetCommand(IndexerState.Stop)));
        controller.swerveController.cross().whileTrue(DriveCommands.joystickDriveAtAngle(Drive.getInsatnce(), ()-> 0, ()-> 0, devButoon::findAngle));
        
        // controller.swerveController.square().onTrue(Shooter.getInstance().setVelocityCommand(3000));

        // controller.swerveController.triangle().onTrue(Shooter.getInstance().setVelocityCommand(4000));
        // controller.swerveController.circle().onTrue(Shooter.getInstance().setVelocityCommand(3000));
        // controller.swerveController.cross().onTrue(Shooter.getInstance().setVelocityCommand(0));
        // controller.swerveController.square().onTrue(Shooter.getInstance().setVelocityCommand(2000));

        // controller.swerveController.triangle().onTrue(Shooter.getInstance().sysidQuasistatic(Direction.kForward));
        // controller.swerveController.square().onTrue(Shooter.getInstance().sysidQuasistatic(Direction.kReverse));
        // controller.swerveController.circle().onTrue(Shooter.getInstance().sysidDynamic(Direction.kForward));
        // controller.swerveController.cross().onTrue(Shooter.getInstance().sysidDynamic(Direction.kReverse));
    }

    private static void shooterButton( Controler controller) {

    }
    private static void spwanFuel(Controler controller) {
        FuelSim.getInstance();
    }
    private static void IntakePitchButton(Controler controller) {
        controller.swerveController.triangle().onTrue(IntakePitch.getInstance().setStateCommand(IntakePitchState.Open)
        .alongWith(IntakeRoller.getInstance().setStateCommand(IntakeRollerState.move)));
        controller.swerveController.triangle().onFalse(IntakePitch.getInstance().setStateCommand(IntakePitchState.colse)
        .alongWith(IntakeRoller.getInstance().setStateCommand(IntakeRollerState.stop)));
    }
    public static double calculateBallLinearSpeed() {
        double g = 9.8;
        double x = Drive.getInsatnce().getPose().getTranslation().getDistance(FieldConstants.Hub.innerCenterPoint.toTranslation2d());
        double upValue = g * (x * x);
        double divide1 = 2 * Math.pow(Math.cos(Units.degreesToRadians(45)), 2);
        double targetHeight = 1.8288; 
        double startHeight = 0.6096;
        double divide2 = (targetHeight - startHeight) - (Math.tan(Units.degreesToRadians(45)) * x);
        double divide = divide1 * divide2;
        double velocitySquared = upValue / divide;
        velocitySquared = -velocitySquared;   
        if (velocitySquared < 0) {
        return 0.0; 
        }
        double idealVelocity = Math.sqrt(velocitySquared);
        idealVelocity = idealVelocity * 1.15;
        return idealVelocity;
  }
}
