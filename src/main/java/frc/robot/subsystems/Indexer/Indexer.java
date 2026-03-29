// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Indexer;

import static edu.wpi.first.units.Units.RPM;
import static frc.lib.util.PhoenixUtil.tryUntilOk;

import org.littletonrobotics.junction.Logger;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;
import frc.robot.Robotstate;

public class Indexer extends SubsystemBase {
  private TalonFX m_Indexer;
  private IndexerInputsAutoLogged inputs = new IndexerInputsAutoLogged();
  private VelocityVoltage velocityVoltage = new VelocityVoltage(RPM.of(0));
  private static Indexer instance;
  private SysIdRoutine sysid = new SysIdRoutine(new SysIdRoutine.Config(null, null, null, (state)-> Logger.recordOutput("sysid/Indexer", state.toString())), 
  new SysIdRoutine.Mechanism((voltage)-> setVoltage(voltage), null, this, "Indexer"));
  /** Creates a new Indexer. */
  public Indexer() {
    m_Indexer = new TalonFX(IndexerConstants.m_IndexerID, IndexerConstants.m_canbus);
    
        TalonFXConfiguration talonFXConfiguration = new TalonFXConfiguration();
          FeedbackConfigs feedbackConfigsspin = talonFXConfiguration.Feedback;
          feedbackConfigsspin.SensorToMechanismRatio = IndexerConstants.POSITION_CONVERSION_FACTOR;
          MotorOutputConfigs motorOutputConfigs = talonFXConfiguration.MotorOutput;
          motorOutputConfigs.NeutralMode = IndexerConstants.NeutralMode;
          motorOutputConfigs.Inverted = IndexerConstants.Inverted;  
          CurrentLimitsConfigs currentLimitsConfigs = talonFXConfiguration.CurrentLimits;
          currentLimitsConfigs.StatorCurrentLimitEnable = true;
          currentLimitsConfigs.StatorCurrentLimit = 40;
          currentLimitsConfigs.SupplyCurrentLimitEnable = true;
          currentLimitsConfigs.SupplyCurrentLimit = 20;
          MotionMagicConfigs motionMagicConfigs = talonFXConfiguration.MotionMagic;
          motionMagicConfigs.MotionMagicCruiseVelocity =
                  IndexerConstants.MotionMagicConstants.MOTION_MAGIC_VELOCITY;
          motionMagicConfigs.MotionMagicAcceleration =
                  IndexerConstants.MotionMagicConstants.MOTION_MAGIC_ACCELERATION;
          motionMagicConfigs.MotionMagicJerk = IndexerConstants.MotionMagicConstants.MOTION_MAGIC_JERK;

          Slot0Configs slot0 = talonFXConfiguration.Slot0;
          slot0.kS = IndexerConstants.MotionMagicConstants.Slot0_MOTOR_KS;
          slot0.kG = IndexerConstants.MotionMagicConstants.Slot0_MOTOR_KG;
          slot0.kV = IndexerConstants.MotionMagicConstants.Slot0_MOTOR_KV;
          slot0.kA = IndexerConstants.MotionMagicConstants.Slot0_MOTOR_KA;
          slot0.kP = IndexerConstants.MotionMagicConstants.Slot0_MOTOR_KP;
          slot0.kI = IndexerConstants.MotionMagicConstants.Slot0_MOTOR_KI;
          slot0.kD = IndexerConstants.MotionMagicConstants.Slot0_MOTOR_KD;
          slot0.GravityType = IndexerConstants.MotionMagicConstants.GravityType;
          tryUntilOk(5, ()-> m_Indexer.getConfigurator().apply(talonFXConfiguration));          
          m_Indexer.setPosition(0);
  }
  public static Indexer getInstance(){
    if (instance == null) {
      instance = new Indexer();
    }
    return instance;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    updateInputs(inputs);
    Logger.processInputs("Indexer", inputs);
  }
  public void updateInputs(IndexerInputs inputs){
    inputs.position = m_Indexer.getPosition().getValue();
    inputs.voltage = m_Indexer.getMotorVoltage().getValue();
    inputs.velocity = m_Indexer.getVelocity().getValue();
  }
  public void setVoltage(Voltage voltage){
    m_Indexer.setControl(new VoltageOut(voltage).withEnableFOC(true));
  }
  public void setState(IndexerState state){
    Robotstate.indexerState = state;
  }
  public Command setStateCommand(IndexerState state){
    return Commands.runOnce(()-> setState(state));
  }
  public void setVelocity(AngularVelocity velocity){
    if (velocity.isEquivalent(RPM.of(0))) {
      m_Indexer.setVoltage(0);
    }
    else{
    m_Indexer.setControl(velocityVoltage.withVelocity(velocity).withEnableFOC(true));
    }
  }
  public Command setVelocityCommand(AngularVelocity velocity){
    return Commands.runOnce(()-> setVelocity(velocity));
  }
  public Command sysidDynamic(SysIdRoutine.Direction direction){
    return sysid.dynamic(direction);
  }
  public Command sysidQuasistatic(SysIdRoutine.Direction direction){
    return sysid.quasistatic(direction);
  }
}
