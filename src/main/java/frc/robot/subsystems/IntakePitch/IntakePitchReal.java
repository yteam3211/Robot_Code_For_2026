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
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.util.Units;

/** Add your docs here. */
public class IntakePitchReal implements IntakePitchIO{
    private TalonFX m_intakePitch = new TalonFX(IntakePitchConstants.m_MotorId, new CANBus(IntakePitchConstants.m_CanBusName));
    private MotionMagicVoltage motionMagicVoltage = new MotionMagicVoltage(0).withEnableFOC(true);
    public IntakePitchReal(){
        TalonFXConfiguration talonFXConfiguration = new TalonFXConfiguration();
        FeedbackConfigs feedbackConfigsspin = talonFXConfiguration.Feedback;
        feedbackConfigsspin.SensorToMechanismRatio = IntakePitchConstants.POSITION_CONVERSION_FACTOR;
        MotorOutputConfigs motorOutputConfigs = talonFXConfiguration.MotorOutput;
        motorOutputConfigs.NeutralMode = IntakePitchConstants.NeutralMode;
        MotionMagicConfigs motionMagicConfigs = talonFXConfiguration.MotionMagic;
        motionMagicConfigs.MotionMagicCruiseVelocity =
                IntakePitchConstants.MotionMagicConstants.MOTION_MAGIC_VELOCITY;
        motionMagicConfigs.MotionMagicAcceleration =
                IntakePitchConstants.MotionMagicConstants.MOTION_MAGIC_ACCELERATION;
        motionMagicConfigs.MotionMagicJerk = IntakePitchConstants.MotionMagicConstants.MOTION_MAGIC_JERK;

        Slot0Configs slot0 = talonFXConfiguration.Slot0;
        slot0.kS = IntakePitchConstants.MotionMagicConstants.MOTOR_KS;
        slot0.kG = IntakePitchConstants.MotionMagicConstants.MOTOR_KG;
        slot0.kV = IntakePitchConstants.MotionMagicConstants.MOTOR_KV;
        slot0.kA = IntakePitchConstants.MotionMagicConstants.MOTOR_KA;
        slot0.kP = IntakePitchConstants.MotionMagicConstants.MOTOR_KP;
        slot0.kI = IntakePitchConstants.MotionMagicConstants.MOTOR_KI;
        slot0.kD = IntakePitchConstants.MotionMagicConstants.MOTOR_KD;
        slot0.GravityType = IntakePitchConstants.MotionMagicConstants.GravityType;

        StatusCode status = StatusCode.StatusCodeNotInitialized;
        for (int i = 0; i < 5; ++i) {
            status = m_intakePitch.getConfigurator().apply(talonFXConfiguration);
            if (status.isOK()) break;
        }
        if (!status.isOK()) {
            System.out.println("Could not configure device. Error: " + status.toString());
        }
    }
    @Override
    public void setSpeed(double dutyCycle){
        m_intakePitch.set(dutyCycle);
    }
    @Override
    public void goToDegree(double degree){
        m_intakePitch.setControl(motionMagicVoltage.withPosition(Units.degreesToRotations(degree)));
    }
    @Override
    public void setPos(double pos){
        m_intakePitch.setPosition(pos);
    }
    @Override
    public void UpdateInputs(IntakePitchIOInputs inputs){
        inputs.isConncted = true;
        inputs.position = Units.rotationsToDegrees(m_intakePitch.getPosition().getValueAsDouble());
        inputs.velocity = Units.rotationsToDegrees(m_intakePitch.getVelocity().getValueAsDouble());
        inputs.acc = Units.rotationsToDegrees(m_intakePitch.getAcceleration().getValueAsDouble());
    }
}
