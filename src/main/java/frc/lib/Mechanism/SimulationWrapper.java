// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.lib.Mechanism;

import static edu.wpi.first.units.Units.RotationsPerSecondPerSecond;

import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;
import edu.wpi.first.wpilibj.simulation.ElevatorSim;
import edu.wpi.first.wpilibj.simulation.FlywheelSim;
import edu.wpi.first.wpilibj.simulation.LinearSystemSim;
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;

import java.util.concurrent.RunnableFuture;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleSupplier;

import org.littletonrobotics.junction.Logger;

/** Add your docs here. */
public class SimulationWrapper {
    private record SimData(
        DoubleSupplier position,
        DoubleSupplier velocity,
        DoubleSupplier accelration,
        DoubleSupplier voltage
) {
        
        public double getPosition() {
            return position.getAsDouble();
        }

        public double getVelocity() {
            return velocity.getAsDouble();
        }

        public double getAccelration() {
            return accelration.getAsDouble();
        }

        public double getVoltage() {
            return voltage.getAsDouble();
        }
    }

    private DoubleConsumer updateSim;
    private DoubleConsumer voltageUpdate;
    private SimData data;
    private double missingData;
    private Runnable updateMissingData;

    public SimulationWrapper(SimulationConfig config) {
        try{
            if (config.mechanismType == null) {
                throw new Exception("the mechanismType is null and i dont know witch Simulation you want");
            }
        switch (config.mechanismType) {
            case Arm:
                SingleJointedArmSim ArmSim = new SingleJointedArmSim(
                        config.dcMotor,
                        config.gearRatio,
                        config.JkMeterSquaerdOrDrum,
                        config.lengthMetersOrMass,
                        config.minRangeOfMotion,
                        config.maxRangeOfMotion,
                        config.simulateGravity,
                        config.startingPosition,
                        config.measurementStdDevs);
                data = new SimData(
                        () -> Units.radiansToDegrees(ArmSim.getAngleRads()),
                        () -> Units.radiansToDegrees(ArmSim.getVelocityRadPerSec()),
                        () -> missingData,
                        () -> ArmSim.getInput(0));
                setUpdateSupplier(ArmSim);
                setVoltageSupplier(ArmSim);
                updateMissingData = new Runnable() {
                    double helper = 0;

                    @Override
                    public void run() {
                        missingData = Units.radiansToDegrees(ArmSim.getVelocityRadPerSec()) - helper;
                        helper = Units.radiansToDegrees(ArmSim.getVelocityRadPerSec());
                    }
                };
                break;
            case Elevator:
                ElevatorSim ElevatorSim = new ElevatorSim(
                        config.dcMotor,
                        config.gearRatio,
                        config.lengthMetersOrMass,
                        config.JkMeterSquaerdOrDrum,
                        config.minRangeOfMotion,
                        config.maxRangeOfMotion,
                        config.simulateGravity,
                        config.startingPosition,
                        config.measurementStdDevs);
                data = new SimData(
                        () -> ElevatorSim.getPositionMeters() / 100,
                        () -> ElevatorSim.getVelocityMetersPerSecond() / 100,
                        () -> missingData,
                        () -> ElevatorSim.getInput(0));
                setUpdateSupplier(ElevatorSim);
                setVoltageSupplier(ElevatorSim);
                updateMissingData = new Runnable() {
                    double helper = 0;

                    @Override
                    public void run() {
                        missingData = ElevatorSim.getPositionMeters() / 100 - helper;
                        helper = ElevatorSim.getVelocityMetersPerSecond() /100;
                    }
                };
                break;
            case Flywheel:
                FlywheelSim Flywheelsim = new FlywheelSim(
                        LinearSystemId.createFlywheelSystem(
                                config.dcMotor, config.JkMeterSquaerdOrDrum, config.gearRatio),
                        config.dcMotor,
                        config.measurementStdDevs);
                data = new SimData(
                        () -> missingData,
                        () -> Flywheelsim.getAngularVelocityRPM(),
                        () -> Flywheelsim.getAngularAcceleration().in(RotationsPerSecondPerSecond),
                        () -> Flywheelsim.getInputVoltage());
                setUpdateSupplier(Flywheelsim);
                setVoltageSupplier(Flywheelsim);
                updateMissingData = () -> {
                    missingData = missingData + Flywheelsim.getAngularVelocityRPM();
                };
                break;
            case dcMotor:
                DCMotorSim DcMotorSim = new DCMotorSim(
                        LinearSystemId.createDCMotorSystem(
                                config.dcMotor, config.JkMeterSquaerdOrDrum, config.gearRatio),
                        config.dcMotor,
                        config.measurementStdDevs);
                data = new SimData(
                        () -> DcMotorSim.getAngularPositionRotations(),
                        () -> DcMotorSim.getAngularVelocityRPM() / 60,
                        () -> DcMotorSim.getAngularAcceleration().in(RotationsPerSecondPerSecond),
                        () -> DcMotorSim.getInputVoltage());
                setUpdateSupplier(DcMotorSim);
                setVoltageSupplier(DcMotorSim);
                break;
            default:
                throw new Exception(
                "how ? i dont know how you did it if you want me to fix this bug send me a masege in whatsapp 0548947448",new Throwable("somehow the enum isnt from the four type that i gave you and isnt null"));
                }}
            catch (Exception e) {
                System.out.println(e.toString());
            }
        }

    private void setUpdateSupplier(LinearSystemSim Sim) {
        updateSim = new DoubleConsumer() {
            @Override
            public void accept(double value) {
                Sim.update(value);
            }
        };
    }

    public void update(double dtSeconds) {
        updateSim.accept(dtSeconds);
        updateMissingData.run();
    }

    private void setVoltageSupplier(LinearSystemSim Sim) {
        voltageUpdate = (voltage) -> Sim.setInput(0, voltage);
    }

    public void setVoltage(double voltage) {
        voltageUpdate.accept(voltage);
    }

    public double getPosition() {
        return data.getPosition();
    }

    public double getVelocity() {
        return data.getVelocity();
    }

    public double getAccelration() {
        return data.getAccelration();
    }

    public double getVoltage() {
        return data.getVoltage();
    }
}
