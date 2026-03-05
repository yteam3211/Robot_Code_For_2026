    package frc.robot;

import static edu.wpi.first.units.Units.Degree;
import static edu.wpi.first.units.Units.RPM;
import static edu.wpi.first.units.Units.Volts;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BooleanSupplier;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.DeferredCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.BasicCommands.DriveCommands;
import frc.robot.subsystems.IntakePitch.IntakePitch;
import frc.robot.subsystems.IntakeRoller.IntakeRoller;
import frc.robot.subsystems.Shooter.Shooter;
import frc.robot.subsystems.drive.Drive;

public class StateSyp {

    public enum robotState{
        OpenIntake,
        stopIntake,
        
        shootWithDisatnce,
        ShootDumb,
        pass,
        keepFuel,
        
        AlignToHub,
        AlignToPass,
        moveSwerve,

        noInputs
    }
    private static Map<BooleanSupplier,robotState> MapState = new HashMap<>();
    public static Command getCommand(BooleanSupplier isSc,robotState... state){
        return Commands.runOnce(()-> putOnMap(isSc,state)).alongWith(getCommandPrivate(state)).withName(getCommandPrivate(state).getName());
    }
    private static void putOnMap(BooleanSupplier isSc,robotState... states){
        for (robotState state : states) {
            if (!MapState.containsValue(state)) {
                MapState.put(isSc, state);
            }            
        }

    }
    private static Command getCommandPrivate(robotState... states){
        Command com = new InstantCommand();
        for (robotState state : states) {
            com = com.andThen(getCommandSwitch(state));
        }
        return com;
    }
    private static Command getCommandSwitch(robotState state){
            switch (state) {
                case OpenIntake:
                    return Commands.runOnce(()->{
                        IntakePitch.getInstance().goToAnlge(Degree.of(140));
                        IntakeRoller.getInstance().SetVoltage(Volts.of(8));
                        },
                        IntakePitch.getInstance(),IntakeRoller.getInstance()
                    );
                case stopIntake:
                    return Commands.runOnce(()-> {
                        IntakePitch.getInstance().goToAnlge(Degree.of(90));
                        IntakeRoller.getInstance().SetVoltage(Volts.of(0));
                    },
                        IntakePitch.getInstance(),IntakeRoller.getInstance()
                    );
                case shootWithDisatnce:
                    return Commands.runOnce(
                        ()->Shooter.getInstance().setVelocity(Shooter.getInstance().CalcRPMToShoot()),
                        Shooter.getInstance()
                    );
                case ShootDumb:
                    return Commands.runOnce(
                        ()->Shooter.getInstance().setVelocity(RPM.of(2000)),
                        Shooter.getInstance()
                    );
                case pass:
                    return Commands.runOnce(
                        ()->Shooter.getInstance().setVelocity(null),
                        Shooter.getInstance());
                case keepFuel:
                    return Commands.runOnce(
                        ()->Shooter.getInstance().setVelocity(RPM.of(0)),
                        Shooter.getInstance());
                case AlignToHub:
                    return
                        new DeferredCommand(DriveCommands::GoToRotationHub, DriveCommands.GoToRotationHub().getRequirements());
                // case AlignToPass:
                //     return
                //         DriveCommands.GoToRotationPass();
                case moveSwerve:
                    return 
                        DriveCommands.joystickDrive(Drive.getInsatnce(), 
                                ()-> -Controller.getSwerve().getLeftY(), 
                                ()-> -Controller.getSwerve().getLeftX(), 
                                ()-> -Controller.getSwerve().getRightX());
                case noInputs:
                    return Commands.runOnce(
                        ()->{
                            DriveCommands.joystickDrive(Drive.getInsatnce(), 
                                ()-> -Controller.getSwerve().getLeftY(), 
                                ()-> -Controller.getSwerve().getLeftX(), 
                                ()-> -Controller.getSwerve().getRightX()).execute();
                            Shooter.getInstance().setVelocity(RPM.of(0));
                            IntakePitch.getInstance().goToAnlge(Degree.of(150));
                            IntakeRoller.getInstance().SetVoltage(Volts.of(8));
                        },  
                        Drive.getInsatnce(),Shooter.getInstance(),IntakePitch.getInstance(),IntakeRoller.getInstance());
                default:
                    return 
                        DriveCommands.joystickDrive(Drive.getInsatnce(), 
                                ()-> -Controller.getSwerve().getLeftY(), 
                                ()-> -Controller.getSwerve().getLeftX(), 
                                ()-> -Controller.getSwerve().getRightX());
        }
    }
    public static void periodic() {
        if (MapState.isEmpty()) {
            CommandScheduler.getInstance().schedule(getCommand(new Trigger(()-> false),robotState.noInputs));
        }
        Logger.recordOutput("State", MapState.values().toArray(new robotState[MapState.size()]));
        MapState.forEach((isSc,state)-> {
            if (!isSc.getAsBoolean()) {
                MapState.remove(isSc,state);
            }
        });
    }
}
