// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.Inch;

import org.ironmaple.simulation.IntakeSimulation;
import org.ironmaple.simulation.IntakeSimulation.IntakeSide;
import org.ironmaple.simulation.drivesims.SwerveDriveSimulation;
import org.ironmaple.simulation.gamepieces.GamePiece;
import org.littletonrobotics.junction.Logger;

import com.reduxrobotics.canand.CanandEventLoop;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import frc.lib.util.DriveToPointFactory;
import frc.lib.util.DONT_TUCH_THIS.Arena_2026_withBump;
import frc.robot.subsystems.IntakePitch.IntakePitch;
import frc.robot.subsystems.IntakePitch.IntakePitchReal;
import frc.robot.subsystems.IntakePitch.IntakePitchSim;
import frc.robot.subsystems.drive.Drive;
import frc.robot.subsystems.drive.GyroIO;
import frc.robot.subsystems.drive.GyroIONavX;
import frc.robot.subsystems.drive.GyroIOSim;
import frc.robot.subsystems.drive.ModuleIO;
import frc.robot.subsystems.drive.ModuleIOTalonFXReal;
import frc.robot.subsystems.drive.ModuleIOTalonFXSim;
import frc.robot.subsystems.drive.TunerConstants;

/** Add your docs here. */
public class Robotsubsystems {
    // GeneralWithSim general = new GeneralWithSim(new GeneralIOSim(SingleJointedArmSim.class.getName()));
    // GeneralWithoutSim general = new GeneralWithoutSim();
    private SwerveDriveSimulation driveSimulation = null;
    public Drive drive;
    public DriveToPointFactory driveToPointFactory;
    public IntakePitch intakePitch;
    private IntakeSimulation intakeSimulation;


    private SwerveDriveSimulation swerveDriveSimulation = null;
    public Robotsubsystems() {
        CanandEventLoop.getInstance(); 
        switch (Constants.currentMode) {
            case REAL:
                // Real robot, instantiate hardware IO implementations
                drive = new Drive(
                        new GyroIONavX(),
                        new ModuleIOTalonFXReal(TunerConstants.FrontLeft),
                        new ModuleIOTalonFXReal(TunerConstants.FrontRight),
                        new ModuleIOTalonFXReal(TunerConstants.BackLeft),
                        new ModuleIOTalonFXReal(TunerConstants.BackRight),
                        (pose)->{});
                intakePitch = new IntakePitch(new IntakePitchReal());
                // this.vision = new Vision(
                //         drive,
                //         new VisionIOLimelight(VisionConstants.camera0Name, drive::getRotation),
                //         new VisionIOLimelight(VisionConstants.camera1Name, drive::getRotation));

                break;
            case SIM:
                swerveDriveSimulation = new SwerveDriveSimulation(
                    Drive.mapleSimConfig,
                    new Pose2d(7,3, new Rotation2d()));
                intakeSimulation = IntakeSimulation.OverTheBumperIntake("Fuel", swerveDriveSimulation, 
                Inch.of(32), Inch.of(12), IntakeSide.FRONT, 40);
                intakeSimulation.register(Arena_2026_withBump.getInstance());
                // Sim robot, instantiate physics sim IO implementations

                driveSimulation = new SwerveDriveSimulation(Drive.mapleSimConfig, new Pose2d(3, 3, new Rotation2d()));
                SimulatedArena.getInstance().addDriveTrainSimulation(driveSimulation);
                drive = new Drive(
                        new GyroIOSim(swerveDriveSimulation.getGyroSimulation()),
                        new ModuleIOTalonFXSim(TunerConstants.FrontLeft, swerveDriveSimulation.getModules()[0]), 
                        new ModuleIOTalonFXSim(TunerConstants.FrontRight, swerveDriveSimulation.getModules()[1]), 
                        new ModuleIOTalonFXSim(TunerConstants.BackLeft, swerveDriveSimulation.getModules()[2]), 
                        new ModuleIOTalonFXSim(TunerConstants.BackRight, swerveDriveSimulation.getModules()[3]),
                        swerveDriveSimulation::setSimulationWorldPose
                        );
                intakePitch = new IntakePitch(new IntakePitchSim(intakeSimulation));
                Arena_2026_withBump.getInstance().addDriveTrainSimulation(swerveDriveSimulation);
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
                        (pose)-> {});
                // vision = new Vision(drive, new VisionIO() {}, new VisionIO() {});

                break;
        }
        driveToPointFactory = new DriveToPointFactory(drive);
        Arena_2026_withBump.getInstance();
    }
    public void updateSim(){
        Arena_2026_withBump.getInstance().simulationPeriodic();
        Logger.recordOutput("FieldSimulation/robotPose",swerveDriveSimulation.getSimulatedDriveTrainPose());
        Logger.recordOutput("FieldSimulation/Fuel", Arena_2026_withBump.getInstance().getGamePiecesArrayByType("Fuel"));
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
