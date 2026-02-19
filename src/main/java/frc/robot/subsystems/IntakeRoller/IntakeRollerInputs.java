// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.IntakeRoller;

import static edu.wpi.first.units.Units.RotationsPerSecond;
import static edu.wpi.first.units.Units.RotationsPerSecondPerSecond;
import static edu.wpi.first.units.Units.Volts;

import org.littletonrobotics.junction.LogTable;
import org.littletonrobotics.junction.inputs.LoggableInputs;

import edu.wpi.first.units.measure.AngularAcceleration;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Velocity;
import edu.wpi.first.units.measure.Voltage;

/** Add your docs here. */
public class IntakeRollerInputs implements LoggableInputs{
    public AngularVelocity velocity = RotationsPerSecond.of(0);
    public Voltage voltage = Volts.of(0);
    public AngularAcceleration acceleration = RotationsPerSecondPerSecond.of(0);
    public boolean isConnected = false;
    public IntakeRollerInputs(){}
    @Override
    public void toLog(LogTable table) {
        table.put("velocity",velocity);
        table.put("voltage",voltage);
        table.put("acceleration",acceleration);
        table.put("isConnected",isConnected);
    }

    @Override
    public void fromLog(LogTable table) {
        velocity = table.get("velocity",velocity);
        voltage = table.get("voltage",voltage);
        acceleration = table.get("acceleration",acceleration);
        isConnected = table.get("isConnected",isConnected);
    }

}
