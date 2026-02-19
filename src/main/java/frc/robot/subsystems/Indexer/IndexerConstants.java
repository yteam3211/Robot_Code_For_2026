// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Indexer;

import com.ctre.phoenix6.CANBus;
import com.ctre.phoenix6.signals.GravityTypeValue;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix6.signals.StaticFeedforwardSignValue;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.system.plant.DCMotor;

/** Add your docs here. */
public class IndexerConstants {
    public static final int m_indexerID = 25;
    /** the DC motor that is used */
    public static final DCMotor dcMotor = DCMotor.getKrakenX60Foc(2);
    /** the id of the beam break */
    public static final int m_beamBreakID = 3;
    /** the canbus of the subsystem */
    public static final CANBus m_canbus = new CANBus("subsystems");
    /** what mod dose the motor be in when idel */
    public static final NeutralModeValue NeutralMode = NeutralModeValue.Coast;
    /** gear ratio of the mechanism */
    public static final double gearRatio = 7.14285715;
    /** the MOI for the mechanism get is from CAD (אתה לוקח את זה מסרטוט) */
    public static final double JKgMeterSqured = 0.00267442;
    /** position factor that cahnge from the sensor to the acual degree of the mechanism */
    public static final double POSITION_CONVERSION_FACTOR = gearRatio;
    public static final InvertedValue Inverted = InvertedValue.CounterClockwise_Positive;

    
    public final class MotionMagicConstants {
        public static final double MOTION_MAGIC_VELOCITY = 9999;
        public static final double MOTION_MAGIC_ACCELERATION = 9999;
        public static final double MOTION_MAGIC_JERK = 0;

        public static final double MOTOR_KS = 0;
        public static final double MOTOR_KA = 0;
        public static final double MOTOR_KV = 0.87;
        public static final double MOTOR_KG = 0;
        public static final double MOTOR_KP = 0.1;
        public static final double MOTOR_KI = 0;
        public static final double MOTOR_KD = 0;

        public static final GravityTypeValue GravityType = GravityTypeValue.Arm_Cosine;
        public static final StaticFeedforwardSignValue staticFeedForward = StaticFeedforwardSignValue.UseVelocitySign;
    }

}
