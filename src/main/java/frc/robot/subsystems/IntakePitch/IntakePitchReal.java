// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.IntakePitch;

import static edu.wpi.first.units.Units.Degree;
import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.DegreesPerSecond;
import static edu.wpi.first.units.Units.DegreesPerSecondPerSecond;
import static edu.wpi.first.units.Units.Rotation;
import static edu.wpi.first.units.Units.RotationsPerSecond;
import static edu.wpi.first.units.Units.RotationsPerSecondPerSecond;

import org.littletonrobotics.junction.Logger;

import com.ctre.phoenix6.CANBus;
import com.ctre.phoenix6.StatusCode;
import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.HardwareLimitSwitchConfigs;
import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.SoftwareLimitSwitchConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.ForwardLimitSourceValue;
import com.ctre.phoenix6.signals.ForwardLimitTypeValue;
import com.ctre.phoenix6.signals.GravityTypeValue;
import com.ctre.phoenix6.signals.StaticFeedforwardSignValue;

import edu.wpi.first.math.util.Units;
import frc.lib.Loggers.TalonFXLogger;
import frc.robot.subsystems.Shooter.ShooterConstants;

/** Add your docs here. */
public class IntakePitchReal implements IntakePitchIO{
    private TalonFXLogger m_intakePitch = new TalonFXLogger(IntakePitchConstants.m_MotorId, new CANBus(IntakePitchConstants.m_CanBusName),"IntakePitch");
    private MotionMagicVoltage motionMagicVoltage = new MotionMagicVoltage(0).withEnableFOC(true);
    public IntakePitchReal(){
        m_intakePitch.setPosition(0);
        TalonFXConfiguration talonFXConfiguration = new TalonFXConfiguration();
        FeedbackConfigs feedbackConfigs = talonFXConfiguration.Feedback;
        feedbackConfigs.SensorToMechanismRatio = IntakePitchConstants.POSITION_CONVERSION_FACTOR;
        MotorOutputConfigs motorOutputConfigs = talonFXConfiguration.MotorOutput;
        motorOutputConfigs.NeutralMode = IntakePitchConstants.NeutralMode;
        motorOutputConfigs.Inverted = IntakePitchConstants.Invetrted;
        SoftwareLimitSwitchConfigs softwareLimitSwitchConfigs = talonFXConfiguration.SoftwareLimitSwitch;
        softwareLimitSwitchConfigs.ForwardSoftLimitEnable = true;
        softwareLimitSwitchConfigs.ReverseSoftLimitEnable = true;
        softwareLimitSwitchConfigs.ForwardSoftLimitThreshold = IntakePitchConstants.maxAngleDegree / 360;
        softwareLimitSwitchConfigs.ReverseSoftLimitThreshold = IntakePitchConstants.minAngleDegree / 360;
        MotionMagicConfigs motionMagicConfigs = talonFXConfiguration.MotionMagic;
        motionMagicConfigs.MotionMagicCruiseVelocity =
                IntakePitchConstants.MotionMagicConstants.MOTION_MAGIC_VELOCITY;
        motionMagicConfigs.MotionMagicAcceleration =
                IntakePitchConstants.MotionMagicConstants.MOTION_MAGIC_ACCELERATION;
        motionMagicConfigs.MotionMagicJerk = IntakePitchConstants.MotionMagicConstants.MOTION_MAGIC_JERK;

        Slot0Configs slot0 = talonFXConfiguration.Slot0;
        slot0.kS = IntakePitchConstants.MotionMagicConstants.MOTOR_KS.get();
        slot0.kG = IntakePitchConstants.MotionMagicConstants.MOTOR_KG.get();
        slot0.kV = IntakePitchConstants.MotionMagicConstants.MOTOR_KV.get();
        slot0.kA = IntakePitchConstants.MotionMagicConstants.MOTOR_KA.get();
        slot0.kP = IntakePitchConstants.MotionMagicConstants.MOTOR_KP.get();
        slot0.kI = IntakePitchConstants.MotionMagicConstants.MOTOR_KI.get();
        slot0.kD = IntakePitchConstants.MotionMagicConstants.MOTOR_KD.get();
        slot0.GravityType = IntakePitchConstants.MotionMagicConstants.GravityType;

        StatusCode status = StatusCode.StatusCodeNotInitialized;
        for (int i = 0; i < 5; ++i) {
            status = m_intakePitch.getConfigurator().apply(talonFXConfiguration);
            if (status.isOK()) break;
        }
        if (!status.isOK()) {
            System.out.println("Could not configure device. Error: " + status.toString());
        }
        m_intakePitch.setPosition(0);
    }
    @Override
    public void setSpeed(double dutyCycle){
        m_intakePitch.set(dutyCycle);
    }
    @Override
    public void goToRotation(double rotation){
        m_intakePitch.setControl(motionMagicVoltage.withPosition(rotation).withSlot(0).withEnableFOC(true));
    }
    @Override
    public void setPos(double pos){
        m_intakePitch.setPosition(pos);
    }
    @Override
    public void UpdateInputs(IntakePitchIOInputs inputs){
        inputs.isConncted = m_intakePitch.isConnected();
        inputs.position = m_intakePitch.getPosition().getValue();
        inputs.velocity = m_intakePitch.getVelocity().getValue();
        inputs.acc = m_intakePitch.getAcceleration().getValue();
        inputs.voltage = m_intakePitch.getMotorVoltage().getValue();
    }
    @Override
    public void apliePIDF(){
        Slot0Configs slot0 = new Slot0Configs();
        slot0.kS = IntakePitchConstants.MotionMagicConstants.MOTOR_KS.get();
        slot0.kG = IntakePitchConstants.MotionMagicConstants.MOTOR_KG.get();
        slot0.kV = IntakePitchConstants.MotionMagicConstants.MOTOR_KV.get();
        slot0.kA = IntakePitchConstants.MotionMagicConstants.MOTOR_KA.get();
        slot0.kP = IntakePitchConstants.MotionMagicConstants.MOTOR_KP.get();
        slot0.kI = IntakePitchConstants.MotionMagicConstants.MOTOR_KI.get();
        slot0.kD = IntakePitchConstants.MotionMagicConstants.MOTOR_KD.get();
        slot0.GravityType = GravityTypeValue.Arm_Cosine;
        slot0.StaticFeedforwardSign = StaticFeedforwardSignValue.UseClosedLoopSign;
        m_intakePitch.getConfigurator().apply(
            slot0
        );
    }
    @Override
    public void setVoltage(double volts){
        m_intakePitch.setVoltage(volts);
    };

}
