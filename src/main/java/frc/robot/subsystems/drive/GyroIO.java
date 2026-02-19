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
  public static class GyroIOInputs implements LoggableInputs{
    public boolean connected = false;
    public Rotation2d yawPosition = Rotation2d.kZero;
    public double yawVelocityRadPerSec = 0.0;
    public double[] odometryYawTimestamps = new double[] {};
    public Rotation2d[] odometryYawPositions = new Rotation2d[] {};
    public Rotation3d yawPitchRollPosition = new Rotation3d();

    @Override
    public void toLog(LogTable table) {
      table.put("Connected", connected);
      table.put("YawPosition", yawPosition);
      table.put("YawVelocityRadPerSec", yawVelocityRadPerSec);
      table.put("OdometryYawTimestamps", odometryYawTimestamps);
      table.put("OdometryYawPositions", odometryYawPositions);
      table.put("YawPitchRollPosition", yawPitchRollPosition);
    }

    @Override
    public void fromLog(LogTable table) {
      connected = table.get("Connected", connected);
      yawPosition = table.get("YawPosition", yawPosition);
      yawVelocityRadPerSec = table.get("YawVelocityRadPerSec", yawVelocityRadPerSec);
      odometryYawTimestamps = table.get("OdometryYawTimestamps", odometryYawTimestamps);
      odometryYawPositions = table.get("OdometryYawPositions", odometryYawPositions);
      yawPitchRollPosition = table.get("YawPitchRollPosition", yawPitchRollPosition);
    }

    public GyroIOInputs clone() {
      GyroIOInputs copy = new GyroIOInputs();
      copy.connected = this.connected;
      copy.yawPosition = this.yawPosition;
      copy.yawVelocityRadPerSec = this.yawVelocityRadPerSec;
      copy.odometryYawTimestamps = this.odometryYawTimestamps.clone();
      copy.odometryYawPositions = this.odometryYawPositions.clone();
      copy.yawPitchRollPosition = this.yawPitchRollPosition;
      return copy;
    }
  }

  public default void updateInputs(GyroIOInputs inputs) {}
}
