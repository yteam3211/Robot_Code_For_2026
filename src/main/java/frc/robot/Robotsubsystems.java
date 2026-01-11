// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import frc.lib.util.DriveToPointFactory;
import frc.robot.subsystems.ArmPitch.ArmPitch;
import frc.robot.subsystems.ElevatorWithSim.Elevator;
import frc.robot.subsystems.Gneralsubsystems.withsim.GeneralWithSim;
import frc.robot.subsystems.Gripper.Gripper;
// import frc.robot.subsystems.drive.;
// import frc.robot.subsystems.drive.GyroIO;
// import frc.robot.subsystems.drive.ModuleIOSim;
// import frc.robot.subsystems.drive.TunerConstants;
import frc.robot.subsystems.drive.Drive;
import frc.robot.subsystems.drive.GyroIO;
import frc.robot.subsystems.drive.GyroIOPigeon2;
import frc.robot.subsystems.drive.GyroIOSim;
import frc.robot.subsystems.drive.ModuleIO;
import frc.robot.subsystems.drive.ModuleIOTalonFXReal;
import frc.robot.subsystems.drive.ModuleIOTalonFXSim;
import frc.robot.subsystems.drive.TunerConstants;
import org.ironmaple.simulation.SimulatedArena;
import org.ironmaple.simulation.drivesims.SwerveDriveSimulation;
import org.littletonrobotics.junction.Logger;

/** Add your docs here. */
public class Robotsubsystems {
    // GeneralWithSim general = new GeneralWithSim(new GeneralIOSim(SingleJointedArmSim.class.getName()));
    // GeneralWithoutSim general = new GeneralWithoutSim();
    private SwerveDriveSimulation driveSimulation = null;
    public Elevator elevator = null;
    public Drive drive;
    public Gripper gripper = null;
    public GeneralWithSim general = null;
    public ArmPitch arm = null;
    public DriveToPointFactory driveToPointFactory;

    public Robotsubsystems() {
        switch (Constants.currentMode) {
            case REAL:
                // Real robot, instantiate hardware IO implementations
                drive = new Drive(
                        new GyroIOPigeon2(),
                        new ModuleIOTalonFXReal(TunerConstants.FrontLeft),
                        new ModuleIOTalonFXReal(TunerConstants.FrontRight),
                        new ModuleIOTalonFXReal(TunerConstants.BackLeft),
                        new ModuleIOTalonFXReal(TunerConstants.BackRight),
                        (pose) -> {});
                // this.vision = new Vision(
                //         drive,
                //         new VisionIOLimelight(VisionConstants.camera0Name, drive::getRotation),
                //         new VisionIOLimelight(VisionConstants.camera1Name, drive::getRotation));

                break;
            case SIM:
                // Sim robot, instantiate physics sim IO implementations

                driveSimulation = new SwerveDriveSimulation(Drive.mapleSimConfig, new Pose2d(3, 3, new Rotation2d()));
                SimulatedArena.getInstance().addDriveTrainSimulation(driveSimulation);
                drive = new Drive(
                        new GyroIOSim(driveSimulation.getGyroSimulation()),
                        new ModuleIOTalonFXSim(
                                TunerConstants.FrontLeft, driveSimulation.getModules()[0]),
                        new ModuleIOTalonFXSim(
                                TunerConstants.FrontRight, driveSimulation.getModules()[1]),
                        new ModuleIOTalonFXSim(
                                TunerConstants.BackLeft, driveSimulation.getModules()[2]),
                        new ModuleIOTalonFXSim(
                                TunerConstants.BackRight, driveSimulation.getModules()[3]),
                        driveSimulation::setSimulationWorldPose);
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
                        new ModuleIO() {},
                        (pose) -> {});
                // vision = new Vision(drive, new VisionIO() {}, new VisionIO() {});

                break;
        }
        driveToPointFactory = new DriveToPointFactory(drive);
    }

    public void resetSimulationField() {
        if (Constants.currentMode != Constants.Mode.SIM) return;
        drive.setPose(new Pose2d(3, 3, new Rotation2d()));
        driveSimulation.setSimulationWorldPose(new Pose2d(3, 3, new Rotation2d()));
        SimulatedArena.getInstance().resetFieldForAuto();
    }

    public void updateSimulation() {
        if (Constants.currentMode != Constants.Mode.SIM) return;

        SimulatedArena.getInstance().simulationPeriodic();
        Logger.recordOutput("FieldSimulation/RobotPosition", driveSimulation.getSimulatedDriveTrainPose());
        Logger.recordOutput(
                "FieldSimulation/Coral", SimulatedArena.getInstance().getGamePiecesArrayByType("Coral"));
        Logger.recordOutput(
                "FieldSimulation/Algae", SimulatedArena.getInstance().getGamePiecesArrayByType("Algae"));
    }
}
