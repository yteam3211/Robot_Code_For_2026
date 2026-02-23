// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.IntakePitch;

import static edu.wpi.first.units.Units.Degree;
import static edu.wpi.first.units.Units.Rotations;
import static edu.wpi.first.units.Units.RotationsPerSecond;
import static edu.wpi.first.units.Units.RotationsPerSecondPerSecond;
import static edu.wpi.first.units.Units.Volts;

import org.littletonrobotics.junction.LogTable;
import org.littletonrobotics.junction.inputs.LoggableInputs;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularAcceleration;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Voltage;

/** Add your docs here. */
public interface IntakePitchIO {
    public class IntakePitchIOInputs implements LoggableInputs{
        public boolean isConncted = false;
        public Angle position = Degree.of(0);
        public AngularVelocity velocity = RotationsPerSecond.of(0);
        public AngularAcceleration acc = RotationsPerSecondPerSecond.of(0);
        public Voltage voltage = Volts.of(0);
        public boolean fuel90 = false;
        @Override
        public void toLog(LogTable table){
            table.put("isConncted", isConncted);
            table.put("position", position);
            table.put("velocity", velocity);
            table.put("acc", acc);
            table.put("voltage", voltage);
            table.put("fuel90",fuel90);
        }
        @Override
        public void fromLog(LogTable table){
            isConncted = table.get("isConncted", isConncted);
            position = table.get("position", position);
            velocity = table.get("velocity", velocity);
            acc = table.get("acc", acc);
            voltage = table.get("voltage", voltage);
            fuel90 = table.get("fuel90", fuel90);
        }
    }
    public default void UpdateInputs(IntakePitchIOInputs inputs){};

    public default void goToRotation(double degree){};

    public default void setSpeed(double dutyCycle){};
    
    public default void setPos(double pos){};
    public default void setVoltage(double volts){};
    public default void apliePIDF(){};
}
