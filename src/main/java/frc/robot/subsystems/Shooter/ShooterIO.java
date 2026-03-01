// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Shooter;

import static edu.wpi.first.units.Units.Rotation;
import static edu.wpi.first.units.Units.RotationsPerSecond;
import static edu.wpi.first.units.Units.Volts;

import org.littletonrobotics.junction.AutoLog;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Voltage;

/** Add your docs here. */
public interface ShooterIO {
    @AutoLog
    public class ShooterIOInputs{
        public Angle position = Rotation.of(0);
        public AngularVelocity velocity = RotationsPerSecond.of(0);
        public Voltage Voltage = Volts.of(0);
        public boolean haveFuel = false;
    }
    public default void updateInputs(ShooterIOInputs inputs){}
    public default void setVelocity(AngularVelocity velRPM){};
    public default void setVoltage(Voltage Voltage){};
    public default void apliePIDF(){}
}
