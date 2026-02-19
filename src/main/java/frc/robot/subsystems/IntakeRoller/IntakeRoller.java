// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.IntakeRoller;

import org.dyn4j.collision.narrowphase.Sat;
import org.littletonrobotics.junction.Logger;

import com.ctre.phoenix6.configs.CustomParamsConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.TorqueCurrentConfigs;
import com.ctre.phoenix6.configs.VoltageConfigs;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.Loggers.TalonFXLogger;
import frc.robot.SubsystemState;

public class IntakeRoller extends SubsystemBase {
  private TalonFXLogger m_Roller = new TalonFXLogger(IntakeRollerConstants.m_masterID, IntakeRollerConstants.m_canbus,"IntakeRoller");
  private IntakeRollerInputs inputs = new IntakeRollerInputs();
  /** Creates a new IntakeRoller. */
  public IntakeRoller() {
    m_Roller.getConfigurator().apply(new MotorOutputConfigs()
    .withNeutralMode(IntakeRollerConstants.NeutralMode));
  }

  private static IntakeRoller Instance;
  public static IntakeRoller getInstance(){
    if (Instance == null) {
      Instance = new IntakeRoller();
    }
    return Instance;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    updateInputs(inputs);
    Logger.processInputs("IntakeRoller", inputs);
  }
  private void updateInputs(IntakeRollerInputs inputs){
    inputs.acceleration = m_Roller.getAcceleration().getValue();
    inputs.velocity = m_Roller.getVelocity().getValue();
    inputs.isConnected = m_Roller.isConnected();
    inputs.voltage = m_Roller.getMotorVoltage().getValue();
  }
  public void SetVoltage(double voltage){
    m_Roller.setVoltage(voltage);
  }
  public void setState(IntakeRollerState state){
    SubsystemState.rollerState = state;
  }
  public Command setStateCommand(IntakeRollerState state){
    return Commands.runOnce(()-> setState(state));
  }
}
