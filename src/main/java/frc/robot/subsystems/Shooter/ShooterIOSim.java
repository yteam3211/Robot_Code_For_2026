// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Shooter;

import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.Millimeter;
import static edu.wpi.first.units.Units.RotationsPerSecond;
import static edu.wpi.first.units.Units.RotationsPerSecondPerSecond;

import org.ironmaple.simulation.SimulatedArena;
import org.ironmaple.simulation.seasonspecific.rebuilt2026.RebuiltFuelOnFly;
import org.littletonrobotics.junction.Logger;

import com.ctre.phoenix6.StatusCode;
import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVelocityTorqueCurrentFOC;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.wpilibj.simulation.FlywheelSim;
import frc.robot.Constants;
import frc.robot.Button.devButoon;
import frc.robot.subsystems.drive.Drive;

/** Add your docs here. */
public class ShooterIOSim implements ShooterIO{
    private FlywheelSim flywheelSim;
    private TalonFX m_master = new TalonFX(ShooterConstants.m_MasterR_ID, ShooterConstants.m_canbus);
    private MotionMagicVelocityTorqueCurrentFOC motionMagicVelocity = new MotionMagicVelocityTorqueCurrentFOC(0);
    public ShooterIOSim(){
        flywheelSim = new FlywheelSim(LinearSystemId.createFlywheelSystem(ShooterConstants.dcMotor, ShooterConstants.JKgMeterSqured, 
        ShooterConstants.gearRatio), ShooterConstants.dcMotor);
                TalonFXConfiguration talonFXConfiguration = new TalonFXConfiguration();
        FeedbackConfigs feedbackConfigsspin = talonFXConfiguration.Feedback;
        feedbackConfigsspin.SensorToMechanismRatio = ShooterConstants.POSITION_CONVERSION_FACTOR;
        MotorOutputConfigs motorOutputConfigs = talonFXConfiguration.MotorOutput;
        motorOutputConfigs.NeutralMode = ShooterConstants.NeutralMode;
        MotionMagicConfigs motionMagicConfigs = talonFXConfiguration.MotionMagic;
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
    }
    @Override
    public void updateInputs(ShooterIOInputs inputs) {
        updateSim();
        shootSim();
        inputs.Voltage = m_master.getMotorVoltage().getValue();
        inputs.velocity = m_master.getVelocity().getValue();
        inputs.position = m_master.getPosition().getValue();
    }
    private void updateSim(){
        flywheelSim.setInputVoltage(m_master.getSimState().getMotorVoltage());
        flywheelSim.update(0.02);

        m_master.getSimState().setRotorVelocity(flywheelSim.getAngularVelocity().in(RotationsPerSecond) 
        * ShooterConstants.POSITION_CONVERSION_FACTOR);
        m_master.getSimState().setRotorAcceleration(flywheelSim.getAngularAcceleration().in(RotationsPerSecondPerSecond)
        * ShooterConstants.POSITION_CONVERSION_FACTOR);
    }
    @Override
    public void setVelocity(AngularVelocity vel) {
        m_master.setControl(motionMagicVelocity.withVelocity(vel));
    }
    @Override
    public void setVoltage(Voltage voltage){
        m_master.setControl(new VoltageOut(voltage).withEnableFOC(true));
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
    private void shootSim(){
        
    }
}
