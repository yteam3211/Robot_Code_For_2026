// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.lib.Mechanism;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.util.Units;
import frc.lib.Mechanism.Mechanism.MechanismType;

/** Add your docs here. */
public class SimulationConfig {
    public MechanismType mechanismType;
    public DCMotor dcMotor;
    public double gearRatio;
    public double JkMeterSquaerdOrDrum;
    public double lengthMetersOrMass;
    public double minRangeOfMotion;
    public double maxRangeOfMotion;
    public double startingPosition;
    public boolean simulateGravity;
    public double[] measurementStdDevs;

    public SimulationConfig(
            MechanismType mechanismType,
            DCMotor dcMotor,
            double gearRatio,
            double JkMeterSquaerdOrDrum,
            double lengthMetersOrMass,
            double minRangeOfMotion,
            double maxRangeOfMotion,
            double startingPosition,
            boolean simulateGravity,
            double... measurementStdDevs) {
        this.mechanismType = mechanismType;
        this.gearRatio = gearRatio;
        this.JkMeterSquaerdOrDrum = JkMeterSquaerdOrDrum;
        this.minRangeOfMotion = minRangeOfMotion;
        this.maxRangeOfMotion = maxRangeOfMotion;
        this.startingPosition = startingPosition;
        this.simulateGravity = simulateGravity;
        this.measurementStdDevs = measurementStdDevs;
    }

    public static SimulationConfig createArmConfig(
            DCMotor dcMotor,
            double gearRatio,
            double JkMeterSquaerd,
            double lengthMeters,
            double minAngleDegree,
            double maxAngleDegree,
            double startingAngleDegree,
            boolean simulateGravity,
            double... measurementStdDevs) {
        return new SimulationConfig(
                MechanismType.Arm,
                dcMotor,
                gearRatio,
                JkMeterSquaerd,
                lengthMeters,
                Units.degreesToRadians(minAngleDegree),
                Units.degreesToRadians(maxAngleDegree),
                Units.degreesToRadians(startingAngleDegree),
                simulateGravity,
                measurementStdDevs);
    }

    public static SimulationConfig createElevatorConfig(
            DCMotor dcMotor,
            double gearRatio,
            double MassKg,
            double drumRadiusMeters,
            double minHightMeters,
            double MaxHightMeters,
            double StartingHightMeters,
            boolean simulateGravity,
            double... measurementStdDevs) {
        return new SimulationConfig(
                MechanismType.Elevator,
                dcMotor,
                gearRatio,
                drumRadiusMeters,
                MassKg,
                minHightMeters,
                MaxHightMeters,
                StartingHightMeters,
                simulateGravity,
                measurementStdDevs);
    }

    public static SimulationConfig createFlyWheelConfig(
            DCMotor dcMotor, double gearRatio, double JkMeterSquaerd, double... measurementStdDevs) {
        return new SimulationConfig(
                MechanismType.Flywheel, dcMotor, gearRatio, JkMeterSquaerd, 0, 0, 0, 0, true, measurementStdDevs);
    }

    public static SimulationConfig createDcMotorConfig(
            DCMotor dcMotor, double JkMeterSquaerd, double gearRatio, double... measurementStdDevs) {
        return new SimulationConfig(
                MechanismType.dcMotor, dcMotor, gearRatio, JkMeterSquaerd, 0, 0, 0, 0, true, measurementStdDevs);
    }
}
