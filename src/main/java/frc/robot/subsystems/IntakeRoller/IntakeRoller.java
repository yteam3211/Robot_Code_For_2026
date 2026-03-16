// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.IntakeRoller;

import static edu.wpi.first.units.Units.Amps;

import org.littletonrobotics.junction.Logger;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robotstate;

public class IntakeRoller extends SubsystemBase {
  private TalonFX m_Roller = new TalonFX(IntakeRollerConstants.m_masterID, IntakeRollerConstants.m_canbus);
  private IntakeRollerInputsAutoLogged inputs = new IntakeRollerInputsAutoLogged();
  /** Creates a new IntakeRoller. */
  public IntakeRoller() {
    m_Roller.getConfigurator().apply(new MotorOutputConfigs()
    .withNeutralMode(IntakeRollerConstants.NeutralMode)
    .withInverted(IntakeRollerConstants.invertedValue));
    m_Roller.getConfigurator().apply(new CurrentLimitsConfigs()
    .withSupplyCurrentLimit(Amps.of(30)).withSupplyCurrentLimitEnable(true));
    // .withStatorCurrentLimit(Amps.of(15)).withStatorCurrentLimitEnable(true));
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
    inputs.position = m_Roller.getPosition().getValue();
  }
  public void SetVoltage(Voltage voltage){
    m_Roller.setControl(new VoltageOut(voltage).withEnableFOC(true));
  }
  public void setState(IntakeRollerState state){
    Robotstate.rollerState = state;
  }
  public Command setStateCommand(IntakeRollerState state){
    return Commands.runOnce(()-> setState(state));
  }
  public Angle getAngle(){
    return inputs.position;
  }
}
