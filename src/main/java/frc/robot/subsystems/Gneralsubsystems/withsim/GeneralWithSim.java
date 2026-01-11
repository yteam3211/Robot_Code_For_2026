// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Gneralsubsystems.withsim;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.SubsystemState;
import frc.robot.subsystems.Gneralsubsystems.withsim.GeneralIO.GeneralInputs;

public class GeneralWithSim extends SubsystemBase {
    /** the state the motor follow */
    GeneralIO io;

    GeneralInputs inputs = new GeneralInputs();
    /** Creates a new GeneralSubsystem. */
    public GeneralWithSim(GeneralIO io) {
        this.io = io;
        io.updateInputs(inputs);
    }

    @Override
    public void periodic() {
        io.updateInputs(inputs);
        // Logger.processInputs("GeneralWithSim", inputs);
    }

    @Override
    public void simulationPeriodic() {
        io.updateSim();
    }

    public void setState(Generalstate state) {
        SubsystemState.generalstate = state;
    }
}
