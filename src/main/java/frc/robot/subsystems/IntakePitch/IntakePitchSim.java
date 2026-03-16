// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.IntakePitch;

import static edu.wpi.first.units.Units.Degree;
import static edu.wpi.first.units.Units.KilogramSquareMeters;
import static edu.wpi.first.units.Units.Radian;
import static edu.wpi.first.units.Units.RadiansPerSecond;

import org.ironmaple.simulation.IntakeSimulation;

import com.ctre.phoenix6.CANBus;
import com.ctre.phoenix6.StatusCode;
import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;

/** Add your docs here. */
public class IntakePitchSim implements IntakePitchIO{
    private TalonFX m_intakePitch = new TalonFX(IntakePitchConstants.m_MotorId, new CANBus(IntakePitchConstants.m_CanBusName));
    private MotionMagicVoltage motionMagicVoltage = new MotionMagicVoltage(0).withEnableFOC(false);
    private SingleJointedArmSim ArmSim;
    private IntakeSimulation intakeSimulation;
    public IntakePitchSim(IntakeSimulation intakeSimulation){
        this.intakeSimulation = intakeSimulation;
        ArmSim = new SingleJointedArmSim(IntakePitchConstants.dcMotor, IntakePitchConstants.gearRatio, 
        IntakePitchConstants.INERTIA.in(KilogramSquareMeters), IntakePitchConstants.lengthMeters, 
        IntakePitchConstants.minAngleDegree.in(Radian), 
        IntakePitchConstants.maxAngleDegree.in(Radian), true, 
        IntakePitchConstants.startingAngle.in(Radian));
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
    }
    @Override
    public void goToRotation(Angle Rotation){
        m_intakePitch.setControl(motionMagicVoltage.withPosition(Rotation).withEnableFOC(false));
    }
    @Override
    public void UpdateInputs(IntakePitchIOInputs inputs){
        updateSim();
        inputs.isConncted = true;
        inputs.position = Radian.of(ArmSim.getAngleRads());
        inputs.velocity = RadiansPerSecond.of(ArmSim.getVelocityRadPerSec());
        inputs.voltage = m_intakePitch.getSimState().getMotorVoltageMeasure();
    }
    private void updateSim(){
        ArmSim.setInputVoltage(m_intakePitch.getSimState().getMotorVoltage());
        ArmSim.update(0.02);
        m_intakePitch.getSimState().setRawRotorPosition(Units.radiansToRotations(ArmSim.getAngleRads()) * IntakePitchConstants.POSITION_CONVERSION_FACTOR);
        if (m_intakePitch.getPosition().getValue().gte(IntakePitchConstants.maxAngleDegree.minus(Degree.of(10)))) {
            intakeSimulation.startIntake();
        }
        else{
            intakeSimulation.stopIntake();
        }
    }
    @Override
    public void setPos(Angle pos) {
        m_intakePitch.setPosition(pos);
    }
    @Override
    public void setVoltage(Voltage volts){
        m_intakePitch.setControl(new VoltageOut(volts).withEnableFOC(false));
    };
}
