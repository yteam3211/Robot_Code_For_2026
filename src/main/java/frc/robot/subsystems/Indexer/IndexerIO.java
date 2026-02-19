// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Indexer;

import static edu.wpi.first.units.Units.Degree;
import static edu.wpi.first.units.Units.RotationsPerSecond;
import static edu.wpi.first.units.Units.Volts;

import org.littletonrobotics.junction.LogTable;
import org.littletonrobotics.junction.inputs.LoggableInputs;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Voltage;

/** Add your docs here. */
public interface IndexerIO {
    public class IndexerIOInputs implements LoggableInputs{
        public boolean isConnected = false;
        public boolean Fuel90 = false;
        public AngularVelocity velocity = RotationsPerSecond.of(0);
        public Voltage volts = Volts.of(0);
        public Angle pos = Degree.of(0);
        @Override
        public void fromLog(LogTable table) {
            isConnected = table.get("isConnected", isConnected);
            Fuel90 = table.get("Fuel90", Fuel90);
            velocity = table.get("velocity", velocity);
            volts = table.get("volts", volts);
            pos = table.get("pos", pos);
        }
        @Override
        public void toLog(LogTable table) {
            table.put("isConnected", isConnected);
            table.put("Fuel90", Fuel90);
            table.put("velocity", velocity);
            table.put("volts", volts);
            table.put("pos", pos);
        }
    }
    public default void updateInputs(IndexerIOInputs inputs){};
    public default void setVoltage(double voltage){};
    public default void setvelocity(double Rpm){};
}
