package frc.lib.util;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathConstraints;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.DriveCommands;
import frc.robot.subsystems.drive.Drive;

import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.Radians;

import java.util.function.Supplier;

public class DriveToPointFactory {
    private final Drive Drive;

    public DriveToPointFactory(Drive Drive) {
        this.Drive = Drive;
    }

    /** PathPlanner constraints for auto-driving */
    private PathConstraints getConstraints() {
        return new PathConstraints(
                2, // 3.0
                3, // 4.0 // max vel, accel (m/s, m/s^2)
                Degrees.of(540).in(Radians), // max ang vel rad/s
                Degrees.of(720).in(Radians) // max ang accel rad/s^2
                );
    }

    private Command fineAlign(Supplier<Pose2d> Target) {
        // if (DriverStation.getAlliance().get() == Alliance.Red) {
        //   Target = Target.div(-1);
        // }
        PIDController xPID = new PIDController(5, 0, 0);
        PIDController yPID = new PIDController(5, 0, 0);
        PIDController rotPID = new PIDController(5, 0, 0);
        rotPID.enableContinuousInput(-Math.PI, Math.PI);

        return Drive.run(() -> {
                    Pose2d current = Drive.getPose();
                    double xOut = xPID.calculate(current.getX(), Target.get().getY());
                    double yOut = yPID.calculate(current.getY(), Target.get().getY());
                    double rotOut = rotPID.calculate(
                            current.getRotation().getRadians(),
                            Target.get().getRotation().getRadians());

                    DriveCommands.joystickDrive(Drive, () -> xOut, () -> yOut, () -> rotOut);
                })
                .until(() -> {
                    Pose2d error = Target.get().relativeTo(Drive.getPose());
                    return Math.abs(error.getX()) < 0.2
                            && Math.abs(error.getY()) < 0.2
                            && Math.abs(error.getRotation().getRadians()) < Math.toRadians(3);
                })
                .finallyDo(() -> Drive.stop());
    }

    /** Builds a full drive-to-pose command (pathfind + PID settle) */
    public Command driveToPose(Pose2d targetPose) {
        Command pathfind = AutoBuilder.pathfindToPose(
                targetPose, getConstraints(), 0.0 // end velocity
                );

        return pathfind.andThen(fineAlign(() -> targetPose));
    }

    public Command driveToPosesimple(Pose2d targetPose) {
        Command pathfind = AutoBuilder.pathfindToPose(targetPose, getConstraints(), 0.0);
        return pathfind;
    }
}
