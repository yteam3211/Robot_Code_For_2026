// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.IntakeRoller;

import static edu.wpi.first.units.Units.Volts;

import edu.wpi.first.units.measure.Voltage;
import frc.lib.util.ITarget;

/** Add your docs here. */
public enum IntakeRollerState implements ITarget<Voltage>{
    move(10),
    stop(0);
    private Voltage voltage;
    private IntakeRollerState(Voltage voltage){
        this.voltage = voltage;
    }
    private IntakeRollerState(double volts){
        this(Volts.of(volts));
    }

    @Override
    public Voltage getTarget() {
        return voltage;
    }
}
