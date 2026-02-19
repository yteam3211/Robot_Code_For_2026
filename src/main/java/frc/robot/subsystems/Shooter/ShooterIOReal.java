// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Shooter;

import com.ctre.phoenix6.StatusCode;
import com.ctre.phoenix6.configs.ClosedLoopGeneralConfigs;
import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.configs.VoltageConfigs;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.GainSchedKpBehaviorValue;
import com.ctre.phoenix6.signals.MotorAlignmentValue;

import edu.wpi.first.wpilibj.DigitalInput;
import frc.lib.Loggers.TalonFXLogger;

/** Add your docs here. */
public class ShooterIOReal implements ShooterIO{
    private TalonFXLogger m_master = new TalonFXLogger(ShooterConstants.m_MasterR_ID, ShooterConstants.m_canbus, "Shooter/MasterR");
    private TalonFXLogger m_SlaveR = new TalonFXLogger(ShooterConstants.m_SlaveR_ID, ShooterConstants.m_canbus, "Shooter/SlaveR");
    private TalonFXLogger m_SlaveL1 = new TalonFXLogger(ShooterConstants.m_SlaveL1_ID, ShooterConstants.m_canbus, "Shooter/SlaveL1");
    private TalonFXLogger m_SlaveL2 = new TalonFXLogger(ShooterConstants.m_SlaveL2_ID, ShooterConstants.m_canbus, "Shooter/SlaveL2");
    private DigitalInput m_Beam = new DigitalInput(9);
    private VelocityVoltage velocityVoltage = new VelocityVoltage(0).withSlot(0).withEnableFOC(true);
    public ShooterIOReal(){
        TalonFXConfiguration talonFXConfiguration = new TalonFXConfiguration();
        FeedbackConfigs feedbackConfigsspin = talonFXConfiguration.Feedback;
        feedbackConfigsspin.SensorToMechanismRatio = ShooterConstants.POSITION_CONVERSION_FACTOR;
        MotorOutputConfigs motorOutputConfigs = talonFXConfiguration.MotorOutput;
        motorOutputConfigs.NeutralMode = ShooterConstants.NeutralMode;
        motorOutputConfigs.Inverted = ShooterConstants.Inverted;
        CurrentLimitsConfigs currentLimitsConfigs = talonFXConfiguration.CurrentLimits;
        currentLimitsConfigs.StatorCurrentLimitEnable = true;
        currentLimitsConfigs.StatorCurrentLimit = 40;
        currentLimitsConfigs.SupplyCurrentLimitEnable = true;
        currentLimitsConfigs.SupplyCurrentLimit = 20;
        VoltageConfigs voltageConfigs = talonFXConfiguration.Voltage;
        voltageConfigs.PeakForwardVoltage = 12;
        voltageConfigs.PeakReverseVoltage = -12; 
        MotionMagicConfigs motionMagicConfigs = talonFXConfiguration.MotionMagic;
        motionMagicConfigs.MotionMagicCruiseVelocity =
                ShooterConstants.MotionMagicConstants.MOTION_MAGIC_VELOCITY;
        motionMagicConfigs.MotionMagicAcceleration =
                ShooterConstants.MotionMagicConstants.MOTION_MAGIC_ACCELERATION;
        motionMagicConfigs.MotionMagicJerk = ShooterConstants.MotionMagicConstants.MOTION_MAGIC_JERK;

        Slot0Configs slot0 = talonFXConfiguration.Slot0;
        slot0.kS = ShooterConstants.MotionMagicConstants.Slot0_MOTOR_KS;
        slot0.kG = ShooterConstants.MotionMagicConstants.Slot0_MOTOR_KG;
        slot0.kV = ShooterConstants.MotionMagicConstants.Slot0_MOTOR_KV;
        slot0.kA = ShooterConstants.MotionMagicConstants.Slot0_MOTOR_KA;
        slot0.kP = ShooterConstants.MotionMagicConstants.Slot0_MOTOR_KP;
        slot0.kI = ShooterConstants.MotionMagicConstants.Slot0_MOTOR_KI;
        slot0.kD = ShooterConstants.MotionMagicConstants.Slot0_MOTOR_KD;
        slot0.GravityType = ShooterConstants.MotionMagicConstants.GravityType;
        StatusCode status = StatusCode.StatusCodeNotInitialized;
        for (int i = 0; i < 5; ++i) {
            status = m_master.getConfigurator().apply(talonFXConfiguration);
            if (status.isOK()) break;
        }
        if (!status.isOK()) {
            System.out.println("Could not configure device. Error: " + status.toString());
        }
        status = StatusCode.StatusCodeNotInitialized;
        for (int i = 0; i < 5; ++i) {
            status = m_SlaveR.getConfigurator().apply(talonFXConfiguration);
            if (status.isOK()) break;
        }
        if (!status.isOK()) {
            System.out.println("Could not configure device. Error: " + status.toString());
        }
        status = StatusCode.StatusCodeNotInitialized;
        for (int i = 0; i < 5; ++i) {
            status = m_SlaveL1.getConfigurator().apply(talonFXConfiguration);
            if (status.isOK()) break;
        }
        if (!status.isOK()) {
            System.out.println("Could not configure device. Error: " + status.toString());
        }
                status = StatusCode.StatusCodeNotInitialized;
        for (int i = 0; i < 5; ++i) {
            status = m_SlaveL2.getConfigurator().apply(talonFXConfiguration);
            if (status.isOK()) break;
        }
        if (!status.isOK()) {
            System.out.println("Could not configure device. Error: " + status.toString());
        }
        m_master.setControl(velocityVoltage);
        m_SlaveR.setControl(new Follower(m_master.getDeviceID(), MotorAlignmentValue.Aligned));
        m_SlaveL1.setControl(new Follower(m_master.getDeviceID(), MotorAlignmentValue.Opposed));
        m_SlaveL2.setControl(new Follower(m_master.getDeviceID(), MotorAlignmentValue.Opposed));
    }   
    @Override
    public void updateInputs(ShooterIOInputs inputs) {
        inputs.Voltage = m_master.getMotorVoltage().getValue();
        inputs.velocity = m_master.getVelocity().getValue();
        inputs.position = m_master.getPosition().getValue();
        inputs.haveFuel = m_Beam.get();
    }
    @Override
    public void setVelocity(double velRPM) {
        m_master.setControl(velocityVoltage.withVelocity(velRPM/60).withAcceleration((velRPM/60)* 5));
    }
    @Override
    public void setVoltage(double Voltage) {
        m_master.setControl(new VoltageOut(Voltage).withEnableFOC(true));
    }
    @Override
    public void apliePIDF(){
        Slot0Configs slot0 = new Slot0Configs();
        slot0.kS = ShooterConstants.MotionMagicConstants.Slot0_MOTOR_KS;
        slot0.kG = ShooterConstants.MotionMagicConstants.Slot0_MOTOR_KG;
        slot0.kV = ShooterConstants.MotionMagicConstants.Slot0_MOTOR_KV;
        slot0.kA = ShooterConstants.MotionMagicConstants.Slot0_MOTOR_KA;
        slot0.kP = ShooterConstants.MotionMagicConstants.Slot0_MOTOR_KP;
        slot0.kI = ShooterConstants.MotionMagicConstants.Slot0_MOTOR_KI;
        slot0.kD = ShooterConstants.MotionMagicConstants.Slot0_MOTOR_KD;
        slot0.GravityType = ShooterConstants.MotionMagicConstants.GravityType;
        m_master.getConfigurator().apply(slot0);
    }
}
