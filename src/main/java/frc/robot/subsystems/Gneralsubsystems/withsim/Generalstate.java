package frc.robot.subsystems.Gneralsubsystems.withsim;

import frc.lib.util.ITarget;

public enum Generalstate implements ITarget {
    /** this is how a state control shold look like */

    /** start with saying all of your varible and then countinue */
    KeepItIn(0),
    Collect(-1),
    Eject(1);
    /** have the double that you want to store */
    private double m_angle;
    /** get a double and put it as degrees */
    Generalstate(double degree) {
        m_angle = degree;
    }

    /** an override method from ITraget that return the value of the double in the enum */
    @Override
    public double getTarget() {
        return m_angle;
    }
}
