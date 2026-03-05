// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.IntakePitch;

import static edu.wpi.first.units.Units.Meter;
import static edu.wpi.first.units.Units.Meters;
import static edu.wpi.first.units.Units.Millimeter;
import static edu.wpi.first.units.Units.Second;
import static edu.wpi.first.units.Units.Volts;

import org.ironmaple.simulation.IntakeSimulation;
import org.ironmaple.simulation.IntakeSimulation.IntakeSide;
import org.littletonrobotics.junction.Logger;

import com.ctre.phoenix6.signals.StatusLedWhenActiveValue;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;
import frc.robot.Constants;
import frc.robot.Robotstate;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.drive.Drive;

public class IntakePitch extends SubsystemBase {
  private final IntakePitchIO io;
  private IntakePitchIOInputsAutoLogged inputs = new IntakePitchIOInputsAutoLogged();
  private SysIdRoutine 
  sysid;
  private static IntakePitch instance;
  private static IntakeSimulation intakeSimulation;
  /** Creates a new IntakePitch. */
  public IntakePitch(IntakePitchIO io) {
    super("IntakePitch");
    this.io = io;
    sysid = new SysIdRoutine( 
      new SysIdRoutine.Config(Volts.of(3).per(Second), Volts.of(8), Second.of(2), 
      (state)-> Logger.recordOutput("intakePitch/sydid",state.toString())),
      new SysIdRoutine.Mechanism((volts)-> io.setVoltage(volts), null, this, "intakePitch/sysid"));
  }

  public static IntakePitch getInstance(){
    if (instance == null) {
      switch (Constants.currentMode) {
        case REAL:
          instance = new IntakePitch(new IntakePitchReal());
          break;
        case SIM:
          instance = new IntakePitch(new IntakePitchSim(getIntakeSimulation()));
          break;
        
        default:
          instance = new IntakePitch(new IntakePitchIO() {});
          break;
      }
    }
    return instance;
  }
          
  public static IntakeSimulation getIntakeSimulation() {
    if (intakeSimulation == null) {
      intakeSimulation = IntakeSimulation.OverTheBumperIntake(
        "Fuel",
        Drive.getSwerveDriveSim(), 
        Meters.of(TunerConstants.FrontLeft.LocationY), 
        Millimeter.of(269.11), 
        IntakeSide.FRONT, 
        80);
      intakeSimulation.register();
    }
    return intakeSimulation;
  }

  @Override
  public void periodic() {
    io.UpdateInputs(inputs);
    Logger.processInputs("IntakePitch", inputs);
    if (inputs.FullyOpen) {
      io.setPos(IntakePitchConstants.maxAngleDegree);
    }
  }
  public void goToAnlge(Angle angle){
    Logger.recordOutput("IntakePitch/AngleToGo", angle);
    io.goToRotation(angle);
  }
  public Command goToAnlgeCommand(Angle angle){
    return Commands.runOnce(()-> goToAnlge(angle));
  }
  public void setPos(Angle degree){
    io.setPos(degree);
  }
  public Command setPosCommand(Angle angle){
    return Commands.runOnce(()-> setPos(angle));
  }
  public Command setVoltage(double volts){
    return Commands.runOnce(()-> io.setVoltage(Volts.of(volts)));
  }
  public void setState(IntakePitchState state){
    Robotstate.intakePitchState = state;
  }
  public Command setStateCommand(IntakePitchState state){
    return Commands.runOnce(()-> setState(state));
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
}
