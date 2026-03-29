// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.IntakePitch;

import static edu.wpi.first.units.Units.Degree;
import static edu.wpi.first.units.Units.Inch;
import static edu.wpi.first.units.Units.KilogramSquareMeters;
import static edu.wpi.first.units.Units.Meter;

import org.littletonrobotics.junction.networktables.LoggedNetworkNumber;

import com.ctre.phoenix6.signals.GravityTypeValue;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.MomentOfInertia;

/** Add your docs here. */
public class IntakePitchConstants {
        /** the DC motor that is used */
    public static final DCMotor dcMotor = DCMotor.getKrakenX44Foc(1);
    /** the id of the motor subsystem */
    public static final int m_MotorId = 55;
    /** the id of the beam break at the front*/
    public static final int m_limitSwitch_max = 1;
    /** the id of the beam break at the back*/
    public static final int m_limitSwitch_min = 0;
    /** the can bus name defualt is rio */
    public static final String m_CanBusName = "subsystems";
    /** what mod dose the motor be in when idel */
    public static final NeutralModeValue NeutralMode = NeutralModeValue.Coast;
    /** inverted or not */
    public static final InvertedValue Invetrted = InvertedValue.Clockwise_Positive;
    /** gear ratio of the mechanism */
    public static final double gearRatio = 81.0;
    /** Min and Max angle in radians */
    public static final Angle minAngleDegree = Degree.of(90);
    public static final Angle maxAngleDegree = Degree.of(140);
    /** the start position of the motor (arm should start at 90 if are facing up) */
    public static final Angle startingAngle = Degree.of(90);
    /** the length of the mechanism */
    public static final double lengthMeters = Inch.of(12).in(Meter);
    /** the MOI for the mechanism get is from CAD (אתה לוקח את זה מסרטוט) */
    public static final MomentOfInertia INERTIA = KilogramSquareMeters.of(0.63298);
    /** position factor that cahnge from the sensor to the acual degree of the mechanism */
    public static final double POSITION_CONVERSION_FACTOR = gearRatio;

    public final class MotionMagicConstants {
        public static final double MOTION_MAGIC_VELOCITY = 0.25;
        public static final double MOTION_MAGIC_ACCELERATION = 0.3;
        public static final double MOTION_MAGIC_JERK = 0;

        public static final double MOTOR_KS = 0.24449;//0.05075
        public static final double MOTOR_KA = 0;//0.028344
        public static final double MOTOR_KV = 9.72;//0.972
        public static final double MOTOR_KG = 0.4;//0.41111
        public static final double MOTOR_KP = 133;//3.596
        public static final double MOTOR_KI = 0;
        public static final double MOTOR_KD = 0;//4.0737
        public static final GravityTypeValue GravityType = GravityTypeValue.Arm_Cosine;
    }
}
