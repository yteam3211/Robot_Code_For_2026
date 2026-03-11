// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.IntakePitch;

import com.ctre.phoenix6.CANBus;
import com.ctre.phoenix6.StatusCode;
import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.SoftwareLimitSwitchConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.configs.VoltageConfigs;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.signals.GravityTypeValue;
import com.ctre.phoenix6.signals.StaticFeedforwardSignValue;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.wpilibj.DigitalInput;
import frc.lib.Loggers.TalonFXLogger;

/** Add your docs here. */
public class IntakePitchReal implements IntakePitchIO{
    private TalonFXLogger m_intakePitch = new TalonFXLogger(IntakePitchConstants.m_MotorId, new CANBus(IntakePitchConstants.m_CanBusName),"IntakePitch");
    private DigitalInput m_limtMax = new DigitalInput(IntakePitchConstants.m_limitSwitch_max);
    private DigitalInput m_limtMin = new DigitalInput(IntakePitchConstants.m_limitSwitch_min);
    private MotionMagicVoltage motionMagicVoltage = new MotionMagicVoltage(0).withEnableFOC(false);
    public IntakePitchReal(){
        TalonFXConfiguration talonFXConfiguration = new TalonFXConfiguration();
        FeedbackConfigs feedbackConfigs = talonFXConfiguration.Feedback;
        feedbackConfigs.SensorToMechanismRatio = IntakePitchConstants.POSITION_CONVERSION_FACTOR;
        MotorOutputConfigs motorOutputConfigs = talonFXConfiguration.MotorOutput;
        motorOutputConfigs.NeutralMode = IntakePitchConstants.NeutralMode;
        motorOutputConfigs.Inverted = IntakePitchConstants.Invetrted;
        SoftwareLimitSwitchConfigs softwareLimitSwitchConfigs = talonFXConfiguration.SoftwareLimitSwitch;
        softwareLimitSwitchConfigs.ForwardSoftLimitEnable = false;
        softwareLimitSwitchConfigs.ReverseSoftLimitEnable = false;
        // softwareLimitSwitchConfigs.ForwardSoftLimitThreshold = IntakePitchConstants.maxAngleDegree.plus(Degree.of(10)).in(Rotation);
        // softwareLimitSwitchConfigs.ReverseSoftLimitThreshold = IntakePitchConstants.minAngleDegree.plus(Degree.of(-10)).in(Rotation);
        VoltageConfigs voltageConfigs = talonFXConfiguration.Voltage;
        voltageConfigs.PeakForwardVoltage = 12;
        voltageConfigs.PeakReverseVoltage = -12;
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
        m_intakePitch.setPosition(IntakePitchConstants.startingAngle);
    }
    @Override
    public void goToRotation(Angle rotation){
        m_intakePitch.setControl(motionMagicVoltage.withPosition(rotation).withSlot(0).withEnableFOC(false));
    }
    @Override
    public void setPos(Angle pos){
        m_intakePitch.setPosition(pos);
    }
    @Override
    public void UpdateInputs(IntakePitchIOInputs inputs){
        inputs.isConncted = m_intakePitch.isConnected();
        inputs.position = m_intakePitch.getPosition().getValue();
        inputs.velocity = m_intakePitch.getVelocity().getValue();
        inputs.acc = m_intakePitch.getAcceleration().getValue();
        inputs.voltage = m_intakePitch.getMotorVoltage().getValue();
        inputs.FullyClosed = m_limtMin.get();
        inputs.FullyOpen = m_limtMax.get();
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
    public void setVoltage(Voltage volts){
        m_intakePitch.setControl(new VoltageOut(volts).withEnableFOC(false));
    };
}
