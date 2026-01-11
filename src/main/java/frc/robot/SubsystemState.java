package frc.robot;

import frc.robot.subsystems.ArmPitch.ArmPitchState;
import frc.robot.subsystems.ElevatorWithSim.ElevatorState;
import frc.robot.subsystems.Gneralsubsystems.withsim.Generalstate;
import frc.robot.subsystems.Gripper.GripperState;

public class SubsystemState {
    public static ElevatorState elevatorState = ElevatorState.colse;
    public static ArmPitchState armState = ArmPitchState.up;
    public static GripperState gripperState = GripperState.STOP;
    public static Generalstate generalstate = Generalstate.KeepItIn;

    public static void resetState() {
        elevatorState = ElevatorState.colse;
        armState = ArmPitchState.up;
        gripperState = GripperState.STOP;
    }
}
