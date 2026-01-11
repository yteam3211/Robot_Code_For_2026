// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Gneralsubsystems.without_sim;

import static edu.wpi.first.units.Units.Degree;

import com.ctre.phoenix6.signals.GravityTypeValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.units.measure.Angle;

/** Add your docs here. */
public class GeneralWithoutSimConstants {
    /** the id of the motor subsystem */
    public static final int m_MotorId = 0;
    /** the can bus name defualt is rio */
    public static final String m_CanBusName = "rio";
    /** the gear ratio of the mechanism */
    public static final double gearRiato = 0;
    /** position factor that cahnge from the sensor to the acual meters of the mechanism */
    public static final double POSITION_CONVERSION_FACTOR = (1 / (gearRiato));
    /** the nutrual mod of the motor (brake/coast) */
    public static final NeutralModeValue NeutralMode = NeutralModeValue.Brake;
    /** the starting angle of the mechanism */
    public static Angle startingAngle = Degree.of(90);
    /** the max and min pos of the mecnisem */
    public static Angle maxAngle = Degree.of(0);

    public static Angle minAngle = Degree.of(0);

    public final class MotionMagicConstants {
        public static final double MOTION_MAGIC_VELOCITY = 0;
        public static final double MOTION_MAGIC_ACCELERATION = 0;
        public static final double MOTION_MAGIC_JERK = 0;
        public static final double MotionMagicExpo_kA = 0;
        public static final double MotionMagicExpo_kV = 0;

        public static final double MOTOR_KS = 0;
        public static final double MOTOR_KA = 0;
        public static final double MOTOR_KV = 0;
        public static final double MOTOR_KG = 0;
        public static final double MOTOR_KP = 0;
        public static final double MOTOR_KI = 0;
        public static final double MOTOR_KD = 0;
        /**
         * what the gravity type ? {@link GravityTypeValue#Arm_Cosine} for Arm (can fall to either side)
         * {@link GravityTypeValue#Elevator_Static} for an Elevator (can fall only down)
         */
        public static final GravityTypeValue GravityType = GravityTypeValue.Arm_Cosine;
    }
}
