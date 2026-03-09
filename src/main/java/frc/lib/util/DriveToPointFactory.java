package frc.lib.util;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathConstraints;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.BasicCommands.DriveCommands;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.drive.Drive;

import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.MetersPerSecond;
import static edu.wpi.first.units.Units.Radians;

import java.util.function.Supplier;

public class DriveToPointFactory {
    private static final Drive drive = Drive.getInsatnce();


    /** PathPlanner constraints for auto-driving */
    private static PathConstraints getConstraints() {
        return new PathConstraints(
                Drive.getInsatnce().getMaxLinearSpeedMetersPerSec(), // 3.0
                Drive.getInsatnce().getMaxLinearSpeedMetersPerSec() * 1.3, // 4.0 // max vel, accel (m/s, m/s^2)
                Drive.getInsatnce().getMaxAngularSpeedRadPerSec(), // max ang vel rad/s
                Drive.getInsatnce().getMaxAngularSpeedRadPerSec() * 1.3 // max ang accel rad/s^2
                );
    }

    private static Command fineAlign(Pose2d Target) {
        // if (DriverStation.getAlliance().get() == Alliance.Red) {
        //   Target = Target.div(-1);
        // }
        PIDController xPID = new PIDController(5, 0, 0);
        PIDController yPID = new PIDController(5, 0, 0);
        PIDController rotPID = new PIDController(5, 0, 0);
        rotPID.enableContinuousInput(-Math.PI, Math.PI);
        yPID.setTolerance(0.01);
        xPID.setTolerance(0.01);

        return drive.run(() -> {
                    Pose2d current = drive.getPose();
                    double xOut = xPID.calculate(current.getX(), Target.getY());
                    double yOut = yPID.calculate(current.getY(), Target.getY());
                    double rotOut = rotPID.calculate(
                            current.getRotation().getRadians(),
                            Target.getRotation().getRadians());

                    ChassisSpeeds chassisSpeeds  = ChassisSpeeds.fromFieldRelativeSpeeds(xOut, yOut, rotOut, drive.getRotation());
                    drive.runVelocity(chassisSpeeds);
                })
                .until(() -> {
                    Pose2d error = Target.relativeTo(drive.getPose());
                    return Math.abs(error.getX()) < 0.2
                            && Math.abs(error.getY()) < 0.2
                            && Math.abs(error.getRotation().getRadians()) < Units.degreesToRadians(3);
                })
                .finallyDo(() -> drive.stopWithX());
    }

    /** Builds a full drive-to-pose command (pathfind + PID settle) */
    public static Command driveToPose(Pose2d targetPose) {
        Command pathfind = AutoBuilder.pathfindToPose(
                targetPose, getConstraints(), 0.0 // end velocity
                );

        return pathfind.andThen(fineAlign(targetPose));
    }

    public static Command driveToPosesimple(Pose2d targetPose) {
        Command pathfind = AutoBuilder.pathfindToPose(targetPose, getConstraints(), 0.0);
        return pathfind;
    }
}
