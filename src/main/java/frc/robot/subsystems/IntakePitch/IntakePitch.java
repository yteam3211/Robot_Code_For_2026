// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.IntakePitch;

import static edu.wpi.first.units.Units.Second;
import static edu.wpi.first.units.Units.Volts;

import java.lang.annotation.Retention;
import java.util.stream.IntStream;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;
import frc.robot.Constants;
import frc.robot.SubsystemState;
import frc.robot.subsystems.IntakePitch.IntakePitchIO.IntakePitchIOInputs;

public class IntakePitch extends SubsystemBase {
  private final IntakePitchIO io;
  private IntakePitchIOInputs inputs = new IntakePitchIOInputs();
  private SysIdRoutine sysid;
  private static IntakePitch instance;
  /** Creates a new IntakePitch. */
  public IntakePitch(IntakePitchIO io) {
    super("IntakePitch");
    this.io = io;
    sysid = new SysIdRoutine(
      new SysIdRoutine.Config(Volts.of(1).per(Second), Volts.of(2), Second.of(2), (state)-> Logger.recordOutput("intakePitch",state.toString())),
      new SysIdRoutine.Mechanism((volts)-> io.setVoltage(volts.in(Volts)), null, this, "intakePitch/sysid"));
  }

  public static IntakePitch getInstance(){
    if (instance == null) {
      switch (Constants.currentMode) {
        case REAL:
          instance = new IntakePitch(new IntakePitchReal());
          break;
        case SIM:
          instance = new IntakePitch(new IntakePitchSim());
        break;
      
        default:
          instance = new IntakePitch(new IntakePitchIO() {});
          break;
      }
    }
    return instance;
  }

  @Override
  public void periodic() {
    io.UpdateInputs(inputs);
    Logger.processInputs("IntakePitch", inputs);
  }
  private void setState(IntakePitchState state){
      SubsystemState.intakePitchState = state;
  }
  public Command setStateCommand(IntakePitchState state){
    return Commands.runOnce(()-> setState(state));
  }
  public void goToAnlge(double degree){
    Logger.recordOutput("IntakePitch/degreeToGo", degree);
    io.goToRotation(degree / 360);
  }
  public Command goToAnlgeCommand(double degree){
    return Commands.runOnce(()-> goToAnlge(degree));
  }
  public void setPos(double degree){
    io.setPos(degree);
  }
  public Command setPosCommand(double degree){
    return Commands.runOnce(()-> setPos(degree));
  }
  public Command setVoltage(double volts){
    return Commands.runOnce(()-> io.setVoltage(volts));
  }
  public Angle getAngle(){
    return inputs.position; 
  }
  public Command apliePIDF(){
    return this.runOnce(()-> io.apliePIDF());
  }
  public Command sysidQuasistatic(SysIdRoutine.Direction direction){
    return sysid.quasistatic(direction);
  }
  public Command sysidDynamic(SysIdRoutine.Direction direction){
    return sysid.dynamic(direction);
  }
  public boolean fuel90(){
    return inputs.fuel90;
  }
}
