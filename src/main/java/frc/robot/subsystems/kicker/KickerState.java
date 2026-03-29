// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.kicker;

import static edu.wpi.first.units.Units.RPM;

import edu.wpi.first.units.measure.AngularVelocity;
import frc.lib.util.ITarget;

/** Add your docs here. */
public enum KickerState implements ITarget<AngularVelocity>{
    moveFuelToShooter(RPM.of(3800)),
    stop(RPM.of(0)),
    moevFuelBack(RPM.of(-4000));
    private AngularVelocity Velocity;
    private KickerState(AngularVelocity velocity){
        this.Velocity = velocity;
    }
    @Override
    public AngularVelocity getTarget() {
        return Velocity;
    }
}
