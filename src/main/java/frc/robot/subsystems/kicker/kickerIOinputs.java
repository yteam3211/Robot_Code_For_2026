// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.kicker;

import static edu.wpi.first.units.Units.Rotation;
import static edu.wpi.first.units.Units.RotationsPerSecond;
import static edu.wpi.first.units.Units.RotationsPerSecondPerSecond;
import static edu.wpi.first.units.Units.Volts;

import org.littletonrobotics.junction.LogTable;
import org.littletonrobotics.junction.inputs.LoggableInputs;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularAcceleration;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Voltage;

/** Add your docs here. */
public class kickerIOinputs implements LoggableInputs{
    public AngularVelocity velocity = RotationsPerSecond.of(0);
    public AngularAcceleration acceleration = RotationsPerSecondPerSecond.of(0);
    public Angle postion = Rotation.of(0);
    public boolean isConncted = false;
    public Voltage voltage = Volts.of(0);
    @Override
    public void toLog(LogTable table) {
        table.put("velocity",velocity);
        table.put("acceleration",acceleration);
        table.put("isConncted",isConncted);
        table.put("voltage",voltage);
        table.put("postion",postion);
    }
    @Override
    public void fromLog(LogTable table) {
        velocity = table.get("velocity", velocity);
        acceleration = table.get("acceleration", acceleration);
        isConncted = table.get("isConncted", isConncted);
        voltage = table.get("voltage", voltage);
        postion = table.get("postion", postion);
    }
}
