// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Indexer;

import static edu.wpi.first.units.Units.Degree;
import static edu.wpi.first.units.Units.RotationsPerSecond;
import static edu.wpi.first.units.Units.Volts;

import org.littletonrobotics.junction.AutoLog;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Voltage;

/** Add your docs here. */
public interface IndexerIO {
    @AutoLog
    public class IndexerIOInputs{
        public boolean isConnected = false;
        public AngularVelocity velocity = RotationsPerSecond.of(0);
        public Voltage volts = Volts.of(0);
        public Angle pos = Degree.of(0);
    }
    public default void updateInputs(IndexerIOInputs inputs){};
    public default void setVoltage(Voltage voltage){};
    public default void setvelocity(AngularVelocity velocity){};
}
