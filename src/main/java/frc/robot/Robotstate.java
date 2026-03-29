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
    public static IntakeRollerState rollerState = IntakeRollerState.stop;
    public static IndexerState indexerState = IndexerState.stop;
    public static void resetState() {
        intakePitchState = IntakePitchState.colse;
        shooterState = ShooterState.stop;
        kickerState = KickerState.stop;
        rollerState = IntakeRollerState.stop;
        indexerState = IndexerState.stop;
    }
    public static void logState(){
        Logger.recordOutput("State/intakePitchState", intakePitchState);
        Logger.recordOutput("State/shooterState", shooterState);
        Logger.recordOutput("State/kickerState", kickerState);
        Logger.recordOutput("State/rollerState", rollerState);
        Logger.recordOutput("State/indexerState", indexerState);
    }
}
