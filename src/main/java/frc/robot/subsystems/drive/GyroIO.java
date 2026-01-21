// Copyright 2021-2024 FRC 6328
// http://github.com/Mechanical-Advantage
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// version 3 as published by the Free Software Foundation or
// available in the root directory of this project.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.

package frc.robot.subsystems.drive;

import edu.wpi.first.math.geometry.Rotation2d;
<<<<<<< Updated upstream
import org.littletonrobotics.junction.AutoLog;

public interface GyroIO {
    @AutoLog
    public static class GyroIOInputs {
=======
import edu.wpi.first.math.geometry.Rotation3d;

import org.littletonrobotics.junction.LogTable;
import org.littletonrobotics.junction.inputs.LoggableInputs;

public interface GyroIO {
    public static class GyroIOInputs implements LoggableInputs{
>>>>>>> Stashed changes
        public boolean connected = false;
        public Rotation2d yawPosition = new Rotation2d();
        public double yawVelocityRadPerSec = 0.0;
        public double[] odometryYawTimestamps = new double[] {};
        public Rotation2d[] odometryYawPositions = new Rotation2d[] {};
<<<<<<< Updated upstream
    }

=======
        public Rotation3d yawPitchRollPosition = new Rotation3d();
        public void toLog(LogTable table) {
            table.put("connected", connected);
            table.put("yawPosition", yawPosition);
            table.put("yawVelocityRadPerSec", yawVelocityRadPerSec);

            table.put("odometryYawTimestamps", odometryYawTimestamps);
            table.put("odometryYawPositions", odometryYawPositions);

            table.put("yawPitchRollPosition", yawPitchRollPosition);
        }
        @Override
        public void fromLog(LogTable table) {
            connected = table.get("connected", connected);
            yawPosition = table.get("yawPosition", yawPosition);
            yawVelocityRadPerSec =
                table.get("yawVelocityRadPerSec", yawVelocityRadPerSec);

            odometryYawTimestamps =
                table.get("odometryYawTimestamps", odometryYawTimestamps);
            odometryYawPositions =
                table.get("odometryYawPositions", odometryYawPositions);

            yawPitchRollPosition =
                table.get("yawPitchRollPosition", yawPitchRollPosition);
        }
    }
    

>>>>>>> Stashed changes
    public default void updateInputs(GyroIOInputs inputs) {}
}
