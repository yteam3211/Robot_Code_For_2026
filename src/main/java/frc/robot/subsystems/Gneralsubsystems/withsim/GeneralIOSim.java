// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Gneralsubsystems.withsim;

import static edu.wpi.first.units.Units.DegreesPerSecond;
import static edu.wpi.first.units.Units.Radian;
import static edu.wpi.first.units.Units.RadiansPerSecond;

import com.ctre.phoenix6.StatusCode;
import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicExpoVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.wpilibj.simulation.BatterySim;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;
import edu.wpi.first.wpilibj.simulation.ElevatorSim;
import edu.wpi.first.wpilibj.simulation.FlywheelSim;
import edu.wpi.first.wpilibj.simulation.LinearSystemSim;
import edu.wpi.first.wpilibj.simulation.RoboRioSim;
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;
import edu.wpi.first.wpilibj.smartdashboard.MechanismLigament2d;

/** Add your docs here. */
public class GeneralIOSim implements GeneralIO {
    private TalonFX subsystemMotor;

    @SuppressWarnings("rawtypes")
    private LinearSystemSim Sim;

    private MotionMagicExpoVoltage motionMagicExpoVoltage = new MotionMagicExpoVoltage(0);

    public GeneralIOSim(String simClass) {
        subsystemMotor = new TalonFX(GeneralWithSimConstants.m_MotorId, GeneralWithSimConstants.m_CanBusName);
        switch (simClass) {
            case "SingleJointedArmSim":
                Sim = new SingleJointedArmSim(
                        LinearSystemId.createSingleJointedArmSystem(
                                GeneralWithSimConstants.dcMotor,
                                GeneralWithSimConstants.rotaion_Sim_Constants.JKgMeterSqured,
                                GeneralWithSimConstants.gearRatio),
                        GeneralWithSimConstants.dcMotor,
                        GeneralWithSimConstants.gearRatio,
                        GeneralWithSimConstants.rotaion_Sim_Constants.lengthMeters,
                        GeneralWithSimConstants.rotaion_Sim_Constants.minAngleRads,
                        GeneralWithSimConstants.rotaion_Sim_Constants.maxAngleRads,
                        true,
                        0);
                break;
            case "ElevatorSim":
                Sim = new ElevatorSim(
                        LinearSystemId.createElevatorSystem(
                                GeneralWithSimConstants.dcMotor,
                                GeneralWithSimConstants.Elevator_Sim_Constants.MassKg,
                                GeneralWithSimConstants.Elevator_Sim_Constants.drumMeters,
                                GeneralWithSimConstants.gearRatio),
                        GeneralWithSimConstants.dcMotor,
                        GeneralWithSimConstants.Elevator_Sim_Constants.minHightMeters,
                        GeneralWithSimConstants.Elevator_Sim_Constants.maxHightMeters,
                        true,
                        GeneralWithSimConstants.Elevator_Sim_Constants.startingHight);
                break;
            case "DCMotorSim":
                Sim = new DCMotorSim(
                        LinearSystemId.createDCMotorSystem(
                                GeneralWithSimConstants.dcMotor,
                                GeneralWithSimConstants.rotaion_Sim_Constants.JKgMeterSqured,
                                GeneralWithSimConstants.gearRatio),
                        GeneralWithSimConstants.dcMotor);
                break;
            case "FlywheelSim":
                Sim = new FlywheelSim(
                        LinearSystemId.createFlywheelSystem(
                                GeneralWithSimConstants.dcMotor,
                                GeneralWithSimConstants.rotaion_Sim_Constants.JKgMeterSqured,
                                GeneralWithSimConstants.gearRatio),
                        GeneralWithSimConstants.dcMotor);
                break;
            default:
                Sim = new LinearSystemSim<>(LinearSystemId.createDCMotorSystem(
                        GeneralWithSimConstants.dcMotor,
                        GeneralWithSimConstants.rotaion_Sim_Constants.JKgMeterSqured,
                        GeneralWithSimConstants.gearRatio));
                break;
        }

        TalonFXConfiguration talonFXConfiguration = new TalonFXConfiguration();
        FeedbackConfigs feedbackConfigsspin = talonFXConfiguration.Feedback;
        feedbackConfigsspin.SensorToMechanismRatio = GeneralWithSimConstants.isArm
                ? GeneralWithSimConstants.rotaion_Sim_Constants.POSITION_CONVERSION_FACTOR
                : GeneralWithSimConstants.Elevator_Sim_Constants.POSITION_CONVERSION_FACTOR;
        ;
        MotorOutputConfigs motorOutputConfigs = talonFXConfiguration.MotorOutput;
        motorOutputConfigs.NeutralMode = GeneralWithSimConstants.NeutralMode;
        MotionMagicConfigs motionMagicConfigs = talonFXConfiguration.MotionMagic;
        motionMagicConfigs.MotionMagicCruiseVelocity =
                GeneralWithSimConstants.MotionMagicConstants.MOTION_MAGIC_VELOCITY;
        motionMagicConfigs.MotionMagicAcceleration =
                GeneralWithSimConstants.MotionMagicConstants.MOTION_MAGIC_ACCELERATION;
        motionMagicConfigs.MotionMagicJerk = GeneralWithSimConstants.MotionMagicConstants.MOTION_MAGIC_JERK;
        motionMagicConfigs.MotionMagicExpo_kA = GeneralWithSimConstants.MotionMagicConstants.MotionMagicExpo_kA;
        motionMagicConfigs.MotionMagicExpo_kV = GeneralWithSimConstants.MotionMagicConstants.MotionMagicExpo_kV;

        Slot0Configs slot0 = talonFXConfiguration.Slot0;
        slot0.kS = GeneralWithSimConstants.MotionMagicConstants.MOTOR_KS;
        slot0.kG = GeneralWithSimConstants.MotionMagicConstants.MOTOR_KG;
        slot0.kV = GeneralWithSimConstants.MotionMagicConstants.MOTOR_KV;
        slot0.kA = GeneralWithSimConstants.MotionMagicConstants.MOTOR_KA;
        slot0.kP = GeneralWithSimConstants.MotionMagicConstants.MOTOR_KP;
        slot0.kI = GeneralWithSimConstants.MotionMagicConstants.MOTOR_KI;
        slot0.kD = GeneralWithSimConstants.MotionMagicConstants.MOTOR_KD;
        slot0.GravityType = GeneralWithSimConstants.MotionMagicConstants.GravityType;

        StatusCode status = StatusCode.StatusCodeNotInitialized;
        for (int i = 0; i < 5; ++i) {
            status = subsystemMotor.getConfigurator().apply(talonFXConfiguration);
            if (status.isOK()) break;
        }
        if (!status.isOK()) {
            System.out.println("Could not configure device. Error: " + status.toString());
        }
        subsystemMotor.setControl(motionMagicExpoVoltage.withEnableFOC(true));
    }

