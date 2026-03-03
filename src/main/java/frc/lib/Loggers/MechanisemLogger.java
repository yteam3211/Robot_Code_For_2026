// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.lib.Loggers;

import static edu.wpi.first.units.Units.Degree;
import static edu.wpi.first.units.Units.Meters;

import org.littletonrobotics.junction.Logger;
import org.littletonrobotics.junction.mechanism.LoggedMechanism2d;
import org.littletonrobotics.junction.mechanism.LoggedMechanismLigament2d;
import org.littletonrobotics.junction.mechanism.LoggedMechanismRoot2d;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;
import frc.robot.subsystems.IntakePitch.IntakePitch;
import frc.robot.subsystems.IntakeRoller.IntakeRoller;
import lombok.Getter;

/** Add your docs here. */
@Getter
public class MechanisemLogger{
    private LoggedMechanism2d mechanism2d;
    private LoggedMechanismRoot2d root2d;
    private LoggedMechanismLigament2d ligamentIntakeRoller;
    private LoggedMechanismLigament2d ligamentIntakePitch;
    private static MechanisemLogger instance;
    public MechanisemLogger(){
        mechanism2d = new LoggedMechanism2d(10, 10,new Color8Bit(Color.kBlack));
        root2d = mechanism2d.getRoot("Intake", 5, 0);
        ligamentIntakePitch = new LoggedMechanismLigament2d("IntakePitch", Meters.of(1), Degree.of(90),10,new Color8Bit(Color.kAqua));
        ligamentIntakeRoller = new LoggedMechanismLigament2d("IntakeRoller", 0.3, 90,10,new Color8Bit(Color.kAntiqueWhite));
        root2d.append(ligamentIntakePitch);
        ligamentIntakePitch.append(ligamentIntakeRoller);
        Logger.recordOutput("Mechanisem2d/Intake", mechanism2d);
    }
    public static MechanisemLogger getInstance(){
        if (instance == null) {
            instance = new MechanisemLogger();
        }
        return instance;
    }
    public void update(){
        ligamentIntakeRoller.setAngle(IntakeRoller.getInstance().getAngle());
        ligamentIntakePitch.setAngle(IntakePitch.getInstance().getAngle());
        Logger.recordOutput("Mechanisem2d/Intake", mechanism2d);
    }   
}
