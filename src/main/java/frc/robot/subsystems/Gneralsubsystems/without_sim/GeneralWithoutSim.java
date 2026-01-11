// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Gneralsubsystems.without_sim;

import static edu.wpi.first.units.Units.Degree;

import com.ctre.phoenix6.StatusCode;
import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicExpoVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.Gneralsubsystems.withsim.Generalstate;

public class GeneralWithoutSim extends SubsystemBase {
    private TalonFX subsystemMotor =
            new TalonFX(GeneralWithoutSimConstants.m_MotorId, GeneralWithoutSimConstants.m_CanBusName);
    private MotionMagicExpoVoltage motionMagicExpoVoltage = new MotionMagicExpoVoltage(0);
    private Generalstate state = Generalstate.KeepItIn;
    /** Creates a new GeneralWithoutSim. */
    public GeneralWithoutSim() {
        TalonFXConfiguration talonFXConfiguration = new TalonFXConfiguration();
        FeedbackConfigs feedbackConfigsspin = talonFXConfiguration.Feedback;
        feedbackConfigsspin.SensorToMechanismRatio = GeneralWithoutSimConstants.POSITION_CONVERSION_FACTOR;
        MotorOutputConfigs motorOutputConfigs = talonFXConfiguration.MotorOutput;
        motorOutputConfigs.NeutralMode = GeneralWithoutSimConstants.NeutralMode;
        MotionMagicConfigs motionMagicConfigs = talonFXConfiguration.MotionMagic;
        motionMagicConfigs.MotionMagicCruiseVelocity =
                GeneralWithoutSimConstants.MotionMagicConstants.MOTION_MAGIC_VELOCITY;
        motionMagicConfigs.MotionMagicAcceleration =
                GeneralWithoutSimConstants.MotionMagicConstants.MOTION_MAGIC_ACCELERATION;
        motionMagicConfigs.MotionMagicJerk = GeneralWithoutSimConstants.MotionMagicConstants.MOTION_MAGIC_JERK;
        motionMagicConfigs.MotionMagicExpo_kA = GeneralWithoutSimConstants.MotionMagicConstants.MotionMagicExpo_kA;
        motionMagicConfigs.MotionMagicExpo_kV = GeneralWithoutSimConstants.MotionMagicConstants.MotionMagicExpo_kV;

        Slot0Configs slot0 = talonFXConfiguration.Slot0;
        slot0.kS = GeneralWithoutSimConstants.MotionMagicConstants.MOTOR_KS;
        slot0.kG = GeneralWithoutSimConstants.MotionMagicConstants.MOTOR_KG;
        slot0.kV = GeneralWithoutSimConstants.MotionMagicConstants.MOTOR_KV;
        slot0.kA = GeneralWithoutSimConstants.MotionMagicConstants.MOTOR_KA;
        slot0.kP = GeneralWithoutSimConstants.MotionMagicConstants.MOTOR_KP;
        slot0.kI = GeneralWithoutSimConstants.MotionMagicConstants.MOTOR_KI;
        slot0.kD = GeneralWithoutSimConstants.MotionMagicConstants.MOTOR_KD;
        slot0.GravityType = GeneralWithoutSimConstants.MotionMagicConstants.GravityType;

        StatusCode status = StatusCode.StatusCodeNotInitialized;
        for (int i = 0; i < 5; ++i) {
            status = subsystemMotor.getConfigurator().apply(talonFXConfiguration);
            if (status.isOK()) break;
        }
        if (!status.isOK()) {
            System.out.println("Could not configure device. Error: " + status.toString());
        }
        subsystemMotor.setControl(motionMagicExpoVoltage
                .withPosition(GeneralWithoutSimConstants.startingAngle)
                .withSlot(0));
        this.setDefaultCommand(this.defualtCommand());
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }

    public void setDegree(double degree) {
        degree = Math.min(
                GeneralWithoutSimConstants.maxAngle.in(Degree),
                Math.max(GeneralWithoutSimConstants.minAngle.in(Degree), degree));
        motionMagicExpoVoltage.withPosition(degree);
    }

    public Command setDegreeCommand(double degree) {
        return Commands.runOnce(() -> setDegree(degree));
    }

    public void setMotorPos(double degree) {
        subsystemMotor.setPosition(degree);
    }

    public Command setMotorPosCommand(double degree) {
        return Commands.runOnce(() -> setMotorPos(degree));
    }

    public Command defualtCommand() {
        return setDegreeCommand(state.getTarget());
    }
}
