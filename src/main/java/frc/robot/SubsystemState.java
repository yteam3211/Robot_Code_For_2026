package frc.robot;

import frc.robot.subsystems.Gneralsubsystems.withsim.Generalstate;
import frc.robot.subsystems.IntakePitch.IntakePitchState;

public class SubsystemState {
    public static IntakePitchState intakePitchState = IntakePitchState.colse;
    public static void resetState() {
        intakePitchState = IntakePitchState.colse;

    }
}
