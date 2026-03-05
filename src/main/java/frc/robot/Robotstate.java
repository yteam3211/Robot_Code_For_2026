package frc.robot;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.wpilibj.Alert;
import edu.wpi.first.wpilibj.Alert.AlertType;
import frc.robot.subsystems.Indexer.IndexerState;
import frc.robot.subsystems.IntakePitch.IntakePitchState;
import frc.robot.subsystems.IntakeRoller.IntakeRollerState;
import frc.robot.subsystems.Shooter.ShooterState;
import frc.robot.subsystems.kicker.KickerState;

public class Robotstate {
    public static IntakePitchState intakePitchState = IntakePitchState.colse;
    public static ShooterState shooterState = ShooterState.stop;
    public static KickerState kickerState = KickerState.stop;
    public static IndexerState indexerState = IndexerState.Stop;
    public static IntakeRollerState rollerState = IntakeRollerState.stop;
    private static Alert ShooterAlert = new Alert("shooter active", AlertType.kError);
    public static void resetState() {
        intakePitchState = IntakePitchState.Open;
        shooterState = ShooterState.stop;
        kickerState = KickerState.stop;
        indexerState = IndexerState.Stop;
        rollerState = IntakeRollerState.move;
    }
    public static void logState(){
        if (shooterState != ShooterState.stop) {
            ShooterAlert.set(true);
        }
        Logger.recordOutput("State/intakePitchState", intakePitchState);
        Logger.recordOutput("State/shooterState", shooterState);
        Logger.recordOutput("State/kickerState", kickerState);
        Logger.recordOutput("State/IndexerState", kickerState);
        Logger.recordOutput("State/rollerState", rollerState);
    }
}
