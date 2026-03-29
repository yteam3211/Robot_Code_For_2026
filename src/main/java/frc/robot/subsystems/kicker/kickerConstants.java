// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.kicker;

import com.ctre.phoenix6.CANBus;
import com.ctre.phoenix6.signals.GravityTypeValue;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix6.signals.StaticFeedforwardSignValue;

/** Add your docs here. */
public class kickerConstants {
    public static final int m_kickerID = 35;
    /** the canbus of the subsystem */
    public static final CANBus m_canbus = new CANBus("subsystems");
    /** what mod dose the motor be in when idel */
    public static final NeutralModeValue NeutralMode = NeutralModeValue.Brake;
    /** gear ratio of the mechanism */
    public static final double gearRatio = 1.25;
    /** position factor that cahnge from the sensor to the acual degree of the mechanism */
    public static final double POSITION_CONVERSION_FACTOR = ((gearRatio));
    public static final InvertedValue Inverted = InvertedValue.Clockwise_Positive;
        public final class MotionMagicConstants {
        public static final double MOTION_MAGIC_VELOCITY = 9999;
        public static final double MOTION_MAGIC_ACCELERATION = 9999;
        public static final double MOTION_MAGIC_JERK = 0;

        public static final double Slot0_MOTOR_KS = 0.37276;//0.23
        public static final double Slot0_MOTOR_KA = 0;
        public static final double Slot0_MOTOR_KV = 0.0257;
        public static final double Slot0_MOTOR_KG = 0;
        public static final double Slot0_MOTOR_KP = 14.834;
        public static final double Slot0_MOTOR_KI = 0;
        public static final double Slot0_MOTOR_KD = 0;
        public static final GravityTypeValue GravityType = GravityTypeValue.Arm_Cosine;
        public static final StaticFeedforwardSignValue staticFeedForward = StaticFeedforwardSignValue.UseVelocitySign;    
    }
}
