// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.lib.util.DONT_TUCH_THIS;

import org.ironmaple.simulation.SimulatedArena;
import org.ironmaple.simulation.seasonspecific.rebuilt2026.Arena2026Rebuilt;

import edu.wpi.first.wpilibj.RobotBase;

/** Add your docs here. */
public class Arena_2026_withBump extends Arena2026Rebuilt{
    private static SimulatedArena instance = null;
    public Arena_2026_withBump(){
        super(false);
    }
    public static SimulatedArena getInstance() {
        if (RobotBase.isReal() && (!ALLOW_CREATION_ON_REAL_ROBOT))
            throw new IllegalStateException(
                    "MapleSim is running on a real robot! (If you would actually want that, set SimulatedArena.ALLOW_CREATION_ON_REAL_ROBOT to true).");

        if (instance == null) instance = new org.ironmaple.simulation.seasonspecific.rebuilt2026.Arena2026Rebuilt(false);

        return instance;
    }



}
