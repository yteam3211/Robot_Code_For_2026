// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Indexer;

import static edu.wpi.first.units.Units.RPM;

import edu.wpi.first.units.measure.AngularVelocity;
import frc.lib.util.ITarget;

/** Add your docs here. */
public enum IndexerState implements ITarget<AngularVelocity>{
    Index(600),
    stop(0),
    Back(-800);
    private AngularVelocity Velocity;
    private IndexerState(double velocity){
        Velocity = RPM.of(velocity);
    }

    @Override
    public AngularVelocity getTarget() {
        return Velocity;
    }

}
