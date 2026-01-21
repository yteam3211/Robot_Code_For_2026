// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Shooter;

import com.ctre.phoenix6.CANBus;
import com.ctre.phoenix6.signals.GravityTypeValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix6.signals.StaticFeedforwardSignValue;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.wpilibj.simulation.FlywheelSim;

/** Add your docs here. */
public class ShooterConstants {
    public static final int m_masterID = 40;
    public static final int m_slaveID1 = 41;
    public static final int m_slaveID2 = 42;
    public static final int m_slaveID3 = 43;
    /** the DC motor that is used */
    public static final DCMotor dcMotor = DCMotor.getKrakenX60Foc(4);
    /** the canbus of the subsystem */
    public static final CANBus m_canbus = new CANBus("rio");
    /** what mod dose the motor be in when idel */
    public static final NeutralModeValue NeutralMode = NeutralModeValue.Coast;
    /** gear ratio of the mechanism */
    public static final double gearRatio = 0;
    /** the MOI for the mechanism get is from CAD (אתה לוקח את זה מסרטוט) */
    public static final double JKgMeterSqured = 0;
    /** position factor that cahnge from the sensor to the acual degree of the mechanism */
    public static final double POSITION_CONVERSION_FACTOR = (1 / (gearRatio * 360));

    
    public final class MotionMagicConstants {
        public static final double MOTION_MAGIC_VELOCITY = 0;
        public static final double MOTION_MAGIC_ACCELERATION = 0;
        public static final double MOTION_MAGIC_JERK = 0;

        public static final double MOTOR_KS = 0;
        public static final double MOTOR_KA = 0;
        public static final double MOTOR_KV = 0;
        public static final double MOTOR_KG = 0;
        public static final double MOTOR_KP = 0;
        public static final double MOTOR_KI = 0;
        public static final double MOTOR_KD = 0;

        public static final GravityTypeValue GravityType = GravityTypeValue.Arm_Cosine;
        public static final StaticFeedforwardSignValue staticFeedForward = StaticFeedforwardSignValue.UseVelocitySign;
    }
    public static FlywheelSim ads = new FlywheelSim(LinearSystemId.createFlywheelSystem(dcMotor, JKgMeterSqured,gearRatio), dcMotor);
}
