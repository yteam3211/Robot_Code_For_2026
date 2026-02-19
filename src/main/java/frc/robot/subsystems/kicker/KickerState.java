// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.kicker;

import frc.lib.util.ITarget;

/** Add your docs here. */
public enum KickerState implements ITarget{
    moveFuelToShooter(3000),
    stop(0);
    private double velocity;
    private KickerState(double velocity){
        this.velocity = velocity;
    }
    @Override
    public double getTarget() {
        return velocity;
    }
}
