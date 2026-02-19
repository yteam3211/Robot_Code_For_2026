// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.IntakePitch;

import static edu.wpi.first.units.Units.Degree;
import static edu.wpi.first.units.Units.DegreesPerSecond;
import static edu.wpi.first.units.Units.DegreesPerSecondPerSecond;

import com.ctre.phoenix6.CANBus;
import com.ctre.phoenix6.StatusCode;
import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.lib.Loggers.TalonFXLogger;

/** Add your docs here. */
public class IntakePitchSim implements IntakePitchIO{
    private TalonFXLogger m_intakePitch = new TalonFXLogger(IntakePitchConstants.m_MotorId, new CANBus(IntakePitchConstants.m_CanBusName),"IntakePitch");
    private MotionMagicVoltage motionMagicVoltage = new MotionMagicVoltage(0).withEnableFOC(true);
    private SingleJointedArmSim ArmSim;
    private Trigger simTrigger;
    public IntakePitchSim(){
        ArmSim = new SingleJointedArmSim(IntakePitchConstants.dcMotor, IntakePitchConstants.gearRatio, 
        IntakePitchConstants.JKgMeterSqured, IntakePitchConstants.lengthMeters, 
        Units.degreesToRadians(IntakePitchConstants.minAngleDegree), 
        Units.degreesToRadians(IntakePitchConstants.maxAngleDegree), true, 
        Units.degreesToRadians(IntakePitchConstants.startingAngle));
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
            // simTrigger = new Trigger(()-> m_intakePitch.getPosition().getValueAsDouble()
            // > Units.degreesToRotations(IntakePitchConstants.maxAngleDegree - 5));
            // simTrigger.whileTrue(Commands.runOnce(intakeSimulation::startIntake));
            // simTrigger.whileFalse(Commands.runOnce(intakeSimulation::stopIntake));
    }
    @Override
    public void setSpeed(double dutyCycle){
        m_intakePitch.set(dutyCycle);
    }
    @Override
    public void goToRotation(double Rotation){
        m_intakePitch.setControl(motionMagicVoltage.withPosition(Rotation));
    }
    @Override
    public void UpdateInputs(IntakePitchIOInputs inputs){
        updateSim();
        
        inputs.isConncted = true;
        inputs.position = m_intakePitch.getPosition().getValue();
        inputs.velocity = m_intakePitch.getVelocity().getValue();
        inputs.acc =m_intakePitch.getAcceleration().getValue();
    }
    private void updateSim(){
        ArmSim.setInputVoltage(m_intakePitch.getSimState().getMotorVoltage());
        ArmSim.update(0.02);
        m_intakePitch.getSimState().setRawRotorPosition(Units.radiansToRotations(ArmSim.getAngleRads()) * IntakePitchConstants.POSITION_CONVERSION_FACTOR);
        m_intakePitch.getSimState().setRotorVelocity(Units.radiansToRotations(ArmSim.getVelocityRadPerSec()) * IntakePitchConstants.POSITION_CONVERSION_FACTOR);
    }
    @Override
    public void setPos(double pos) {
        m_intakePitch.setPosition(pos);
    }
}
