// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Indexer;

import static edu.wpi.first.units.Units.RotationsPerSecond;

import org.littletonrobotics.junction.Logger;

import com.ctre.phoenix6.StatusCode;
import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVelocityVoltage;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Voltage;

/** Add your docs here. */
public class IndexerIOReal implements IndexerIO{
    private TalonFX m_indexer;
    private MotionMagicVelocityVoltage motionMagicVelocityVoltage = new MotionMagicVelocityVoltage(0).withSlot(0).withEnableFOC(true);
    public IndexerIOReal(){
        m_indexer = new TalonFX(IndexerConstants.m_indexerID, IndexerConstants.m_canbus);
            TalonFXConfiguration talonFXConfiguration = new TalonFXConfiguration();
        FeedbackConfigs feedbackConfigsspin = talonFXConfiguration.Feedback;
        feedbackConfigsspin.SensorToMechanismRatio = IndexerConstants.POSITION_CONVERSION_FACTOR;
        MotorOutputConfigs motorOutputConfigs = talonFXConfiguration.MotorOutput;
        motorOutputConfigs.NeutralMode = IndexerConstants.NeutralMode;
        motorOutputConfigs.Inverted = IndexerConstants.Inverted;
        CurrentLimitsConfigs currentLimitsConfigs = talonFXConfiguration.CurrentLimits;
        currentLimitsConfigs.StatorCurrentLimitEnable = false;
        currentLimitsConfigs.SupplyCurrentLimitEnable = true;
        currentLimitsConfigs.SupplyCurrentLimit = 30;
        MotionMagicConfigs motionMagicConfigs = talonFXConfiguration.MotionMagic;
        motionMagicConfigs.MotionMagicCruiseVelocity =
                IndexerConstants.MotionMagicConstants.MOTION_MAGIC_VELOCITY;
        motionMagicConfigs.MotionMagicAcceleration =
                IndexerConstants.MotionMagicConstants.MOTION_MAGIC_ACCELERATION;
        motionMagicConfigs.MotionMagicJerk = IndexerConstants.MotionMagicConstants.MOTION_MAGIC_JERK;

        Slot0Configs slot0 = talonFXConfiguration.Slot0;
        slot0.kS = IndexerConstants.MotionMagicConstants.MOTOR_KS;
        slot0.kG = IndexerConstants.MotionMagicConstants.MOTOR_KG;
        slot0.kV = IndexerConstants.MotionMagicConstants.MOTOR_KV;
        slot0.kA = IndexerConstants.MotionMagicConstants.MOTOR_KA;
        slot0.kP = IndexerConstants.MotionMagicConstants.MOTOR_KP;
        slot0.kI = IndexerConstants.MotionMagicConstants.MOTOR_KI;
        slot0.kD = IndexerConstants.MotionMagicConstants.MOTOR_KD;
        slot0.GravityType = IndexerConstants.MotionMagicConstants.GravityType;
        StatusCode status = StatusCode.StatusCodeNotInitialized;
        for (int i = 0; i < 5; ++i) {
            status = m_indexer.getConfigurator().apply(talonFXConfiguration);
            if (status.isOK()) break;
        }
        if (!status.isOK()) {
            System.out.println("Could not configure device. Error: " + status.toString());
        }
    }
    @Override
    public void updateInputs(IndexerIOInputs inputs) {
        inputs.isConnected = m_indexer.isConnected();
        inputs.pos = m_indexer.getPosition().getValue();
        inputs.velocity = m_indexer.getVelocity().getValue();
        inputs.volts = m_indexer.getMotorVoltage().getValue();
    }

    @Override
    public void setVoltage(Voltage voltage) {
        m_indexer.setControl(new VoltageOut(voltage).withEnableFOC(true));
    }
    @Override
    public void setvelocity(AngularVelocity velocity){
    if (velocity.isEquivalent(RotationsPerSecond.of(0))) {
      m_indexer.setVoltage(0);
    }
    else{
    m_indexer.setControl(motionMagicVelocityVoltage.withVelocity(velocity).withSlot(0));
    AngularVelocity req = velocity;
    Logger.recordOutput("Indexer/RPMreq", req);
        }
    }
}
