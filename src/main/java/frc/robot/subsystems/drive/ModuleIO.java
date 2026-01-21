// Copyright 2021-2025 FRC 6328
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

public interface ModuleIO {
    @AutoLog
    class ModuleIOInputs {
        public boolean driveConnected = false;
        public double drivePositionRad = 0.0;
        public double driveVelocityRadPerSec = 0.0;
        public double driveAppliedVolts = 0.0;
        public double driveCurrentAmps = 0.0;
=======
import org.littletonrobotics.junction.LogTable;
import org.littletonrobotics.junction.inputs.LoggableInputs;

public interface ModuleIO {

  public static class ModuleIOInputs implements LoggableInputs{
    public boolean driveConnected = false;
    public double drivePositionRad = 0.0;
    public double driveVelocityRadPerSec = 0.0;
    public double driveAppliedVolts = 0.0;
    public double driveCurrentAmps = 0.0;
>>>>>>> Stashed changes

        public boolean turnConnected = false;
        public boolean turnEncoderConnected = false;
        public Rotation2d turnAbsolutePosition = new Rotation2d();
        public double turnVelocityRadPerSec = 0.0;
        public double turnAppliedVolts = 0.0;
        public double turnCurrentAmps = 0.0;

        public double[] odometryTimestamps = new double[] {};
        public double[] odometryDrivePositionsRad = new double[] {};
        public Rotation2d[] odometryTurnPositions = new Rotation2d[] {};
    }

    /** Updates the set of loggable inputs. */
    default void updateInputs(ModuleIOInputs inputs) {}

<<<<<<< Updated upstream
    /** Run the drive motor at the specified open loop value. */
    default void setDriveOpenLoop(double output) {}

    /** Run the turn motor at the specified open loop value. */
    default void setTurnOpenLoop(double output) {}

=======
      // Odometry
      table.put("odometryTimestamps", odometryTimestamps);
      table.put("odometryDrivePositionsRad", odometryDrivePositionsRad);
      table.put("odometryTurnPositions", odometryTurnPositions);
  }
  @Override
  public void fromLog(LogTable table) {
        // Drive
        driveConnected = table.get("driveConnected", driveConnected);
        drivePositionRad = table.get("drivePositionRad", drivePositionRad);
        driveVelocityRadPerSec =
            table.get("driveVelocityRadPerSec", driveVelocityRadPerSec);
        driveAppliedVolts = table.get("driveAppliedVolts", driveAppliedVolts);
        driveCurrentAmps = table.get("driveCurrentAmps", driveCurrentAmps);

        // Turn
        turnConnected = table.get("turnConnected", turnConnected);
        turnEncoderConnected =
            table.get("turnEncoderConnected", turnEncoderConnected);
        turnAbsolutePosition =
            table.get("turnAbsolutePosition", turnAbsolutePosition);
        turnPosition = table.get("turnPosition", turnPosition);
        turnVelocityRadPerSec =
            table.get("turnVelocityRadPerSec", turnVelocityRadPerSec);
        turnAppliedVolts = table.get("turnAppliedVolts", turnAppliedVolts);
        turnCurrentAmps = table.get("turnCurrentAmps", turnCurrentAmps);

        // Odometry
        odometryTimestamps =
            table.get("odometryTimestamps", odometryTimestamps);
        odometryDrivePositionsRad =
            table.get("odometryDrivePositionsRad", odometryDrivePositionsRad);
        odometryTurnPositions =
            table.get("odometryTurnPositions", odometryTurnPositions);
    }
}

    /** Updates the set of loggable inputs. */
    default void updateInputs(ModuleIOInputs inputs) {}

    /** Run the drive motor at the specified open loop value. */
    default void setDriveOpenLoop(double output) {}

    /** Run the turn motor at the specified open loop value. */
    default void setTurnOpenLoop(double output) {}

>>>>>>> Stashed changes
    /** Run the drive motor at the specified velocity. */
    default void setDriveVelocity(double velocityRadPerSec) {}

    /** Run the turn motor to the specified rotation. */
    default void setTurnPosition(Rotation2d rotation) {}
}
