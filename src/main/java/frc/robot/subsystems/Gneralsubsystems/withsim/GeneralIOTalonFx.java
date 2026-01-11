// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Gneralsubsystems.withsim;

import com.ctre.phoenix6.StatusCode;
import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicExpoTorqueCurrentFOC;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.wpilibj.Alert;

/** Add your docs here. */
public class GeneralIOTalonFx implements GeneralIO {
    private TalonFX subsystemMotor = new TalonFX(GeneralWithSimConstants.m_MotorId);
    private MotionMagicExpoTorqueCurrentFOC motionMagicExpoVoltage = new MotionMagicExpoTorqueCurrentFOC(0);

    public GeneralIOTalonFx() {
        TalonFXConfiguration talonFXConfiguration = new TalonFXConfiguration();
        FeedbackConfigs feedbackConfigsspin = talonFXConfiguration.Feedback;
        feedbackConfigsspin.SensorToMechanismRatio = GeneralWithSimConstants.isArm
                ? GeneralWithSimConstants.rotaion_Sim_Constants.POSITION_CONVERSION_FACTOR
                : GeneralWithSimConstants.Elevator_Sim_Constants.POSITION_CONVERSION_FACTOR;
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
        subsystemMotor.setControl(motionMagicExpoVoltage
                .withPosition(GeneralWithSimConstants.rotaion_Sim_Constants.startingAngle)
                .withSlot(0));
    }

    @Override
    public void updateInputs(GeneralInputs inputs) {
        inputs.MotorConncted = subsystemMotor.isConnected();
        inputs.rotaion = subsystemMotor.getPosition().getValue();
        inputs.speed = subsystemMotor.getVelocity().getValue();
    }

    @Override
    public void setPosition(Angle angle) {
        motionMagicExpoVoltage.withPosition(angle);
    }

    @Override
    public void setVoltz(double Vlotz) {}

    @Override
    public void setMotorAngle(Angle angle) {
        subsystemMotor.setPosition(angle);
    }

    private Alert call_Sim_Func_While_Real = new Alert("call sim func while real", Alert.AlertType.kInfo);

    @Override
    public void updateSim() {
        call_Sim_Func_While_Real.set(true);
    }
}
