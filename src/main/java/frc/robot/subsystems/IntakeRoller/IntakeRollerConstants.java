// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.IntakeRoller;

import com.ctre.phoenix6.CANBus;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;  

/** Add your docs here. */
public class IntakeRollerConstants {
    public static final int m_masterID = 56;
    /** the canbus of the subsystem */
    public static final CANBus m_canbus = new CANBus("subsystems");
    /** what mod dose the motor be in when idel */
    public static final NeutralModeValue NeutralMode = NeutralModeValue.Coast;
    public static final double gearRatio = 1.777777777777;
    public static final InvertedValue invertedValue = InvertedValue.Clockwise_Positive;
 
}
