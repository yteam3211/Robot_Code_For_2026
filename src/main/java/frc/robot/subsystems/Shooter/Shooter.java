// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Shooter;

import static edu.wpi.first.units.Units.InchesPerSecond;
import static edu.wpi.first.units.Units.RPM;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.RotationsPerSecond;
import static edu.wpi.first.units.Units.Second;
import static edu.wpi.first.units.Units.Volts;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.interpolation.InterpolatingDoubleTreeMap;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.LinearVelocity;
import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;
import frc.robot.Constants;
import frc.robot.Robotstate;

public class Shooter extends SubsystemBase {
  /** Creates a new Shooter. */
  private ShooterIO io;
  private ShooterIOInputsAutoLogged inputs = new ShooterIOInputsAutoLogged();
  private AngularVelocity requireVelRPM = RPM.of(0);
  private Timer Fuel_Timer = new Timer();
  private boolean haveFuel;
  private SysIdRoutine sysid;
  private static Shooter instance;
  private InterpolatingDoubleTreeMap RpmFromDistance = new InterpolatingDoubleTreeMap();
  public Shooter(ShooterIO io) {
    this.io = io;
    io.updateInputs(inputs);
    sysid = new SysIdRoutine(
      new SysIdRoutine.Config(Volts.of(1.2).per(Second), Volts.of(8), Second.of(11), (state)-> Logger.recordOutput("sysid/shooter", state.toString())), 
      new SysIdRoutine.Mechanism(
        (volts)-> io.setVoltage(volts), null, this, "Shooter"));
      updateMAP();
  }
  private void updateMAP(){
    RpmFromDistance.put(2.0, 2100.0);
    RpmFromDistance.put(2.25, 2100.0);
    RpmFromDistance.put(2.5, 2150.0);
    RpmFromDistance.put(2.75, 2200.0);
    RpmFromDistance.put(3.0, 2300.0);
    RpmFromDistance.put(3.25, 2400.0);
    RpmFromDistance.put(3.5, 2500.0);
    RpmFromDistance.put(3.75, 2600.0);
  }

  public static Shooter getInstance(){
    if (instance == null) {
      switch (Constants.currentMode) {
        case REAL:
          instance = new Shooter(new ShooterIOReal());
          break;
        case SIM:
          instance = new Shooter(new ShooterIOSim());
        break;
        default:
          instance = new Shooter(new ShooterIO() {});
          break;
      }
    }
    return instance;
  }

  @Override
  public void periodic() {
    io.updateInputs(inputs);
    TimerCheckFuel();
    Logger.recordOutput("Shooter/require Velocity RPM", requireVelRPM);
    Logger.recordOutput("Shooter/is At Velocity", isAtVel());
    Logger.processInputs("Shooter", inputs);
    // This method will be called once per scheduler run
  }
  public void setVelocity(AngularVelocity velRPM){
    requireVelRPM = velRPM;
    if (velRPM.isEquivalent(RotationsPerSecond.of(0))) {
      Logger.recordOutput("Shooter/what", "stop");
      setVoltage(Volts.of(0));
    } 
    else{
      io.setVelocity(velRPM);
    }
  }

  public void TimerCheckFuel(){
    if (inputs.haveFuel) {
      Fuel_Timer.restart();
      haveFuel = true;
    }
    else if (Fuel_Timer.get() > 0.4) {
      haveFuel = false;
    }
  }

  public boolean haveFuel(){
    return haveFuel;
  }
  public Command setVelocityCommand(AngularVelocity velRPM){
    return Commands.runOnce(()-> setVelocity(velRPM));
  }
  public void setVoltage(Voltage voltage){
    io.setVoltage(voltage);
  }
  public Command setVotlageCommand(Voltage voltage){
    return Commands.runOnce(()-> setVoltage(voltage));
  }
  public boolean isAtVel(){ 
    return Math.abs(inputs.velocity.minus(requireVelRPM).in(RPM)) < 40 && requireVelRPM.in(RotationsPerSecond) != 0;
  }
  public Command appliePIDF(){
    return this.runOnce(()->{
      io.apliePIDF();
    });
  }
  public Command Stop(){
    return setVotlageCommand(Volts.of(0));
  }
  public AngularVelocity CalcRPMToShoot(){
    // double Distance = Drive.getInsatnce().getPose().getTranslation().getDistance(AllianceFlipUtil.apply(FieldConstants.Hub.innerCenterPoint.toTranslation2d()));
    // return RPM.of(RpmFromDistance.get(Distance));
    return RPM.of(Constants.launchParameters().rpm());
  }
  public Command sysidQuasistatic(Direction direction){
    return sysid.quasistatic(direction);
  }
  public Command sysidDynamic(Direction direction){
    return sysid.dynamic(direction);
  }
  public AngularVelocity getVelocity(){
    return inputs.velocity;
  }
  private final double ShooterRadius = 1.5;
  public LinearVelocity ToLinearVelocity(AngularVelocity velocity){
    return InchesPerSecond.of((ShooterRadius * velocity.in(RadiansPerSecond))/2);
  }
  public void setState(ShooterState state){
    Robotstate.shooterState = state;
  }
  public Command setStateCommand(ShooterState state){
    return Commands.runOnce(()-> setState(state));
  }
}
