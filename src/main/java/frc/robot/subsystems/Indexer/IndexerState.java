// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Indexer;

import static edu.wpi.first.units.Units.Minute;
import static edu.wpi.first.units.Units.Rotation;

import edu.wpi.first.units.measure.AngularVelocity;
import frc.lib.util.ITarget;

/** Add your docs here. */
public enum IndexerState implements ITarget<AngularVelocity>{
    Index(300),
    Stop(0);
    private AngularVelocity velocity;
    private IndexerState(double velocity){
        this.velocity = Rotation.per(Minute).of(velocity);
    }
    @Override
    public AngularVelocity getTarget() {
        return velocity;
    }
}
