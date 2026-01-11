// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.lib.Mechanism;

import java.security.AccessControlContext;

import org.littletonrobotics.junction.LogTable;
import org.littletonrobotics.junction.inputs.LoggableInputs;

import com.ctre.phoenix6.hardware.TalonFX;

/** Add your docs here. */
public class MotorInputsLogged implements LoggableInputs{
    private double position;
    private double velocity;
    private double accelration;
    private double voltage;
    public MotorInputsLogged(double position,double velocity,double accelration, double voltage){
        this.position = position;
        this.velocity = velocity;
        this.accelration = accelration;
        this.voltage = voltage;
    }
    public MotorInputsLogged(TalonFX Motor){
        this.position = Motor.getPosition().getValueAsDouble();
        this.velocity = Motor.getVelocity().getValueAsDouble();
        this.accelration = Motor.getAcceleration().getValueAsDouble();
        this.voltage = Motor.getMotorVoltage().getValueAsDouble();
    }
    @Override
    public void toLog(LogTable table) {
        table.put("position", position);
        table.put("velocity", velocity);
        table.put("accelration", accelration);
        table.put("voltage", voltage);
    }
    @Override
    public void fromLog(LogTable table) {
        position = table.get("position", position);
        velocity = table.get("velocity", velocity);
        accelration = table.get("accelration", accelration);
        voltage = table.get("voltage", voltage);
    }
}
