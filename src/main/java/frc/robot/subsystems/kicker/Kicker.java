// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.kicker;

import static edu.wpi.first.units.Units.RotationsPerSecond;
import static edu.wpi.first.units.Units.Volts;

import org.littletonrobotics.junction.Logger;

import com.ctre.phoenix6.StatusCode;
import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;
import frc.robot.Robotstate;

public class Kicker extends SubsystemBase {
  private TalonFX m_kicker = new TalonFX(kickerConstants.m_kickerID, kickerConstants.m_canbus);
  private kickerIOinputsAutoLogged inputs = new kickerIOinputsAutoLogged();
  private MotionMagicVelocityVoltage motionMagicVelocityVoltage = new MotionMagicVelocityVoltage(0).withEnableFOC(true);
  private SysIdRoutine sysid = new SysIdRoutine(new SysIdRoutine.Config(null, null, null, (state)-> Logger.recordOutput("sysid/kicker", state.toString())), 
  new SysIdRoutine.Mechanism((voltage)-> setVoltage(voltage.in(Volts)), null, this, "kicker"));
  /** Creates a new kicker. */
  public Kicker() {
    TalonFXConfiguration talonFXConfiguration = new TalonFXConfiguration();
        FeedbackConfigs feedbackConfigsspin = talonFXConfiguration.Feedback;
        feedbackConfigsspin.SensorToMechanismRatio = kickerConstants.POSITION_CONVERSION_FACTOR;
        MotorOutputConfigs motorOutputConfigs = talonFXConfiguration.MotorOutput;
        motorOutputConfigs.NeutralMode = kickerConstants.NeutralMode;
        motorOutputConfigs.Inverted = kickerConstants.Inverted;
        CurrentLimitsConfigs currentLimitsConfigs = talonFXConfiguration.CurrentLimits;
        currentLimitsConfigs.StatorCurrentLimitEnable = false;
        currentLimitsConfigs.SupplyCurrentLimitEnable = true;
        currentLimitsConfigs.SupplyCurrentLimit = 30;
        MotionMagicConfigs motionMagicConfigs = talonFXConfiguration.MotionMagic;
        motionMagicConfigs.MotionMagicCruiseVelocity =
                kickerConstants.MotionMagicConstants.MOTION_MAGIC_VELOCITY;
        motionMagicConfigs.MotionMagicAcceleration =
                kickerConstants.MotionMagicConstants.MOTION_MAGIC_ACCELERATION;
        motionMagicConfigs.MotionMagicJerk = kickerConstants.MotionMagicConstants.MOTION_MAGIC_JERK;

        Slot0Configs slot0 = talonFXConfiguration.Slot0;
        slot0.kS = kickerConstants.MotionMagicConstants.Slot0_MOTOR_KS;
        slot0.kG = kickerConstants.MotionMagicConstants.Slot0_MOTOR_KG;
        slot0.kV = kickerConstants.MotionMagicConstants.Slot0_MOTOR_KV;
        slot0.kA = kickerConstants.MotionMagicConstants.Slot0_MOTOR_KA;
        slot0.kP = kickerConstants.MotionMagicConstants.Slot0_MOTOR_KP;
        slot0.kI = kickerConstants.MotionMagicConstants.Slot0_MOTOR_KI;
        slot0.kD = kickerConstants.MotionMagicConstants.Slot0_MOTOR_KD;
        slot0.GravityType = kickerConstants.MotionMagicConstants.GravityType;
        StatusCode status = StatusCode.StatusCodeNotInitialized;
        for (int i = 0; i < 5; ++i) {
            status = m_kicker.getConfigurator().apply(talonFXConfiguration);
            if (status.isOK()) break;
        }
        if (!status.isOK()) {
            System.out.println("Could not configure device. Error: " + status.toString());
        }
        m_kicker.setPosition(0);
  }

  private static Kicker Instance;
  public static Kicker getInstance(){
    if (Instance == null) {
      Instance = new Kicker();
    }
    return Instance;
  }
  @Override
  
  public void periodic() {
    updateInputs(inputs);
    Logger.processInputs("kicker", inputs);
    // This method will be called once per scheduler run
  }
  private void updateInputs(kickerIOinputs inputs){
    inputs.isConncted = m_kicker.isConnected();
    inputs.voltage = m_kicker.getMotorVoltage().getValue();
    inputs.velocity = m_kicker.getVelocity().getValue();
    inputs.acceleration = m_kicker.getAcceleration().getValue();
    inputs.postion = m_kicker.getPosition().getValue();
  }
  public void setVoltage(double voltage){
    m_kicker.setVoltage(voltage);
  }
  public Command setVoltageCommand(double volatge){
    return Commands.runOnce(()-> setVoltage(volatge));
  }
  public Command setVelocityCommand(AngularVelocity velocity){
    return Commands.runOnce(()-> setVelocity(velocity));
  }
  public void setVelocity(AngularVelocity velocity){
    if (velocity.in(RotationsPerSecond) == 0) {
      m_kicker.setVoltage(0);
    }
    else{
    m_kicker.setControl(motionMagicVelocityVoltage.withVelocity(velocity).withSlot(0));
    AngularVelocity req = velocity;
    Logger.recordOutput("kicker/RPMreq", req);
    }
  }
  public Command sysidDynamic(SysIdRoutine.Direction direction){
    return sysid.dynamic(direction);
  }
  public Command sysidQuasistatic(SysIdRoutine.Direction direction){
    return sysid.quasistatic(direction);
  }
  public void setState(KickerState state){
    Robotstate.kickerState = state;
  }
  public Command setStateCommand(KickerState state){
    return Commands.runOnce(()->setState(state));
  }
}
