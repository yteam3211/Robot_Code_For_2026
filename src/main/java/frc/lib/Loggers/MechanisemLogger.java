// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.lib.Loggers;

import static edu.wpi.first.units.Units.Degree;
import static edu.wpi.first.units.Units.Meters;
import static edu.wpi.first.units.Units.Volts;

import java.io.FileFilter;
import java.nio.file.ProviderNotFoundException;

import org.dyn4j.geometry.Rectangle;
import org.ironmaple.simulation.IntakeSimulation;
import org.littletonrobotics.junction.AutoLog;
import org.littletonrobotics.junction.LogTable;
import org.littletonrobotics.junction.Logger;
import org.littletonrobotics.junction.inputs.LoggableInputs;
import org.littletonrobotics.junction.mechanism.LoggedMechanism2d;
import org.littletonrobotics.junction.mechanism.LoggedMechanismLigament2d;
import org.littletonrobotics.junction.mechanism.LoggedMechanismRoot2d;

import com.google.flatbuffers.Table;
import com.reduxrobotics.sensors.canandcolor.ColorData;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularAcceleration;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;
import frc.robot.subsystems.IntakePitch.IntakePitch;
import lombok.Getter;

/** Add your docs here. */
@Getter
public class MechanisemLogger{
    // @AutoLog
    // public class Inputs {
    // protected Angle position;
    // protected AngularVelocity velocity;
    // protected Voltage voltage;
    // protected boolean isConnected;
    // public void updateRecored(TalonFXLogger talonFX){
    //     position = talonFX.getPosition().getValue();
    //     velocity = talonFX.getVelocity().getValue();
    //     voltage = talonFX.getMotorVoltage().getValue();
    //     isConnected = talonFX.isConnected();
    // }}
    private static LoggedMechanism2d mechanism2d = new LoggedMechanism2d(10, 10);
    private static LoggedMechanismRoot2d root2d = mechanism2d.getRoot("Intake", 5, 0);
    private static LoggedMechanismLigament2d ligament2d = new LoggedMechanismLigament2d("Intake", Meters.of(1), Degree.of(0),10,new Color8Bit(Color.kAqua));
    static{
        root2d.append(ligament2d);
    }
    public static void updateIntakeLigmatSim(){
        ligament2d.setAngle(IntakePitch.getInstance().getAngle());
        Logger.recordOutput("Intake2d", mechanism2d);
    }
}
