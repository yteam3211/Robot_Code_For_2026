// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.IntakeRoller;

import frc.lib.util.ITarget;

/** Add your docs here. */
public enum IntakeRollerState implements ITarget{
    move(8),
    stop(0);
    private double voltage;
    private IntakeRollerState(double voltage){
        this.voltage = voltage;
    }

    @Override
    public double getTarget() {
        return voltage;
    }
}
