// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.IntakePitch;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.SubsystemState;
import frc.robot.subsystems.IntakePitch.IntakePitchIO.IntakePitchIOInputs;

public class IntakePitch extends SubsystemBase {
  private final IntakePitchIO io;
  private IntakePitchIOInputs inputs = new IntakePitchIOInputs();
  /** Creates a new IntakePitch. */
  public IntakePitch(IntakePitchIO io) {
    this.io = io;
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
  public void goToPos(double degree){
    io.setPos(degree);
  }
  public Command goToPosCommand(double degree){
    return Commands.runOnce(()-> goToPos(degree));
  }
  public void setPos(double degree){
    io.setPos(degree);
  }
  public Command setPosCommand(double degree){
    return Commands.runOnce(()-> setPos(degree));
  }
}
