// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.lib.Loggers;

import java.lang.Thread.State;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes.Name;

import org.littletonrobotics.junction.LogTable;
import org.littletonrobotics.junction.Logger;
import org.littletonrobotics.junction.inputs.LoggableInputs;
import org.littletonrobotics.junction.networktables.LoggedNetworkInput;

import com.ctre.phoenix6.CANBus;
import com.ctre.phoenix6.hardware.CANcoder;

/** Add your docs here. */
public class CanBusLogger extends CANBus implements LoggableInputs{
        private static List<CanBusLogger> listLog = new ArrayList<>();

        public CanBusLogger(String canbus) {
            super(canbus);
            listLog.add(null);
        }
        @Override
        public void toLog(LogTable table) {
            this.getStatus().BusOffCount = table.get(this.getName() + "/BusOffCount", this.getStatus().BusOffCount);
            this.getStatus().BusUtilization = table.get(this.getName() + "/BusUtilization", this.getStatus().BusUtilization);
        }

        @Override
        public void fromLog(LogTable table) {
            table.put("/" + this.getName() + "/BusUtilization", this.getStatus().BusUtilization);
            table.put("/" + this.getName() + "/BusOffCount", this.getStatus().BusOffCount);
        }

        public static void Log(){
            for (CanBusLogger canBusLogger : listLog) {
                Logger.processInputs("CanBus", canBusLogger);
            }
        }

}
