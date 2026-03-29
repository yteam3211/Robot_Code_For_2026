// Copyright 2021-2024 FRC 6328
// http://github.com/Mechanical-Advantage
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// version 3 as published by the Free Software Foundation or
// available in the root directory of this project.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.

package frc.robot;

import static edu.wpi.first.units.Units.Degree;
import static edu.wpi.first.units.Units.Meter;
import static edu.wpi.first.units.Units.Millimeter;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.RobotBase;
import frc.lib.FuelSimulation.FuelPhysicsSim;
import frc.lib.FuelSimulation.ProjectileSimulator;
import frc.lib.FuelSimulation.ShotCalculator;
import frc.lib.FuelSimulation.ShotCalculator.LaunchParameters;
import frc.lib.FuelSimulation.ShotCalculator.ShotInputs;
import frc.lib.util.AllianceFlipUtil;
import frc.lib.util.FieldConstants;
import frc.robot.subsystems.drive.Drive;

/**
 * This class defines the runtime mode used by AdvantageKit. The mode is always "real" when running on a roboRIO. Change
 * the value of "simMode" to switch between "sim" (physics sim) and "replay" (log replay from a file).
 */
public final class Constants {
    public static final Mode simMode = Mode.SIM;
    public static final Mode currentMode = RobotBase.isReal() ? Mode.REAL : simMode;

    public static enum Mode {
        /** Running on a real robot. */
        REAL,

        /** Running a physics simulator. */
        SIM,
        MapleSim,

        /** Replaying from a log file. */
        REPLAY
    }
    public static Pose2d LeftShot = AllianceFlipUtil.apply(new Pose2d(2.293, 5.766, Rotation2d.fromDegrees(143)));
    public static Pose2d rightShot = AllianceFlipUtil.apply(new Pose2d(2.293,FieldConstants.fieldWidth - 5.766, Rotation2d.fromDegrees(-143)));

    public static boolean disableHAL = false;
    public void disableHAL(){
        disableHAL = true;
    }
    public static final Transform3d OFF_SET_SHOOTER = 
    new Transform3d(Millimeter.of(-162.22), Millimeter.of(-9.44), Millimeter.of(546),new Rotation3d(Degree.of(180),Degree.of(0),Degree.of(0)));
    public static final Angle SHOOTER_ANGLE = Degree.of(62);
    public static final Pose3d LIME_LIGHT_3G_POSE = 
        new Pose3d(-0.1808,-0.023073,0.44788,
            new Rotation3d(Degree.of(0),Degree.of(0),Degree.of(90)));
    public final class ShooterLookUpTables {
    public static FuelPhysicsSim fuelPhysicsSim;

    public static ProjectileSimulator.SimParameters Hubparams = new ProjectileSimulator.SimParameters(
        0.215,   // ball mass kg
        0.1501,  // ball diameter m
        0.47,    // drag coeff (smooth sphere)
        0.2,     // Magnus coeff
        1.225,   // air density
        Millimeter.of(546).in(Meter),// exit height (m), floor to where the ball leaves the shooter
        Units.inchesToMeters(3),  // flywheel diameter (m), measure with calipers
        1.83,    // target height (m), from game manual
        0.7,     // slip factor (0=no grip, 1=perfect), tune this on the real robot. 
        //higher slipFactor is less power and lower slipFactor is more power
        62,    // launch angle from horizontal, measure from CAD
        0.001,   // sim timestep
        1500, 6000, 25, 5.0  // RPM search range, iterations, max sim time
    );

    public static ProjectileSimulator.SimParameters PassParams = new ProjectileSimulator.SimParameters(
        0.215,   // ball mass kg
        0.1501,  // ball diameter m
        0.47,    // drag coeff (smooth sphere)
        0.2,     // Magnus coeff
        1.225,   // air density
        Millimeter.of(546).in(Meter),// exit height (m), floor to where the ball leaves the shooter
        Units.inchesToMeters(3),  // flywheel diameter (m), measure with calipers
        1.83,    // target height (m), from game manual
        0.71,     // slip factor (0=no grip, 1=perfect), tune this on the real robot. 
        //higher slipFactor is less power and lower slipFactor is more power
        62,    // launch angle from horizontal, measure from CAD
        0.001,   // sim timestep
        1500, 6000, 25, 5.0  // RPM search range, iterations, max sim time
    );

    
    public static ProjectileSimulator PassSim = new ProjectileSimulator(Hubparams);
    public static ProjectileSimulator.GeneratedLUT passlut = PassSim.generateLUT();


