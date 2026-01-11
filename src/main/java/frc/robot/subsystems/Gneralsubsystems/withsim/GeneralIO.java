package frc.robot.subsystems.Gneralsubsystems.withsim;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.wpilibj.smartdashboard.MechanismLigament2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import java.util.function.DoubleSupplier;
import org.littletonrobotics.junction.AutoLog;

public interface GeneralIO {
    @AutoLog
    public static class GeneralInputs {
        public Angle rotaion;
        public AngularVelocity speed;
        public boolean MotorConncted = false;
        public boolean isClosed = true;
    }
    /**
     * update inputs from the sim/real motors
     *
     * @param inputs the inputs to update call at the {@link SubsystemBase#periodic()}
     */
    public default void updateInputs(GeneralInputs inputs) {}
    /**
     * set rotaion for the motor sim
     *
     * @param rotaion the setpoint that you want to go to
     */
    public default void setPosition(Angle angle) {}
    /**
     * set the speed of the motor
     *
     * @param speed the speed the motor shold speen at voltz that given
     * @deprecated use setRotaion insted for beter control {@link GeneralIO#setRotaion(DoubleSupplier)}
     */
    @Deprecated
    public default void setVoltz(double Vlotz) {}
    /**
     * set motor roataion
     *
     * @param rotaion the rotaion to set the motor to
     */
    public default void setMotorAngle(Angle rotaion) {}
    /** update the sim of the subsystem */
    public default void updateSim() {}
    ;
    /** update the moudle of the Sim */
    public default void updateVisual(MechanismLigament2d mechanismLigament2d) {}
}
