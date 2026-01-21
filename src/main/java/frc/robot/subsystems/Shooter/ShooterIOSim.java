// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Shooter;

import org.ironmaple.simulation.IntakeSimulation;

import com.ctre.phoenix6.controls.MotionMagicVelocityTorqueCurrentFOC;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.wpilibj.simulation.FlywheelSim;

/** Add your docs here. */
public class ShooterIOSim implements ShooterIO{
    private FlywheelSim flywheelSim;
    private IntakeSimulation intakeSimulation;
    private TalonFX m_master = new TalonFX(ShooterConstants.m_masterID, ShooterConstants.m_canbus);
    private TalonFX m_Slvae1 = new TalonFX(ShooterConstants.m_slaveID1, ShooterConstants.m_canbus);
    private TalonFX m_Slvae2 = new TalonFX(ShooterConstants.m_slaveID2, ShooterConstants.m_canbus);
    private TalonFX m_Slvae3 = new TalonFX(ShooterConstants.m_slaveID3, ShooterConstants.m_canbus);
    private MotionMagicVelocityTorqueCurrentFOC motionMagicVelocity = new MotionMagicVelocityTorqueCurrentFOC(0);
    public ShooterIOSim(IntakeSimulation intakeSimulation){
        this.intakeSimulation = intakeSimulation;
        flywheelSim = new FlywheelSim(LinearSystemId.createFlywheelSystem(ShooterConstants.dcMotor, ShooterConstants.JKgMeterSqured, 
        ShooterConstants.gearRatio), ShooterConstants.dcMotor);
    }
    @Override
    public void updateInputs(ShooterIOInputs inputs) {

    }
    private void updateSim(){
        flywheelSim.setInputVoltage(m_master.getSimState().getMotorVoltage());
        flywheelSim.update(0.02);
    }
    @Override
    public void clacShootVelocity(Pose2d robotPose) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'clacShootVelocity'");
    }
    @Override
    public void setVelocity(double vel) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setVelocity'");
    }
    @Override
    public void shoot() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'shoot'");
    }
    
}
