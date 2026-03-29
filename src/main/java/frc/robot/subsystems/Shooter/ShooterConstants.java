// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Shooter;

import com.ctre.phoenix6.CANBus;
import com.ctre.phoenix6.signals.GravityTypeValue;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix6.signals.StaticFeedforwardSignValue;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.system.plant.DCMotor;

/** Add your docs here. */
public class ShooterConstants {
    public static final int m_MasterR_ID = 40;
    public static final int m_SlaveR_ID = 41;
    public static final int m_SlaveL1_ID = 42;
    public static final int m_SlaveL2_ID = 43;

    public static final int m_Beam_Break = 3;
    /** the DC motor that is used */
    public static final DCMotor dcMotor = DCMotor.getKrakenX60Foc(4);
    /** the canbus of the subsystem */
    public static final CANBus m_canbus = new CANBus("subsystems");
    /** what mod dose the motor be in when idel */
    public static final NeutralModeValue NeutralMode = NeutralModeValue.Coast;
    /** gear ratio of the mechanism */
    public static final double gearRatio = 1;
    /** the MOI for the mechanism get is from CAD (אתה לוקח את זה מסרטוט) */
    public static final double JKgMeterSqured = 0.03792961;
    /** position factor that cahnge from the sensor to the acual degree of the mechanism */
    public static final double POSITION_CONVERSION_FACTOR = gearRatio;

    public static final InvertedValue Right_Inverted = InvertedValue.CounterClockwise_Positive;
    public static final InvertedValue Left_Inverted = InvertedValue.Clockwise_Positive;

    
    public final class MotionMagicConstants {
        public static final double MOTION_MAGIC_VELOCITY = 6000;
        public static final double MOTION_MAGIC_ACCELERATION = 24000;
        public static final double MOTION_MAGIC_JERK = 0;

        public static final double Slot0_MOTOR_KS = 0.3;//0.13261
        public static final double Slot0_MOTOR_KA = 0.0023092;//0.0013639
        public static final double Slot0_MOTOR_KV = 0.124137931;//0.121
        public static final double Slot0_MOTOR_KG = 0;
        public static final double Slot0_MOTOR_KP = 0.1;//0.025024
        public static final double Slot0_MOTOR_KI = 0;
        public static final double Slot0_MOTOR_KD = 0.0001;//0.0001s

        public static final GravityTypeValue GravityType = GravityTypeValue.Arm_Cosine;
        public static final StaticFeedforwardSignValue staticFeedForward = StaticFeedforwardSignValue.UseVelocitySign;
    }
}
