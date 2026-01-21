// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.IntakePitch;

import frc.lib.util.ITarget;

/** Add your docs here. */
public enum IntakePitchState implements ITarget {
    colse(0),
    Open(493.12 + 90),
    middle(291.56);

    private double degree;
    private IntakePitchState(double degree){
        this.degree = degree;
    }
    @Override
    public double getTarget() {
        return degree;
    }
}
