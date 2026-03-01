// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.IntakePitch;

import static edu.wpi.first.units.Units.Degree;

import edu.wpi.first.units.measure.Angle;
import frc.lib.util.ITarget;

/** Add your docs here. */
public enum IntakePitchState implements ITarget<Angle> {
    colse(0),
    Open(70),
    middle(45);

    private Angle angle;
    private IntakePitchState(Angle angle){
        this.angle = angle;
    }
    private IntakePitchState(double degree){
        this(Degree.of(degree));
    }
    @Override
    public Angle getTarget() {
        return angle;
    }
}
