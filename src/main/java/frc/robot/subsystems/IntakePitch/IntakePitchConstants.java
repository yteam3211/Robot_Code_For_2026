// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.IntakePitch;

import static edu.wpi.first.units.Units.Inch;
import static edu.wpi.first.units.Units.Meter;

import com.ctre.phoenix6.signals.GravityTypeValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;

/** Add your docs here. */
public class IntakePitchConstants {
        /** the DC motor that is used */
    public static final DCMotor dcMotor = DCMotor.getKrakenX60(1);
    /** the id of the motor subsystem */
    public static final int m_MotorId = 0;
    /** the can bus name defualt is rio */
    public static final String m_CanBusName = "rio";
    /** what mod dose the motor be in when idel */
    public static final NeutralModeValue NeutralMode = NeutralModeValue.Brake;
    /** gear ratio of the mechanism */
    public static final double gearRatio = 71.1;
    /** Min and Max angle in radians */
    public static final double minAngleDegree = 0 + 90;
    public static final double maxAngleDegree = 493.12 + 90;
    /** the start position of the motor (arm should start at 90 if are facing up) */
    public static final double startingAngle = 90;
    /** the length of the mechanism */
    public static final double lengthMeters = Inch.of(12).in(Meter);
    /** the wight of the ar, becuse we estamite moi */
    private static final double MassKg = 1.5;
    /** the MOI for the mechanism get is from CAD (אתה לוקח את זה מסרטוט) */
    public static final double JKgMeterSqured = SingleJointedArmSim.estimateMOI(lengthMeters, MassKg);
    /** position factor that cahnge from the sensor to the acual degree of the mechanism */
    public static final double POSITION_CONVERSION_FACTOR = (1 / (gearRatio));

    public final class MotionMagicConstants {
        public static final double MOTION_MAGIC_VELOCITY = 0;
        public static final double MOTION_MAGIC_ACCELERATION = 0;
        public static final double MOTION_MAGIC_JERK = 0;

        public static final double MOTOR_KS = 0;
        public static final double MOTOR_KA = 0;
        public static final double MOTOR_KV = 0;
        public static final double MOTOR_KG = 0;
        public static final double MOTOR_KP = 20;
        public static final double MOTOR_KI = 0;
        public static final double MOTOR_KD = 0;
        /**
         * what the gravity type ? {@link GravityTypeValue#Arm_Cosine} for Arm (can fall to either side)
         * {@link GravityTypeValue#Elevator_Static} for an Elevator (can fall only down)
         */
        public static final GravityTypeValue GravityType = GravityTypeValue.Arm_Cosine;
    }
}
