// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.lib.Loggers;

import org.littletonrobotics.junction.LogTable;
import org.littletonrobotics.junction.Logger;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Voltage;

/** Add your docs here. */
public class MechanisemLogger {
    public record MechanisemRecored(Angle positon, AngularVelocity velocity, Voltage voltage, boolean isConnected) {
        public void taoLog(LogTable table){
            table.put("positon", positon);
            table.put("positon", positon);
            table.put("positon", positon);
            table.put("positon", positon);
            table.put("positon", positon);
        }
    }
}
