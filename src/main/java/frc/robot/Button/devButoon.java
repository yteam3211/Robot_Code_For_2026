// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Button;

import static edu.wpi.first.units.Units.RPM;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import frc.lib.FuelSimulation.ShotCalculator.LaunchParameters;
import frc.robot.Constants;
import frc.robot.Controller;
import frc.robot.commands.BasicCommands.ShootCommands;
import frc.robot.subsystems.Indexer.Indexer;
import frc.robot.subsystems.Indexer.IndexerState;
import frc.robot.subsystems.IntakePitch.IntakePitch;
import frc.robot.subsystems.IntakePitch.IntakePitchState;
import frc.robot.subsystems.Shooter.Shooter;
import frc.robot.subsystems.kicker.Kicker;
import frc.robot.subsystems.kicker.KickerState;

/** Add your docs here. */
public class devButoon {
    public static void loadButton() {
        // IntakePitchButton(controller);
        // shooterButton(controller);
        sysidAll();
    }
    private static void sysidAll() {
        Controller.getSub().R2().whileTrue(ShootCommands.ShotAndMoveCommand());
        Controller.getSub().R2().onFalse(ShootCommands.StopShootCommand());
        Controller.getSwerve
        ().L2().onTrue(
            new ConditionalCommand(IntakePitch.getInstance().setStateCommand(IntakePitchState.Open), 
            IntakePitch.getInstance().setStateCommand(IntakePitchState.colse), 
            ()->isActive()));
        Controller.getSub().triangle().whileTrue(Indexer.getInstance().setStateCommand(IndexerState.Back).alongWith(Kicker.getInstance().setStateCommand(KickerState.moevFuelBack)));
        Controller.getSub().triangle().onFalse(Indexer.getInstance().setStateCommand(IndexerState.stop).alongWith(Kicker.getInstance().setStateCommand(KickerState.stop)));
        // Controller.getSwerve().triangle().onTrue(Shooter.getInstance().sysidDynamic(Direction.kForward));
        // Controller.getSwerve().square().onTrue(Shooter.getInstance().sysidDynamic(Direction.kReverse));
        // Controller.getSwerve().cross().onTrue(Shooter.getInstance().sysidQuasistatic(Direction.kForward));
        // Controller.getSwerve().circle().onTrue(Shooter.getInstance().sysidQuasistatic(Direction.kReverse));
        // Controller.getSwerve().triangle().onTrue(Shooter.getInstance().setVelocityCommand(RPM.of(3000)));
        // Controller.getSwerve().square().onTrue(Shooter.getInstance().setVelocityCommand(RPM.of(2000)));
        // Controller.getSwerve().cross().onTrue(Shooter.getInstance().setVelocityCommand(RPM.of(0)));
    }
    private static boolean isActive = true;
    private static boolean isActive(){
        isActive = !isActive;
        return isActive;
    }
    private static void shootBall(){
        LaunchParameters shot = Constants.HubParameters();
        double exitSpeed = Constants.ShooterLookUpTables.Hubsim.exitVelocity(shot.rpm());
        double launchRad = Math.toRadians(62);

        double vHorizontal = exitSpeed * Math.cos(launchRad);
        double vVertical = exitSpeed * Math.sin(launchRad);

        Rotation2d azimuth = shot.driveAngle(); // or robot yaw if you're not doing SOTM
        double vx = vHorizontal * azimuth.getCos();
        double vy = vHorizontal * azimuth.getSin();

        Translation3d launchPos = Constants.OFF_SET_SHOOTER.getTranslation();
        Translation3d launchVel = new Translation3d(vx, vy, vVertical);

        Constants.ShooterLookUpTables.fuelPhysicsSim.launchBall(launchPos, launchVel, Shooter.getInstance().getVelocity().in(RPM));
    }
}
