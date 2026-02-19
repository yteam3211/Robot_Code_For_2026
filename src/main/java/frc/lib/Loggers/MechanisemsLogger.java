// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.lib.Loggers;

import static edu.wpi.first.units.Units.Degree;
import static edu.wpi.first.units.Units.Meters;

import org.littletonrobotics.junction.mechanism.LoggedMechanism2d;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.Distance;
import frc.robot.Robot;
import frc.robot.subsystems.Indexer.Indexer;
import frc.robot.subsystems.IntakePitch.IntakePitch;

/** Add your docs here. */
public class MechanisemsLogger {
    private static boolean isInit;
    private static Pose3d IntakePitchPose;
    private static Pose3d HopperExtandsPose;
    private static Pose3d SpinDexerLeftPose;
    private static Pose3d spinDexerRightPose;
    private static LoggedMechanism2d IntakePitchMechanisem;
    private static LoggedMechanism2d HopperExtandsMechanisem;
    private static LoggedMechanism2d SpinDexerLeftMechanisem;
    private static LoggedMechanism2d spinDexerRightMechanisem;

    public static void init(){
        isInit = true;
        if (Robot.isSimulation()) {
            return;
        }
        IntakePitchPose = new Pose3d();
        HopperExtandsPose = new Pose3d();
        spinDexerRightPose = new Pose3d();
        SpinDexerLeftPose = new Pose3d();
    }
    public static void update(){
        if (!isInit) {
            init();
        }
        IntakePitchPose = new Pose3d(IntakePitchPose.getTranslation(),
        new Rotation3d(Degree.of(0),IntakePitch.getInstance().getAngle(),Degree.of(0)));
        HopperExtandsPose = new Pose3d(HopperExtandsPose.getMeasureX(),HopperOut(),HopperExtandsPose.getMeasureZ(),HopperExtandsPose.getRotation());
        spinDexerRightPose = new Pose3d(spinDexerRightPose.getTranslation(),new Rotation3d(Degree.of(0),Degree.of(0),Indexer.getInstance().getAngle()));
        SpinDexerLeftPose = new Pose3d(SpinDexerLeftPose.getTranslation(),new Rotation3d(Degree.of(0),Degree.of(0),Indexer.getInstance
        ().getAngle().times(-1)));
    }
    private static final double MultiDegreeToMeters = 0;
    private static Distance HopperOut(){
        double InatkeDegree = IntakePitch.getInstance().getAngle().in(Degree);
        double meters = MultiDegreeToMeters * InatkeDegree;
        return Meters.of(meters);
    }
}
