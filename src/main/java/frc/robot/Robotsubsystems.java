// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import frc.lib.util.DriveToPointFactory;
import frc.robot.subsystems.Gneralsubsystems.withsim.GeneralWithSim;
// import frc.robot.subsystems.drive.;
// import frc.robot.subsystems.drive.GyroIO;
// import frc.robot.subsystems.drive.ModuleIOSim;
// import frc.robot.subsystems.drive.TunerConstants;
import frc.robot.subsystems.drive.Drive;
import frc.robot.subsystems.drive.GyroIO;
import frc.robot.subsystems.drive.GyroIOPigeon2;
import frc.robot.subsystems.drive.ModuleIO;
import frc.robot.subsystems.drive.ModuleIOSim;
import frc.robot.subsystems.drive.ModuleIOTalonFX;
import frc.robot.subsystems.drive.TunerConstants;

import org.ironmaple.simulation.SimulatedArena;
import org.ironmaple.simulation.drivesims.SwerveDriveSimulation;
import org.littletonrobotics.junction.Logger;

/** Add your docs here. */
public class Robotsubsystems {
    public Drive drive;
    public DriveToPointFactory driveToPointFactory;
    public Robotsubsystems() {
        switch (Constants.currentMode) {
            case REAL:
                // Real robot, instantiate hardware IO implementations
                drive = new Drive(
                        new GyroIOPigeon2(),
                        new ModuleIOTalonFX(TunerConstants.FrontLeft),
                        new ModuleIOTalonFX(TunerConstants.FrontRight),
                        new ModuleIOTalonFX(TunerConstants.BackLeft),
                        new ModuleIOTalonFX(TunerConstants.BackRight));
                // this.vision = new Vision(
                //         drive,
                //         new VisionIOLimelight(VisionConstants.camera0Name, drive::getRotation),
                //         new VisionIOLimelight(VisionConstants.camera1Name, drive::getRotation));

                break;
            case SIM:
                // Sim robot, instantiate physics sim IO implementations
                drive = new Drive(
                        new GyroIO() { },
                        new ModuleIOSim(TunerConstants.FrontLeft), 
                        new ModuleIOSim(TunerConstants.FrontRight), 
                        new ModuleIOSim(TunerConstants.BackLeft), 
                        new ModuleIOSim(TunerConstants.BackRight)
                        );
                // vision = new Vision(
                //         drive,
                //         new VisionIOPhotonVisionSim(
                //                 camera0Name, robotToCamera0, driveSimulation::getSimulatedDriveTrainPose),
                //         new VisionIOPhotonVisionSim(
                //                 camera1Name, robotToCamera1, driveSimulation::getSimulatedDriveTrainPose));

                break;

            default:
                // Replayed robot, disable IO implementations
                drive = new Drive(
                        new GyroIO() {},
                        new ModuleIO() {},
                        new ModuleIO() {},
                        new ModuleIO() {},
                        new ModuleIO() {});
                // vision = new Vision(drive, new VisionIO() {}, new VisionIO() {});

                break;
        }
        driveToPointFactory = new DriveToPointFactory(drive);
    }
}
