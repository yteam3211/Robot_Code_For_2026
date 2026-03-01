// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.kicker;

import static edu.wpi.first.units.Units.Minute;
import static edu.wpi.first.units.Units.Rotation;

import edu.wpi.first.units.measure.AngularVelocity;
import frc.lib.util.ITarget;

/** Add your docs here. */
public enum KickerState implements ITarget<AngularVelocity>{
    moveFuelToShooter(Rotation.per(Minute).of(3000)),
    stop(Rotation.per(Minute).of(0));
    private AngularVelocity velocity;
    private KickerState(AngularVelocity velocity){
        this.velocity = velocity;
    }
    @Override
    public AngularVelocity getTarget() {
        return velocity;
    }
}
