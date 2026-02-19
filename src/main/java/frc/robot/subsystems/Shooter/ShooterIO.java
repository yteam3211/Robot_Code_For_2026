// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Shooter;

import static edu.wpi.first.units.Units.Minute;
import static edu.wpi.first.units.Units.Rotation;
import static edu.wpi.first.units.Units.RotationsPerSecond;
import static edu.wpi.first.units.Units.Second;
import static edu.wpi.first.units.Units.Volts;

import org.littletonrobotics.junction.LogTable;
import org.littletonrobotics.junction.inputs.LoggableInputs;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Voltage;

/** Add your docs here. */
public interface ShooterIO {
    public class ShooterIOInputs implements LoggableInputs{
        public Angle position = Rotation.of(0);
        public AngularVelocity velocity = RotationsPerSecond.of(0);
        public Voltage Voltage = Volts.of(0);
        public boolean haveFuel = false;
        @Override
        public void toLog(LogTable table) {
            // table.put("velocity", velocity.in(Rotation.per(Minute)));
            table.put("velocity", velocity);
            table.put("Voltage", Voltage);
            table.put("position", position.in(Rotation));
            table.put("haveFuel", haveFuel);

        }
        @Override
        public void fromLog(LogTable table) {
            // velocity = Rotation.per(Minute).of(table.get("velocity", velocity.in(Rotation.per(Minute))));
            Voltage = table.get("Voltage", Voltage);
            position = table.get("position", position);
            velocity = table.get("velocity", velocity);
            haveFuel = table.get("haveFuel", haveFuel);
        }
    }
    public default void updateInputs(ShooterIOInputs inputs){}
    public default void setVelocity(double velRPM){};
    public default void setVoltage(double Voltage){};
    public default void apliePIDF(){}
}
