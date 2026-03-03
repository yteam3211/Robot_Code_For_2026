// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.IntakeRoller;

import static edu.wpi.first.units.Units.Degree;
import static edu.wpi.first.units.Units.RotationsPerSecond;
import static edu.wpi.first.units.Units.RotationsPerSecondPerSecond;
import static edu.wpi.first.units.Units.Volts;

import org.littletonrobotics.junction.AutoLog;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularAcceleration;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Voltage;

/** Add your docs here. */
@AutoLog
public class IntakeRollerInputs{
    public AngularVelocity velocity = RotationsPerSecond.of(0);
    public Voltage voltage = Volts.of(0);
    public AngularAcceleration acceleration = RotationsPerSecondPerSecond.of(0);
    public boolean isConnected = false;
    public Angle position = Degree.of(0);
}
