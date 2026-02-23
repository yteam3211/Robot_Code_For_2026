// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.IntakePitch;

import static edu.wpi.first.units.Units.Inch;
import static edu.wpi.first.units.Units.Meter;

import org.littletonrobotics.junction.networktables.LoggedNetworkNumber;

import com.ctre.phoenix6.signals.GravityTypeValue;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;
import frc.robot.Robot;

/** Add your docs here. */
public class IntakePitchConstants {
        /** the DC motor that is used */
    public static final DCMotor dcMotor = DCMotor.getKrakenX60(1);
    /** the id of the motor subsystem */
    public static final int m_MotorId = 55;
    /** the id of the beam break at the front*/
    public static final int m_beambreak_Front_Id = 5;
    /** the id of the beam break at the back*/
    public static final int m_beambreak_back_Id = 6;
    /** the can bus name defualt is rio */
    public static final String m_CanBusName = "subsystems";
    /** what mod dose the motor be in when idel */
    public static final NeutralModeValue NeutralMode = NeutralModeValue.Coast;
    /** inverted or not */
    public static final InvertedValue Invetrted = InvertedValue.Clockwise_Positive;
    /** gear ratio of the mechanism */
    public static final double gearRatio = 40.26;
    /** Min and Max angle in radians */
    public static final double minAngleDegree = 0;
    public static final double maxAngleDegree = 75;
    /** the start position of the motor (arm should start at 90 if are facing up) */
    public static final double startingAngle = 90;
    /** the length of the mechanism */
    public static final double lengthMeters = Inch.of(12).in(Meter);
    /** the MOI for the mechanism get is from CAD (אתה לוקח את זה מסרטוט) */
    public static final double JKgMeterSqured = 1.3424871;
    /** position factor that cahnge from the sensor to the acual degree of the mechanism */
    public static final double POSITION_CONVERSION_FACTOR = gearRatio;

    public final class MotionMagicConstants {
        public static final double MOTION_MAGIC_VELOCITY = 0;
        public static final double MOTION_MAGIC_ACCELERATION = 0;
        public static final double MOTION_MAGIC_JERK = 0;


        public static final LoggedNetworkNumber MOTOR_KS = new LoggedNetworkNumber("/Tuning/IntakePitch/S",0);
        public static final LoggedNetworkNumber MOTOR_KA = new LoggedNetworkNumber("/Tuning/IntakePitch/A",0);
        public static final LoggedNetworkNumber MOTOR_KV = new LoggedNetworkNumber("/Tuning/IntakePitch/V",0);
        public static final LoggedNetworkNumber MOTOR_KG = new LoggedNetworkNumber("/Tuning/IntakePitch/G",0);
        public static final LoggedNetworkNumber MOTOR_KP = new LoggedNetworkNumber("/Tuning/IntakePitch/P",0);
        public static final LoggedNetworkNumber MOTOR_KI = new LoggedNetworkNumber("/Tuning/IntakePitch/I",0);
        public static final LoggedNetworkNumber MOTOR_KD = new LoggedNetworkNumber("/Tuning/IntakePitch/D",0);
        public static final GravityTypeValue GravityType = GravityTypeValue.Arm_Cosine;
    }
}
