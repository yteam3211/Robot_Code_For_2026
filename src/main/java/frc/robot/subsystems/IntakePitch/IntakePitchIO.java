// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.IntakePitch;

import org.littletonrobotics.junction.LogTable;
import org.littletonrobotics.junction.inputs.LoggableInputs;

/** Add your docs here. */
public interface IntakePitchIO {
    public class IntakePitchIOInputs implements LoggableInputs{
        public boolean isConncted = false;
        public double position = 0;
        public double velocity = 0;
        public double acc = 0;
        @Override
        public void toLog(LogTable table){
            table.put("isConncted", isConncted);
            table.put("position", position);
            table.put("velocity", velocity);
            table.put("acc", acc);
        }
        @Override
        public void fromLog(LogTable table){
            isConncted = table.get("isConncted", isConncted);
            position = table.get("position", position);
            velocity = table.get("velocity", velocity);
            acc = table.get("acc", acc);
        }

    }
    public void UpdateInputs(IntakePitchIOInputs inputs);

    public void goToDegree(double degree);

    public void setSpeed(double dutyCycle);
    
    public void setPos(double pos);
}