    public void updateInputs(GeneralInputs inputs) {
        inputs.MotorConncted = true;
        inputs.rotaion = Radian.of(Sim.getOutput(0));
        inputs.speed = RadiansPerSecond.of(Sim.getOutput(1));
        inputs.isClosed = Units.radiansToDegrees(Sim.getOutput(0)) < 3;
    }

    private double POSITION_CONVERSION_FACTOR = GeneralWithSimConstants.isArm
            ? GeneralWithSimConstants.rotaion_Sim_Constants.POSITION_CONVERSION_FACTOR
            : GeneralWithSimConstants.Elevator_Sim_Constants.POSITION_CONVERSION_FACTOR;

    @Override
    public void updateSim() {
        var SimState = subsystemMotor.getSimState();
        Sim.setInput(SimState.getMotorVoltage());

        Sim.update(0.02);
        subsystemMotor
                .getSimState()
                .setRawRotorPosition(Units.radiansToRotations(Sim.getOutput(0)) * (POSITION_CONVERSION_FACTOR));

        subsystemMotor
                .getSimState()
                .setRotorVelocity(
                        RadiansPerSecond.of(Sim.getOutput(1)).in(DegreesPerSecond) * (POSITION_CONVERSION_FACTOR));

        RoboRioSim.setVInVoltage(BatterySim.calculateDefaultBatteryLoadedVoltage(Sim.getInput(0)));
    }

    @Override
    public void setMotorAngle(Angle Angle) {
        subsystemMotor.setPosition(Angle);
    }

    @Override
    public void setVoltz(double voltz) {
        subsystemMotor.setVoltage(voltz);
    }

    @Override
    public void setPosition(Angle angle) {
        motionMagicExpoVoltage.withPosition(angle);
    }

    @Override
    public void updateVisual(MechanismLigament2d mechanismLigament2d) {
        if (Sim instanceof SingleJointedArmSim) {
            mechanismLigament2d.setAngle(Units.radiansToDegrees(Sim.getOutput(0)));
        } else if (Sim instanceof ElevatorSim) {
            mechanismLigament2d.setLength(Sim.getOutput(0));
        } else if (Sim instanceof DCMotorSim) {
            mechanismLigament2d.setAngle(Units.radiansToDegrees(Sim.getOutput(0)));
        } else if (Sim instanceof FlywheelSim) {
            mechanismLigament2d.setAngle(mechanismLigament2d.getAngle()
                    + RadiansPerSecond.of(Sim.getOutput(0)).in(DegreesPerSecond));
        } else {
            mechanismLigament2d.setAngle(Sim.getOutput(0));
        }
    }
}
