// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.IntakePitch;

import static edu.wpi.first.units.Units.Degree;
import static edu.wpi.first.units.Units.RotationsPerSecond;
import static edu.wpi.first.units.Units.RotationsPerSecondPerSecond;
import static edu.wpi.first.units.Units.Volts;

import org.littletonrobotics.junction.AutoLog;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularAcceleration;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Voltage;

/** Add your docs here. */
public interface IntakePitchIO {
    @AutoLog
    public class IntakePitchIOInputs{
        public boolean isConncted = false;
        public Angle position = Degree.of(0);
        public AngularVelocity velocity = RotationsPerSecond.of(0);
        public AngularAcceleration acc = RotationsPerSecondPerSecond.of(0);
        public Voltage voltage = Volts.of(0);
        public boolean FullyOpen = false;
        public boolean FullyClosed = false;
    }
    public default void UpdateInputs(IntakePitchIOInputs inputs){};
    public default void goToRotation(Angle angle){};
    public default void setPos(Angle pos){};
    public default void setVoltage(Voltage volts){};
    public default void apliePIDF(){};
}
