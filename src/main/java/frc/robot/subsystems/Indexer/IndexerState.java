// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Indexer;

import frc.lib.util.ITarget;

/** Add your docs here. */
public enum IndexerState implements ITarget{
    Index(300),
    Stop(0);
    private double velocity;
    private IndexerState(double velocity){
        this.velocity = velocity;
    }
    @Override
    public double getTarget() {
        return velocity;
    }
}
