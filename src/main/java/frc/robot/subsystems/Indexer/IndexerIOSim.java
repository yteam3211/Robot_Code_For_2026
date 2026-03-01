// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Indexer;

import com.ctre.phoenix6.controls.VoltageOut;

import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.wpilibj.simulation.FlywheelSim;
import frc.lib.Loggers.TalonFXLogger;

/** Add your docs here. */
public class IndexerIOSim implements IndexerIO{
    private TalonFXLogger m_Indexer;
    private FlywheelSim flywheelSim;
    public IndexerIOSim(){
        this.flywheelSim = new FlywheelSim(LinearSystemId.createFlywheelSystem(IndexerConstants.dcMotor, 
        IndexerConstants.JKgMeterSqured, IndexerConstants.gearRatio),
        IndexerConstants.dcMotor);
        m_Indexer = new TalonFXLogger(IndexerConstants.m_indexerID, IndexerConstants.m_canbus,"Indexer");
    }
    @Override
    public void updateInputs(IndexerIOInputs inputs) {
        updateSIM();
        inputs.isConnected = m_Indexer.isConnected();
        inputs.pos = m_Indexer.getPosition().getValue();
        inputs.velocity = m_Indexer.getVelocity().getValue();
        inputs.volts = m_Indexer.getMotorVoltage().getValue();
    }

    @Override
    public void setVoltage(Voltage voltage) {
        m_Indexer.setControl(new VoltageOut(voltage).withEnableFOC(true));
    }
    private void updateSIM(){
        flywheelSim.setInputVoltage(m_Indexer.getSimState().getMotorVoltage());
        flywheelSim.update(0.02);
        m_Indexer.getSimState().setRotorVelocity(flywheelSim.getAngularVelocity());
        m_Indexer.getSimState().setRotorAcceleration(flywheelSim.getAngularAcceleration());
    }
}
