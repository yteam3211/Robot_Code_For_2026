// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.lib.Loggers;

import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.Volts;

import java.util.ArrayList;
import java.util.List;

import org.littletonrobotics.junction.LogTable;
import org.littletonrobotics.junction.Logger;
import org.littletonrobotics.junction.inputs.LoggableInputs;

import com.ctre.phoenix6.CANBus;
import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.TorqueCurrentConfigs;
import com.ctre.phoenix6.configs.VoltageConfigs;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj.Alert;
import edu.wpi.first.wpilibj.Alert.AlertType;

/** Add your docs here. */
public class TalonFXLogger extends TalonFX implements LoggableInputs{
    private String name;
    private Alert logAlert = new Alert(name + "cant log check if null", AlertType.kInfo);
    private static List<TalonFXLogger> listLog = new ArrayList<>(); 
    public TalonFXLogger(int ID , CANBus canbus,String name){
        super(ID, canbus);
        this.name = name;
        listLog.add(this);
    }
    @Override
    public void toLog(LogTable table){
        try {
            logAlert.set(false);
            table.put(name + "/Velocity", super.getVelocity().getValue());
            table.put(name + "/Acceleration", super.getAcceleration().getValue());
            table.put(name + "/Voltage", super.getMotorVoltage().getValue());
            table.put(name + "/StatorCurrent", super.getStatorCurrent().getValue());
            table.put(name + "/SupplayCurrent", super.getSupplyCurrent().getValue());
            table.put(name + "/Torque", super.getTorqueCurrent().getValue());
            table.put(name + "/ControlMode", super.getControlMode().getValue());
            table.put(name + "/Position", super.getPosition().getValue());
            table.put(name + "/isConnected", super.isConnected());
        } catch (Exception e) {
            logAlert.set(true);
        }
    }
    public static void LogTalons(){
        for (TalonFXLogger talonFXLogger : listLog) {
            Logger.processInputs("TalonFX", talonFXLogger);
        }
    }
    @Override
    public void fromLog(LogTable table) {
        
    }
}
