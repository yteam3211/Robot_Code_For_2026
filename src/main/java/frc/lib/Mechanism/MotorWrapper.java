// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.lib.Mechanism;

import java.util.logging.Logger;

import com.ctre.phoenix6.StatusCode;
import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.ControlRequest;
import com.ctre.phoenix6.hardware.TalonFX;

/** Add your docs here. */
public class MotorWrapper {
    private TalonFX Motor;
    private ControlRequest controlRequest;
    private String MotorName;
    public MotorWrapper(MotorConfig config){
        this.Motor = new TalonFX(config.getId(),config.getCanBusName());
        this.MotorName = config.getMotorName();
        TalonFXConfiguration talonFXConfiguration = new TalonFXConfiguration();
        FeedbackConfigs feedbackConfigs = talonFXConfiguration.Feedback;
        feedbackConfigs.SensorToMechanismRatio = config.getSensorToMechanismRatio();
        feedbackConfigs.RotorToSensorRatio = config.getMotorToSensorRatio();
        MotorOutputConfigs motorOutputConfigs = talonFXConfiguration.MotorOutput;
        motorOutputConfigs.NeutralMode = config.getNeutralMode();
        MotionMagicConfigs motionMagicConfigs = talonFXConfiguration.MotionMagic;
        motionMagicConfigs.MotionMagicCruiseVelocity = config.getCruiseVelocity();
        motionMagicConfigs.MotionMagicAcceleration = config.getAcceleration();
        motionMagicConfigs.MotionMagicJerk = config.getJerk();

        Slot0Configs Slot0 = talonFXConfiguration.Slot0;
        Slot0.kP = config.getKP();
        Slot0.kI = config.getKI();
        Slot0.kD = config.getKD();
        Slot0.kV = config.getKV();
        Slot0.kA = config.getKA();
        Slot0.kS = config.getKS();
        Slot0.kG = config.getKG();
        Slot0.GravityType = config.getGravityType();
        Slot0.StaticFeedforwardSign = config.getStaticFeedforwardSign();
        StatusCode status = StatusCode.StatusCodeNotInitialized;
        for (int i = 0; i < 5; ++i) {
            status = Motor.getConfigurator().apply(talonFXConfiguration);
            if (status.isOK()) break;
        }
        if (!status.isOK()) {
            System.out.println("Could not configure " + config.getMotorName() + ". Error: " + status.toString());
        }
    }
}
