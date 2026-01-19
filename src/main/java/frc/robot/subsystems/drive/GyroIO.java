// Copyright (c) 2021-2026 Littleton Robotics
// http://github.com/Mechanical-Advantage
//
// Use of this source code is governed by a BSD
// license that can be found in the LICENSE file
// at the root directory of this project.

package frc.robot.subsystems.drive;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import org.littletonrobotics.junction.AutoLog;
import org.littletonrobotics.junction.LogTable;
import org.littletonrobotics.junction.inputs.LoggableInputs;

public interface GyroIO {
  public static class GyroIOInputs implements LoggableInputs {
    public boolean connected = false;
    public Rotation2d yawPosition = Rotation2d.kZero;
    public double yawVelocityRadPerSec = 0.0;
    public double[] odometryYawTimestamps = new double[] {};
    public Rotation2d[] odometryYawPositions = new Rotation2d[] {};
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

  public default void updateInputs(GyroIOInputs inputs) {}
}
