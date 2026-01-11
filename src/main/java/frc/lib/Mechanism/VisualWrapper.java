// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.lib.Mechanism;

import org.littletonrobotics.junction.mechanism.LoggedMechanism2d;
import org.littletonrobotics.junction.mechanism.LoggedMechanismLigament2d;
import org.littletonrobotics.junction.mechanism.LoggedMechanismRoot2d;

import edu.wpi.first.cameraserver.CameraServerShared;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;
import frc.lib.Mechanism.Mechanism.MechanismType;

/** Add your docs here. */
public class VisualWrapper {
    private LoggedMechanism2d mechanism2d;
    private LoggedMechanismLigament2d ligament2d;
    private LoggedMechanismRoot2d root2d;
    private MechanismType mechanismType;
    public VisualWrapper(MechanismType mechanismType , String nameOftheMechanism){
        this.mechanismType = mechanismType;
        this.mechanism2d = new LoggedMechanism2d(10, 10, new Color8Bit(Color.kWhite));
        switch (mechanismType) {
            case Arm:
                this.root2d = mechanism2d.getRoot(nameOftheMechanism, 5, 5);
                this.ligament2d = new LoggedMechanismLigament2d(nameOftheMechanism, 4, 90,2,new Color8Bit(Color.kBlack));
                break;
            case Elevator:
                this.root2d = mechanism2d.getRoot(nameOftheMechanism, 5, 0);
                this.ligament2d = new LoggedMechanismLigament2d(nameOftheMechanism, 0, 90,2,new Color8Bit(Color.kBlack));
                break;
            case dcMotor:
                this.root2d = mechanism2d.getRoot(nameOftheMechanism, 5, 5);
                this.ligament2d = new LoggedMechanismLigament2d(nameOftheMechanism, 1, 0, 1, new Color8Bit(Color.kBlack));
                break;
            case Flywheel:
                this.root2d = mechanism2d.getRoot(nameOftheMechanism, 5, 5);
                this.ligament2d = new LoggedMechanismLigament2d(nameOftheMechanism, 1, 0, 1, new Color8Bit(Color.kBlack));
                break;
            default:
                break;
        }
        
    }
}