    public static ProjectileSimulator Hubsim = new ProjectileSimulator(Hubparams);
    public static ProjectileSimulator.GeneratedLUT hublut = Hubsim.generateLUT();

    
    public static final ShotCalculator.Config config = new ShotCalculator.Config();
    static{
        config.launcherOffsetX = Constants.OFF_SET_SHOOTER.getX();
        config.launcherOffsetY = Constants.OFF_SET_SHOOTER.getY();
        config.phaseDelayMs = 20.0;     // your vision pipeline latency
        config.mechLatencyMs = 20.0;    // how long the mechanism takes to respond
        config.maxTiltDeg = 5.0;        // suppress firing when chassis tilts past this (bumps/ramps)
        config.headingSpeedScalar = 3.0; // heading tolerance tightens with robot speed (0 to disable)
        config.headingReferenceDistance = 2.5; // heading tolerance scales with distance from hub
        config.maxScoringDistance = 10.0;
        config.minScoringDistance = 0.1;
        config.maxSOTMSpeed = 3.0;
        config.headingMaxErrorRad = Units.degreesToRadians(2);
        config.shooterAngleOffsetRad = Math.PI;
    }
    public static final ShotCalculator shot_Calc_Hub = new ShotCalculator(config);
        static{
            for (var entry : hublut.entries()) {
                if (entry.reachable()) {
                    shot_Calc_Hub.loadLUTEntry(entry.distanceM(), entry.rpm(), entry.tof());
                }
            }
        }
    public static final ShotCalculator shot_Calc_Pass = new ShotCalculator(config);
        static{
            for (var entry : passlut.entries()) {
                if (entry.reachable()) {
                    shot_Calc_Pass.loadLUTEntry(entry.distanceM(), entry.rpm(), entry.tof());
                }
            }
        }
    }
    
    private static ShotInputs inputs;
    private static LaunchParameters shootParameters = LaunchParameters.INVALID;
    public static void updateHubPrams(){
        inputs = new ShotInputs(Drive.getInsatnce().getPose(),      
        ChassisSpeeds.fromRobotRelativeSpeeds(Drive.getInsatnce().getChassisSpeeds(),Drive.getInsatnce().getRotation()), Drive.getInsatnce().getChassisSpeeds(), 
        AllianceFlipUtil.apply(new Translation2d(4.5974, 4.035)), 
        DriverStation.getAlliance().isPresent() ? 
        DriverStation.getAlliance().get()== Alliance.Blue  ? new Translation2d(1,0) : new Translation2d(-1,0) 
        : new Translation2d(1,0), 
        0.9);
        shootParameters = ShooterLookUpTables.shot_Calc_Hub.calculate(inputs);
    }
    private static LaunchParameters PassParameters = LaunchParameters.INVALID;
    public static void updatePassPrams(){
        inputs = new ShotInputs(Drive.getInsatnce().getPose(),      
        ChassisSpeeds.fromRobotRelativeSpeeds(Drive.getInsatnce().getChassisSpeeds(),Drive.getInsatnce().getRotation()), Drive.getInsatnce().getChassisSpeeds(), 
        new Translation2d(Drive.getInsatnce().getPose().getX(),AllianceFlipUtil.applyY(2)), 
        DriverStation.getAlliance().isPresent() ? 
        DriverStation.getAlliance().get()== Alliance.Blue  ? new Translation2d(-1,0) : new Translation2d(1,0) 
        : new Translation2d(-1,0), 
        0.9);
        PassParameters = ShooterLookUpTables.shot_Calc_Pass.calculate(inputs);
    }
    public static LaunchParameters PassPrameters(){
        updatePassPrams();
        Logger.recordOutput("Pass/Parmeters", PassParameters);
        if (PassParameters.isValid()) {
            return PassParameters;
        }
        return new LaunchParameters(0, 0, PassParameters.driveAngle(), 0, false, 0, 0, 0,false);
    }
    public static LaunchParameters HubParameters(){
        updateHubPrams();
        Logger.recordOutput("Hub/Parmeters", shootParameters);
        if (shootParameters.isValid() ){ //&& launchParameters.confidence() > 50
            return shootParameters;
        }
        return new LaunchParameters(0, 0, shootParameters.driveAngle(), 0, false, 0, 0, 0, false);
    }
}
