// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Indexer;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Robotstate;

public class Indexer extends SubsystemBase {
  private IndexerIO io;
  private IndexerIOInputsAutoLogged inputs = new IndexerIOInputsAutoLogged();
  private static Indexer instance;
  /** Creates a new Indixer. */
  public Indexer(IndexerIO io) {
    this.io = io;
    io.updateInputs(inputs);
  }
  public static Indexer getInstance(){
    if (instance == null) {
      switch (Constants.currentMode) {
        case REAL:
          instance = new Indexer(new IndexerIOReal());
          break;
        case SIM:
          instance = new Indexer(new IndexerIOSim());
        break;
        default:
          instance = new Indexer(new IndexerIO() {});
          break;
      }
    }
    return instance;
  }

  @Override
  public void periodic() {
    io.updateInputs(inputs);
    Logger.processInputs("Indexer", inputs);
  }
  public void setVoltage(Voltage voltage){
    io.setVoltage(voltage);
  }
  public Command setVoltageCommand(Voltage voltage){
    return Commands.runOnce(()-> setVoltage(voltage));
  }
  public void setState(IndexerState state){
    Robotstate.indexerState = state;
  }
  public Command setStaetCommand(IndexerState state){
    return Commands.runOnce(()-> setState(state));
  }
  public Angle getAngle(){
    return inputs.pos;
  }
  public AngularVelocity getVelocity(){
    return inputs.velocity;
  }
  public void setVelocity(AngularVelocity Velocity){
    io.setvelocity(Velocity);
  }
  public Command setVelocityCommand(AngularVelocity velocity){
    return Commands.runOnce(()-> setVelocity(velocity));
  }
}
