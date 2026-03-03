// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.lib.Mechanism;

import static edu.wpi.first.units.Units.DegreesPerSecondPerSecond;
import static edu.wpi.first.units.Units.Meters;
import static edu.wpi.first.units.Units.MetersPerSecond;
import static edu.wpi.first.units.Units.MetersPerSecondPerSecond;
import static edu.wpi.first.units.Units.Radian;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.Rotation;
import static edu.wpi.first.units.Units.RotationsPerSecond;
import static edu.wpi.first.units.Units.RotationsPerSecondPerSecond;
import static edu.wpi.first.units.Units.Volts;

import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.Supplier;

import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularAcceleration;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.units.measure.LinearAcceleration;
import edu.wpi.first.units.measure.LinearVelocity;
import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;
import edu.wpi.first.wpilibj.simulation.ElevatorSim;
import edu.wpi.first.wpilibj.simulation.FlywheelSim;
import edu.wpi.first.wpilibj.simulation.LinearSystemSim;
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;


/** Add your docs here. */
public class SimulationWrapper<P,V,A>{
    private class SimData <P,V,A>{
        Supplier<P> position;
        Supplier<V> velocity;
        Supplier<A> accelration;
        Supplier<Voltage> voltage;
        
        public SimData(        
        Supplier<P> position,
        Supplier<V> velocity,
        Supplier<A> accelration,
        Supplier<Voltage> voltage){
            this.position = position;
            this.velocity = velocity;
            this.accelration = accelration;
            this.voltage = voltage;
        }
        public P getPosition() {
            return position.get();
        }

        public V getVelocity() {
            return velocity.get();
        }

        public A getAccelration() {
            return accelration.get();
        }

        public Voltage getVoltage() {
            return voltage.get();
        }
    }

    private DoubleConsumer updateSim;
    private Consumer<Voltage> voltageUpdate;
    private SimData data;
    private double missingData;
    private Runnable updateMissingData;

    public SimulationWrapper(SimulationConfig config) {
        try{
            if (config.mechanismType == null) {
                for (int i = 0; i < 100; i++) {
                    System.out.println("fuck you\n\n");
                }
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
                data = new SimData<Angle,AngularVelocity,AngularAcceleration>(
                        () -> Radian.of(ArmSim.getAngleRads()),
                        () -> RadiansPerSecond.of(ArmSim.getVelocityRadPerSec()),
                        () -> DegreesPerSecondPerSecond.of(missingData),
                        () -> Volts.of(ArmSim.getInput(0)));
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
                data = new SimData<Distance,LinearVelocity,LinearAcceleration>(
                        () -> Meters.of(ElevatorSim.getPositionMeters()),
                        () -> MetersPerSecond.of(ElevatorSim.getVelocityMetersPerSecond()),
                        () -> MetersPerSecondPerSecond.of(missingData),
                        () -> Volts.of(ElevatorSim.getInput(0)));
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
                data = new SimData<Angle,AngularVelocity,AngularAcceleration>(
                        () -> Radian.of(missingData),
                        () -> Flywheelsim.getAngularVelocity(),
                        () -> Flywheelsim.getAngularAcceleration(),
                        () -> Volts.of(Flywheelsim.getInputVoltage()));
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
                data = new SimData<Angle,AngularVelocity,AngularAcceleration>(
                        () -> DcMotorSim.getAngularPosition(),
                        () -> DcMotorSim.getAngularVelocity(),
                        () -> DcMotorSim.getAngularAcceleration(),
                        () -> Volts.of(DcMotorSim.getInputVoltage()));
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
        voltageUpdate = (voltage) -> Sim.setInput(0, voltage.in(Volts));
    }

    public void setVoltage(Voltage voltage) {
        voltageUpdate.accept(voltage);
    }
    public void setVoltage(double voltage){
        setVoltage(Volts.of(voltage));
    }
    public double getPosition() {
        if (data.position.get().getClass().equals(Angle.class)) {
            return ((Angle)data.getPosition()).in(Rotation);
        }
        return ((Distance)data.getPosition()).in(Meters);
    }

    public double getVelocity() {
        if (data.position.get().getClass().equals(AngularVelocity.class)) {
            return ((AngularVelocity)data.getPosition()).in(RotationsPerSecond);
        }
        return ((LinearVelocity)data.getPosition()).in(MetersPerSecond);
    }

    public double getAccelration() {
        if (data.position.get().getClass().equals(AngularAcceleration.class)) {
            return ((AngularAcceleration)data.getPosition()).in(RotationsPerSecondPerSecond);
        }
        return ((LinearAcceleration)data.getPosition()).in(MetersPerSecondPerSecond);
    }

    public double getVoltage() {
        return data.getVoltage().in(Volts);
    }
}
