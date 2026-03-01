// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.kicker;

import static edu.wpi.first.units.Units.Rotation;
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
public class kickerIOinputs{
    public AngularVelocity velocity = RotationsPerSecond.of(0);
    public AngularAcceleration acceleration = RotationsPerSecondPerSecond.of(0);
    public Angle postion = Rotation.of(0);
    public boolean isConncted = false;
    public Voltage voltage = Volts.of(0);
}
