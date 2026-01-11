// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.lib.Mechanism;

import java.util.function.Consumer;
import java.util.function.DoubleConsumer;

import org.littletonrobotics.junction.inputs.LoggableInputs;
import org.littletonrobotics.junction.mechanism.LoggedMechanism2d;
import org.littletonrobotics.junction.mechanism.LoggedMechanismLigament2d;

import com.ctre.phoenix6.hardware.TalonFX;

import frc.robot.Robot;

/** Add your docs here. */
public class Mechanism {
    /** use each for what is the mechanism */
    public enum MechanismType {
        /** for Elevator and mechanism that go up and dwon in the smae direction */
        Elevator,
        /** for Arm and mechanism that go in a rotational way */
        Arm,
        /** for shooter and things that need to spin fast and not go to a specific place */
        Flywheel,
        /** for evrything else */
        dcMotor;
    }

    private TalonFX Motor;
    private SimulationWrapper simulation;
    private double positionFactorForSimulation;;
    public Mechanism(TalonFX Motor, SimulationConfig config) {
        this.Motor = Motor;
        if (config != null && Robot.isSimulation()) {
            simulation = new SimulationWrapper(config);
        }
        positionFactorForSimulation = config.mechanismType == MechanismType.Elevator
                ? config.JkMeterSquaerdOrDrum * 2 * Math.PI * config.gearRatio
                : config.gearRatio;
    }

    public void periodic() {
        
    }
    public void updateVisual(){
        
    }
    public LoggableInputs inputsFromMotor(){
        return new MotorInputsLogged(Motor);
    }

    public void simulationPeriodic() {
        simulation.setVoltage(Motor.getSimState().getMotorVoltage());
        simulation.update(0.02);
        Motor.getSimState().setRawRotorPosition(simulation.getPosition() * positionFactorForSimulation);
        Motor.getSimState().setRotorVelocity(simulation.getVelocity());
        Motor.getSimState().setRotorAcceleration(simulation.getAccelration());
    }
    public double getPosition(){
        return Motor.getPosition().getValueAsDouble();
    }
    public double getVelocity(){
        return Motor.getVelocity().getValueAsDouble();
    }
    public double getAccelration(){
        return Motor.getAcceleration().getValueAsDouble();
    }
    public double getVoltage(){
        return Motor.getMotorVoltage().getValueAsDouble();
    }
}
